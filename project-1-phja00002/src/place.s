.text
.globl place

place:
    mv t0, a0
    mv t1, a2
    slli t1, t1, 1
    add t2, t0, t1

    lhu t3, 0(t2)
    bnez t3, fail

    sh a3, 0(t2)
	li a0 0
    jr ra

fail:
    li a0, 1
    jr ra