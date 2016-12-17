;;===============================
;; Name: Alan Chiang
;;===============================

;; Write a program that sorts an array location at address ARRAY of length
;; LENGTH in ascending order.

.orig x3000

;; CODE GOES HERE! :D

    LD R1, ARRAY        ;; set R1 to array
    LD R2, LENGTH       ;; set R2 to length

FIR AND R3, R3, 0       ;; set isSorted to 0
    ADD R3, R3, 1       ;; set isSorted to 1
    AND R5, R5, 0       ;; set second counter to 0
    ADD R5, R5, R2      ;; set second counter to Length - k

    AND R6, R6, 0
SEC ADD R6, R6, 1
    ADD R7, R6, R1      ;; set temp to length + i
    LDR R4, R7, 0       ;; R4  = array[i]
    LDR R0, R7, -1      ;; R0  = array[i-1]

    NOT R0, R0         ;; negate R0
    ADD R0, R0, 1      ;; get 2's complement negative
    ADD R0, R0, R4     ;; R0 = array[i] - array[i-1]
    BRZP SWP
    			;; swaps values regardless, how to fix?
    LDR R0, R7, -1
    STR R0, R7, 0       ;; array[i] = array[i-1]
    STR R4, R7, -1      ;; array[i-1] = array[i]
    AND R3, R3, 0      ;; isSorted = 0

SWP ADD R5, R5, -1      ;; decrement second counter
    BRP SEC

    ADD R3, R3, 0       ;; set CC
    BRP DON             ;; break
    ADD R2, R2, -1      ;; decrement first counter
    BRP FIR

DON HALT

ARRAY   .fill x6000
LENGTH  .fill 12
.end

;; This array should be sorted when finished
.orig x6000
.fill 28
.fill -50
.fill 7
.fill 2
.fill 42
.fill 4
.fill 15
.fill -8
.fill 56
.fill 101
.fill -96
.fill 177
.end
