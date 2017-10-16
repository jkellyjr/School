/*
 *  Project 4: File system
 *  By: John Kelly Jr
 *
 *
 *  FUSE: Filesystem in Userspace
 *  Copyright (C) 2001-2007  Miklos Szeredi <miklos@szeredi.hu>
 *
 *  This program can be distributed under the terms of the GNU GPL.
 *  See the file COPYING.
 *
*/



/***************************************************** LIBRARY INCLUDES *****************************************************/
#include <fuse.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>
#include <stdlib.h>
#include <time.h>

/***************************************************** WHITE LISTED MACROS *****************************************************/
#define	FUSE_USE_VERSION 26

//size of a disk block
#define	BLOCK_SIZE 512

//we'll use 8.3 filenames
#define	MAX_FILENAME 8
#define	MAX_EXTENSION 3

//How many files can there be in one directory?
#define MAX_FILES_IN_DIR (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + (MAX_EXTENSION + 1) + sizeof(size_t) + sizeof(long))


/***************************************************** WHITE LISTED STRUCTS *****************************************************/
//The attribute packed means to not align these things
struct cs1550_directory_entry{
	int nFiles;	//How many files are in this directory.
	int free_space;
	long next_block[MAX_FAT_ENTRIES - 1];

	struct cs1550_file_directory{

		char fname[MAX_FILENAME + 1];	//filename (plus space for nul)
		char fext[MAX_EXTENSION + 1];	//extension (plus space for nul)
		size_t fsize;					//file size
		long nStartBlock;				//where the first block is on disk
	} __attribute__((packed)) files[MAX_FILES_IN_DIR];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.
	char padding[BLOCK_SIZE - MAX_FILES_IN_DIR * sizeof(struct cs1550_file_directory) - sizeof(int)];
} ;
typedef struct cs1550_root_directory cs1550_root_directory;


#define MAX_DIRS_IN_ROOT (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + sizeof(long))

struct cs1550_root_directory{
	int nDirectories;	//How many subdirectories are in the root

	struct cs1550_directory{

		char dname[MAX_FILENAME + 1];	//directory name (plus space for nul)
		long nStartBlock;				//where the directory block is on disk
	} __attribute__((packed)) directories[MAX_DIRS_IN_ROOT];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.
	char padding[BLOCK_SIZE - MAX_DIRS_IN_ROOT * sizeof(struct cs1550_directory) - sizeof(int)];
} ;
typedef struct cs1550_directory_entry cs1550_directory_entry;


////How much data can one block hold?
#define	MAX_DATA_IN_BLOCK (BLOCK_SIZE)

struct cs1550_disk_block{
	unsigned long nBlock;

	//All of the space in the block can be used for actual data
	//storage.
	char data[MAX_DATA_IN_BLOCK];
};
typedef struct cs1550_disk_block cs1550_disk_block;


#define MAX_FAT_ENTRIES (BLOCK_SIZE/sizeof(short))

struct cs1550_file_alloc_table_block {
	short table[MAX_FAT_ENTRIES];
};
typedef struct cs1550_file_alloc_table_block cs1550_fat_block;



/***************************************************** FUNCTION PROTOTYPES *****************************************************/
// for good measure
static int cs1550_getattr(const char *path, struct stat *stbuf);
static int cs1550_readdir(const char *path, void *buf, fuse_fill_dir_t filler, off_t offset, struct fuse_file_info *fi);
static int cs1550_mkdir(const char *path, mode_t mode);
static int cs1550_rmdir(const char *path);
static int cs1550_mknod(const char *path, mode_t mode, dev_t dev);
static int cs1550_unlink(const char *path);
static int cs1550_read(const char *path, char *buf, size_t size, off_t offset, struct fuse_file_info *fi);
static int cs1550_write(const char *path, const char *buf, size_t size,  off_t offset, struct fuse_file_info *fi);
static int cs1550_truncate(const char *path, off_t size);
static int cs1550_open(const char *path, struct fuse_file_info *fi);
static int cs1550_flush (const char *path , struct fuse_file_info *fi);



/*******************************************************************************************************************/
/***************************************************** CUSTOM  *****************************************************/
/******************************************************************************************************************/

