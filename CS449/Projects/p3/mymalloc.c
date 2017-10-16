/*==========================================================================
  Project 3: Custom malloc()
  By: John Kelly Jr
  This program dynamically allocates data to the heap using the worst-fit algorithm.

  I have added my own mini driver at the bottom, along with node outputs, and
  various bug check print statements along the way (I just commented them out).
============================================================================*/

/*========================
    PROTOTYPES
=========================*/
#include "mymalloc.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

/*======================
    GLOBALS
=======================*/
NODE *HEAD;
NODE *TAIL;
int NUM = 0;

/*==============================
   LINKED-LIST CONTROL
================================*/

// generates the nodes
NODE *getNewNode(int size, void *nodeHeap, void*dataHeap){

  // address set to space needed for node
  NODE *newNode = nodeHeap;

  // struct variables
  newNode->chunk = size;
  newNode->inUse = 1;
  newNode->dataLocation = dataHeap;
  newNode->next = NULL;
  newNode->previous = NULL;

  return newNode;
}

// adds node to the tail of the list
NODE *addToTail(int size){
  NODE *temp = getNewNode(size, sbrk(sizeof(NODE)), sbrk(size));
  NODE *current = TAIL;

  // connect the new node to previous tail
  temp->previous = current;
  current->next = temp;
  current = temp;

  return current;
}

/*=====================================
      MALLOC() WORST-FIT
=======================================*/

// returns a pointer to the worst-fit location
void *my_worst_fit(int size){
  NODE *current;
  NODE *worstNode;
  NODE *temp;
  NODE *reattatch;
  int maxAvailable;
  int difference;
  void *insertLocation;
  int found;

  // generate the head node and return data address
  if(HEAD == NULL){
    HEAD = getNewNode(size, sbrk(sizeof(NODE)), sbrk(size));
    insertLocation = HEAD->dataLocation;
    TAIL = HEAD;
    return insertLocation;
  }

  current = HEAD;

  found = 0;
  maxAvailable = 0;
  difference = 0;
  // determines availability
  while(current != NULL){
    // if space is bigger than size and other availables
    if( current->chunk > size && current->chunk > maxAvailable && !current->inUse){
      maxAvailable = current->chunk;
      difference = (maxAvailable - size);
      worstNode = current;
      found = 1;
    }
    current = current->next;
  }

  // splice node front: update (inUse) back: create (free)
  if(found){
    // sets reattatch to node right of worst node
    reattatch = worstNode->next;
    // create new temporary node
    temp = getNewNode(difference, sbrk(sizeof(NODE)), sbrk(difference));
    reattatch->previous = temp;
    temp->previous = worstNode;
    temp->next = reattatch;
    temp->inUse = 0;

    // updates newly allocated space
    worstNode->chunk = size;
    worstNode->next = temp;
    worstNode->inUse = 1;
    insertLocation = worstNode->dataLocation;

    return insertLocation;
  }

  // if theres no available space
  TAIL = addToTail(size);
  insertLocation = TAIL->dataLocation;
  return insertLocation;
}

