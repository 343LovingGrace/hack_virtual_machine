package virtualMachine

import org.junit.Assert
import virtualMachine.stack.datawrappers.Word
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser
import kotlin.test.Test

class StackArithmetic {

    @Test
    fun testLoadConstants() {
        val line = "push constant 2"
        val vmParser = VMInstructionParser()
        vmParser.processInstruction(line)
        val data = vmParser.getVirtualMemory().popStack()
        Assert.assertEquals(2, data.convertToInteger())
    }

    @Test
    fun testSimpleAddition() {
        val vmParser = VMInstructionParser()
        val instructions: List<String> = listOf(
                "push constant 2",
                "push constant 7",
                "add"
        )
        instructions.forEach {
            vmParser.processInstruction(it)
        }
        val result: Word = vmParser.getVirtualMemory().popStack()
        Assert.assertEquals(9, result.convertToInteger())
    }

    @Test
    fun testMoreComplexAddition() {
        val vmParser = VMInstructionParser()
        val instructions: List<String> = listOf(
                "push constant 2",
                "push constant 7",
                "add",
                "push constant 2",
                "add"
        )
        instructions.forEach {
            vmParser.processInstruction(it)
        }
        val result: Word = vmParser.getVirtualMemory().popStack()
        Assert.assertEquals(11, result.convertToInteger())
    }

    @Test
    fun testExampleWithLogicalInputs() {
        val vmParser = VMInstructionParser()
        val instructions: List<String> = listOf(
                "push constant 2",
                "push constant 7",
                "add",
                "push constant 2",
                "gt"
        )
        instructions.forEach {
            vmParser.processInstruction(it)
        }
        val result: Word = vmParser.getVirtualMemory().popStack()

        val trueSixteenBit = Word(true)
        Assert.assertEquals(trueSixteenBit, result)
    }

    @Test
    fun testLoadFile() {
        val vmParser : VMInstructionParser = ProcessVirtualMachineFile().processVmFile("./src/test/StackArithmetic/StackTest/StackTest.vm", null)
        val result: Word = vmParser.getVirtualMemory().popStack()
        //TODO check this test
        Assert.assertEquals(32685, result.convertToInteger())
    }

}
