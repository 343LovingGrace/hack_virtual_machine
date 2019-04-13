package virtualMachine

import org.junit.Assert
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import kotlin.test.Test

class StackArithmetic {

    @Test
    fun testLoadConstants() {
        val line = "push constant 2"
        val vmParser = VMParser()
        vmParser.readLine(line)
        val currentStack: InstructionStack = vmParser.getInstructionStack()
        val data = currentStack.pop()
        Assert.assertEquals(2, data.convertToInteger())
    }

    @Test
    fun testSimpleAddition() {
        val vmParser = VMParser()
        val instructions: List<String> = listOf(
                "push constant 2",
                "push constant 7",
                "add"
        )
        instructions.forEach {
            vmParser.readLine(it)
        }
        val instructionStack: InstructionStack = vmParser.getInstructionStack()
        val result: SixteenBit = instructionStack.pop()
        Assert.assertEquals(9, result.convertToInteger())
    }

    @Test
    fun testMoreComplexAddition() {
        val vmParser = VMParser()
        val instructions: List<String> = listOf(
                "push constant 2",
                "push constant 7",
                "add",
                "push constant 2",
                "add"
        )
        instructions.forEach {
            vmParser.readLine(it)
        }
        val instructionStack: InstructionStack = vmParser.getInstructionStack()
        val result: SixteenBit = instructionStack.pop()
        Assert.assertEquals(11, result.convertToInteger())
    }

    @Test
    fun testExampleWithLogicalInputs() {
        val vmParser = VMParser()
        val instructions: List<String> = listOf(
                "push constant 2",
                "push constant 7",
                "add",
                "push constant 2",
                "gt"
        )
        instructions.forEach {
            vmParser.readLine(it)
        }
        val instructionStack: InstructionStack = vmParser.getInstructionStack()
        val result: SixteenBit = instructionStack.pop()

        val trueSixteenBit = SixteenBit(true)
        Assert.assertEquals(trueSixteenBit, result)
    }

    @Test
    fun testLoadFile() {
        val vmParser : VMParser = App().processInputFile("./src/test/StackArithmetic/StackTest/StackTest.vm")
        val instructionStack: InstructionStack = vmParser.getInstructionStack()
        val result: SixteenBit = instructionStack.pop()
        Assert.assertEquals(65453, result.convertToInteger())
    }

}
