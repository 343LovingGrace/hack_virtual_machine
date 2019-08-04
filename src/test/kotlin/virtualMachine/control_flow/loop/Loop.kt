package virtualMachine.control_flow.loop

import org.junit.Assert
import org.junit.Test
import virtualMachine.VirtualMachineFileParser
import virtualMachine.VirtualMachineFileParser.getInstructionFromRawInput
import virtualMachine.VirtualMachine

class Loop {

    @Test
    fun loop6() {

        //load 6 into argument, adds 6 + 5 + 4 + 3 + 2 + 1
        val setup = listOf(
                getInstructionFromRawInput("push constant 6"),
                getInstructionFromRawInput("pop argument 0"))

        val virtualMachine : VirtualMachine = VirtualMachineFileParser()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/control_flow/loop/loop.vm",
                        setup)

        Assert.assertEquals(21, virtualMachine.pop().convertToInteger())
    }

    @Test
    fun loop81() {

        var sum = 0
        var counter = 81
        while (counter > 0) {
            sum += counter
            counter--
        }

        val setup = listOf(
                getInstructionFromRawInput("push constant 81"),
                getInstructionFromRawInput("pop argument 0"))

        val virtualMachine : VirtualMachine = VirtualMachineFileParser()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/control_flow/loop/loop.vm",
                        setup)

        Assert.assertEquals(sum, virtualMachine.pop().convertToInteger())
    }

    @Test
    fun loop165() {
        val counterStart = 165

        var sum = 0
        var counter = counterStart
        while (counter > 0) {
            sum += counter
            counter--
        }

        val setup = listOf(
                getInstructionFromRawInput("push constant $counterStart"),
                getInstructionFromRawInput("pop argument 0"))

        val virtualMachine : VirtualMachine = VirtualMachineFileParser()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/control_flow/loop/loop.vm",
                        setup)

        Assert.assertEquals(sum, virtualMachine.pop().convertToInteger())
    }

}