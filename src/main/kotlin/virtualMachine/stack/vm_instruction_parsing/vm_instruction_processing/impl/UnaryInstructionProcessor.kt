package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class UnaryInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack,
                                    staticVariables: StaticVariables, stackMemory: StackMemory) {
        val uniaryInstruction: SixteenBit = instructionStack.pop()

        if (instruction == "not") {
            instructionStack.push(uniaryInstruction.not16Bit())
        }
    }
}