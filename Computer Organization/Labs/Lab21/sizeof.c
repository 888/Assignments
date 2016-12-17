#include <stdlib.h>
#include <stdio.h>

void main() 
{

char a = 'a';
short b = 5;
int c = 4;
long d = 49; 
char* pa = &a;
int* pc = &c;
void* pd = &d;
int** ppc = &pc; 
struct p1 {char a; char b;};
struct p2 {short p; char a; int b; char x;}; 

printf("%lu", sizeof(char));
printf("%lu", sizeof(short));
printf("%lu", sizeof(int));
printf("%lu", sizeof(long));
printf("%lu", sizeof(char*));
printf("%lu", sizeof(int*));
printf("%lu", sizeof(void*));
printf("%lu", sizeof(struct p1));
printf("%lu", sizeof(struct p2));
}
