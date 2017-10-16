/*---------------------------------------------------------------------------------------------------
  Project 2 Part 1
  By: John Kelly Jr
  Read in a file, count the number of strings (ascii characters
  >= 4) and print out the string.
------------------------------------------------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>

int main(int argc, char *argv[]){
  unsigned char input;
  FILE *file_ptr;
  int char_num;
  int index;
  struct stat file_len;

  // if file not included
  if(argc == 1){
    printf("Please enter in the filename.\n");
    return 1;
  }

  // gets file info (size)
  stat(argv[1], &file_len);
  char str[file_len.st_size];

  // opens file
  file_ptr = fopen(argv[1], "r");

  // file name check
  printf("---- file name: %s\n", argv[1]);

  // empty file check
  if(file_ptr == NULL){
    printf("I\'m sorry the file was not accesible.\n");
    return 1;
  }

  // LOGIC
  while(input){
    // read 1 byte at a time
    //fread(&input, sizeof(input), 1, file_ptr);
    input = getc(file_ptr);

    if(input < 32 || input > 126){

      // print found string bc input != ASCII
      if(strlen(str) >= 4){
        printf("The string is: %s\n", str);
      }
      
      // if it is not an ASCII char skip adding
      continue;
    }
    // add to str if character
    else{
      index = 0;
      str[index] = input;
      index ++;
    }
  }
  return 0;
}
