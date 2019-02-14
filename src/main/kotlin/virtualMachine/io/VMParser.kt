package virtualMachine.io

import virtualMachine.stack.InstructionStack
import virtualMachine.stackArithmetic.StackArithmeticProcessor

class VMParser {

    private val instructionStack = InstructionStack()

    fun readLine(line: String) {
        val processor = StackArithmeticProcessor()
        processor.processArthimeticOrLogicalInstruction(instructionStack, line)
    }

    fun getInstructionStack() : InstructionStack = instructionStack.copy()
}