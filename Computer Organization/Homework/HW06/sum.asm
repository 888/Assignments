;;===============================
;; Name: Alan Chiang
;;===============================

;; Write a program that sums up the array, you should then check if your sum is
;; negative. If your result is negative then return the absolute value of it. So if
;; your sum happens to be -42, you should return 42. If your sum is 42 then return
;; that.

.orig x3000

;; CODE GOES HERE! :D

    AND R1, R1, 0       ;; set sum in R1 to 0
    AND R2, R2, 0       ;; set input in R2 to 0
    LD R5, ARRAY        ;; load the start of the array
    LDR R2, R5, 0       ;; load the first element of the array
    LD R3, LENGTH       ;; set counter in R2 to length

MOR ADD R1, R1, R2      ;; add sum and latest input
    ADD R5, R5, 1       ;; increments starting point
    LDR R2, R5, 0       ;; loads the next element of array
    ADD R3, R3, -1      ;; decrements counter
    BRP MOR             ;; loop

    ADD R1, R1, 0       ;; sets CC
    BRZP NEG            ;; checks if sum is negative
    NOT R1, R1          ;; negate sum
    ADD R1, R1, 1       ;; add 1 to sum to get abs(sum) in 2's complement

NEG ST R1, ANSWER       ;; stores sum in ANSWER
    HALT                ;; stops the PC

ARRAY   .fill x6000
LENGTH  .fill 10
ANSWER	.fill 0		; The answer should have the abs(sum) when finished
.end

.orig x6000
.fill 10
.fill 9
.fill 7
.fill 0
.fill -3
.fill 11
.fill 9
.fill -9
.fill 2
.fill 9
.end
