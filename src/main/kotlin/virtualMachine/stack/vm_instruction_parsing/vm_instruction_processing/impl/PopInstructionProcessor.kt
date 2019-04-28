package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.memory.GlobalVirtualMemory
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PopInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, virtualMemory: GlobalVirtualMemory) {

        val head : SixteenBit = virtualMemory.popLocalStack()
        val splitCommand: List<String> = instruction.split(" ")
        val reference = splitCommand[splitCommand.size - 1].toInt()
        val segment = MemorySegments.getFromName(splitCommand[1])

        virtualMemory.loadIntoMemory(head, reference, segment)
    }
}