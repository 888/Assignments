; ==========================
; CS2110 Lab 13 Spring 2016
; Name: Alan Chiang
; ==========================

;@plugin filename=lc3_udiv vector=x80

; Main
; Do not edit this function!

.orig x3000

	; Initialize stack
	LD R6, STACK

	; Call digisum(n)
	LD R0, N
	ADD R6, R6, -1
	STR R0, R6, 0
	JSR DIGISUM

	; Pop return value and arg off stack
	LDR R0, R6, 0
	ADD R6, R6, 2

	; Save the answer
	ST R0, ANSWER

	HALT

STACK
	.fill xF000
N
	.fill 45
ANSWER
	.blkw 1

; To call UDIV, use TRAP x80
; Preconditions:
;    R0 = X
;    R1 = Y
; Postconditions:
;    R0 = X / Y
;    R1 = X % Y

DIGISUM ADD R6, R6, -5
	STR R5, R6, 2
	ADD R5, R6, 1
	STR R7, R5, 2

	LDR R0, R5, 4
	AND R1, R1, 0
	ADD R1, R1, 10
	TRAP x80
	STR R0, R5, 0
	STR R1, R5, -1

	LDR R2, R5, 4
	BRZ BAS
	ADD R6, R6, -1
	STR R0, R6, 0
	JSR DIGISUM
	LDR R0, R6, 0
	LDR R1, R5, -1
	ADD R0, R0, R1
	STR R0, R5, 3
	BRNP REC

BAS	AND R2, R2, 0	
	STR R2, R5, 3


REC	STR R0, R5, 3
	LDR R7, R5, 2
	ADD R6, R5, 3
	LDR R5, R5, 1	
		
	RET

.end
