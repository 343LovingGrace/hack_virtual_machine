package virtualMachine

import org.junit.Test
import virtualMachine.ProcessVirtualMachineFile.getInstructionFromRawInput
import virtualMachine.stack.datawrappers.Word
import virtualMachine.stack.datawrappers.instruction.Instruction
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {

        val instructions: List<Instruction> = listOf(
                getInstructionFromRawInput("push constant 1450"),
                getInstructionFromRawInput("pop pointer 0")
        )

        val vmProcessor = VMInstructionParser(instructions)
        vmProcessor.executeVmInstructions()

        val memory = vmProcessor.virtualMemory
        val storedInPointer : Word = memory.getFromMemory(0, MemorySegments.POINTER)

        assertEquals(1450, storedInPointer.convertToInteger())
    }

}