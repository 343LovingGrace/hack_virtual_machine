package virtualMachine.stack.vm_instruction_parsing

import virtualMachine.stack.*
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.*
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.BinaryInstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.PopInstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.PushInstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.UnaryInstructionProcessor

fun getValueFromCommand(instruction: String) : String {
    if (instruction.contains(" ")) {
        val splitCommand: List<String> = instruction.split(" ")
        return splitCommand[splitCommand.size - 1]
    }
    return instruction
}

class VMInstructionParser {

    private val staticVariables = StaticVariables()
    private val stackMemory = StackMemory()

    private val allBinaryInstructions: Set<String> = setOf("add", "sub", "eq", "gt", "lt", "and", "or")
    private val allUnaryInstructions: Set<String> = setOf("neg", "not")

    fun processInstruction(instructionStack: InstructionStack, instruction: String): InstructionStack {
        val processor : InstructionProcessor = getInstructionProcessor(instruction)
        processor.processInstruction(instruction, instructionStack, staticVariables, stackMemory)
        return instructionStack
    }

    private fun getInstructionProcessor(instruction: String) : InstructionProcessor {
        if (instruction.contains("pop")) {
            return PopInstructionProcessor()
        } else if (instruction.contains("push")) {
            return PushInstructionProcessor()
        } else if (allBinaryInstructions.contains(instruction)) {
            return BinaryInstructionProcessor()
        } else if (allUnaryInstructions.contains(instruction)) {
            return UnaryInstructionProcessor()
        } else {
            throw RuntimeException("Unknown instruction " + instruction)
        }
    }

    fun getThis() : Int {
        return stackMemory.getAddress(THIS)
    }

    fun getThat() : Int {
        return stackMemory.getAddress(THAT)
    }

}