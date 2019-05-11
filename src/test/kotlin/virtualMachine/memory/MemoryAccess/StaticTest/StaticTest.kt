package virtualMachine.memory.MemoryAccess.StaticTest

import org.junit.Assert
import org.junit.Test
import virtualMachine.ProcessVirtualMachineFile
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.VmParser

class StaticTest {

    @Test
    fun testScript() {
        val vmParser : VmParser = ProcessVirtualMachineFile()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/memory/MemoryAccess/StaticTest/StaticTest.vm",
                        null)

        Assert.assertEquals(1110, vmParser.virtualMemory.popStack()
                .convertToInteger())

        Assert.assertEquals(888, vmParser.virtualMemory
                .getFromMemory(8, MemorySegments.STATIC)
                .convertToInteger())
        Assert.assertEquals(333, vmParser.virtualMemory
                .getFromMemory(3, MemorySegments.STATIC)
                .convertToInteger())
        Assert.assertEquals(111, vmParser.virtualMemory
                .getFromMemory(1, MemorySegments.STATIC)
                .convertToInteger())
    }
}