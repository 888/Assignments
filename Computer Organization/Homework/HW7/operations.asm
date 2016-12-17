;;===============================
;; CS 2110 Fall 2016
;; Homework 7 - Functions
;; Name: Alan Chiang
;;===============================

.orig x3000
	LD R6, STACK

	LD R0, OP			; load OP
	ADD R6, R6, -1		; \
	STR R0, R6, 0 		; / push OP on stack

	LD R0, B 			; load B
	ADD R6, R6, -1 		; \
	STR R0, R6, 0 		; / push B on stack

	LD R0, A 			; load A
	ADD R6, R6, -1 		; \
	STR R0, R6, 0 		; / push A on stack

	LD R0, OPERATIONS_ADDR 	; load address of OPERATIONS
	JSRR R0 			; call OPERATIONS

	LDR R0, R6, 0 		; load return value from top of stack
	ADD R6, R6, 4 		; pop 3 args and return value from stack

	ST R0, ANSWER 		; store final value in answer
	HALT

A 		.fill 7
B		.fill 4
OP		.fill 1
ANSWER 	.fill 0
STACK 	.fill xF000

OPERATIONS_ADDR .fill OPERATIONS
.end

.orig x4000
OPERATIONS
	; put OPERATIONS code here
	ADD R6, R6, -3 	; move R6 up 3
    STR R7, R6, 1 	; store return address
    STR R5, R6, 0 	; store old FP
    ADD R5, R6, -1 	; set new FP
	ADD R6, R6, -2 	; create space for local
	LDR R2, R5, 4 	; load A
	LDR R1, R5, 5	; load B
	STR R2, R6, 0 	; store a
	STR R1, R6, 1	; store b

	LDR R0, R5, 6 	; R0 = OP
	BRZ JXOR 		; if OP = 0, xor(a, b)
	ADD R3, R0, -4  ; R3 = R0 - 4
	BRZ JMYS 		; mys(a)
	ADD R3, R0, -3 	; R3 = R0 - 3
	BRZ JMOD 		; mod(a,b)
	ADD R3, R0, -2	; R3 = R0 - 2
	BRZ JDIV 		; div(a, b)
	ADD R3, R0, -1  ; set CC to OP
	BRZ JMULT 		; if OP > 0, mult(a, b)

ELSE	AND R0, R0, 0 	; R0 = 0
	ADD R0, R0, -1 	; R0 = -1
	STR R0, R6, 0 	; set RV = -1

DONE	LDR R0, R6, 0 	; load function val
	STR R0, R5, 3	; store in return
	LDR R7, R5, 2 	; load return addr
	ADD R6, R5, 0 	; R6 = R5
	LDR R5, R5, 1 	; load old FP
	ADD R6, R6, 3 	; point at return val

	RET


JXOR	LD R0, XOR_ADDR
	JSRR R0
  	BR DONE

JDIV	LD R0, DIV_ADDR
	JSRR R0
	BR DONE

JMOD 	LD R0, MOD_ADDR
	JSRR R0
	BR DONE

JMULT	LD R0, MULT_ADDR
	JSRR R0
	BR DONE

JMYS 	LD R0, MYSTERY_ADDR
	JSRR R0
	BR DONE


XOR_ADDR 		.fill XOR
MULT_ADDR 		.fill MULT
DIV_ADDR 		.fill DIV
MOD_ADDR 		.fill MOD
MYSTERY_ADDR 		.fill MYSTERY
.end

.orig x4800
XOR
	; put XOR code here
	ADD R6, R6, -3 	; move R6 up 3
        STR R7, R6, 1 	; store return address
        STR R5, R6, 0 	; store old FP
        ADD R5, R6, -1 	; set new FP
	ADD R6, R6, -1 	; allocate local vars

	LDR R2, R5, 4 	; R2 = a
	LDR R3, R5, 5 	; R3 = b
	AND R4, R2, R3 	; R4 = a & b
	STR R4, R5, 0	; store and1
	NOT R2, R2	; R2 = !a
	NOT R3, R3 	; R3 = !b
	AND R0, R2, R3 	; R0 = !a & !b
	STR R0, R5, -1	; store and2
	NOT R0, R0	; !and1
	NOT R4, R4	; !and2
	AND R0, R0, R4 	; R0 = (!a & !b) & (a & b)
	STR R0, R5, 3 	; set RV = R0

        ADD R6, R5, 3   ; shift R6 back
        LDR R7, R5, 2	; load return addr
        LDR R5, R5, 1	; load old FP

	RET
.end

.orig x5000
MULT
	; put MULT code here
	ADD R6, R6, -3 	; move R6 up 3
        STR R7, R6, 1 	; store return address
        STR R5, R6, 0 	; store old FP
        ADD R5, R6, -1 	; set new FP
	ADD R6, R6, 0 	; allocate local var

	AND R0, R0, 0 	; R0 = 0
	LDR R2, R5, 4 	; R2 = a
	LDR R3, R5, 5 	; R3 = b
	ADD R3, R3, 0 	; set CC to a
	BRZ FIN
