.text
.globl merge

merge:
    mv t0, a0
    mv t1, a1

    li t2, 0

m_loop:
    addi t3, t1, -1
    bge t2, t3 m_done

    slli t4, t2, 2
    add t5, t0, t4
    lw t5, 0(t5)

    addi t6, t2, 1
    slli t6, t6, 2
    add t6, t0, t6
    lw t6, 0(t6)

    lhu t3, 0(t5)
    lhu t4, 0(t6)

    beqz t3, m_cont
    beqz t4, m_cont
    bne t3, t4, m_cont

    add t3, t3, t4
    sh t3, 0(t5)

    sh x0, 0(t6)

m_cont:
    addi t2, t2, 1
    j m_loop

m_done:
    jr ra