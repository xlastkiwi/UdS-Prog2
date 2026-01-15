.text
.globl complete_move

.import "../src/move_left.s"
.import "../src/merge.s"


complete_move:
    addi sp sp -16
    sw ra 12(sp)
    sw s0 8(sp)
    sw s1 4(sp)

    mv s0, a0
    mv s1, a1

    mv a0, s0
    mv a1, s1

    jal move_left

    mv a0, s0
    mv a1, s1

    jal merge

    mv a0, s0
    mv a1, s1

    jal move_left

    lw s1 4(sp)
    lw s0 8(sp)
    lw ra 12(sp)
    addi sp sp 16

    jr ra
