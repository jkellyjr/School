/**
 * CS 1550 Project 1: Graphics Library
 * Basic driver to test my graphics library
 *
 */

#include "library.h"
#include "colors.h"				// the macros need to be casted to color_t !!


int main(int argc, char** argv){
	int choice;

	int i = 40;
	int j = 40;

	char key;
	int x = (640-20)/2;
	int y = (480-20)/2;

	/*printf("\nPress 1 to draw a rectangle.\n");
	printf("Press 2 to play \"square\" maze.\n");
	printf("Press 3 to run a modified \"square\" game.\n");
	printf("*** press 'q' to exit ***\n");
	scanf("%d", &choice);*/


	init_graphics();
	clear_screen();

	//draw_pixel(mess_x, mess_y, (color_t) orange);
	//draw_character(i,j,74, (color_t) green);
	//draw_text(i, j, "shalom...", (color_t) blue);
	//draw_rect(x, y, 200, 200, (color_t) red);
	//char c = getkey();
	//printf("char: %c\n", c);

	//draw_text(i, j, "Hello, welcome to the maze...", (color_t) red);

	// rectangle
	if(choice == 1){
		draw_rect(x, y, 50, 50, (color_t) purple);
	}

	// square
	if(choice == 2){
		do{
			//draw a black rectangle to erase the old one
			draw_rect(x, y, 20, 20, 0);

			key = getkey();
			if(key == 'w') y-=10;
			else if(key == 's') y+=10;
			else if(key == 'a') x-=10;
			else if(key == 'd') x+=10;

			//draw a blue rectangle
			draw_rect(x, y, 20, 20, (color_t) orange);
			sleep_ms(20);
			} while(key != 'q');
	}

	// keeo tail
	if(choice == 3){
		do{

			key = getkey();
			if(key == 'w') y-=10;
			else if(key == 's') y+=10;
			else if(key == 'a') x-=10;
			else if(key == 'd') x+=10;

			//draw a blue rectangle
			draw_rect(x, y, 20, 20, (color_t) green);
			sleep_ms(20);
		} while(key != 'q');
	}

	clear_screen();
	exit_graphics();

	return 0;

}
