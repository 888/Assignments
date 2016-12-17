;;===============================
;; Name: Alan Chiang
;; Semester: Autumn 2016
;;===============================

;@plugin filename=lc3_udiv vector=x80

.orig x3000
MAIN

LD R6, STACK                ; Initialize stack

;;=====================================
;; Call GCD subroutine with correct arguments here
LD R0, B 		; load B
ADD R6, R6, -1 		; \
STR R0, R6, 0 		; / push B on stack

LD R0, A 		; load A
ADD R6, R6, -1 		; \
STR R0, R6, 0 		; / push A on stack

JSR GCD 

LDR R0, R6, 0 		; load return value from top of stack
ADD R6, R6, 3 		; pop 2 args and return value from stack

ST R0, ANSWER 		; store final value in answer
;;=====================================



HALT

A           .fill 100
B           .fill 66
ANSWER      .fill 0         ; Store ANSWER here
STACK       .fill xF000

GCD

;;=====================================
;; Implement your subroutine here
	ADD R6, R6, -3 	; move R6 up 3
        STR R7, R6, 1 	; store return address
        STR R5, R6, 0 	; store old FP
        ADD R5, R6, -1 	; set new FP
	ADD R6, R6, -2 	; create space for local

	LDR R0, R5, 4 	; load A
	LDR R1, R5, 5	; load B
	STR R0, R6, 0 	; store a
	STR R1, R6, 1	; store b

	ADD R0, R0, 0 	; set CC a
	BRZ BASE1
	ADD R1, R1, 0 	; set CC b
	BRZ BASE2
	NOT R4, R0 	; R4 = !a
	ADD R4, R4, 1	; R4 = -a
	ADD R4, R1, R4 	; R4 = b - a
	BRZ BASE2

	ADD R6, R6, -1	
	STR R7, R6, 0  
	TRAP x80	; UDIV
	LDR R7, R6, 0
	AND R2, R2, 0 	; clear R2
	ADD R2, R2, R1 	; R2 = a % b	

	LDR R0, R5, 5 	; load B
	LDR R1, R5, 4	; load A

	ADD R6, R6, -1	
	STR R7, R6, 0  
	TRAP x80 
	LDR R7, R6, 0
	AND R3, R3, 0 	; clear R3
	ADD R3, R3, R1 	; R3 = b % a	

	LDR R0, R5, 4 	; load A
	LDR R1, R5, 5	; load B

	ADD R4, R4, 0 	; CC to b - a
	BRN FIRST
	ADD R4, R4, 0 	; CC to b - a
	BRP SECOND

FIRST 	ADD R6, R6, -2 	; allocate space
	STR R1, R6, 0 	; store b
	STR R2, R6, 1 	; store a % b
	JSR GCD
	LDR R0, R6, 0 	; load function call
	STR R0, R5, 3 	; RV = gcd(b, a % b)
	BR CLEAN

SECOND  ADD R6, R6, -2 	; allocate space
	STR R0, R6, 0	; store a
	STR R3, R6, 1 	; store b % a
	JSR GCD
	LDR R0, R6, 0 	; load function call
	STR R0, R5, 3 	; RV = gcd(a, b % a)
	BR CLEAN

BASE1 	STR R1, R5, 3 	; RV = b
	BR CLEAN
BASE2	STR R0, R5, 3	; RV = a
	BR CLEAN

CLEAN	ADD R6, R5, 0 	; R6 = R5
	LDR R7, R5, 2 	; load return addr
	LDR R5, R5, 1 	; load old FP	
	ADD R6, R6, 3 	; point at return val 	

	RET
;;=====================================



.end
