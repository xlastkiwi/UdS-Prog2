.text
.globl move_one

move_one:
	li t0, 0
	mv t1, a0
	mv t2, a1

	li t3, 1

mo_loop:
	bge t3, t2, mo_done

	slli t4, t3, 2
	add t5, t1, t4
	lw t6, 0(t5)

	lhu t4, 0(t6)

	beqz t4, mo_cont

	addi t5, t5, -4
	lw t6, 0(t5)
	
	lhu t5, 0(t6)

	bnez t5, mo_cont

	sh t4, 0(t6)
	
	slli t4, t3, 2
	add t5, t1, t4
	lw t6, 0(t5)
	sh x0, 0(t6)

	li t0, 1

mo_cont:
	addi t3, t3, 1
	j mo_loop

mo_done:
	mv a0, t0
	jr ra