#include "list.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

struct testdata {
    int integer_val;
    char* string_val;
};

// Create a new testdata
struct testdata *create_testdata(const char *string_val, int integer_val) {
	struct testdata *p = (struct testdata*) malloc(sizeof(struct testdata));
	p->string_val = malloc(strlen(string_val) + 1);
	strcpy(p->string_val, string_val);
	p->integer_val = integer_val;
	return p;
}

static int print_count;
static char *last_printed;

// Print a testdata
void print_testdata(void *data) {
	struct testdata *p = (struct testdata*) data;
	++print_count;
	last_printed = p->string_val;
	printf("%s, %d\n", p->string_val, p->integer_val);
}

// Copy a testdata
void *copy_testdata(const void *data) {
	struct testdata *p = (struct testdata*) data;
	return create_testdata(p->string_val, p->integer_val);
}

// Free a testdata
void free_testdata(void *data) {
	// This is safe because we should only be passing in testdata struct pointers
	struct testdata *p = (struct testdata*) data;
	// free any malloc'd pointers contained in the testdata struct (just string_val)
	free(p->string_val);
	// Now free the struct itself; this takes care of non-malloc'd data, like integer_val.
	free(p);
}

// Compare people by integer_val
int testdata_integer_val_comp(const void *data1, const void *data2) {
	struct testdata *p1 = (struct testdata*) data1;
	struct testdata *p2 = (struct testdata*) data2;
	return p1->integer_val - p2->integer_val;
}

// Compare people by string_val
int testdata_string_val_comp(const void *data1, const void *data2) {
	struct testdata *p1 = (struct testdata*) data1;
	struct testdata *p2 = (struct testdata*) data2;
	return strcmp(p1->string_val, p2->string_val);
}

// Tell if a testdata is integer_val 50
int is_integer_val_50(const void *data) {
	struct testdata *p = (struct testdata*) data;
	return p->integer_val == 50;
}

// Tell if a testdata is 23 or older
int is_integer_val_23_or_greater(const void *data) {
	struct testdata *p = (struct testdata*) data;
	return p->integer_val >= 23;
}

// add tests
void test_add(list* l) {
    struct testdata* bad = create_testdata("a", 0);
    assert(add(l, 1, bad) == false);
    assert(add(l, 0, create_testdata("b", 1)) == true);
    assert(add(l, 1, create_testdata("c", 2)) == true);
    assert(add(l, 0, create_testdata("a", 0)) == true);
    assert(size(l) == 3);
    free_testdata(bad);
}

// list ops tests
void _traverse_all_integer_val_eq_50(void *ptr) {
    assert(((struct testdata*) ptr)->integer_val == 50);
}
void _traverse_all_integer_val_ne_50(void *ptr) {
    assert(((struct testdata*) ptr)->integer_val != 50);
}

list* before() {
    return create_list();
}

void free_td_list(list* l) {
    empty_list(l, free_testdata);
    free(l);
}

void after(list* l) {
    free_td_list(l);
}

void test_emptyList(list* l) {
    printf("empty list tests\n");
    assert(is_empty(l));
    assert(pop_front(l) == NULL);

    empty_list(l, free_testdata);
    empty_list(l, free_testdata);
    assert(is_empty(l));
    assert(size(l) == 0);

    print_count = 0;
    last_printed = NULL;
    traverse(l, print_testdata);
    assert(print_count == 0);

    list* l2 = split_list(l, is_integer_val_23_or_greater);
    assert(is_empty(l));
    assert(is_empty(l2));

    list* l3 = copy_list(l, copy_testdata);
    assert(is_empty(l3));

    assert(get(l, 0) == NULL);
    assert(front(l) == NULL);
    struct testdata* td = create_testdata("Alex", 0);
    assert(contains(l, td, testdata_string_val_comp) == NULL);
    free_testdata(td);

    free_td_list(l2);
    free_td_list(l3);
}

void test_endOfList(list* l) {
    printf("end of list tests\n");
    struct testdata* last = create_testdata("Alex", 0);
    push_front(l, last);
    push_front(l, create_testdata("Steve", 0));
    push_front(l, create_testdata("Joe", 0));

    // also checks contains pointer return
    struct testdata* me = create_testdata("Alex", 55);
    assert(contains(l, me, testdata_string_val_comp)
            == last);
    free_testdata(me);

    list* l2 = copy_list(l, copy_testdata);
    for (int num_prints = 3; num_prints > 0; num_prints--) {
        print_count = 0;
        last_printed = NULL;
        printf("Expecting %d elements to print:\n", num_prints);
        traverse(l2, print_testdata);
        assert(print_count == num_prints);
        assert(strcmp(last_printed, "Alex") == 0);
        free_testdata(pop_front(l2));
    }
    print_count = 0;
    last_printed = NULL;
    traverse(l2, print_testdata);
    assert(print_count == 0);

    free_td_list(l2);
}

