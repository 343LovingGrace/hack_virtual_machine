package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.InstructionStack
import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.datawrappers.StackPermittedDataType
import virtualMachine.stack.datawrappers.getValue
import virtualMachine.stack.vm_instruction_parsing.getValueFromCommand
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor

class PopInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack, staticVariables: StaticVariables, stackMemory: StackMemory) {
        val head : StackPermittedDataType = instructionStack.popHead()
        val headValue : Int = getValue(head) as Int
        if (instruction.contains("pointer")) {
            val pointerOffset = 3
            val addressToSet: String = getValueFromCommand(instruction)
            stackMemory.setAddress(pointerOffset + addressToSet.toInt(), headValue)
        }
    }
}