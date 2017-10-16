/*========================================================
  Project 3: Header File
  By: John Kelly Jr
  This is the header file for mymalloc program
========================================================*/

/*============================
    NODE STRUCTURE
==============================*/
typedef struct node{
  int chunk;
  int inUse;
  void *dataLocation;
  struct node *next;
  struct node *previous;
} NODE;


/*============================
      REQUIRED
==============================*/
void *my_worst_fit(int size);
void my_free(void *ptr);


/*============================
      HELPERS
==============================*/
NODE* generateNode(int size, void *startNode, void *dataStart);
void linkNewNode(NODE* temp);
void deallocate(NODE *badNode);
NODE *coealesce(NODE *main, NODE *helper);
//void printJaunt(NODE *x, int d);
