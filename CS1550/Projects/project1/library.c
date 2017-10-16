/**
**Project 1: Graphics Library
**By: John Kelly Jr
**
**Creates a basic graphics library that can set a pixel to a particular color, draw some basic shapes
**and read keypresses.
**/


/* local custom library made for a simple graphics library */
#include "library.h"

/* apple header for array encoded font */
#include "iso_font.h"

/* globals */
int fd;
unsigned long x_virt_dimension;
unsigned long y_virt_dimension;
unsigned long bit_len;
color_t *address;



/* initialize graphics library */
void init_graphics(){

  struct fb_var_screeninfo var_screeninfo;  // structs holding display info
  struct fb_fix_screeninfo fix_screeninfo;

  struct termios cmd_settings;						// struct for command line settings

  // get file id
  fd = open("/dev/fb0", O_RDWR);
  if(fd == -1){
    perror("Error opening /dev/fb0");
    exit(1);
  }

  // get virtual memory frame buffer info
  if( ioctl(fd, FBIOGET_VSCREENINFO, &var_screeninfo) == -1){
    perror("Error accessing FBIOGET_VSCREENINFO");
    exit(1);
  }

  if( ioctl(fd, FBIOGET_FSCREENINFO, &fix_screeninfo) == -1){
    perror("Error accessing FBIOGET_FSCREENINFO");
    exit(1);
  }

  // get frame buffer dimensions from structs
  x_virt_dimension = var_screeninfo.xres_virtual;
  y_virt_dimension = var_screeninfo.yres_virtual;
  bit_len = fix_screeninfo.line_length;

  // map frame buffer into process address space
  address = (color_t *) mmap(0, (bit_len * x_virt_dimension), PROT_READ | PROT_WRITE,
                                      MAP_SHARED, fd, 0);
  if(address == (void *) -1){
    perror("Error mapping memory");
    exit(1);
  }

  // get terminal settings
  if( ioctl(STDIN_FILENO, TCGETS, &cmd_settings) == -1){
    perror("Error accessing TCGETS");
  	exit(1);
  }

  // change local terminal settings to original state
  cmd_settings.c_lflag &= ~ICANON;
  cmd_settings.c_lflag &= ~ECHO;

  // implement altered settings
  if( ioctl(STDIN_FILENO, TCSETS, &cmd_settings) == -1){
  	perror("Error enabling echo and/or buffering keystrokes");
  }
}



/* reads user input using select (synchronous I/O multiplexing) */
char getkey(){

  char key;    // key received from the user

  fd_set fd_ids;        // bit array for fd set
  struct timeval timeout;   // select timeout: the amount of time that select should block

  // clear set then adds fd
  FD_ZERO(&fd_ids);
  FD_SET(STDIN_FILENO, &fd_ids);

  // set timeout line_length
  timeout.tv_sec = 5;
  timeout.tv_usec = 0;

  // block until the input is available then read
  if( select(STDIN_FILENO + 1, &fd_ids, NULL, NULL, &timeout) == -1){
    perror("Error with select function");
    exit(1);
  }

  read(STDIN_FILENO, &key, sizeof(char));

  return key;
}



/* set coordinates and draw the pixel */
void draw_pixel(int x, int y, color_t c){

  unsigned short *draw_coordinate_ptr;    // the pixel to draw

  // check parameter bounds
  if( x < 0 || x > x_virt_dimension || y < 0 || y > y_virt_dimension){
    perror("Error not an addressable index");
    exit(1);
  }

  // calculate the coordinate (row-major)
  draw_coordinate_ptr = (address + x + (y * x_virt_dimension));

  // set address to color specified
  *draw_coordinate_ptr = RMASK(c) | GMASK(c) | BMASK(c);
}



/* use draw pixel to draw a rectangle */
void draw_rect(int x1, int y1, int width, int height, color_t c){

  int x, y;     // counters

  // draw continuous rectangle horizontal first
  for(y = y1; y < (y1 + height); y++){
	  for( x = x1; x < (x1 + width); x++){

		draw_pixel(x, y, c);
	  }
  }
}



/* handles the drawing of each character */
void draw_character(int x, int y, int ascii, color_t c){

  int i, j;     // counters
  int draw_bit;   // bit representing a pixel

  // check ascii value
  if( ascii < ISO_CHAR_MIN || ascii > ISO_CHAR_MAX){
    perror("Error invalid ascii");
    exit(1);
  }

  // search for draw bit
  for(i = 0; i < ISO_CHAR_HEIGHT; i++){
  	for(j = 0; j < 8; j++){

  		draw_bit = (iso_font[(ascii * ISO_CHAR_HEIGHT) + i] & 1 << j) >> j;

  		if(draw_bit == 1){
  			draw_pixel(x + j, y + i, c);
  		}
  	}
  }
}



/* controls the drawing of the user specified string */
void draw_text(int x, int y, const char *text, color_t c){

  const char *cur_char = text;     // temporary string to keep text unchanged

  // iterate through sting
  while(*cur_char != '\0'){
  	draw_character(x, y, *cur_char, c);

  	cur_char++;
  	x += 8;
  }
}



/* make the program go to sleep during the drawing of the graphics */
void sleep_ms(long ms){

  // initialize new time struct to set sleep in nanoseconds
  struct timespec sleep_time;

  // set the specified sleep time between graphics
  sleep_time.tv_sec = 0;
  sleep_time.tv_nsec = ms * 1000000;

  nanosleep(&sleep_time, NULL);
}



/* clears the screen using an ANSI code */
void clear_screen(){
  write(STDIN_FILENO, "\033[2J", 8);
}



/* clean up and undo alterations */
void exit_graphics(){

	struct termios cmd_settings;						// struct for command line settings

	// change terminal settings
	if( ioctl(STDIN_FILENO, TCGETS, &cmd_settings) == -1){
		perror("Error accessing TCGETS");
		exit(1);
	}

	// change local terminal settings to original state
	cmd_settings.c_lflag |= ~ICANON;
	cmd_settings.c_lflag |= ~ECHO;

	 // implement altered settings
	 if( ioctl(STDIN_FILENO, TCSETS, &cmd_settings) == -1){
		perror("Error enabling echo and/or buffering keystrokes");
	 }

  // close file descriptor
  if( close(fd) == -1 ){
    perror("Error closing /dev/fb0");
  }

  // unmap memory
  if( munmap(address, (y_virt_dimension * bit_len)) == -1){
    perror("Error unmapping memory");
    exit(1);
  }
}
