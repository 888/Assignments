/*
 * student.c
 * Multithreaded OS Simulation for CS 2200, Project 4
 * Fall 2014
 *
 * This file contains the CPU scheduler for the simulation.
 * Name: Alan Chiang
 * GTID: 903129489
 */

#include <assert.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "os-sim.h"


/*
 * current[] is an array of pointers to the currently running processes.
 * There is one array element corresponding to each CPU in the simulation.
 *
 * current[] should be updated by schedule() each time a process is scheduled
 * on a CPU.  Since the current[] array is accessed by multiple threads, you
 * will need to use a mutex to protect it.  current_mutex has been provided
 * for your use.
 */
static pcb_t **current;
static pthread_mutex_t current_mutex;
static pthread_mutex_t qmutex;
static pthread_cond_t condVar;
static int timeslice;
static int SRTF = 0;
static int cpu_count;

static pcb_t *head = NULL;
static pcb_t *tail = NULL;
static int size = 0;

static void enqueue(pcb_t *process) {
	pthread_mutex_lock(&qmutex);
	if (head == NULL) {
		head = process;
		tail = process;
	} else {
		tail -> next = process;
		tail = process;
	}
	size++;
	printf("ENQUEUED %d\n", size);
	pthread_cond_broadcast(&condVar);
	pthread_mutex_unlock(&qmutex);
}

static pcb_t *dequeue() {
	pthread_mutex_lock(&qmutex);
	pcb_t *tmp = head;
	if (head == NULL) {
		return NULL;
	} else if (head == tail) {
		head = NULL;
		tail = NULL;
	} else {
		head = head -> next;
	}
	size--;
	printf("DEQUEUED %d\n", size);
	pthread_mutex_unlock(&qmutex);
	return tmp;
}

static pcb_t* smallest() {
	pcb_t* node;
	pthread_mutex_lock(&qmutex);
	if (!(head)) {
		node = NULL;
	} else {
		int small = head->time_remaining;
		node = head;
		pcb_t* now = head->next;
		pcb_t* prev = head;
		pcb_t* prevNode = NULL;
		while (now) {
			if (now->time_remaining < small) {
				small = now->time_remaining;
				node = now;
				prevNode = prev;
			}
			prev = now;
			now = now->next;
		}
		if (node == head) {
			head = head->next;
			if (tail == node) {
				tail = NULL;			
			}
		} else if (node == tail) {
			tail = prevNode;
			prevNode->next = NULL;
		} else {
			prevNode->next = node->next;
		}
	}
	if (node) {
		node->next = NULL;
	}
	pthread_mutex_unlock(&qmutex);
	return node;
}

/*
 * schedule() is your CPU scheduler.  It should perform the following tasks:
 *
 *   1. Select and remove a runnable process from your ready queue which 
 *	you will have to implement with a linked list or something of the sort.
 *
 *   2. Set the process state to RUNNING
 *
 *   3. Call context_switch(), to tell the simulator which process to execute
 *      next on the CPU.  If no process is runnable, call context_switch()
 *      with a pointer to NULL to select the idle process.
 *	The current array (see above) is how you access the currently running process indexed by the cpu id. 
 *	See above for full description.
 *	context_switch() is prototyped in os-sim.h. Look there for more information 
 *	about it and its parameters.
 */
static void schedule(unsigned int cpu_id)
{
    /* FIX ME */
	pcb_t* blank = NULL;

	if (head == NULL) {
		context_switch(cpu_id, blank, -1);
	} else if (!(SRTF)) {
		pcb_t* run = dequeue(0);
		if (run != NULL) {
			run->state = PROCESS_RUNNING;
		}		
		pthread_mutex_lock(&current_mutex);
		current[cpu_id] = run;
		pthread_mutex_unlock(&current_mutex);
		context_switch(cpu_id, run, timeslice);
	} else if (SRTF) {
		pcb_t* node = smallest();
		if (node != NULL) {
			node->state = PROCESS_RUNNING;
		}
		pthread_mutex_lock(&current_mutex);
		current[cpu_id] = node;
		pthread_mutex_unlock(&current_mutex);
		context_switch(cpu_id, node, timeslice);
	}		
}


/*
 * idle() is your idle process.  It is called by the simulator when the idle
 * process is scheduled.
 *
 * This function should block until a process is added to your ready queue.
 * It should then call schedule() to select the process to run on the CPU.
 */
