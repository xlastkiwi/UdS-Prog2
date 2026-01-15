.text
.globl check_victory

check_victory:
    # Save input parameters
    mv t0, a0
    mv t1, a1

    li t2, 2048
    li t3, 0

loop:
    beq t3, t1, not_found

    lhu t4, 0(t0)
    beq t4, t2, found

    addi t0, t0, 2
    addi t3, t3, 1
    j loop

found:
    li a0, 1
    jr ra

not_found:
    li a0 0
	jr ra
