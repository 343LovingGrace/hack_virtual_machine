package virtualMachine

import org.junit.Test
import virtualMachine.stack.InstructionStack
import virtualMachine.stackArithmetic.StackProcessor
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {
        val line1 = "push constant 1450"
        val line2 = "pop pointer 0"

        val vmProcessor = StackProcessor()
        val stack = InstructionStack()
        vmProcessor.processInstruction(stack, line1)
        vmProcessor.processInstruction(stack, line2)

        assertEquals(1450, vmProcessor.getThis())
    }

}