/***************************************************** CUSTOM STRUCTS *****************************************************/

// flags for path elements
typedef enum PathType{
	root_t,
	subdir_t,
	file_t,
	f_extension_t,
	nada
} PathType;


/***************************************************** HELPER FUNCTION PROTOTYPES *****************************************************/

void parse_path_s(const char *path, char *directory, char *filename, char *f_extension);
static PathType get_path_type(const char *path, char *directory, char *filename, char *f_extension);
static int directory_exists(const char *dir);
static int get_root(cs1550_root_directory *root_dir);
static int get_directory(cs1550_directory_entry *root_dir, char *directory);
static int get_file_size(char *path, char *filename, char *f_extension, PathType path_type);
static int create_directory(char *dir_name);
static int get_next_mem(void);
static int update_root(cs1550_root_directory *n_root);
static int update_directory(cs1550_directory_entry *new_dir, char *dir);
static int update_fat(int index, const char *event);
static int write_block(cs1550_disk_block *f_block, long seek);

/***************************************************** START DIRECTORY PORTION *****************************************************/


/*
 * Called whenever the system wants to know the file attributes, including
 * simply whether the file exists or not.
 *
 * man -s 2 stat will show the fields of a stat structure
 */
static int cs1550_getattr(const char *path, struct stat *stbuf){
	int file_size;
	int res = 0;
	time_t mod_time;

	memset(stbuf, 0, sizeof(struct stat));

	// initialize
	char directory[MAX_FILENAME +1];
	char filename[MAX_FILENAME +1];
	char f_extension[MAX_EXTENSION +1];

	// parse path
	parse_path_s(path, directory, filename, f_extension);

	// new PathType instance
	PathType path_type = get_path_type(path, directory, filename, f_extension);

	file_size = get_file_size(directory, filename, f_extension, path_type);
	mod_time = time();

	// if at root
	if (path_type == root_t) {
		stbuf->st_mode = S_IFDIR | 0755;
		stbuf->st_nlink = 2;

	} else if(path_type == subdir_t){

		// 1 if sub directories exist (-ENOENT if sub DNE)
		if(directory_exists(directory)){
			stbuf->st_mode = S_IFDIR | 0755;
			stbuf->st_nlink = 2;
		} else {
			printf("ERROR: sub directory not found (cs1550_getattr)\n");
			res = -ENOENT;
		}

	} else if(path_type != nada){
		file_size = get_file_size(directory, filename, f_extension, path_type);

		// if file exists
		if(file_size != -1){
			stbuf->st_mode = S_IFREG | 0666;
			stbuf->st_nlink = 1;
			stbuf->st_size = (size_t)file_size;
			stbuf->st_mtime = (size_t)mod_time;

		} else {
			printf("ERROR: the file does not exist (cs1550_getattr)\n");
			res = -ENOENT;
		}

	} else {
		printf("ERROR: invalid path (cs1550_getattr)\n");
		res = -ENOENT;
	}

	return res;
}



/*
 * Creates a directory. We can ignore mode since we're not dealing with
 * permissions, as long as getattr returns appropriate ones for us.
 */
static int cs1550_mkdir(const char *path, mode_t mode){
	(void) path;
	(void) mode;
	int directory_len;

	// initialize
	char directory[MAX_FILENAME +1];
	char filename[MAX_FILENAME +1];
	char f_extension[MAX_EXTENSION +1];

	// parse path
	parse_path_s(path, directory, filename, f_extension);

	// new PathType instance
	PathType path_type = get_path_type(path, directory, filename, f_extension);

	directory_len = strlen(directory);

	// new instance for checks
	cs1550_root_directory root;
	get_root(&root);


	// checks
	if(directory_len >= MAX_FILENAME){
		printf("ERROR: directory name cannot be longer than 8 characters (cs1550_mkdir)\n");
		return -ENAMETOOLONG;

	} else if(path_type != subdir_t){
		printf("ERROR: subdirectory must be under root (cs1550_mkdir)\n");
		return -EPERM;

	} else if(directory_exists(directory)){
		printf("ERROR: the specified directory already exists (cs1550_mkdir)\n");
		return -EEXIST;

	} else if(root.nDirectories >= MAX_DIRS_IN_ROOT){
		printf("ERROR: cannot exceed the limit of valid directories (cs1550_mkdir)\n");
		return -EACCES;
	}


	// create new directory
	create_directory(directory);


	return 0;
}




