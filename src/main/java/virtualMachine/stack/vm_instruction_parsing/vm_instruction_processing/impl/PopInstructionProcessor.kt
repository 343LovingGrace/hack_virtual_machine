package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.types.Word
import virtualMachine.stack.types.instruction.Instruction
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.memory.GlobalVirtualMemory
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PopInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: Instruction, virtualMemory: GlobalVirtualMemory) {

        val head : Word = virtualMemory.pop()
        val reference = instruction.numericValue
        val segment = MemorySegments.getFromName(instruction.operand)

        virtualMemory.loadIntoMemory(head, reference, segment)
    }
}