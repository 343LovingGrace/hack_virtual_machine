package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing

import virtualMachine.stack.types.instruction.Instruction
import virtualMachine.stack.memory.GlobalVirtualMemory

interface InstructionProcessor {
    fun processInstruction(instruction: Instruction,
                           virtualMemory: GlobalVirtualMemory)
}