/*
 * Called whenever the contents of a directory are desired. Could be from an 'ls'
 * or could even be when a user hits TAB to do autocompletion
 */
static int cs1550_readdir(const char *path, void *buf, fuse_fill_dir_t filler,
			 off_t offset, struct fuse_file_info *fi){

	//Since we're building with -Wall (all warnings reported) we need
	//to "use" every parameter, so let's just cast them to void to
	//satisfy the compiler
	(void) offset;
	(void) fi;
	int i;
	int j;
	char *complete_filename;

	// initialize
	char directory[MAX_FILENAME +1];
	char filename[MAX_FILENAME +1];
	char f_extension[MAX_EXTENSION +1];

	// parse path
	parse_path_s(path, directory, filename, f_extension);

	// new PathType
	PathType path_type = get_path_type(path, directory, filename, f_extension);

	get_file_size(directory, filename, f_extension, path_type);

	// handle path
	if(path_type == root_t){

		filler(buf, ".", NULL, 0);
		filler(buf, "..", NULL, 0);

		cs1550_root_directory root;
		get_root(&root);

		for(i = 0; i < root.nDirectories; i++) {
			filler(buf, root.directories[i].dname, NULL, 0);
		}

	} else if(path_type == subdir_t){

		// directory does not exist
		if(!directory_exists(directory)){
			printf("ERROR: cannot locate the directory (cs1550_readdir)\n");
			return -ENOENT;
		}

		filler(buf, ".", NULL, 0);
		filler(buf, "..", NULL, 0);

		cs1550_directory_entry temp_dir;

		get_directory(&temp_dir, directory);

		for(j = 0; temp_dir.nFiles; j++){

			// write regular files
			if(strcmp(temp_dir.files[j].fext, "\0") == 0){
				filler(buf, temp_dir.files[j].fname, NULL, 0);

			} else {
				complete_filename = (char *) malloc(MAX_FILENAME + MAX_EXTENSION + 2);

				strcpy(complete_filename, temp_dir.files[j].fname);
				strcat(complete_filename, ".");
				strcat(complete_filename, temp_dir.files[j].fext);

				filler(buf, complete_filename, NULL, 0);
			}
		}

	} else if(path_type == nada){
		printf("ERROR: the directory is not valid (cs1550_readdir)\n");
		return -ENOENT;
	}

	// returns success (failure returns in control statement)
	return 0;
}




/*
 * Removes a directory.
 */
static int cs1550_rmdir(const char *path){
	(void) path;
    return 0;
}



/***************************************************** END DIRECTORY PORTION *****************************************************/

/***************************************************** START FILE PORTION *****************************************************/
/*
 * Does the actual creation of a file. Mode and dev can be ignored.
 *
 */
