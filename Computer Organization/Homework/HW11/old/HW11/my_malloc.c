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
metadata_t* freelist;

void add_to_freelist(metadata_t* node)
{
	if (freelist == NULL)
	{
	    freelist = node;
	    return; //is necessary?
	}
	else
	{
	    metadata_t* current = freelist;
	    metadata _t* prev = NULL;
	    while ((uintptr_t)node > (uintptr_t)current && current)
	    {
			prev = current;
			current = current->next;
	    }
		node->next = current;
		if (prev != NULL)
		{
	    	prev->next = node;
		}	
	}
}

void* remove_from_freelist(size_t size)
{
	if (size  
}

void merge_rfreelist(metadata_t* node)
{
	if (((char*) (node) + node->block_size) == node->next)
	{
		node->block_size += node->next->block_size;
		node->next = node->next->next;
		node->request_size = 0;
		node->canary = calc_canary(node);
	}
}

void merge_lfreelist(metadata_t* node)
{
	metadata_t* current = freelist;
	while (current->next != node)
	{	
		current = current->next;
	}
	merge_rfreelist(current);
}

void* split_freelist()
{

}

unsigned int calc_canary(metadata_t* block) 
{
	return ((((int)block->block_size) << 16) | ((int)block->request_size)) ^ (int)(uintptr_t)block;
}


void free(void* addr) 
{
	metadata_t* address = (metadata_t*) addr;
	add_to_freelist(address - 1);
	merge_rfreelist(address - 1);
	merge_lfreelist(address - 1);
}

void* my_malloc(size_t size) {
	//null, size checks
	b = find_smallest_fit(); //TODO
	if (b == NULL)
	{
		b = my_sbrk(SBRK_SIZE);
		if (b == NULL)
		{
			return OUT_OF_MEMORY;
		}
		b->size = SBRK_SiZE;
		b->request_size = 0;
		b->next = NULL;
		add_to_freelist(b);
		return malloc(size);
	else 
	{
		if (should_split(b, s))
		{
			return split_block(b, s); //TODO
		else 
		{
			b->request_size = s;
			remove_from_freelist(b); //TODO
			return b + 1;
		}
	}
}



/*

	if (size < 	
	{
		//handle errors
	}	



	metadata_t* current = freelist;		
	int init = 5000;
	metadata_t* best = NULL;
	while (current != NULL) 
	{
		if (current->block_size >= (size + sizeof(metadata_t) + sizeof(canary) && current->block_size < init)
		{
			best = current;			
			init = current->block_size;
		} 
	}
	if (best != NULL)
	{
	} else 
	{
		metadata_t* block = (metadata_t*) my_sbrk(SBRK_SIZE);
		if (block == NULL)
		{
			//handle error
		} else if 
		{ 
		
		} else 
		{
			block->block_size = SBRK_SIZE;
			block->request_size = 0;
			block->next = NULL;
			block->canary = 0;
			add_to_freelist(block);
		}
	}

    return NULL;
}

*/



void* my_realloc(void* ptr, size_t new_size) {
    return NULL;
}

void* my_calloc(size_t nmemb, size_t size) {
    return NULL;
}

void my_free(void* ptr) {
}
