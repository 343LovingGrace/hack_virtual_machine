package virtualMachine

import org.junit.Test
import virtualMachine.ProcessVirtualMachineFile.getInstructionFromRawInput
import virtualMachine.stack.types.Word
import virtualMachine.stack.types.instruction.Instruction
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.VmParser
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {

        val instructions: List<Instruction> = listOf(
                getInstructionFromRawInput("push constant 1450"),
                getInstructionFromRawInput("pop pointer 0")
        )

        val vmProcessor = VmParser(instructions)
        vmProcessor.executeVmInstructions()

        val memory = vmProcessor.virtualMemory
        val storedInPointer : Word = memory.getFromMemory(0, MemorySegments.POINTER)

        assertEquals(1450, storedInPointer.convertToInteger())
    }

}