static int cs1550_mknod(const char *path, mode_t mode, dev_t dev){
	(void) mode;
	(void) dev;
	int file_size;
	int i;
	int new_block_start;
	int parent_start;
	long file_start;
	int disk_size;
	char *disk_buf;


	// initialize
	char directory[MAX_FILENAME +1];
	char filename[MAX_FILENAME +1];
	char f_extension[MAX_EXTENSION +1];

	// parse path
	parse_path_s(path, directory, filename, f_extension);

	// new PathType
	PathType path_type = get_path_type(path, directory, filename, f_extension);

	file_size = get_file_size(directory, filename, f_extension, path_type);

	// file name is too long
	if(strlen(filename) > MAX_FILENAME){
		printf("ERROR: the filename is too long (cs1550_mknod)\n");
		return -ENAMETOOLONG;
	}
	// file is trying to be created in root
	if(path_type == root_t || path_type == subdir_t){
		printf("ERROR: a file can only be created in a subdirectory (cs1550_mknod)\n");
		return -EPERM;
	}
	// file already exits
	if(file_size == -1){
		printf("ERROR: the file already exists (cs1550_mknod)\n");
		return -EEXIST;
	}


	// added file
	cs1550_root_directory root;
	get_root(&root);

	// locate sub directory -> update file -> update directory
	for(i = 0; i < root.nDirectories; i++){

		if(strcmp(root.directories[i].dname, directory) == 0){

			// get starting address
			new_block_start = get_next_mem();
			file_start = (long)(BLOCK_SIZE * new_block_start);

			// allocate file
			update_fat(new_block_start, "allocate");

			// new reference
			cs1550_directory_entry parent;
			get_directory(&parent, directory);

			// update file info
			strcpy(parent.files[parent.nFiles].fname, filename);
			strcpy(parent.files[parent.nFiles].fext, f_extension);
			parent.files[parent.nFiles].fsize = 0;
			parent.files[parent.nFiles].nStartBlock = new_block_start;
			parent.nFiles++;


			// update sub directory
			parent_start = root.directories[i].nStartBlock;

			FILE *file_ptr = fopen(".disk", "rb");
			if(file_ptr == NULL){
				printf("ERROR: failed to open .disk (cs1550_mknod)\n");
				return -ENOENT;
			}

			// get size
			fseek(file_ptr, 0, SEEK_END);
			disk_size = ftell(file_ptr);
			rewind(file_ptr);

			disk_buf = (char *)malloc(disk_size);
			fread(disk_buf, disk_size, 1, file_ptr);
			rewind(file_ptr);

			// update buffer with new parent
			memmove(parent_start + disk_buf, &parent, BLOCK_SIZE);

			// write back to disk
			fwrite(disk_buf, disk_size, 1, file_ptr);
			fclose(file_ptr);

			free(disk_buf);
		}
	}

	return 0;
}




/*
 * Write size bytes from buf into file starting from offset
 *
 * Returns size or error code
 */
static int cs1550_write(const char *path, const char *buf, size_t size,  off_t offset, struct fuse_file_info *fi){
	(void) buf;
	(void) offset;
	(void) fi;
	(void) path;
	int file_size;
	int i;
	long f_start_block;
	int block_offset;


	// initialize
	char directory[MAX_FILENAME +1];
	char filename[MAX_FILENAME +1];
	char f_extension[MAX_EXTENSION +1];

	// parse path
	parse_path_s(path, directory, filename, f_extension);

	// new PathType
	PathType path_type = get_path_type(path, directory, filename, f_extension);

	file_size = get_file_size(directory, filename, f_extension, path_type);


	// DNE
	if(!directory_exists(directory) || file_size == -1){
		printf("ERROR: unable to locate the directory (cs1550_write)\n");
		return -ENOENT;
	}

	// invalid size
	if(size <= 0 ){
		printf("ERROR: size must be greater than 0 (cs1550_write)\n");
		return -EAGAIN;
	}


	// continue if path exists
	if(path_type != root_t && path_type != subdir_t){

		cs1550_directory_entry parent;
		get_directory(&parent, directory);

		// locating file
		for(i = 0; i < parent.nFiles; i++){

			if(( path_type == file_t && strcmp(parent.files[i].fname, filename) == 0) ||
				(path_type == f_extension_t && strcmp(parent.files[i].fext, f_extension) == 0 && strcmp(parent.files[i].fname, filename) == 0)){

				// offset is beyond the file size
				if(offset > parent.files[i].fsize){
					printf("ERROR: the offset is beyond the file size (cs1550_write)\n");
					return -EFBIG;
				}

				// start address
				f_start_block = parent.files[i].nStartBlock;
				block_offset = offset / BLOCK_SIZE;

				/* !! became counter-intuitive to keep symbol declaration at beginning !!*/
				long b_seek = f_start_block;
				long starting_block = 0;
				int j;

				// create new file pointer, exit on failure
				FILE *file_ptr = fopen(".disk", "rb");
				if(file_ptr == NULL){
					printf("ERROR: failed to open .disk (cs1550_write)\n");
					return -ENOENT;
				}

				// locate offset block
				for(j = 0; j < block_offset; j++){
					starting_block = b_seek;

					fseek(file_ptr, b_seek, SEEK_SET);
					cs1550_disk_block file_block;
					fread(&file_block, BLOCK_SIZE, 1, file_ptr);

					b_seek = file_block.nBlock;
				}
				rewind(file_ptr);

				// locate where do begin modifications
				int offset_from_block = (int)offset - (BLOCK_SIZE * block_offset);


				// setup for overwriting
				int char_buf;
				int counter = offset_from_block;
				b_seek = starting_block;

				fseek(file_ptr, b_seek, SEEK_SET);
				cs1550_disk_block cur_block;
				fread(&cur_block, BLOCK_SIZE, 1, file_ptr);

				// write
				for(char_buf = 0; char_buf < strlen(buf); char_buf++){

					if(counter > MAX_DATA_IN_BLOCK){
						cur_block.data[counter] = (char)buf[char_buf];
						counter++;

					} else {
						// reset
						counter = 0;

						// handle end of blocks
						if(cur_block.nBlock == 0){
							// update pointer
							long temp_seek = b_seek;
							int next_index = get_next_mem();
							temp_seek = BLOCK_SIZE * next_index;

							cur_block.nBlock = b_seek;
							write_block(&cur_block, temp_seek);

							fseek(file_ptr, b_seek, SEEK_SET);
							fread(&cur_block, BLOCK_SIZE, 1, file_ptr);

							// update
							update_fat(next_index, "allocate");

						} else {
							write_block(&cur_block, b_seek);

							b_seek = cur_block.nBlock;

							fseek(file_ptr, b_seek, SEEK_SET);
							fread(&cur_block, BLOCK_SIZE, 1, file_ptr);
						}
					}

					// force disk write
					if(char_buf == strlen(buf) -1){
						write_block(&cur_block, b_seek);
						counter = 0;
					}
				}

				fclose(file_ptr);

				// update size of file
				int former = parent.files[i].fsize;
				parent.files[i].fsize = (former - (former - offset)) + (size - (former - offset));
				update_directory(&parent, directory);
			}
		}
	}

	// size should not have changed
	return size;
}



