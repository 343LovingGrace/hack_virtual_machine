package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.memory.GlobalVirtualMemory
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PushInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, virtualMemory: GlobalVirtualMemory) {

        val splitCommand: List<String> = instruction.split(" ")
        val index = splitCommand[2].toInt()
        val segment = MemorySegments.getFromName(splitCommand[1])

        val storedVariable : SixteenBit = virtualMemory.getFromMemory(index, segment)
        virtualMemory.pushToLocalStack(storedVariable)
    }

}