package virtualMachine

import org.junit.Assert
import virtualMachine.VirtualMachineFileParser.getInstructionFromRawInput
import virtualMachine.stack.types.Word
import virtualMachine.stack.types.instruction.Instruction
import kotlin.test.Test

class StackArithmetic {

    @Test
    fun testLoadConstants() {
        val line = getInstructionFromRawInput("push constant 2")
        val vmParser = VirtualMachine(listOf(line))

        vmParser.executeVmInstructions()
        val data = vmParser.virtualMemory.pop()
        Assert.assertEquals(2, data.convertToInteger())
    }

    @Test
    fun testSimpleAddition() {
        val instructions: List<Instruction> = listOf(
                getInstructionFromRawInput("push constant 2"),
                getInstructionFromRawInput("push constant 7"),
                getInstructionFromRawInput("add")
        )

        val vmParser = VirtualMachine(instructions)
        vmParser.executeVmInstructions()

        val result: Word = vmParser.virtualMemory.pop()
        Assert.assertEquals(9, result.convertToInteger())
    }

    @Test
    fun testMoreComplexAddition() {
        val instructions: List<Instruction> = listOf(
                getInstructionFromRawInput("push constant 2"),
                getInstructionFromRawInput("push constant 7"),
                getInstructionFromRawInput("add"),
                getInstructionFromRawInput("push constant 2"),
                getInstructionFromRawInput("add")
        )

        val vmParser = VirtualMachine(instructions)
        vmParser.executeVmInstructions()

        val result: Word = vmParser.virtualMemory.pop()
        Assert.assertEquals(11, result.convertToInteger())
    }

    @Test
    fun testExampleWithLogicalInputs() {
        val instructions: List<Instruction> = listOf(
                getInstructionFromRawInput("push constant 2"),
                getInstructionFromRawInput("push constant 7"),
                getInstructionFromRawInput("add"),
                getInstructionFromRawInput("push constant 2"),
                getInstructionFromRawInput("gt")
        )

        val vmParser = VirtualMachine(instructions)
        vmParser.executeVmInstructions()

        val result: Word = vmParser.virtualMemory.pop()

        val trueSixteenBit = Word(true)
        Assert.assertEquals(trueSixteenBit, result)
    }

}