/*
 * Read size bytes from file into buf starting from offset
 *
 */
static int cs1550_read(const char *path, char *buf, size_t size, off_t offset, struct fuse_file_info *fi){
	(void) buf;
	(void) offset;
	(void) fi;
	(void) path;
	int file_size;
	int i;
	long f_start_block;
	int block_offset;

	// initialize
	char directory[MAX_FILENAME +1];
	char filename[MAX_FILENAME +1];
	char f_extension[MAX_EXTENSION +1];

	// parse path
	parse_path_s(path, directory, filename, f_extension);

	// new PathType
	PathType path_type = get_path_type(path, directory, filename, f_extension);

	file_size = get_file_size(directory, filename, f_extension, path_type);

	// if path is a directory
	if(path_type == root_t || path_type == subdir_t){
		printf("ERROR: the path specified is a directory (cs1550_read)\n");
		return -EISDIR;
	}

	// DNE (!! removed size < 0 !!)
	if(!directory_exists(directory) || file_size == -1){
		printf("ERROR: unable to locate the directory (cs1550_read)\n");
		return -ENOENT;
	}

	cs1550_directory_entry parent;
	get_directory(&parent, directory);

	// locate
	for(i = 0; i < parent.nFiles; i++){

		if(( path_type == file_t && strcmp(parent.files[i].fname, filename) == 0) ||
				(path_type == f_extension_t && strcmp(parent.files[i].fext, f_extension) == 0 && strcmp(parent.files[i].fname, filename) == 0)){

			// if the offset is larger than file itself
			if(parent.files[i].fsize < offset){
				printf("ERROR: the offset is beyond the file size (cs1550_read)\n");
				return -EFBIG;
			}

			f_start_block = parent.files[i].nStartBlock;
			block_offset = offset / BLOCK_SIZE;

			/* !! became counter-intuitive to keep symbol declaration at beginning !!*/
			long b_seek = f_start_block;
			long starting_block = 0;
			int j;

			// create new file pointer, exit on failure
			FILE *file_ptr = fopen(".disk", "rb");
			if(file_ptr == NULL){
				printf("ERROR: failed to open .disk (cs1550_write)\n");
				return -ENOENT;
			}

			// locate block containing offset and set to b_seek
			for(j = 0; j < block_offset; j++){
				starting_block = b_seek;

				fseek(file_ptr, b_seek, SEEK_SET);
				cs1550_disk_block file_block;
				fread(&file_block, BLOCK_SIZE, 1, file_ptr);

				b_seek = file_block.nBlock;
			}
			rewind(file_ptr);

			// locate where to begin modifications
			int offset_from_block = (int)offset - (BLOCK_SIZE * block_offset);

			// setup for overwriting
			int char_buf;
			int counter = offset_from_block;
			b_seek = starting_block;

			// begin reading
			while(b_seek != 0){

				fseek(file_ptr, b_seek, SEEK_SET);
				cs1550_disk_block cur_block;
				fread(&cur_block, BLOCK_SIZE, 1, file_ptr);

				// still room (else = end)
				if(counter < MAX_DATA_IN_BLOCK){
					buf[char_buf] = (char)cur_block.data[counter];

					counter++;
					char_buf++;

				} else {
					b_seek = cur_block.nBlock;
					counter = 0;
				}
			}
			fclose(file_ptr);
		}
	}

	// size should not have changed
	return size;
}



