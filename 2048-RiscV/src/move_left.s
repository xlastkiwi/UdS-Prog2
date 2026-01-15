.text
.globl move_left

.import "../src/move_one.s"

move_left:
    addi sp sp -16
    sw ra 12(sp)
    sw s0 8(sp)
    sw s1 4(sp)
    mv s0 a0
    mv s1 a1

loop:
    mv a0 s0
    mv a1 s1
    jal move_one
    beqz a0 done
    j loop

done:
    lw s1 4(sp)
    lw s0 8(sp)
    lw ra 12(sp)
    addi sp sp 16
    jr ra