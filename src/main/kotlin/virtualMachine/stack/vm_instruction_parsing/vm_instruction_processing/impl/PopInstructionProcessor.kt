package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.getValueFromCommand
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PopInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack,
                                    staticVariables: StaticVariables, stackMemory: StackMemory) {
        val head : SixteenBit = instructionStack.pop()
        if (instruction.contains("pointer")) {
            val pointerOffset = 3
            val addressToSet: String = getValueFromCommand(instruction)
            stackMemory.setAddress(pointerOffset + addressToSet.toInt(), head.convertToInteger())
        }
    }
}