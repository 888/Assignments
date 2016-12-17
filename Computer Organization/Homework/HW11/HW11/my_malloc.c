/*
 * CS 2110 Fall 2016
 * Author: Alan Chiang
 */

/* we need this for uintptr_t */
#include <stdint.h>
/* we need this for memcpy/memset */
#include <string.h>
/* we need this for my_sbrk */
#include "my_sbrk.h"
/* we need this for the metadata_t struct definition */
#include "my_malloc.h"

/* You *MUST* use this macro when calling my_sbrk to allocate the
 * appropriate size. Failure to do so may result in an incorrect
 * grading!
 */
#define SBRK_SIZE 2048

/* If you want to use debugging printouts, it is HIGHLY recommended
 * to use this macro or something similar. If you produce output from
 * your code then you may receive a 20 point deduction. You have been
 * warned.
 */
#ifdef DEBUG
#define DEBUG_PRINT(x) printf(x)
#else
#define DEBUG_PRINT(x)
#endif

/* Our freelist structure - this is where the current freelist of
 * blocks will be maintained. failure to maintain the list inside
 * of this structure will result in no credit, as the grader will
 * expect it to be maintained here.
 * DO NOT CHANGE the way this structure is declared
 * or it will break the autograder.
 */
metadata_t* freelist; //pointer to the head of the freelist

void merge_rfreelist(metadata_t* node) //merges given node with the node to its right
{
	if ((metadata_t*) ((char*)node + node->block_size) == node->next) //checks if nodes are adjacent
	{
		node->block_size += node->next->block_size;
		node->next = node->next->next;
	}
}

void merge_lfreelist(metadata_t* node) //iterates thru freelist to find node to the left of given node, then merges that node with given node
{
	metadata_t* current = freelist;
	while (current->next && current->next != node)
	{	
		current = current->next;
	}
	merge_rfreelist(current);
}


void add_to_freelist(metadata_t* node) //adds given node to freelist, does appropriate updates, merges, etc
{
	if (freelist == NULL)
	{
	    freelist = node;
	    node->next = NULL;
	    return;
	}
	metadata_t* current = freelist;
    metadata_t* prev = NULL;
    while (current && (uintptr_t)node > (uintptr_t)current)
    {
		prev = current;
		current = current->next;
    }
	node->next = current;
	merge_rfreelist(node);
	if (prev != NULL)
	{
    	prev->next = node;
		merge_rfreelist(prev);
	} else 
	{
		freelist = node;
	}
}

void calc_canary(metadata_t* block)  //calculates and sets both canaries for the given node
{
	block->canary = ((((int)block->block_size) << 16) | ((int)block->request_size))
            ^ (int)(uintptr_t)block;
	int* second = (int*) (((char*) block) + sizeof(metadata_t) + block->request_size);
	*second = block->canary;
}

void* find_smallest_fit(size_t size) //finds the smallest node in the freelist that can contain the given size in its request space
{
	metadata_t* current = freelist;
	while (current && current->block_size < size)
	{
		current = current->next;
	}
	return current;
}

void* remove_from_freelist(metadata_t* node) //removes given node from the freelist, does appropriate pointer shifts, etc
{
	if (freelist == NULL)
	{
		return NULL;
	}
	if (freelist->next == NULL)
	{
		metadata_t* temp = freelist;	
		freelist = NULL;	
		return temp;
	}
	metadata_t* current = freelist;
	metadata_t* prev = NULL;
	while (current && current != node)
	{	
		prev = current;
		current = current->next;
	}
	if (prev != NULL)
	{
		prev->next = current->next;
	} else 
	{
		freelist = current->next;
	}
	return current;
}

int should_split(metadata_t* node, size_t size) //determines whether or not the given block should be split, given that it should be the smallest appropriate block which holds the given size
{
	if ((node->block_size - size) > (sizeof(metadata_t) + sizeof(int))) 
	{
		return 1;
	}
	return 0;
}

void* split_freelist(metadata_t* node, size_t size) //splits the given node into two, the rightmost of which will be appropriate to contain the given size
{
	metadata_t *new = (metadata_t*) remove_from_freelist(node);
	metadata_t* move = (metadata_t*) ((char*)node + size);
	move->block_size = new->block_size - size;
	add_to_freelist(move);
	return new;
}

void* my_malloc(size_t size) {  //main function which is called by the user to dynamically allocate the given size of memory, calls sbrk if necessary, calls most of the helper functions and returns a 								//pointer to the allocated memory
	
	size_t usersize = size + sizeof(metadata_t) + sizeof(int);
	if (usersize > SBRK_SIZE)
	{
		ERRNO = SINGLE_REQUEST_TOO_LARGE;
		return NULL;
	}
	metadata_t* b = find_smallest_fit(usersize);
	if (b == NULL)
	{
		b = my_sbrk(SBRK_SIZE);
		if (b == NULL)
		{
			ERRNO = OUT_OF_MEMORY;
			return NULL;
		}
		b->next = NULL;
		b->request_size = 0;
		b->block_size = SBRK_SIZE;
		add_to_freelist(b);
		return my_malloc(size);
	}
	else 
	{
		ERRNO = NO_ERROR;
		if (should_split(b, usersize))
		{
			b = split_freelist(b, usersize);
			b->block_size = usersize;
			b->request_size = size;
			calc_canary(b);
			return b + 1;
		}			
		else 
		{
			b = remove_from_freelist(b);
			b->block_size = usersize;
			b->request_size = size;
			calc_canary(b);
			return b + 1;
		}
	}
}

void* my_realloc(void* ptr, size_t new_size) { //reallocates memory to the given size, copying over existing values if necessa
	if (ptr == NULL)
	{
		return my_malloc(new_size);
   	}
	if (new_size == 0)
	{
		my_free(ptr);
		return NULL;
	}
	char* reall = my_malloc(new_size);
	memcpy(reall, ptr, new_size);
	my_free(ptr);
	return reall;
}

void* my_calloc(size_t nmemb, size_t size) { //dynamically allocates memory and sets a initial value
	metadata_t* ptr = my_malloc(nmemb * size);
	return memset(ptr, 0, (nmemb * size));
}

void my_free(void* ptr) { //places the memory indicated by the given pointer back into the freelist, if it is uncorrupted, and merges if necessary
	if (ptr == NULL)
	{
		return;
	}
	metadata_t* block = ((metadata_t*)((char*)ptr - sizeof(metadata_t)));
	int check = ((((int)block->block_size) << 16) | ((int)block->request_size))
            ^ (int)(uintptr_t)block;
	if (check != block->canary)
	{
		ERRNO = CANARY_CORRUPTED;
	} else if (check != *(int*)((char*)ptr + block->request_size))
	{
		ERRNO = CANARY_CORRUPTED;
	} else 
	{
	add_to_freelist(block);
	ERRNO = NO_ERROR;
	}
}
