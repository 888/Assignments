;;======================================================================
;; CS2110                         Lab #12                      Fall 2016
;;======================================================================
;; Filename: sumtorial.axm
;; Author: Alan Chiang
;; Date: 2016-05-10
;; Assignment: Lab 12
;; Description: An assembly program to calculate sumtorial
;;======================================================================

;;Pseudocode:
;;-----------

; Sumtorial(n)
; {
;     int i;
;     int sum = 0;
;     for(i = 0; i < n; i++)
;     {
;        sum += n
;        n = n-1;
;     }
;     return sum;
; }

.orig x3000

;;Your Code Here

    LD R2, NUM
    AND R3, R3, 0
LOP ADD R3, R3, R2
    ADD R2, R2, -1
    BRP LOP
    ST R3, NUM

HALT

NUM     .FILL     #5
.end
