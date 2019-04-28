package virtualMachine.memory.MemoryAccess.StaticTest

import org.junit.Assert
import org.junit.Test
import virtualMachine.ReadInputFile
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser

class StaticTest {

    @Test
    fun testScript() {
        val vmParser : VMInstructionParser = ReadInputFile()
                .processInputFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/memory/MemoryAccess/StaticTest/StaticTest.vm",
                        VMInstructionParser())

        Assert.assertEquals(1110, vmParser.getVirtualMemory().popStack()
                .convertToInteger())

        Assert.assertEquals(888, vmParser.getVirtualMemory()
                .getFromMemory(8, MemorySegments.STATIC)
                .convertToInteger())
        Assert.assertEquals(333, vmParser.getVirtualMemory()
                .getFromMemory(3, MemorySegments.STATIC)
                .convertToInteger())
        Assert.assertEquals(111, vmParser.getVirtualMemory()
                .getFromMemory(1, MemorySegments.STATIC)
                .convertToInteger())
    }
}