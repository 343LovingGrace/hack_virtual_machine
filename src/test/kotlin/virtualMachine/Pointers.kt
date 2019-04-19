package virtualMachine

import org.junit.Test
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {
        val line1 = "push constant 1450"
        val line2 = "pop pointer 0"

        val vmProcessor = VMInstructionParser()
        vmProcessor.processInstruction(line1)
        vmProcessor.processInstruction(line2)

        val memory = vmProcessor.getVirtualMemory()
        val storedInPointer : SixteenBit = memory.getFromMemory(0, MemorySegments.POINTER)

        assertEquals(1450, storedInPointer.convertToInteger())
    }

}