// deallocates the desired node and coealesces it
void deallocate(NODE *badNode){
  //printf("\n\n-- recieved the node at %p\n", badNode);
  NUM++;
  NODE *nextNode;
  NODE *previousNode;

  nextNode = badNode->next;
  previousNode = badNode->previous;

  // if deallocating end of the list
  if(nextNode == NULL){
    // make sure head still exists
    if(HEAD == TAIL){
      HEAD = getNewNode(TAIL->chunk, sbrk(sizeof(NODE)), sbrk(TAIL->chunk));
    }

    TAIL = badNode;
    TAIL->next = NULL;
  }

  // walk previous back to valid node
  if(previousNode == NULL){
    printf("previous is null\n");
    printf("--- 2nd previous : %p\n", previousNode->previous->previous );
    while(previousNode != HEAD && previousNode != NULL){
        previousNode = previousNode->previous;
    }
  }

  // update tail if needs to be deallocated
  if(badNode ==  TAIL){
    printf("bad node is the tail\n");
    TAIL = previousNode;
    previousNode = previousNode-> previous;
    // decrement break point
    sbrk( - (TAIL->chunk));
    sbrk( - sizeof(NODE));
    return;
  }



  /*printf("%d: badNode: %p\n",NUM, badNode);
  printf("%d: nextNode: %p\n",NUM, nextNode);
  printf("%d: previousNode: %p\n",NUM, previousNode);*/

  //sets badNode free (aka good to overwrite)
  badNode->inUse = 0;

  //printf("-- previous to badNode %p\n", previousNode);

  /*printf("-------- %d: badNode: %p\n",NUM, badNode);
  printf("-------- %d: nextNode: %p\n",NUM, nextNode);
  printf("-------- %d: previousNode: %p\n",NUM, previousNode);*/

  printf("--- nextNode ---\n");
  printJaunt(nextNode,NUM);
  printf("--- previousNode ---\n");
  printJaunt(previousNode,NUM);


  // if next is free
  if( nextNode != NULL && !nextNode->inUse){
    printf("coalescing next node\n");
    badNode = coealesce(badNode, nextNode);
    previousNode = badNode->previous;
    nextNode = badNode->next;
  }

  // if previous is free
  if( previousNode != NULL && !previousNode->inUse){
    printf("coalescing previous node\n");
    badNode = coealesce(badNode, previousNode);
    previousNode = badNode->previous;
    nextNode = badNode->next;
  }

  // check previous nodes for usage
  /*while(!previousNode->inUse){
    printf("--- prevous node not in use---\n");
    // keep list in tact
    badNode->previous = previousNode->previous;
    // increase size allowed
    badNode->chunk += previousNode->chunk;

    previousNode = previousNode->previous;
  }

  //check next nodes for usage
  while(!nextNode->inUse){
    printf("--- the next node is not in use\n");
    // keep list in tact
    badNode->next = nextNode->next;
    // increase size
    badNode->chunk += nextNode->chunk;

    nextNode = nextNode->next;
  }*/

  printf("## leaving deallocation ##\n");
  //printJaunt(badNode, 33);
}

NODE *coealesce(NODE *main, NODE *helper){
  NODE *temp;
  printf("now coalescing\n");

  // update size
  main->chunk += helper->chunk;
  // update pointer
  main->next = helper->next;

  // link back
  temp = helper->next;
  temp->previous = main;


  return main;
}

/*=====================================
        FREE()
=======================================*/

// deallocates the memory pointed to by the argument
void my_free(void *ptr){
  NODE *current = HEAD;

  while(current != NULL){

    if(current->dataLocation == ptr && current->inUse){
      // pass node with data needed to be deallocated
      printf("-- passing %p to deallocation\n", current);
      deallocate(current);
    }
    current = current->next;
  }
}



/*===================================================================
              INFO OUTPUT
=====================================================================*/

/*void printJaunt(NODE *current, int num){
  printf("\n\n=========================================\n");
  printf("\t %d lists\n", num);
  printf("The address of node: %p\n", current);
  printf("The data address: %p\n", current->dataLocation);
  printf("Is the node in use: %d\n", current->inUse);
  printf("The size of the space is: %d\n", current->chunk);
  printf("The previous node is pointing: %p\n", current->previous);
  printf("The next node is pointing to: %p\n", current->next);
  printf("=========================================\n\n");

}*/


/*===================================================================
              MINI DRIVER
=====================================================================*/

/*int main(){
  NODE *current;
  int testers[5] = {3,5,7,8,1};
  int *pointers[5];


  int i = 0;
  while(i<5){

    // pointers to the data location of the struct
    pointers[i] = my_worst_fit(testers[i]);
    printf("-- val: %d -- data: %p\n", testers[i], pointers[i]);
    i++;
  }


  my_free(pointers[3]);
  my_free(pointers[2]);

  my_worst_fit(5);

  current = HEAD;
  while(current != NULL){
    printJaunt(current, NUM);
    current = current->next;
  }



  return 0;
}*/

