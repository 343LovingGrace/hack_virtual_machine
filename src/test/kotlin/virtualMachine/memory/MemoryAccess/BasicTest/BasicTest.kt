package virtualMachine.memory.MemoryAccess.BasicTest

import org.junit.Assert
import org.junit.Test
import virtualMachine.VirtualMachineFileParser
import virtualMachine.VirtualMachineFileParser.getInstructionFromRawInput
import virtualMachine.memory.MemorySegments
import virtualMachine.VirtualMachine

class BasicTest {

    @Test
    fun testScript() {

        val virtualMachine : VirtualMachine = VirtualMachineFileParser()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/memory/MemoryAccess/BasicTest/BasicTest.vm",
                        listOf(getInstructionFromRawInput("push constant 3000"),
                                getInstructionFromRawInput("pop pointer 0"),
                                getInstructionFromRawInput("push constant 3010"),
                                getInstructionFromRawInput("pop pointer 1")))

        Assert.assertEquals(10, virtualMachine.getFromMemory(0, MemorySegments.LOCAL).convertToInteger())

        Assert.assertEquals(21, virtualMachine.getFromMemory(1, MemorySegments.ARGUMENT).convertToInteger())
        Assert.assertEquals(22, virtualMachine.getFromMemory(2, MemorySegments.ARGUMENT).convertToInteger())

        Assert.assertEquals(42, virtualMachine.getFromMemory(2, MemorySegments.THAT).convertToInteger())
        Assert.assertEquals(45, virtualMachine.getFromMemory(5, MemorySegments.THAT).convertToInteger())

        Assert.assertEquals(36, virtualMachine.getFromMemory(6, MemorySegments.THIS).convertToInteger())
        Assert.assertEquals(42, virtualMachine.getFromMemory(12, MemorySegments.THIS).convertToInteger())
        Assert.assertEquals(45, virtualMachine.getFromMemory(15, MemorySegments.THIS).convertToInteger())

        Assert.assertEquals(472, virtualMachine.pop()
                .convertToInteger())

    }
}