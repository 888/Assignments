;;===============================
;; CS 2110 Fall 2016
;; Homework 7 - Functions
;; Name: Alan Chiang
;;===============================

.orig x3000
	LD R6, STACK

	LEA R0, STRING 		; load address of string
	ADD R6, R6, -1 		; \ 
	STR R0, R6, 0 		; / push address of string on stack as arg to LENGTH
	
	LD R0, LEN_FUNC 	; load address of LENGTH function
	JSRR R0 			; call LENGTH

	LDR R0, R6, 0 		; load return value
	ADD R6, R6, 2 		; pop return value and arg off stack

	ST R0, STRLEN 		; store length

	LEA R1, STRING 		; \ 
	ADD R2, R1, R0 		; | string + len - 1
	ADD R2, R2, -1 		; / 

	ADD R6, R6, -1
	STR R2, R6, 0 		; push (string + len - 1)
	ADD R6, R6, -1
	STR R1, R6, 0 		; push string address

	LD R0, PAL_FUNC 	; load address of PALINDROME function
	JSRR R0 			; call PALINDROME

	LDR R0, R6, 0 		; load return value
	ADD R6, R6, 3		; pop return value and both args off stack

	ST R0, ANSWER 		; store answer
	HALT

STRLEN   .fill 0
ANSWER 	 .fill 0
STRING   .stringz "rAceCaR"

STACK 	 .fill xF000
LEN_FUNC .fill LENGTH
PAL_FUNC .fill PALINDROME
.end

.orig x6000
LENGTH
	; put LENGTH code here
	ADD R6, R6, -3 	; move R6 up 3
        STR R7, R6, 1 	; store return address
        STR R5, R6, 0 	; store old FP
        ADD R5, R6, -1 	; set new FP
	ADD R6, R6, -1 	; create space for local
	
	LDR R0, R5, 4 	; load string addr 	
	LDR R1, R0, 0 	; load char
	STR R1, R5, 0 	; store local var
	BRZ BASE1	; string end
	ADD R0, R0, 1	; increment addr
	ADD R6, R6, -1 	; allocate space 
	STR R0, R5, -1 	; store str + 1
	JSR LENGTH	; function call 
	LDR R0, R6, 0 	; load function call
	ADD R0, R0, 1	; add 1 to function result
	STR R0, R5, 3 	; RV = 1 + length(str + 1)
	BR CLEAN1	

BASE1 	AND R1, R1, 0 	; R1 = 0 	
	STR R1, R5, 3	; RV = 0

CLEAN1	LDR R7, R5, 2 	; load return addr
	ADD R6, R5, 0 	; R6 = R5
	LDR R5, R6, 1 	; load old FP	
	ADD R6, R6, 3 	; point at return val 

	RET
.end

.orig x7000
PALINDROME
	; put PALINDROME code here
	ADD R6, R6, -3 	 ; move R6 up 3
        STR R7, R6, 1 	 ; store return address
        STR R5, R6, 0 	 ; store old FP
        ADD R5, R6, -1 	 ; set new FP
	ADD R6, R6, -2 	 ; create space for local	
	
	LDR R2, R5, 4 	 ; load start addr
	LDR R3, R5, 5	 ; load end addr
	NOT R4, R3 	 ; negate R3
	ADD R4, R4, 1 	 ; 2's complement
	ADD R4, R4, R2 	 ; R4 = -R3 + R2
	BRZP BASE2
	LDR R2, R2, 0	 ; load start c1
	LDR R3, R3, 0 	 ; load end c2
	STR R2, R5, 0 	 ; store local 1
	STR R3, R5, -1 	 ; store local 2

	AND R4, R4, 0 	 ; clear R4
	ADD R4, R4, -15  ; R4 += -15
	ADD R4, R4, -15  ; R4 += -15
	ADD R4, R4, -15  ; R4 += -15
	ADD R4, R4, -15  ; R4 += -15
	ADD R4, R4, -15  ; R4 += -15
	ADD R4, R4, -15  ; R4 += -15
	ADD R4, R4, -7   ; R4 += -7
	ADD R0, R2, R4   ; R0 = R2 - 97	
	BRZP UP1
DOWN1	ADD R1, R3, R4   ; R1 = R3 - 97
	BRZP UP2
DOWN2	NOT R1, R2 	 ; R1 = !R2
	ADD R1, R1, 1 	 ; R1 = -R2
	ADD R0, R3, R1 	 ; c2 + -c1
	BRZ REC
	AND R0, R0, 0	 ; R0 = 0
	STR R0, R5, 3 	 ; RV = 0
	BR CLEAN2

UP1	ADD R2, R2, -15  ; R2 -= 15
	ADD R2, R2, -15  ; R2 -= 15
	ADD R2, R2, -2   ; R2 -= 2
	STR R2, R5, 0	 ; store local 1
	BR DOWN1

UP2	ADD R3, R3, -15  ; R3 -= 15
	ADD R3, R3, -15  ; R3 -= 15
	ADD R3, R3, -2   ; R3 -= 2
	STR R3, R5, -1 	 ; store local 2
	BR DOWN2

REC 	ADD R6, R6, -2 	 ; allocate space
	LDR R2, R5, 4 	 ; load start addr
	ADD R2, R2, 1 	 ; startaddr++
	LDR R3, R5, 5	 ; load end addr
	ADD R3, R3, -1 	 ; endaddr--
	STR R2, R6, 0    ; store new start
	STR R3, R6, 1	 ; store new end
	JSR PALINDROME 
	LDR R0, R6, 0 	; load function call
	STR R0, R5, 3 	; RV = palindrome(start, end)
	BR CLEAN2

BASE2 	AND R1, R1, 0 	 ; R1 = 0 	
	ADD R1, R1, 1 	 ; R1 = 1	
	STR R1, R5, 3	 ; RV = 1					

CLEAN2	LDR R7, R5, 2 	; load return addr
	ADD R6, R5, 0 	; R6 = R5
	LDR R5, R6, 1 	; load old FP	
	ADD R6, R6, 3 	; point at return val 


	RET
.end
