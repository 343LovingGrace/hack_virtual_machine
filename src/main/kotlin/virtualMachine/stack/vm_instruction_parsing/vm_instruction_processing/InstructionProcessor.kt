package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing

import virtualMachine.stack.InstructionStack
import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables

interface InstructionProcessor {
    fun processInstruction(instruction: String,
                           instructionStack: InstructionStack,
                           staticVariables: StaticVariables,
                           stackMemory: StackMemory)
}
