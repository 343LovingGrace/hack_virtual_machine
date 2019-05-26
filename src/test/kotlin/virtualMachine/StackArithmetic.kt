package virtualMachine

import org.junit.Assert
import virtualMachine.ProcessVirtualMachineFile.getInstructionFromRawInput
import virtualMachine.stack.types.Word
import virtualMachine.stack.types.instruction.Instruction
import virtualMachine.stack.vm_instruction_parsing.VmParser
import kotlin.test.Test

class StackArithmetic {

    @Test
    fun testLoadConstants() {
        val line = getInstructionFromRawInput("push constant 2")
        val vmParser = VmParser(listOf(line))

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

        val vmParser = VmParser(instructions)
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

        val vmParser = VmParser(instructions)
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

        val vmParser = VmParser(instructions)
        vmParser.executeVmInstructions()

        val result: Word = vmParser.virtualMemory.pop()

        val trueSixteenBit = Word(true)
        Assert.assertEquals(trueSixteenBit, result)
    }

}
