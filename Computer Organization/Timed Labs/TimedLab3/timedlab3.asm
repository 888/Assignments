;;=========================
;;     Timed Lab 3
;; Name: Alan Chiang
;;=========================

;; DESCRIPTION: Write a program that returns the length of the array (not inclusive of -1),
;; the minimum value in the array, the maximum value in the array,
;; and the difference between the maximum and minimum values in the array.

.orig x3000

	;; ==================================
	
	;; CODE GOES IN HERE
	
	AND R1, R1, 0 	; max = 0
	AND R2, R2, 0 	; min = 0
	AND R3, R3, 0 	; diff = 0
	AND R4, R4, 0 	; length = 0
	LD R5, ARRAY 	; load array 

WIL	LDR R6, R5, 0  	; load next element
	BRN FIN 	; if array is done
	ADD R4, R4, 0 	; set CC to length	
	BRZ FIR 		
	LDR R6, R5, 0 	; reload current	
	NOT R6, R6	; !current
	ADD R6, R6, 1	; increment 2's comp
	ADD R7, R6, R2 	; min + -current	
	BRP SEC
	LDR R6, R5, 0 	; reload current
	NOT R6, R6	; !current
	ADD R6, R6, 1	; increment 2's comp
	ADD R7, R6, R1 	; max + -current
	BRN THR 	

	ADD R4, R4, 1	; increment length 
	ADD R5, R5, 1 	; increment element
	BR WIL	


FIR	LDR R1, R5, 0 	; max = current
 	LDR R2, R5, 0 	; min = current
	ADD R4, R4, 1	; increment length 
	ADD R5, R5, 1 	; increment element
	BR WIL

SEC 	LDR R2, R5, 0 	; min = current
	ADD R4, R4, 1	; increment length 
	ADD R5, R5, 1 	; increment element
	BR WIL

THR 	LDR R1, R5, 0 	; max = current
	ADD R4, R4, 1	; increment length 
	ADD R5, R5, 1 	; increment element
	BR WIL

FIN	ADD R6, R6, 0	; R6 = 0
	ADD R6, R2, 0 	; R6 = R2
	NOT R6, R6 	; !min
	ADD R6, R6, 1 	; 2's complement
	ADD R3, R1, R6 	; diff = max + -min
	ST R1, MAX_VAL	
	ST R2, MIN_VAL
	ST R3, MAX_DIFF
	ST R4, LENGTH 

	HALT

	;; ==================================

	ARRAY       .fill x6000
	LENGTH      .fill 0
	MIN_VAL     .fill 0
	MAX_VAL     .fill 0
	MAX_DIFF    .fill 0
.end

;; A test array. Feel free to modify for more testing.
.orig x6000
	.fill 9
	.fill 4
	.fill 5
	.fill 8
	.fill 10
	.fill 120
	.fill 11
	.fill 100
	.fill 3
	.fill 25
	.fill 3
	.fill -1
.end