void test_list_ops(list* l) {
    printf("list ops tests\n");
    for (int i = 0; i < 100; i++) {
        for (int j = 0; j < 100; j++) {
            assert(add(l, i, create_testdata("test_testdata", i)) == true);
        }
    }
    assert(size(l) == 10000);

    struct testdata* test_testdata = create_testdata("our_test", 20);
    add(l, 1024, test_testdata);
    assert(get(l, 1024) == test_testdata);
    assert(size(l) == 10001);

    assert(list_remove(l, 1024) == test_testdata);
    free_testdata(test_testdata);

    assert(size(l) == 10000);

    list* l2 = split_list(l, is_integer_val_50);
    assert(size(l2) == 100);
    assert(size(l) == 9900);

    traverse(l, _traverse_all_integer_val_ne_50);
    traverse(l2, _traverse_all_integer_val_eq_50);

    free_td_list(l2);
}

const char* string_vals0[] = {"a", "b", "c", "d", "e", "f", "OUTOFBOUNDS", "OUTOFBOUNDS"};
const char* string_vals1[] = {     "b", "c", "d", "e", "f", "OUTOFBOUNDS", "OUTOFBOUNDS"};
const char* string_vals2[] = {     "b", "c", "d", "e",      "OUTOFBOUNDS", "OUTOFBOUNDS"};
const char* string_vals3[] = {     "b",      "d", "e",      "OUTOFBOUNDS", "OUTOFBOUNDS"};
const char* string_vals4[] = {     "b",           "e",      "OOB", "OOB"};
const char* string_vals5[] = {                    "e",      "OOB", "OOB"};
const char* string_vals6[] = {"OOB", "OOB"};
char** string_vals_sel;
int string_vals_sel_idx = 0;
void _traverse_assert_string_vals(void* ptr) {
    struct testdata* p = (struct testdata*) ptr;
    const char *testdata_string_val = p->string_val, *assertion = string_vals_sel[string_vals_sel_idx];
    printf("traverse_assert: comparing llist<%s> to assertion<%s>\n",
            testdata_string_val, assertion);
    assert(strcmp(testdata_string_val, assertion) == 0);
    string_vals_sel_idx++;
}

// remove tests
void test_remove(list* l) {
    printf("list remove tests\n");
    struct testdata* pa = create_testdata("a", 0);
    struct testdata* pb = create_testdata("b", 10);
    struct testdata* pc = create_testdata("c", 20);
    struct testdata* pd = create_testdata("d", 30);
    struct testdata* pe = create_testdata("e", 40);
    struct testdata* pf = create_testdata("f", 50);

    add(l, 0, pa);
    add(l, 1, pb);
    add(l, 2, pc);
    add(l, 3, pd);
    add(l, 4, pe);
    add(l, 5, pf);

    string_vals_sel_idx = 0;
    string_vals_sel = (char**) string_vals0;
    assert(size(l) == 6);
    traverse(l, _traverse_assert_string_vals);

    fprintf(stderr, "Removing first element.\n");
    assert(list_remove(l, 0) == pa);
    assert(size(l) == 5);
    string_vals_sel_idx = 0;
    string_vals_sel = (char**) string_vals1;
    traverse(l, _traverse_assert_string_vals);

    fprintf(stderr, "Removing last element.\n");
    assert(list_remove(l, 4) == pf);
    assert(size(l) == 4);
    string_vals_sel_idx = 0;
    string_vals_sel = (char**) string_vals2;
    traverse(l, _traverse_assert_string_vals);
    
    fprintf(stderr, "Removing list[1] ('c').\n");
    assert(list_remove(l, 1) == pc);
    assert(size(l) == 3);
    string_vals_sel_idx = 0;
    string_vals_sel = (char**) string_vals3;
    traverse(l, _traverse_assert_string_vals);

    fprintf(stderr, "Removing list[1] ('d').\n");
    assert(list_remove(l, 1) == pd);
    assert(size(l) == 2);
    string_vals_sel_idx = 0;
    string_vals_sel = (char**) string_vals4;
    traverse(l, _traverse_assert_string_vals);

    fprintf(stderr, "Removing list[0] ('b').\n");
    assert(list_remove(l, 0) == pb);
    assert(size(l) == 1);
    string_vals_sel_idx = 0;
    string_vals_sel = (char**) string_vals5;
    traverse(l, _traverse_assert_string_vals);

    fprintf(stderr, "Removing list[0] ('e').\n");
    assert(list_remove(l, 0) == pe);
    assert(size(l) == 0);
    string_vals_sel_idx = 0;
    string_vals_sel = (char**) string_vals6;
    traverse(l, _traverse_assert_string_vals);
    free_testdata(pa);
    free_testdata(pb);
    free_testdata(pc);
    free_testdata(pd);
    free_testdata(pe);
    free_testdata(pf);
}

void test_copy_list_trivial(list *l)
{
    printf("Trivial test.\n");
    add(l, 0, create_testdata("hi mum", 392));
    add(l, 1, create_testdata("i wont drop out i swear", 392));
    list *backup = copy_list(l, copy_testdata);
    assert(size(backup) == size(l));
    free_td_list(backup);
}

// run all tests
void run_alex_tests() {
    const int numTests = 6;
    for (int i = 1; i <= numTests; i++) {
        list* l = before();

        switch(i) {
            case 1: test_emptyList(l);
                    break;
            case 2: test_endOfList(l);
                    break;
            case 3: test_add(l);
                    break;
            case 4: test_remove(l);
                    break;
            case 5: test_list_ops(l);
                    break;
            case 6: test_copy_list_trivial(l);
                    break;
        }

        after(l);
    }
}

int main() {
    run_alex_tests();
}
