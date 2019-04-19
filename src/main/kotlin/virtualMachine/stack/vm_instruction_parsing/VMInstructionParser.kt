package virtualMachine.stack.vm_instruction_parsing

import virtualMachine.stack.memory.VirtualMemory
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.BinaryInstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.PopInstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.PushInstructionProcessor
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.UnaryInstructionProcessor

// <command> <datatype> <value>
fun getValueFromCommand(instruction: String) : String {
    if (instruction.contains(" ")) {
        val splitCommand: List<String> = instruction.split(" ")
        return splitCommand[splitCommand.size - 1]
    }
    return instruction
}

private val allBinaryInstructions: Set<String> = setOf("add", "sub", "eq", "gt", "lt", "and", "or")
private val allUnaryInstructions: Set<String> = setOf("neg", "not")

class VMInstructionParser {

    private val virtualMemory = VirtualMemory()
    private val instructionStack = InstructionStack()

    fun processInstruction(instruction: String) {
        val processor : InstructionProcessor = getInstructionProcessor(instruction)
        processor.processInstruction(instruction, instructionStack, virtualMemory)
    }

    private fun getInstructionProcessor(instruction: String) : InstructionProcessor {
        return when {
            instruction.contains("pop") -> PopInstructionProcessor()
            instruction.contains("push") -> PushInstructionProcessor()
            allBinaryInstructions.contains(instruction) -> BinaryInstructionProcessor()
            allUnaryInstructions.contains(instruction) -> UnaryInstructionProcessor()
            else -> throw RuntimeException("Unknown instruction $instruction")
        }
    }

    fun getVirtualMemory() : VirtualMemory {
        return virtualMemory
    }

    fun getInstructionStack() : InstructionStack {
        return instructionStack
    }

}