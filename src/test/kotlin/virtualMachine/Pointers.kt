package virtualMachine

import org.junit.Test
import virtualMachine.ProcessVirtualMachineFile.getInstructionFromRawInput
import virtualMachine.stack.datawrappers.Word
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {
        val line1 = "push constant 1450"
        val line2 = "pop pointer 0"

        val vmProcessor = VMInstructionParser()
        vmProcessor.processInstruction(getInstructionFromRawInput(line1))
        vmProcessor.processInstruction(getInstructionFromRawInput(line2))

        val memory = vmProcessor.getVirtualMemory()
        val storedInPointer : Word = memory.getFromMemory(0, MemorySegments.POINTER)

        assertEquals(1450, storedInPointer.convertToInteger())
    }

}