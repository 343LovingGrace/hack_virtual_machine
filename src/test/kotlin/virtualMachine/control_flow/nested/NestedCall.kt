package virtualMachine.control_flow.nested

import org.junit.Assert
import org.junit.Test
import virtualMachine.ProcessVirtualMachineFile
import virtualMachine.stack.vm_instruction_parsing.VmParser

class NestedCall {

    @Test
    fun nestedCalls() {
        val vmReader : VmParser = ProcessVirtualMachineFile()
                .processVmFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/control_flow/nested/NestedCall.vm",
                        null)

        val memory = vmReader.virtualMemory

        val head = memory.pop()
        //todo : check this
        Assert.assertEquals(246, head.convertToInteger())
    }
}