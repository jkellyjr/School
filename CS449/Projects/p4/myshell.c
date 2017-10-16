/**********************************************
  Project 4: Unix Shell
  By: John Kelly Jr
  This is a basic unix shell, made in order to familiarize myself with system calls
  on a unix machine.
*********************************************/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>

// macros to help readability/changes
#define MAX_COMMAND_LEN  100
#define MAX_NUM_COMMANDS  100


/********************************************
            GLOBALS
********************************************/
char DELIMITERS[11] = " \t\n()<>|&;";

// globals for modularization (I/O redirects)
int OUTPUT_REDIRECT;
int APPEND;
int INPUT_REDIRECT;

// int to control redirects
int REDIRECTS;

// number of commands
int COMMAND_NUM = 0;


/********************************************
        GETS THE NUMBER OF ARGUMENTS
********************************************/
void getCommandNum(char *input){
  char cmd[MAX_NUM_COMMANDS];
  char *temp;

  // keep original
  strcpy(cmd, input);

  // tokenize first argument
  temp = strtok(cmd, DELIMITERS);

  // num rest of arguments
  while(temp != NULL){
    temp = strtok(NULL, DELIMITERS);
    COMMAND_NUM++;
  }
}


/********************************************
          HANDLES EXIT AND CD
********************************************/
void handleInternal(char **table){
  // exit
  if( strcmp(table[0], "exit") == 0){
    printf("It was nice working with you. Thank you for using my shell.\n");
    exit(0);
  }

  // change directory
  else if( strcmp(table[0], "cd") == 0){
    if(chdir(table[1]) != 0 ){
      if(table[1] == NULL){
        printf("cd: Please specifiy a directory\n");
      } else {
        printf("cd: %s: No such file or directory\n", table[1]);
      }
    }
  }
}


/********************************************
        HANDLES I/O REDIRECTION
********************************************/
int handleRedirect(char *input){

  // appends
  if(strstr(input, ">>") != NULL){
    REDIRECTS = 1;
    return 1;
  }

  // output redirect
  if(strstr(input, ">") != NULL){
    REDIRECTS = 2;
    return 1;
  }

  // input redirect
  if(strstr(input, "<") != NULL){
    REDIRECTS = 3;
    return 1;
  }

  return 0;
}


/********************************************
          APPEND CONTROL
********************************************/
void append(char *filename){
  FILE *file;

  // open and append file
  if(filename != NULL){
    file = freopen(filename, "a", stdout );
  } else {
    printf("file: %s: Could not be found\n", (char *) filename);
  }

  // error
  if(file == NULL){
    printf("There was an error\n");
  }
}


/********************************************
        OUPUT REDIRECT CONTROL
********************************************/
void outputRedirect(char *filename){
  FILE *file;

  // open and redirects output
  if(filename != NULL){
    file = freopen(filename, "w", stdout );
  } else {
    printf("file: %s: Could not be found\n", (char *) filename);
  }

  // error
  if(file == NULL){
    printf("There was an error\n");
  }
}


/********************************************
        INPUT REDIRECT CONTROL
********************************************/
void inputRedirect(char *filename){
  FILE *file;

  // open and redirects input
  if(filename != NULL){
    file = freopen(filename, "r", stdin);
  } else {
    printf("file: %s: Could not be found\n", (char *) filename);
  }

  // error
  if(file == NULL){
    printf("There was an error\n");
  }
}


/********************************************
        HANDLES PARENT CHILD PROCESSES
********************************************/
void handleExternal(char **table){
  pid_t pid;
  int status;
  char filename[MAX_COMMAND_LEN];

  pid = fork();

  // if theres an error
  if(pid == -1 ){
    printf("There was an error imaging the child\n");
  }

  // wait if parent
  if(pid > 0){
    wait(&status);
  }

  // control I/O redirects
  if(REDIRECTS != 0){

    // filename should be last argument
    strcpy(filename, table[COMMAND_NUM]);

    switch(REDIRECTS){

      // append
      case 1:
        append(filename);
        break;

      // output redirect
      case 2:
        outputRedirect(filename);
        break;

      // input redirect
      case 3:
        inputRedirect(filename);
    }
  }

  // execute process
  execvp(table[0], table);

  exit(0);
}


/********************************************
            MAIN LOGIC
********************************************/
int main(){
  static char input[MAX_NUM_COMMANDS];
  static char redirectTemp[MAX_NUM_COMMANDS];
  //int commandNum;
  int commandIndex;

  // table of commands
  char **cmdTable ;
  char *command;

  // welcome message
  printf("\n\t\t Hello, welcome to jkj's shell. \n\n");

  while(1){

    // prompt user
    printf("jkj-shell: %s$ ", getenv("USER"));
    fgets(input, MAX_NUM_COMMANDS, stdin);

    // copy input for redirect check
    strcpy(redirectTemp, input);

    // set redirect
    REDIRECTS = 0;

    // get the number of user commands
    getCommandNum(input);

    // if no commands are entered
    if(COMMAND_NUM == 0){
      printf("Please enter in a command\n");
    }

    // malloc the right size of the command table neeeded
    cmdTable  = malloc( sizeof(command) * (COMMAND_NUM + 1));
    commandIndex = 0;

    command = strtok(input, DELIMITERS);

    // fill command table from input through tokenization
    while( command != NULL){
      cmdTable[commandIndex] = malloc(strlen(command));

      strcpy(cmdTable[commandIndex], command);
      command = strtok(NULL, DELIMITERS);

      commandIndex++;
    }

    // main function calls
    handleInternal(cmdTable);
    handleRedirect(redirectTemp);
    handleExternal(cmdTable);
  }

  free(cmdTable);
  return 0;
}
