package virtualMachine

import org.junit.Assert
import virtualMachine.stack.datawrappers.StackPermittedDataType
import virtualMachine.stack.datawrappers.getValue
import virtualMachine.stack.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.bitWiseNot16Bit
import virtualMachine.stack.vm_instruction_parsing.padStringTo16bit
import kotlin.test.Test

class StackArithmetic {

    @Test
    fun testLoadConstants() {
        val line = "push constant 2"
        val vmParser = VMParser()
        vmParser.readLine(line)
        val currentStack: InstructionStack = vmParser.getInstructionStack()
        val data = currentStack.popHead()
        val res = getValue(data)
        Assert.assertEquals(2, res)
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
        val result: StackPermittedDataType = instructionStack.popHead()
        val actualData = getValue(result)
        Assert.assertEquals(9, actualData)
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
        val result: StackPermittedDataType = instructionStack.popHead()
        val actualData = getValue(result)
        Assert.assertEquals(11, actualData)
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
        val result: StackPermittedDataType = instructionStack.popHead()
        val actualData = getValue(result)
        Assert.assertEquals(true, actualData)
    }

    @Test
    fun testLoadFile() {
        val vmParser : VMParser = App().processInputFile("./src/test/StackArithmetic/StackTest/StackTest.vm")
        val instructionStack: InstructionStack = vmParser.getInstructionStack()
        val result: StackPermittedDataType = instructionStack.popHead()
        val actualData = getValue(result)
        Assert.assertEquals(65453, actualData)
    }

    @Test
    fun testBitWiseOps() {
        Assert.assertEquals(29, 12 or 25)
        Assert.assertEquals(8, 12 and 25)
        Assert.assertEquals(0, 0 and 1)
        Assert.assertEquals(1, 1 and 1)
        Assert.assertEquals(1, 1 or 0)
        val not112 = bitWiseNot16Bit(padStringTo16bit(Integer.toBinaryString(112)))
        Assert.assertEquals(65423, not112)
    }

}
