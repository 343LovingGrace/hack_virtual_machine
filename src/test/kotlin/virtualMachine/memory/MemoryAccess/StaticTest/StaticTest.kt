package virtualMachine.memory.MemoryAccess.StaticTest

import org.junit.Assert
import org.junit.Test
import virtualMachine.VirtualMachineFileParser
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.VirtualMachine

class StaticTest {

    @Test
    fun testScript() {
        val virtualMachine : VirtualMachine = VirtualMachineFileParser()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/memory/MemoryAccess/StaticTest/StaticTest.vm",
                        null)

        Assert.assertEquals(1110, virtualMachine.pop()
                .convertToInteger())

        Assert.assertEquals(888, virtualMachine
                .getFromMemory(8, MemorySegments.STATIC)
                .convertToInteger())
        Assert.assertEquals(333, virtualMachine
                .getFromMemory(3, MemorySegments.STATIC)
                .convertToInteger())
        Assert.assertEquals(111, virtualMachine
                .getFromMemory(1, MemorySegments.STATIC)
                .convertToInteger())
    }
}