WHILE	ADD R0, R0, R2 	; R0 += a
	ADD R3, R3, -1 	; b--
	STR R0, R5, 0 	; store sum
	BRP WHILE
FIN	STR R0, R5, 0 	; store sum
	STR R0, R5, 3 	; set RV = -1

	LDR R7, R5, 2 	; load return addr
	ADD R6, R5, 0 	; R6 = R5
	LDR R5, R5, 1 	; load old FP
	ADD R6, R6, 3 	; point at return val


	RET
.end

.orig x5800
DIV
	; put DIV code here
	ADD R6, R6, -3 	; move R6 up 3
        STR R7, R6, 1 	; store return address
        STR R5, R6, 0 	; store old FP
        ADD R5, R6, -1 	; set new FP

	AND R1, R1, 0 	; R1 = 0
	LDR R2, R5, 4 	; R2 = a
	LDR R3, R5, 5 	; R3 = b
	NOT R4, R3 		; R4 = !b
	ADD R4, R4, 1 	; R4 += 1
	ADD R1, R2, R4 	; a - b
	BRN BASE1

	ADD R6, R6, -2 	; allocate space
	STR R1, R6, 0 	; store a - b
	STR R3, R6, 1	; store b
	JSR DIV
	LDR R0, R6, 0 	; load function call
	ADD R0, R0, 1	; add 1 to function result
	STR R0, R5, 3 	; RV = 1 + div(a - b, b)
	BR CLEAN1

BASE1 	AND R1, R1, 0 	; R1 = 0
	STR R1, R5, 3		; RV = 0

CLEAN1	LDR R7, R5, 2 	; load return addr
	ADD R6, R5, 0 		; R6 = R5
	LDR R5, R5, 1 		; load old FP
	ADD R6, R6, 3 		; point at return val

	RET
.end

.orig x6000
MOD
	; put MOD code here
	ADD R6, R6, -3 	; move R6 up 3
        STR R7, R6, 1 	; store return address
        STR R5, R6, 0 	; store old FP
        ADD R5, R6, -1 	; set new FP

	LDR R2, R5, 4 	; R2 = a
	LDR R3, R5, 5 	; R3 = b
	NOT R4, R3 		; R4 = !b
	ADD R4, R4, 1 	; R4 += 1
	ADD R1, R2, R4 	; a - b
	BRN BASE2

	ADD R6, R6, -2 	; allocate space
	STR R1, R6, 0 	; store a - b
	STR R3, R6, 1 	; store b
	JSR MOD
	LDR R0, R6, 0 	; load function call
	STR R0, R5, 3 	; RV = mod(a - b, b)
	BR CLEAN2

BASE2	STR R2, R5, 3 	; RV = a

CLEAN2	LDR R7, R5, 2 	; load return addr
	ADD R6, R5, 0 	; R6 = R5
	LDR R5, R5, 1 	; load old FP
	ADD R6, R6, 3 	; point at return val

	RET
.end

.orig x7000
MYSTERY
	ADD R6, R6, -3
	STR R7, R6, 1
	STR R5, R6, 0
	ADD R5, R6, -1

	LDR R0, R5, 4

	ADD R1, R0, -1
	BRnp SKIP_MYSTERY1
	AND R0, R0, 0
	BR RETURN_MYSTERY

SKIP_MYSTERY1
	AND R1, R1, 0
	ADD R1, R1, 2

	ADD R6, R6, -1
	STR R1, R6, 0
	ADD R6, R6, -1
	STR R0, R6, 0

	LD R0, DIV_FUNC
	JSRR R0

	LDR R1, R6, 0
	ADD R6, R6, 3

	ADD R6, R6, -1
	STR R1, R5, 0

	LDR R0, R5, 4

	AND R1, R1, 0
	ADD R1, R1, 2

	ADD R6, R6, -1
	STR R1, R6, 0
	ADD R6, R6, -1
	STR R0, R6, 0

	LD R0, MOD_FUNC
	JSRR R0

	LDR R1, R6, 0
	ADD R6, R6, 3

	ADD R1, R1,0
	BRnp SKIP_MYSTERY2

	LDR R0, R5, 0
	ADD R6, R6, -1
	STR R0, R6, 0
	JSR MYSTERY

	LDR R0, R6, 0
	ADD R6, R6, 2

	ADD R0, R0, 1

	BR RETURN_MYSTERY

SKIP_MYSTERY2
	LDR R0, R5, 4
	ADD R1, R0, R0
	ADD R1, R1, R0
	ADD R1, R1, 1

	ADD R6, R6, -1
	STR R1, R6, 0
	JSR MYSTERY

	LDR R0, R6, 0
	ADD R6, R6, 2
	ADD R0, R0, 1

RETURN_MYSTERY
	STR R0, R5, 3
	ADD R6, R5, 3
	LDR R7, R5, 2
	LDR R5, R5, 1
	RET


DIV_FUNC .fill DIV
MOD_FUNC .fill MOD
.end
