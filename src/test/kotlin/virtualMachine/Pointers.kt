package virtualMachine

import org.junit.Test
import virtualMachine.VirtualMachineFileParser.getInstructionFromRawInput
import virtualMachine.types.Word
import virtualMachine.types.instruction.Instruction
import virtualMachine.memory.MemorySegments
import kotlin.test.assertEquals

class Pointers {

    @Test
    fun canSetPointer() {

        val instructions: List<Instruction> = listOf(
                getInstructionFromRawInput("push constant 1450"),
                getInstructionFromRawInput("pop pointer 0")
        )

        val virtualMachine = VirtualMachine(instructions)
        virtualMachine.executeVmInstructions()

        val storedInPointer : Word = virtualMachine.getFromMemory(0, MemorySegments.POINTER)

        assertEquals(1450, storedInPointer.convertToInteger())
    }

}