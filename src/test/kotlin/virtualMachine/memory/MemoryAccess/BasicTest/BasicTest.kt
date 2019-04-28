package virtualMachine.memory.MemoryAccess.BasicTest

import org.junit.Assert
import org.junit.Test
import virtualMachine.ReadInputFile
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser

class BasicTest {

    @Test
    fun testScript() {
        val THIS_OFFSET = 3000
        val THAT_OFFSET = 3010

        val vmInstructionParser = VMInstructionParser()
        vmInstructionParser.getVirtualMemory().loadIntoMemory(SixteenBit(THIS_OFFSET), 0, MemorySegments.POINTER)
        vmInstructionParser.getVirtualMemory().loadIntoMemory(SixteenBit(THAT_OFFSET), 1, MemorySegments.POINTER)

        val vmParser : VMInstructionParser = ReadInputFile()
                .processInputFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/memory/MemoryAccess/BasicTest/BasicTest.vm",
                        vmInstructionParser)

        val memory = vmParser.getVirtualMemory()

        Assert.assertEquals(10, memory.getFromMemory(0, MemorySegments.LOCAL).convertToInteger())

        Assert.assertEquals(21, memory.getFromMemory(1, MemorySegments.ARGUMENT).convertToInteger())
        Assert.assertEquals(22, memory.getFromMemory(2, MemorySegments.ARGUMENT).convertToInteger())

        Assert.assertEquals(42, memory.getFromMemory(2, MemorySegments.THAT).convertToInteger())
        Assert.assertEquals(45, memory.getFromMemory(5, MemorySegments.THAT).convertToInteger())

        Assert.assertEquals(36, memory.getFromMemory(6, MemorySegments.THIS).convertToInteger())
        Assert.assertEquals(42, memory.getFromMemory(12, MemorySegments.THIS).convertToInteger())
        Assert.assertEquals(45, memory.getFromMemory(15, MemorySegments.THIS).convertToInteger())

//        Assert.assertEquals(472, memory.getFromMemory(0, MemorySegments.GLOBAL_STACK).convertToInteger())
//        Assert.assertEquals(510, memory.getFromMemory(1, MemorySegments.GLOBAL_STACK).convertToInteger())

        Assert.assertEquals(472, vmParser.getVirtualMemory().popStack()
                .convertToInteger())

    }
}