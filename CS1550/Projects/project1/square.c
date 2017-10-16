
#include "library.h"
#include "colors.h"

void draw_rect(int x1, int y1, int width, int height, color_t c);

int main(int argc, char** argv)
{
	int i;

	init_graphics();
	clear_screen();

	char key;
	int x = (640-20)/2;
	int y = (480-20)/2;

	do
	{
		//draw a black rectangle to erase the old one
		//draw_rect(x, y, 20, 20, 0);

		key = getkey();
		if(key == 'w') y-=10;
		else if(key == 's') y+=10;
		else if(key == 'a') x-=10;
		else if(key == 'd') x+=10;

		//draw a blue rectangle
		draw_rect(x, y, 20, 20, (color_t) orange);
		sleep_ms(10);
	} while(key != 'q');

	exit_graphics();

	return 0;

}
