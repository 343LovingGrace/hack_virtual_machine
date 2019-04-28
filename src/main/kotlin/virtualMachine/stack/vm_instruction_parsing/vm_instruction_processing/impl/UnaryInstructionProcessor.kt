package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.memory.GlobalVirtualMemory
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class UnaryInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, virtualMemory: GlobalVirtualMemory) {
        val uniaryInstruction: SixteenBit = virtualMemory.popLocalStack()

        if (instruction == "not") {
            virtualMemory.pushToLocalStack(uniaryInstruction.not16Bit())
        }
    }
}