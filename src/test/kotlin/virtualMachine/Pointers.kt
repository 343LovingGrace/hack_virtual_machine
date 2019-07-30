package virtualMachine

import org.junit.Test
import virtualMachine.VirtualMachineFileParser.getInstructionFromRawInput
import virtualMachine.stack.types.Word
import virtualMachine.stack.types.instruction.Instruction
import virtualMachine.stack.memory.MemorySegments
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {

        val instructions: List<Instruction> = listOf(
                getInstructionFromRawInput("push constant 1450"),
                getInstructionFromRawInput("pop pointer 0")
        )

        val vmProcessor = VirtualMachine(instructions)
        vmProcessor.executeVmInstructions()

        val memory = vmProcessor.virtualMemory
        val storedInPointer : Word = memory.getFromMemory(0, MemorySegments.POINTER)

        assertEquals(1450, storedInPointer.convertToInteger())
    }

}