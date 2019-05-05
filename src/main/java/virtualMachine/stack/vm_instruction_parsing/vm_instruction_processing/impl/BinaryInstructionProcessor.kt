package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.memory.GlobalVirtualMemory
import virtualMachine.stack.datawrappers.Word
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor
import java.lang.RuntimeException

class BinaryInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: String, virtualMemory: GlobalVirtualMemory) {
        val latestHead: Word = virtualMemory.popStack()
        val secondFromHead : Word = virtualMemory.popStack()

        val result : Word = processComparison(instruction, latestHead, secondFromHead)
        virtualMemory.loadIntoMemory(result, virtualMemory.stackPointer, MemorySegments.GLOBAL_STACK)
        virtualMemory.pushToStack(result)
    }

    private fun processComparison(instruction: String, latestHead: Word,
                                  secondFromHead: Word) : Word {
        return when (instruction) {
            "eq" -> Word(secondFromHead == latestHead)
            "and" -> secondFromHead.bitwizeAnd(latestHead)
            "or" -> secondFromHead.bitwiseOr(latestHead)
            "gt" -> secondFromHead.greaterThan(latestHead)
            "lt" -> secondFromHead.lessThan(latestHead)
            "add" -> secondFromHead.add16Bit(latestHead)
            "sub" -> secondFromHead.subtract(latestHead)
            else -> throw RuntimeException("command not implemented yet")
        }
    }

}
