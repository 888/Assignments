# Fill in this Makefile using the explained in the lab

hello:  hello.c hello.i hello.s hello.o
	gcc hello.o -o hello

hello.o: hello.i hello.s 
	gcc -c hello.s -o hello.o

hello.s: hello.i
	gcc -S hello.i -o hello.s

hello.i: hello.c 
	cpp hello.c hello.i
