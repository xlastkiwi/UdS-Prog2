.text
.globl move_check

move_check:
	mv t0, a0
	mv t1, a1
	li t2, 1
	li t6, 2

loop:
	bge t2, t1, done

	li t4, 2
	mul t5, t2, t4
	addi t5, t5, -2
	add t3, t0, t5
	lhu t3, 0(t3)
	lhu t3, 0(t3)

	li t4, 2
	mul t5, t2, t4
	add t4, t0, t5
	lhu t4, 0(t4)
	lhu t4, 0(t4)

	beqz t3, check_if_nonzero
	j check_merge

check_if_nonzero:
	beqz t4, next
	li a0, 1
	jr ra

check_merge:
	beq t3, t4, found
	j next

found:
	li a0, 1
	jr ra

next:
	addi t2, t2, 1
	j loop

done:
	li a0 0
	jr ra
