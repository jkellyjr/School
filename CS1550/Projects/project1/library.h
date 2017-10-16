/**
**Project 1: Graphics Library Header File
**By: John Kelly Jr
**File containing Linux system call libraries, typdefs and macros for a simple graphics library.
**/

/* linux libraries */
#include <sys/mman.h>
#include <stdlib.h>
#include <sys/ioctl.h>
#include <linux/fb.h>
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>
#include <stdio.h>


/* color_t typedef */
typedef unsigned short color_t;


/* RGB: bit masking macros */
#define RMASK(c) (c & 0xF800)   // red mask
#define GMASK(c) (c & 0x07E0)   // green mask
#define BMASK(c) (c & 0x001F)   // blue mask
// 16 bit color depth
// |   R   |  G  |  B  |
// |16 - 12|11 - 5|4 - 0|


/* function prototypes */
void init_graphics();
void exit_graphics();
void clear_screen();
char getkey();
void sleep_ms(long ms);
void draw_pixel(int x, int y, color_t c);
void draw_rect(int x, int y, int width, int height, color_t c);
void draw_text(int x, int y, const char *text, color_t c);
void draw_character(int x, int y, int acsii, color_t c);





