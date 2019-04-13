package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.datawrappers.SixteenBit
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor
import java.lang.RuntimeException

class BinaryInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, instructionStack: InstructionStack,
                                    staticVariables: StaticVariables, stackMemory: StackMemory) {
        val latestHead: SixteenBit = instructionStack.pop()
        val secondFromHead : SixteenBit = instructionStack.pop()

        val result : SixteenBit = processComparison(instruction, latestHead, secondFromHead)
        instructionStack.push(result)
    }

    private fun processComparison(instruction: String, latestHead: SixteenBit,
                                  secondFromHead: SixteenBit) : SixteenBit {
        return when (instruction) {
            "eq" -> SixteenBit(secondFromHead == latestHead)
            "and" -> secondFromHead.and16Bit(latestHead)
            "or" -> secondFromHead.or16Bit(latestHead)
            "gt" -> secondFromHead.greaterThan16Bit(latestHead)
            "lt" -> secondFromHead.lessThan16Bit(latestHead)
            "add" -> secondFromHead.add16Bit(latestHead)
            "sub" -> secondFromHead.subtract(latestHead)
            else -> throw RuntimeException("command not implemented yet")
        }
    }

}