/*
 * Deletes a file
 */
static int cs1550_unlink(const char *path){
    (void) path;

    return 0;
}






/***************************************************** END FILE PORTION *****************************************************/





/******************************************************************************
 *
 *  DO NOT MODIFY ANYTHING BELOW THIS LINE
 *
 *****************************************************************************/

/*
 * truncate is called when a new file is created (with a 0 size) or when an
 * existing file is made shorter. We're not handling deleting files or
 * truncating existing ones, so all we need to do here is to initialize
 * the appropriate directory entry.
 *
 */
static int cs1550_truncate(const char *path, off_t size)
{
	(void) path;
	(void) size;

    return 0;
}

/*
 * Called when we open a file
 *
 */
static int cs1550_open(const char *path, struct fuse_file_info *fi)
{
	(void) path;
	(void) fi;
    /*
        //if we can't find the desired file, return an error
        return -ENOENT;
    */

    //It's not really necessary for this project to anything in open

    /* We're not going to worry about permissions for this project, but
	   if we were and we don't have them to the file we should return an error

        return -EACCES;
    */

    return 0; //success!
}

/*
 * Called when close is called on a file descriptor, but because it might
 * have been dup'ed, this isn't a guarantee we won't ever need the file
 * again. For us, return success simply to avoid the unimplemented error
 * in the debug log.
 */
static int cs1550_flush (const char *path , struct fuse_file_info *fi)
{
	(void) path;
	(void) fi;

	return 0; //success!
}





//register our new functions as the implementations of the syscalls
static struct fuse_operations hello_oper = {
    .getattr	= cs1550_getattr,
    .readdir	= cs1550_readdir,
    .mkdir	= cs1550_mkdir,
	.rmdir = cs1550_rmdir,
    .read	= cs1550_read,
    .write	= cs1550_write,
	.mknod	= cs1550_mknod,
	.unlink = cs1550_unlink,
	.truncate = cs1550_truncate,
	.flush = cs1550_flush,
	.open	= cs1550_open,
};



 //Don't change this.
 int main(int argc, char *argv[]){
 	return fuse_main(argc, argv, &hello_oper, NULL);
 }










/****************************************************************************************************
 *
 * 						HELPER FUNCTIONS
 *
 ***************************************************************************************************/


// parses input path (string) and assigns the properties respectively
void parse_path_s(const char *path, char *directory, char *filename, char *f_extension){

	// initialize
	directory[0] =  '\0';
	filename[0] = '\0';
	f_extension[0] = '\0';

	// parse path
	sscanf(path, "/%[^/]/%[^.].%s", directory, filename, f_extension);

	// add null terminator as delimiter
	directory[MAX_FILENAME] = '\0';
	filename[MAX_FILENAME] = '\0';
	f_extension[MAX_EXTENSION] = '\0';
}


// sets flags for path elements
static PathType get_path_type(const char *path, char *directory, char *filename, char *f_extension){

	PathType pt = nada;

	// set flags
	if(strcmp(path, "/") == 0){ pt = root_t; }
	if(strcmp(directory, "\0") != 0){ pt = subdir_t; }
	if(strcmp(filename, "\0") != 0){ pt = file_t; }
	if(strcmp(f_extension, "\0") != 0){ pt = f_extension_t; }


	return pt;
}


