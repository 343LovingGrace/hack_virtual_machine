package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.memory.VirtualMemory
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PushInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack,
                                    virtualMemory: VirtualMemory) {

        val splitCommand: List<String> = instruction.split(" ")
        val index = splitCommand[2].toInt()
        val segment = MemorySegments.getFromName(splitCommand[1])

        val res : SixteenBit = virtualMemory.getFromMemory(index, segment)
        instructionStack.push(res)
    }

}