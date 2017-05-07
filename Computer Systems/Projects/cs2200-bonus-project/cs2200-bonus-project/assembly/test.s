        ! Increment $a0 from 0 to 15
        ADD $a0, $zero, $zero
        ADDI $a2, $zero, 15
register:
        ADDI $a0, $a0, 1
        ADDI $a2, $a2, -1
        BEQ $a2, $zero, two
        BEQ $zero, $zero, register

        ! Increment memory address 0x0 from 0 to 15
two:    ADD $a1, $zero, $zero

memory:
        STR $a1, 0($a1)
        ADDI $a1, $a1, 1
        ADDI $a0, $a0, -1 ! Loop decrements $a0 from 15 to 0

        BEQ $a0, $zero, done
        BEQ $zero, $zero, memory
done:   STR $a1, 0($a1)
        ADDI $a0, $a0, 15 ! Reset $a0 to 15 after decrementing
        HALT