extern void idle(unsigned int cpu_id)
{
    /* FIX ME */
    //schedule(0);

    /*
     * REMOVE THE LINE BELOW AFTER IMPLEMENTING IDLE()
     *
     * idle() must block when the ready queue is empty, or else the CPU threads
     * will spin in a loop.  Until a ready queue is implemented, we'll put the
     * thread to sleep to keep it from consuming 100% of the CPU time.  Once
     * you implement a proper idle() function using a condition variable,
     * remove the call to mt_safe_usleep() below.
     */
	pthread_mutex_lock(&qmutex);
	while(head == NULL) {
		pthread_cond_wait(&condVar, &qmutex);
	}	
	pthread_mutex_unlock(&qmutex);
	schedule(cpu_id);
}


/*
 * preempt() is the handler called by the simulator when a process is
 * preempted due to its timeslice expiring.
 *
 * This function should place the currently running process back in the
 * ready queue, and call schedule() to select a new runnable process.
 */
extern void preempt(unsigned int cpu_id)
{
    /* FIX ME */
	pthread_mutex_lock(&current_mutex);
	current[cpu_id]->state = PROCESS_READY;
	pthread_mutex_unlock(&current_mutex);

	enqueue(current[cpu_id]);
	schedule(cpu_id);
}


/*
 * yield() is the handler called by the simulator when a process yields the
 * CPU to perform an I/O request.
 *
 * It should mark the process as WAITING, then call schedule() to select
 * a new process for the CPU.
 */
extern void yield(unsigned int cpu_id)
{
    /* FIX ME */
	pthread_mutex_lock(&current_mutex);
	current[cpu_id]->state = PROCESS_WAITING;
	pthread_mutex_unlock(&current_mutex);
	schedule(cpu_id);
}


/*
 * terminate() is the handler called by the simulator when a process completes.
 * It should mark the process as terminated, then call schedule() to select
 * a new process for the CPU.
 */
extern void terminate(unsigned int cpu_id)
{
    /* FIX ME */
	pthread_mutex_lock(&current_mutex);
	current[cpu_id]->state = PROCESS_TERMINATED;
	pthread_mutex_unlock(&current_mutex);
	schedule(cpu_id);
}


/*
 * wake_up() is the handler called by the simulator when a process's I/O
 * request completes.  It should perform the following tasks:
 *
 *   1. Mark the process as READY, and insert it into the ready queue.
 *
 *   2. If the scheduling algorithm is SRTF, wake_up() may need
 *      to preempt the CPU with the highest remaining time left to allow it to
 *      execute the process which just woke up.  However, if any CPU is
 *      currently running idle, or all of the CPUs are running processes
 *      with a lower remaining time left than the one which just woke up, wake_up()
 *      should not preempt any CPUs.
 *	To preempt a process, use force_preempt(). Look in os-sim.h for 
 * 	its prototype and the parameters it takes in.
 */
extern void wake_up(pcb_t *process)
{
    /* FIX ME */
	process->state = PROCESS_READY;
	enqueue(process);
	if (SRTF) {
		int highest = 0;
		int victim = 0;
		pthread_mutex_lock(&current_mutex);
		for (int i = 0; i < cpu_count; i++) {
			if (current[i] == NULL) {
				victim = -1;
				break;
			} else if (current[i]->time_remaining > highest) {
				highest = current[i]->time_remaining;
				victim = i;
			}
		}
		pthread_mutex_unlock(&current_mutex);
		if (victim > -1 && process->time_remaining < highest) {
			force_preempt(victim);
		}
	}
}	

/*
 * main() simply parses command line arguments, then calls start_simulator().
 * You will need to modify it to support the -r and -s command-line parameters.
 */
int main(int argc, char *argv[])
{

    /* Parse command-line arguments */
    if (argc < 2)
    {
        fprintf(stderr, "CS 2200 Project 4 -- Multithreaded OS Simulator\n"
            "Usage: ./os-sim <# CPUs> [ -r <time slice> | -s ]\n"
            "    Default : FIFO Scheduler\n"
            "         -r : Round-Robin Scheduler\n"
            "         -s : Shortest Remaining Time First Scheduler\n\n");
        return -1;
    }
    cpu_count = atoi(argv[1]);
	timeslice = -1;

    /* FIX ME - Add support for -r and -s parameters*/
	if (argc == 4 && !(strncmp(argv[2], "-r", 2))) {
		timeslice = atoi(argv[3]);
	}
	if (argc == 3 && !(strncmp(argv[2], "-s", 2))) {
		SRTF = 1;
	}
    /* Allocate the current[] array and its mutex */
    current = malloc(sizeof(pcb_t*) * cpu_count);
    assert(current != NULL);
    pthread_mutex_init(&current_mutex, NULL);
	pthread_mutex_init(&qmutex, NULL);
	pthread_cond_init(&condVar, NULL);

    /* Start the simulator in the library */
    start_simulator(cpu_count);
    return 0;
}
