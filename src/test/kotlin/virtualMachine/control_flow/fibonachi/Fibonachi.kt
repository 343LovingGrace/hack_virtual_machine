package virtualMachine.control_flow.fibonachi

import org.junit.Assert.assertEquals
import org.junit.Test
import virtualMachine.VirtualMachineFileParser
import virtualMachine.VirtualMachineFileParser.getInstructionFromRawInput
import virtualMachine.memory.MemorySegments
import virtualMachine.VirtualMachine

class Fibonachi {

    @Test
    fun fib10() {

        val addressToStoreSequenceFrom = 1000

        val setup = listOf(
                getInstructionFromRawInput("push constant 10"),
                getInstructionFromRawInput("pop argument 0"),
                getInstructionFromRawInput("push constant $addressToStoreSequenceFrom"), //address to store variables from
                getInstructionFromRawInput("pop argument 1"))

        val virtualMachine : VirtualMachine = VirtualMachineFileParser()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/control_flow/fibonachi/Fibonachi.vm",
                        setup)

        //the script updates THAT pointer to 1008. If need application code to directly query an address this test could be updated
        assertEquals(0, virtualMachine.getFromMemory(-8, MemorySegments.THAT).convertToInteger())
        assertEquals(1, virtualMachine.getFromMemory(-7, MemorySegments.THAT).convertToInteger())
        assertEquals(1, virtualMachine.getFromMemory(-6, MemorySegments.THAT).convertToInteger())
        assertEquals(2, virtualMachine.getFromMemory(-5, MemorySegments.THAT).convertToInteger())
        assertEquals(3, virtualMachine.getFromMemory(-4, MemorySegments.THAT).convertToInteger())
        assertEquals(5, virtualMachine.getFromMemory(-3, MemorySegments.THAT).convertToInteger())
        assertEquals(8, virtualMachine.getFromMemory(-2, MemorySegments.THAT).convertToInteger())
        assertEquals(13, virtualMachine.getFromMemory(-1, MemorySegments.THAT).convertToInteger())
        assertEquals(21, virtualMachine.getFromMemory(0, MemorySegments.THAT).convertToInteger())
        assertEquals(34, virtualMachine.getFromMemory(1, MemorySegments.THAT).convertToInteger())
    }

    @Test
    fun fibonachiRecursive() {
        val setup = listOf(
                getInstructionFromRawInput("push constant 4"),
                getInstructionFromRawInput("call Main.fibonacci 1")
        )

        val vmReader : VirtualMachine = VirtualMachineFileParser()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/control_flow/fibonachi/FibonachiRecursive.vm",
                        setup)

    }
}