package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.getValueFromCommand
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PushInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack,
                                    staticVariables: StaticVariables, stackMemory: StackMemory) {
        val constant: String = getValueFromCommand(instruction)

        if (instruction.contains("constant")) {
            instructionStack.push(SixteenBit(constant.toInt()))
        } else if (instruction.contains("static")) {
            staticVariables.addVariable(constant.toInt())
        }
    }

}