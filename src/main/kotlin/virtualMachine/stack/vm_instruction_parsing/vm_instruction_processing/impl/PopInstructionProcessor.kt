package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.memory.VirtualMemory
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PopInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack,
                                    virtualMemory: VirtualMemory) {

        val head : SixteenBit = instructionStack.pop()
        val splitCommand: List<String> = instruction.split(" ")
        val reference = splitCommand[splitCommand.size - 1].toInt()
        val segment = MemorySegments.getFromName(splitCommand[1])

        virtualMemory.loadIntoMemory(head, reference, segment)
    }
}