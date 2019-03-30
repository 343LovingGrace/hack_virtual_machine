package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.InstructionStack
import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.datawrappers.StackPermittedDataType
import virtualMachine.stack.vm_instruction_parsing.bitWiseNot16Bit
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.StackComputations

class UnaryInstructionProcessor : StackComputations(), InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack, staticVariables: StaticVariables, stackMemory: StackMemory) {
        val uniaryInstruction: StackPermittedDataType = instructionStack.popHead()
        if (uniaryInstruction is StackPermittedDataType.BooleanWrapper) {
            val res: Boolean = uniaryInstruction.value
            instructionStack.pushHead(calculateBooleanInstruction(res, false, instruction))
        } else if (uniaryInstruction is StackPermittedDataType.IntegerWrapper) {
            val res: Int = uniaryInstruction.i.value
            if (instruction == "not") {
                instructionStack.pushHead(bitWiseNot16Bit(uniaryInstruction.i.to16BitArray()))
            } else {
                instructionStack.pushHead(calculateIntegerInstruction(res, -1, instruction))
            }
        }
    }
}