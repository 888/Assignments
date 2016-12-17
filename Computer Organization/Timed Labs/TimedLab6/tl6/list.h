#ifndef LIST_H
#define LIST_H

typedef struct node_t {
	struct node_t *prev;
	struct node_t *next;
	void* data;
} NODE;

typedef struct llist_t {
	NODE *head;
	NODE *tail;
	unsigned int size;
} LIST;

typedef void (*list_op)(void*);

LIST *create_list();
NODE *create_node(void* data);
void push_front(LIST *llist, void* data);
void* pop_back(LIST *llist);
void destroy(LIST *llist, list_op free_func);
void** to_array(LIST *llist);

#endif