// returns 1 if directory exists 0 otherwise
static int directory_exists(const char *dir){
	int i;
	int result = 0;

	cs1550_root_directory root_dir;
	get_root(&root_dir);

	// search directories
	for(i = 0; i < root_dir.nDirectories; i++){
		if (strcmp(dir, root_dir.directories[i].dname) == 0){
			result = 1;
		}
	}

	return result;
}


// checks existence, 0 on success / error #
static int get_root(cs1550_root_directory *root_dir) {
	FILE *file_ptr = fopen(".disk", "rb");

	// exit if DNE
	if(file_ptr == NULL){
		printf("ERROR: failed to open .disk (get_root)\n");
		return -ENOENT;
	}

	fread(root_dir, sizeof(cs1550_root_directory), 1, file_ptr);
	fclose(file_ptr);

	return 0;
}


// get directory, 0 on success / error  #
static int get_directory(cs1550_directory_entry *root_dir, char *directory){
	int i;
	long dir_start_block = 0;

	cs1550_root_directory root;
	get_root(&root);

	for(i = 0; i < root.nDirectories; i++){
		if(strcmp(directory, root.directories[i].dname) == 0){
			dir_start_block = root.directories[i].nStartBlock;
		}
	}

	FILE *file_ptr = fopen(".disk", "rb");

	// exit if DNE
	if(file_ptr == NULL){
		printf("ERROR: failed to open .disk (get_directory)\n");
		return -ENOENT;
	}

	fseek(file_ptr, dir_start_block, SEEK_SET);
	fread(root_dir, BLOCK_SIZE, 1, file_ptr);
	fclose(file_ptr);

	return 0;
}


// returns file size / -1 or error #
static int get_file_size(char *path, char *filename, char *f_extension, PathType path_type){
	int i;
	int result = -1;

	// if directory does not exist
	if(!directory_exists(path)){
		return result;
	}

	cs1550_directory_entry parent_dir;
	get_directory(&parent_dir, path);

	// iterate through files
	for(i = 0; i < parent_dir.nFiles; i++){

		if( strcmp(filename, parent_dir.files[i].fname) == 0 && path_type == file_t){
			result = (int) parent_dir.files[i].fsize;

		} else if(strcmp(filename, parent_dir.files[i].fname) == 0 && strcmp(f_extension, parent_dir.files[i].fext) == 0 && path_type == f_extension_t ){
			result = (int) parent_dir.files[i].fsize;

		} else {
			printf("ERROR: unable to locate file (get_file_size)\n");
		}
	}

	return result;
}


// creates a new directory, 0 success / error #
static int create_directory(char *dir_name){
	// get next free memory block
	int block_num = get_next_mem();

	if(block_num == -1){
		printf("ERROR: unable to find an available block of memory (create_directory)\n");
		return -ENOMEM;
	}

	cs1550_root_directory root;
	get_root(&root);

	// assign attributes
	strcpy(root.directories[root.nDirectories].dname, dir_name);
	root.directories[root.nDirectories].nStartBlock = (long)(block_num * BLOCK_SIZE);
	root.nDirectories++;

	update_root(&root);
	update_fat(block_num, "allocate");

	return 0;
}


// returns next free memory block / error #
static int get_next_mem(void){
	int x, i;
	int result = -1;
	int offset;

	FILE *file_ptr = fopen(".disk", "rb");

	// exit if DNE
	if(file_ptr == NULL){
		printf("ERROR: failed to open .disk (get_next_mem)\n");
		return -ENOENT;
	}

	cs1550_directory_entry dir;
	//get_directory(&dir);

	for(x = 0; x < dir.nFiles; x++){
		if(dir.free_space){
			offset = BLOCK_SIZE - dir.files[x].fsize;
		}
	}


	fseek(file_ptr, offset, SEEK_END);


	for(i = 0; i < dir.nFiles ; i++){

		if(i != 0 && dir.free_space){
			result = i;
			break;
		}

		offset++;
		fseek(file_ptr, offset, SEEK_END);
	}

	fclose(file_ptr);

	return result;
}


