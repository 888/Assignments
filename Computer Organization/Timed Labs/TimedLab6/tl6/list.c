#include "list.h"
#include <stdio.h>
#include <stdlib.h>

/**
 * CS 2110 Timed Lab 6 Student Implementation
 * November 30, 2016
 * Alan Chiang
 */

/**
 * This function will create a new linked list, initialize its fields and return a pointer to the list.
 */
LIST *create_list()
{
	LIST* newList = malloc(sizeof(LIST));
    newList->head = NULL;
	newList->tail = NULL;
    newList->size = 0;
    return newList;
}

/**
 * This function will create a node from the given data and return a pointer to that node.
 * If the data pointer is NULL, return NULL
 */
NODE *create_node(void* data)
{
	if (data == NULL) 
    {
	return NULL;
    }
    NODE* created = malloc(sizeof(NODE));
    created->next = NULL;
	created->prev = NULL;
    created->data = data;	
    return created;
}

/**
 * This function will add a node with the given data pointer to the head of the linked list.
 * If the data pointer or list is NULL, return from the function gracefully.
 */
void push_front(LIST *llist, void* data)
{
	if (data == NULL || llist == NULL)
	{
		return;
	}
	NODE* special = create_node(data);
	special->next = llist->head;
	if (llist->size == 0)
	{
		llist->tail = special;	
	}
	else 
	{
		llist->head->prev = special;

	}
	llist->head = special;	
	llist->size++;
}

/**
 * Removes the tail of the linked list and returns the data pointer from the removed node to the user.
 * If the list is empty or NULL, return NULL.
 */
void* pop_back(LIST *llist)
{
	if (llist == NULL || llist->size == 0)
	{
		return NULL;
	}
	NODE* garbage = llist->tail;
	void* output = garbage->data;	
	if (llist->size == 1)
	{
		llist->head = NULL;
	}
	else 
	{
		llist->tail->prev->next = NULL;
	}
	llist->tail = llist->tail->prev;
	free(garbage);
	llist->size--;
	return output;
}

/**
 * All memory used in the linked list that was allocated from the heap should be freed. 
 * This includes all of the node structs in the linked list and the linked list struct.
 * The data pointed at by the data pointers in each node should be freed with free_func.
 * If the list is NULL, return from the function gracefully.
 */
void destroy(LIST *llist, list_op free_func)
{
	if (llist == NULL) 
    {
		return;
    }
    NODE* current = llist->head;
    while(current)
    {
		NODE* temp = current->next;
		free_func(current->data);
		free(current);	
		current = temp;
    }
    free(llist);
}

/**
 * This function will return an array of the data pointers stored in the linked list.
 * Read the PDF for more information on this function.
 * If the list is empty or NULL, return NULL.
 */
void** to_array(LIST *llist)
{
	if (llist == NULL || llist->size == 0) 
    {
		return NULL;
    }
	
	void* array[llist->size];
    NODE* current = llist->head;
    for (unsigned int i = 0; i < llist->size; i++)
    {
		array[i] = current->data;	
		current = current->next;
    }
	return *array;


//use malloc to make array b/c the stack is deleted?
	/*
	void* output = malloc(sizeof(array));
    for (unsigned int i = 0; i < llist->size; i++)
	{
		output = //equivalent array 
    return *output;
	*/
}
