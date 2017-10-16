/*****************************************************************
  Project 5: e_digits
  By: John Kelly Jr (jjk79@pitt.edu)
  Basic program to determine specific digits of e

  e = 2.718281828459045235360287471352662497757247093699959574966
*****************************************************************/
#include <stdio.h>
#include <stdlib.h>

/*****************************************************************
                         PROTOTYPES AND	GLOBAL
*****************************************************************/
void display_digits(int min, int max);
char DIGITS[1000];


/*****************************************************************
                         MAIN LOGIC
*****************************************************************/
int main(int argc, char *argv[]){
  int max, min;
  FILE *file;
  int elements;

  // asigned index values for more control
  min = atoi(argv[1]);
  max = atoi(argv[2]);

  // checks validity
  if(max < min){
    printf("The number of digits was negative.\n");
    return 0;
  }

  //open file for reading
  file = fopen("/dev/e", "r");

  // existence check
  if(file == NULL){
    printf("ERROR: failed to open /dev/e.\n");
    return 0;
  }

  // read in the digits
  for( elements = 0; elements <= max; elements++){
    fread(&DIGITS[elements], sizeof(char), 1, file);
   // printf("elements: %d digit: %c\n", elements, DIGITS[elements]);
  }

  display_digits(min, max);

  fclose(file);
  return 0;
}

/*****************************************************************
                    DISPLAYS THE CORRECT DIGITS
*****************************************************************/
void display_digits(int min, int max){
  printf("The digits from %d to %d are: ", min, max);
  for( ; min <= max+1; min++ ){
    putc(isprint(DIGITS[min]) ? DIGITS[min] : '\n' , stdout);
  }
}

