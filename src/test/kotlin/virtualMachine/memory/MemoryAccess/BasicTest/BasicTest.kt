package virtualMachine.memory.MemoryAccess.BasicTest

import org.junit.Assert
import org.junit.Test
import virtualMachine.ProcessVirtualMachineFile
import virtualMachine.ProcessVirtualMachineFile.getInstructionFromRawInput
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser

class BasicTest {

    @Test
    fun testScript() {

        val vmReader : VMInstructionParser = ProcessVirtualMachineFile()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/memory/MemoryAccess/BasicTest/BasicTest.vm",
                        listOf(getInstructionFromRawInput("push constant 3000"),
                                getInstructionFromRawInput("pop pointer 0"),
                                getInstructionFromRawInput("push constant 3010"),
                                getInstructionFromRawInput("pop pointer 1")))

        val memory = vmReader.virtualMemory

        Assert.assertEquals(10, memory.getFromMemory(0, MemorySegments.LOCAL).convertToInteger())

        Assert.assertEquals(21, memory.getFromMemory(1, MemorySegments.ARGUMENT).convertToInteger())
        Assert.assertEquals(22, memory.getFromMemory(2, MemorySegments.ARGUMENT).convertToInteger())

        Assert.assertEquals(42, memory.getFromMemory(2, MemorySegments.THAT).convertToInteger())
        Assert.assertEquals(45, memory.getFromMemory(5, MemorySegments.THAT).convertToInteger())

        Assert.assertEquals(36, memory.getFromMemory(6, MemorySegments.THIS).convertToInteger())
        Assert.assertEquals(42, memory.getFromMemory(12, MemorySegments.THIS).convertToInteger())
        Assert.assertEquals(45, memory.getFromMemory(15, MemorySegments.THIS).convertToInteger())

        Assert.assertEquals(472, memory.getFromGlobalStack(1).convertToInteger())
        Assert.assertEquals(510, memory.getFromGlobalStack(2).convertToInteger())

        Assert.assertEquals(472, vmReader.getVirtualMemory().popStack()
                .convertToInteger())

    }
}