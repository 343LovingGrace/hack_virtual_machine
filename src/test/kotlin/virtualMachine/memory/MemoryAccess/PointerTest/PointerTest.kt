package virtualMachine.memory.MemoryAccess.PointerTest

import org.junit.Test
import virtualMachine.ReadInputFile
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser

class PointerTest {

    @Test
    fun testScript() {

        val vmParser : VMInstructionParser = ReadInputFile()
                .processInputFile(System.getProperty("user.dir") + "/src/test/kotlin/virtualMachine/memory/MemoryAccess/StaticTest/StaticTest.vm",
                        VMInstructionParser())


    }

}