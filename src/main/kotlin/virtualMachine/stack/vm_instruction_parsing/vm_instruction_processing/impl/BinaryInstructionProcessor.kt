package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.InstructionStack
import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.datawrappers.StackPermittedDataType
import virtualMachine.stack.datawrappers.getValue
import virtualMachine.stack.vm_instruction_parsing.bitWiseOp16Bit
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.StackComputations
import java.lang.RuntimeException

class BinaryInstructionProcessor: StackComputations(), InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack, staticVariables: StaticVariables, stackMemory: StackMemory) {
        val latestHead: StackPermittedDataType = instructionStack.popHead()
        val secondFromHead: StackPermittedDataType = instructionStack.popHead()

        when {
            latestHead is StackPermittedDataType.BooleanWrapper
                    && secondFromHead is StackPermittedDataType.BooleanWrapper -> {
                instructionStack.pushHead(calculateBooleanInstruction(latestHead.value, secondFromHead.value, instruction))
            }
            latestHead is StackPermittedDataType.IntegerWrapper
                    && secondFromHead is StackPermittedDataType.IntegerWrapper -> {
                //e.g. 12 7 lt yields false
                instructionStack.pushHead(processComparison(instruction, latestHead, secondFromHead))
            }
            else -> {
                //invalid state reached -> terminate
            }
        }
    }

    private fun processComparison(instruction: String, latestHead: StackPermittedDataType,
                                  secondFromHead : StackPermittedDataType) : Any {
        val headVal = getValue(latestHead) as Int
        val previousHeadVal = getValue(secondFromHead) as Int
        return when (instruction) {
            "gt" -> headVal < previousHeadVal
            "lt" -> headVal > previousHeadVal
            "eq" -> headVal == previousHeadVal
            "and" -> bitWiseOp16Bit(headVal, previousHeadVal, "and")
            "or" -> bitWiseOp16Bit(headVal, previousHeadVal, "or")
            else -> calculateIntegerInstruction(headVal, previousHeadVal, instruction)
        }
    }

    private fun processComparison(instruction: String, latestHead: SixteenBit,
                                  secondFromHead: SixteenBit) : SixteenBit {
        return when (instruction) {
            "eq" -> latestHead.equals(secondFromHead)
            "and" -> latestHead.and16Bit(secondFromHead)
            "or" -> latestHead.or16Bit(secondFromHead)
            "gt" -> latestHead.greaterThan16Bit(secondFromHead)
            "lt" -> latestHead.lessThan16Bit(secondFromHead)
            "add" -> latestHead.add16Bit(secondFromHead)
            else -> throw RuntimeException("command not implemented yet")

        }
    }

}
