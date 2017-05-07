!============================================================
! CS-2200 Homework 1
!
! Please do not change main's functionality, 
! except to change the argument for factorial or to meet your 
! calling convention
!============================================================

main:           la $sp, stack		! load ADDRESS of stack label into $sp
		lw $sp, 0x0($sp) 
                add $zero, $zero, $zero ! FIXME: load the actual value of the 
                                        ! stack (defined in the label below) 
                                        ! into $sp

		la $at, factorial	! load address of factorial label into $at
		addi $a0, $zero, 5 	! $a0 = 5, the number to factorialize
		jalr $at, $ra		! jump to factorial, set $ra to return addr
		halt				! when we return, just halt

factorial:	! change me to your factorial implementation
			beq $a0, $zero, end 	!if num == 0 
			addi $s0, $a0, 0 	!store arg in sreg
			addi $a0, $a0, -1	!decrement num in arg
			beq $zero, $zero, dummy !skip end block

end: 			addi $v0, $zero, 1
 			lw $ra, 0x1($fp)
			lw $sp, 0x2($fp)
			lw $fp, 0x0($fp)
			jalr $ra, $zero

dummy:			addi $sp, $sp, -3	!decrement sp thrice
			sw $s0, 0x3($sp) 	!store argument on stack
			sw $ra, 0x2($sp)	!store ra on top of stack
			sw $fp, 0x1($sp)	!store fp on top of stack
			addi $fp, $sp, 1	!set new fp	
			la $at, factorial 	!load address of factorial
			jalr $at, $ra 		!call factorial(num - 1)

			lw $a0, 0x2($fp) 	!load argument into a0
			addi $a1, $v0, 0 	!a1 = return val of fact(n-1)			
			la $at, multiply 	!load address of mult into $at
			jalr $at, $ra

			beq $zero, $zero, clean

clean: 			lw $ra, 0x1($fp)
			lw $sp, 0x2($fp)
			lw $fp, 0x0($fp)
			jalr $ra, $zero





!save ra
multiply:		addi $t0, $zero, 0	!$t0 = 0	
loop:			beq $a1, $zero, done    !if counter == 0, done
			add $t0, $t0, $a0 	!$t0 += $a0
			addi $a1, $a1, -1	!decrement  
			beq $zero, $zero, loop	!add again

done: 			add $v0, $t0, $zero	!$v0 = product
			jalr $ra, $zero		!return to factorial

stack:	.word 0x4000		! the stack begins here (for example, that is)


!int multiply(a, b)
!{
!	int sum = 0;
!	while (b > 0)
!	{
!		sum += a;
!		b--;
!	} 
!	return sum;

!int factorial(num)
!{
!	if (num == 0)
!	{
!		return 1;
!	} else {
!		return multiply(num, factorial(num -1));
!	}
!}