// update root, 0 success / error #
static int update_root(cs1550_root_directory *n_root){
	int disk_size;
	char *disk_buf;

	FILE *file_ptr = fopen(".disk", "rb");
	if(file_ptr == NULL){
		printf("ERROR: failed to open .disk (update_root)\n");
		return -ENOENT;
	}

	fseek(file_ptr, 0, SEEK_END);
	disk_size = ftell(file_ptr);
	rewind(file_ptr);

	disk_buf = (char *)malloc(disk_size);

	fread(disk_buf, disk_size, 1, file_ptr);
	rewind(file_ptr);

	memmove(disk_buf, n_root, BLOCK_SIZE);

	fwrite(disk_buf, disk_size, 1, file_ptr);
	fclose(file_ptr);

	free(disk_buf);

	return 0;
}


// update the directory, 0 success / error #
static int update_directory(cs1550_directory_entry *new_dir, char *dir){
	int i;
	long dir_startB;
	int disk_size;
	char *disk_buf;


	cs1550_root_directory root;
	get_root(&root);

	// locate and update directory
	for(i = 0; i < root.nDirectories; i++){

		// locate and replace
		if(strcmp(root.directories[i].dname, dir) == 0){

			FILE *file_ptr = fopen(".disk", "rb");
			if(file_ptr == NULL){
				printf("ERROR: unable to open .disk (udpate_directory)\n");
				return -ENOENT;
			}

			// get starting block
			dir_startB = root.directories[i].nStartBlock;

			fseek(file_ptr, 0, SEEK_END);
			disk_size = ftell(file_ptr);
			rewind(file_ptr);

			disk_buf = (char *)malloc(disk_size);

			fread(disk_buf, disk_size, 1, file_ptr);
			rewind(file_ptr);

			memmove( (int )dir_startB + disk_buf, new_dir, BLOCK_SIZE);

			fwrite(disk_buf, disk_size, 1, file_ptr);
			free(disk_buf);

			fclose(file_ptr);

		} else {
			// cannot locate file in root
			printf("ERROR: unable to locate the directory in the list (update_directory)\n");
			return -ENOENT;
		}
	}

	return 0;
}


// update fat, 0 success / error #
static int update_fat(int index, const char *event){
	int disk_size;
	int offset;
	char *buffer;

	FILE *file_ptr = fopen(".disk", "rb");
	if(file_ptr == NULL){
		printf("ERROR: failed to open .disk (update_fat)\n");
		return -ENOENT;
	}

	cs1550_directory_entry dir;
	//get_directory(&dir);

	fseek(file_ptr, 0, SEEK_END);

	disk_size = ftell(file_ptr);
	offset = disk_size - BLOCK_SIZE;

	rewind(file_ptr);

	buffer = (char *) malloc(disk_size);

	rewind(file_ptr);
	fread(buffer, disk_size, 1, file_ptr);
	rewind(file_ptr);


	// could need change (bit flipping)
	if( strcmp(event, "allocate") == 0){
		buffer[offset + index] = 1;

	} else if(strcmp(event, "free") == 0){
		buffer[offset + index] = 1;
	}


	fwrite(buffer, disk_size, 1, file_ptr);
	fclose(file_ptr);
	free(buffer);

	return 0;
}


// write disk block to disk via address, 0 success / error #
static int write_block(cs1550_disk_block *f_block, long seek){
	int disk_size;
	char *disk_buf;

	FILE *file_ptr = fopen(".disk", "rb");
	if(file_ptr == NULL){
		printf("ERROR: unable to write to disk (write_block)\n");
		return -ENOENT;
	}

	fseek(file_ptr, 0, SEEK_END);
	disk_size = ftell(file_ptr);
	rewind(file_ptr);

	disk_buf = (char *)malloc(disk_size);

	fread(disk_buf, disk_size, 1, file_ptr);
	rewind(file_ptr);

	memmove(seek + disk_buf, f_block, BLOCK_SIZE);

	fwrite(disk_buf, disk_size, 1, file_ptr);
	fclose(file_ptr);

	free(disk_buf);
	return 0;
}
