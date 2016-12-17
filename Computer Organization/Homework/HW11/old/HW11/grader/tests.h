#include <string.h>
#include <unistd.h>
#include "grader.h"
#include "malloc_common.h"

#define SBRK_SIZE 2048


/**********************************************************
 * if you add a test function to tests.c, add its name here
 * This list is how we delcare them below and how we setup
 * an array, an enum, and the switch statement in grader.c
 * so that they all get run.
 *********************************************************/
#define TEST_FUNCTION_LIST \
X(check_malloc_1)\
X(check_malloc_2)\
X(check_malloc_3)\
X(check_realloc_1)\
X(check_realloc_2)\
X(check_realloc_3)\
X(check_calloc)\
X(check_free_1)\
X(check_free_2)\
X(check_free_3)\
X(check_free_4)\
X(check_free_5)\
X(check_EC_1)\
X(check_EC_2)\
X(check_EC_3)\
X(check_EC_4_canary1)\
X(check_EC_4_canary2)\
X(check_EC_4_canary3)

/* here's an example of how this macro works -
 * This will declare each function as void and
 * taking no parameters */
#define X(test_func) void test_func();
TEST_FUNCTION_LIST
#undef X

void printList();

/* students methods */
void* my_malloc(size_t);
void* my_realloc(void*, size_t);
void* my_calloc(size_t, size_t);
void my_free(void *);

/* the freelist */
extern metadata_t* freelist;

/* Point distribution. The total must add up to TOTAL_POINTS_POSSIBLE. */
#define TOTAL_POINTS_POSSIBLE 100

#define M_REQUEST_SIZE_POINTS     4
#define M_SIZE_POINTS       4
#define M_FREELIST_POINTS   5 /* First check wrong */
#define M_FREELIST2_POINTS  4 /* Second check wrong */
#define M_FREELIST3_POINTS  4 /* Third check wrong */
#define MALLOC_STRESS       8
#define MALLOC_EDGE_MAX     4
#define MALLOC_EDGE_MAX1    4
#define MALLOC_EDGE_HUGE    4
#define MALLOC_OOM          4

#define REALLOC_SMALLER     3
#define REALLOC_LARGER      3
#define REALLOC_NULL        3
#define REALLOC_FREE        3

#define CALLOC              4

#define F_NO_COMBINE        3
#define F_COMBINE           3
#define F_COMBINE_RECURSE   3
#define F_DIFF_SIZE         3
#define FREE_MULTI_HEAD     3
#define FREE_MULTI_MID      3
#define FREE_MULTI_TAIL     3

#define ERR_NO_ERROR_POINTS 3
#define ERR_OUT_OF_MEMORY_POINTS 3
#define ERR_SINGLE_REQUEST_TOO_LARGE_POINTS 3
// CANARY_CORRUPTED should be divisible by 3
#define ERR_CANARY_CORRUPTED 9

