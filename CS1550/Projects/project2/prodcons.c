/*
 * 1550 Project 2: Producer/consumer
 * By: John Kelly
 *
 *
 */

/*
 * move files
 *
 * scp /u/OSLab/jjk79/p2/linux-2.6.23.1/kernel/sys.c .
 * scp /u/OSLab/jjk79/p2/linux-2.6.23.1/arch/i386/kernel/syscall_table.S .
 * scp /u/OSLab/jjk79/p2/linux-2.6.23.1/include/asm/unistd .
 */


// general libraries
#include <sys/mman.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <linux/unistd.h>


// create semaphore data type
struct cs1550_sem {
	int val;
	struct Node *start;
	struct Node *end;
};



// custom down
void cs1550_down(struct cs1550_sem *sem){
	syscall(__NR_sys_cs1550_down, sem);
}


// custom up
void cs1550_up(struct cs1550_sem *sem){
	syscall(__NR_sys_cs1550_up, sem);
}


/* main control */
int main(int argc, char *argv[]){

	// local variable declarations
	int producers, consumers, buffer_size;
	void *sem_address, *buffer_address;
	struct cs1550_sem *empty, *full, *mutex;
	int *producer_ptr, *consumer_ptr, *buffer_ptr;
	//int temp_prod, temp_con;
	int i, j;


	// check cmd number
	if( argc != 4){
		printf("Error: wrong number of commands...\n");
		return 1;
	}

	// get command arguments
	producers = atoi(argv[1]);
	consumers = atoi(argv[2]);
	buffer_size = atoi(argv[3]);

	//printf("CMDS: prod: %d con: %d buffer: %d\n", producers, consumers, buffer_size);

	// allocate space for semaphores
	sem_address = mmap(NULL, sizeof(struct  cs1550_sem) * 3, PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, 0, 0);
	if( sem_address == (void *) -1){
		printf("Error: failed semaphore space allocation.\n");
		return 1;
	}

	// set semaphores
	full = (struct cs1550_sem *) sem_address;
	empty = (struct cs1550_sem *) sem_address + 1;
	mutex = (struct cs1550_sem *) sem_address + 2;

	// allocate space for buffer
	buffer_address = mmap(NULL, sizeof(int) * (buffer_size + 2), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, 0, 0);
	if( buffer_address == (void *) -1){
		printf("Error: failed buffer allocation. \n");
		return 1;
	}

	// set pointers
	producer_ptr = (int *) buffer_address;
	consumer_ptr = (int *) buffer_address + 1;
	buffer_ptr = (int *) buffer_address + 2;

	// zero out pointers
	*producer_ptr = 0;
	*consumer_ptr = 0;

	// set semaphores
	full->val = 0;
	full->start = NULL;
	full->end = NULL;
	mutex->val = 1;
	mutex->start = NULL;
	mutex->end = NULL;
	empty->val = buffer_size;
	empty->start = NULL;
	empty->end = NULL;


	// clear out buffer
	for(j = 0; j < buffer_size; j++){
		buffer_ptr[j] = 0;
	}

	// generate producer threads
	for( i = 0; i < producers; i++){
		//printf("PRODUCERS: i: %d producers: %d\n", i, producers);

		// if child
		if( fork() == 0){

			int temp_prod;

			while(1){

				// lock
				cs1550_down(empty);
				cs1550_down(mutex);

				temp_prod = *producer_ptr;
				// update address
				buffer_ptr[*producer_ptr] = temp_prod;

				// add 65 for ascii offset
				printf("Producer %c Produced: %d \n", i + 65, temp_prod);

				*producer_ptr = (*producer_ptr + 1) % buffer_size;

				// unlock
				cs1550_up(mutex);
				cs1550_up(full);
			}
		}
	}

	// consumer threads
	for( i = 0; i  < consumers; i++){
		printf("CONSUMERS: i: %d consumers: %d \n", i, consumers);

		//printf("...generating consumers...\n");
		// if child
		if( fork() == 0){

			//printf("...consumers...\n");
			int temp_con;

			while(1){

				// lock
				cs1550_down(full);
				cs1550_down(mutex);

				//printf("con down\n");

				temp_con = buffer_ptr[*consumer_ptr];

				printf("Consumer %c Consumed: %d \n", i + 65, temp_con);

				*consumer_ptr = (*consumer_ptr + 1) % buffer_size;

				// lock
				cs1550_up(mutex);
				cs1550_up(empty);
			}
		}
	}


	printf("... exiting now, buh bye...\n");
	return 0;
}


























