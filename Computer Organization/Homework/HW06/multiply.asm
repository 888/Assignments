;;===============================
;; Name: Alan Chiang
;;===============================

;; Write a program that stores the value of U * V in ANSWER.

.orig x3000

;; CODE GOES HERE! :D

    AND R3, R3, 0   ;; set negative in R3 to 0
    AND R0, R0, 0   ;; set output in R0 to 0
    LD R2, V        ;; load V into R2
    LD R1, U        ;; load U into R1

    BRZP NEG        ;; check if U is negative
    ADD R3, R3, 1   ;; set negative to 1
    NOT R1, R1      ;; negate U
    ADD R1, R1, 1   ;; add 1 to U to get -U in 2's complement

NEG ADD R0, R0, R2   ;; add V to output
    ADD R1, R1, -1  ;; decrement U
    BRP NEG

    ADD R0, R0, 0   ;; add 0 to set CC
    BRZP MAG        ;; check if output is negative
    NOT R0, R0      ;; negate output
    ADD R0, R0, 1   ;; add 1 to output to get -output in 2's complement

MAG ST R0, ANSWER   ;; store output in answer
    HALT            ;; stops the PC

U       .fill 6
V       .fill -8
ANSWER  .fill 0
.end
