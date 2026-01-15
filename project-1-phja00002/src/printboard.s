    .data
nl:         .asciiz "\n"
hl:         .asciiz "-----------------------------\n"
el:         .asciiz "|      |      |      |      |\n"
space:      .asciiz " "
vl:         .asciiz "|"

    .text
    .globl printboard
printboard:
    mv t0, a0
    la a1, hl
    li a0, 4
    ecall

    li t1, 0
    li t2, 4
    li t3, 0
    li t4, 0
    li a3, 10
    li a4, 100
    li a5, 1000
    li a6, 0

loop:
    beq a6, t2, endloop
    la a1, el
    li a0, 4
    ecall
    j ins_num
ins_num:
    beq t1, t2, ivnl
    la a1, vl
    li a0, 4
    ecall
    slli t5, t3, 1
    add t6, t0, t5
    lhu a2 0(t6)
    bge a2, a5, num4
    bge a2, a4, num3
    bge a2, a3, num2
    bge a2, t4, num1
num4:
    mv a1, a2
    li a0, 1
    ecall
    la a1, space
    li a0, 4
    ecall
    addi t3, t3, 1
    addi t1, t1, 1
    j ins_num
num3:
    la a1, space
    li a0, 4
    ecall
    mv a1, a2
    li a0, 1
    ecall
    la a1, space
    li a0, 4
    ecall
    addi t3, t3, 1
    addi t1, t1, 1
    j ins_num
num2:
    la a1, space
    li a0, 4
    ecall
    la a1, space
    li a0, 4
    ecall
    la a1, space
    li a0, 4
    ecall
    mv a1, a2
    li a0, 1
    ecall
    la a1, space
    li a0, 4
    ecall
    addi t3, t3, 1
    addi t1, t1, 1
    j ins_num
num1:
    la a1, space
    li a0, 4
    ecall
    la a1, space
    li a0, 4
    ecall
    la a1, space
    li a0, 4
    ecall
    la a1, space
    li a0, 4
    ecall
    mv a1, a2
    li a0, 1
    ecall
    la a1, space
    li a0, 4
    ecall
    addi t3, t3, 1
    addi t1, t1, 1
    j ins_num
ivnl:
    la a1, vl
    li a0, 4
    ecall
    la a1, nl
    li a0, 4
    ecall
    la a1, el
    li a0, 4
    ecall
    la a1, hl
    li a0, 4
    ecall
    li t1 0
    addi a6, a6, 1
    j loop
endloop:
    jr ra