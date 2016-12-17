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
	    node->next = NULL;
	    return;
	}
	else
	{
	    metadata_t* current = freelist;
	    metadata_t* prev = NULL;
	    while (current && (uintptr_t)node > (uintptr_t)current)
	    {
			prev = current;
			current = current->next;
	    }
		node->next = current;
		if (prev != NULL)
		{
	    	prev->next = node;
		} else 
		{
			freelist = node;
		}
	}
}

void calc_canary(metadata_t* block) 
{
	block->canary = ((((int)block->block_size) << 16) | ((int)block->request_size))
            ^ (int)(uintptr_t)block;
	int* second = (int*) (((char*) block) + sizeof(metadata_t) + block->request_size);
	*second = block->canary;
}

void* find_smallest_fit(size_t size)
{
	if (freelist == NULL)
	{
		return NULL;
	}
	metadata_t* current = freelist;
	int max = 5000;
	metadata_t* output = NULL;
	while (current)
	{
		if (current->block_size >= (size + sizeof(metadata_t) + sizeof(int)) && current->block_size < max)
		{
			output = current;
			max = current->block_size;			
		}
		current = current->next;
	}
	return output;
}

void* remove_from_freelist(metadata_t* node)
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
	calc_canary(current);
	return current;
}

void merge_rfreelist(metadata_t* node)
{
	metadata_t* target = (metadata_t*) ((char*) node) + node->block_size;
	metadata_t* current = freelist;
	int found = 0;
	while (!found && current)
	{
		if (current == target)
		{
			found = 1;
		} else 
		{
			current = current->next; //segfaulting here, during i = 42 when merge_rfreelist() is called by merge_lfreelist(), "next 57364"
		}
	}
	if (found)
	{
		node->block_size += current->block_size;
		node->next = current->next;
	}
}

void merge_lfreelist(metadata_t* node)
{
	metadata_t* current = freelist;
	while (current->next && current->next != node)
	{	
		current = current->next;
	}
	merge_rfreelist(current);
}

int should_split(metadata_t* node, size_t size)
{
	if (node->block_size >= (size + sizeof(metadata_t) + sizeof(int) + (1 + sizeof(metadata_t) + sizeof(int))))
	{
		return 1;
	}
	else
	{
		return 0;
	} 
}

void* split_freelist(metadata_t* node, size_t size)
{
	metadata_t* piece = NULL;
	piece = (metadata_t*) ((char*) node) + node->block_size - (size + sizeof(metadata_t) + sizeof(int));
	piece->block_size = size + sizeof(metadata_t) + sizeof(int);
	piece->request_size = size;	
 	piece->next = NULL;
	node->block_size -= piece->block_size;
	calc_canary(node);
	calc_canary(piece);
	return piece;
}

void* my_malloc(size_t size) {
	if (size < 0)
	{
		return NULL;
	}
	if (size > (SBRK_SIZE - sizeof(metadata_t) - sizeof(int)))
	{
		ERRNO = SINGLE_REQUEST_TOO_LARGE;
		return NULL;
	}
	metadata_t* b = find_smallest_fit(size);
	if (b == NULL)
	{
		b = my_sbrk(SBRK_SIZE);
		if (b == NULL)
		{
			ERRNO = OUT_OF_MEMORY;
			return NULL;
		}
		b->block_size = SBRK_SIZE;
		b->request_size = size;
		b->next = NULL;
		b->canary = ((((int)b->block_size) << 16) | ((int)b->request_size))
            ^ (int)(uintptr_t)b;
		int* second = (int*) (((char*) b) + sizeof(metadata_t) + b->request_size);
		*second = b->canary;
		add_to_freelist(b);
		return my_malloc(size);
	}
	else 
	{
		ERRNO = NO_ERROR;
		if (should_split(b, size))
		{
			b = split_freelist(b, size);
			return b + 1;
		}			
		else 
		{
			b = remove_from_freelist(b);		
			b->request_size = size;	
			return b + 1;
		}
	}
}

void* my_realloc(void* ptr, size_t new_size) {
	if (ptr == NULL)
	{
		return my_malloc(new_size);
   	}
	if (new_size == 0)
	{
		my_free(ptr);
		return NULL;
	}
	metadata_t* reall = my_malloc(new_size);
	memcpy(reall, ptr, new_size);
	my_free(ptr);
	return reall;
}

void* my_calloc(size_t nmemb, size_t size) {
	metadata_t* ptr = my_malloc(nmemb * size);
	return memset(ptr, 0, (nmemb * size));
}

void my_free(void* ptr) {
	if (ptr == NULL)
	{
		return;
	}
	metadata_t* address = (metadata_t*) ptr;
	metadata_t* block = address - 1;
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
	merge_rfreelist(block);
	merge_lfreelist(block);
	ERRNO = NO_ERROR;
	}
}
