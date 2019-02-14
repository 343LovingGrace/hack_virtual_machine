package virtualMachine.stackArithmetic

import virtualMachine.datawrappers.StackPermittedDataType
import virtualMachine.stack.InstructionStack
import virtualMachine.util.bitWiseNot16Bit
import virtualMachine.util.bitWiseOp16Bit
import java.util.logging.Level
import java.util.logging.Logger

class StackArithmeticProcessor {

    private val allBinaryInstructions: Set<String> = setOf("add", "sub", "eq", "gt", "lt", "and", "or")
    private val allUnaryInstructions: Set<String> = setOf("neg", "not")

    fun processArthimeticOrLogicalInstruction(instructionStack: InstructionStack, instruction: String): InstructionStack {
        when {
            instruction.contains("pop") -> {

            }
            instruction.contains("push") -> processPushCommand(instruction, instructionStack)
            allUnaryInstructions.contains(instruction) -> processUnaryInstruction(instructionStack, instruction)
            allBinaryInstructions.contains(instruction) -> processBinaryInstruction(instructionStack, instruction)
            else -> Logger.getLogger("StackArithmeticProcessor").log(Level.SEVERE, "invalid message provided" + instruction)
        }
        return instructionStack
    }

    private fun processPushCommand(instruction: String, instructionStack: InstructionStack) {
        if (instruction.contains("constant")) {
            val splitCommand: List<String> = instruction.split(" ")
            val constant: String = splitCommand[splitCommand.size - 1]
            instructionStack.pushHead(constant.toInt())
        }
    }

    private fun processBinaryInstruction(instructionStack: InstructionStack, instruction: String) {
        val latestHead: StackPermittedDataType = instructionStack.popHead()
        val secondFromHead: StackPermittedDataType = instructionStack.popHead()

        when {
            latestHead is StackPermittedDataType.BooleanWrapper
                    && secondFromHead is StackPermittedDataType.BooleanWrapper -> {
                instructionStack.pushHead(processBooleanInstruction(latestHead.value, secondFromHead.value, instruction))
            }
            latestHead is StackPermittedDataType.IntegerWrapper
                    && secondFromHead is StackPermittedDataType.IntegerWrapper -> {
                //e.g. 12 7 lt yields false
                val headVal = latestHead.i.value
                val previousHeadVal = secondFromHead.i.value
                when (instruction) {
                    "gt" -> instructionStack.pushHead(headVal < previousHeadVal)
                    "lt" -> instructionStack.pushHead(headVal > previousHeadVal)
                    "eq" -> instructionStack.pushHead(headVal == previousHeadVal)
                    "and" -> {
                        val result = bitWiseOp16Bit(headVal, previousHeadVal, "and")
                        instructionStack.pushHead(result)
                    }
                    "or" -> {
                        val result = bitWiseOp16Bit(headVal, previousHeadVal, "or")
                        instructionStack.pushHead(result)
                    }
                    else -> instructionStack.pushHead(processIntegerInstruction(headVal, previousHeadVal, instruction))
                }
            }
            else -> {
                //invalid state reached -> terminate
            }
        }
    }

    private fun processUnaryInstruction(instructionStack: InstructionStack, instruction: String) {
        val uniaryInstruction: StackPermittedDataType = instructionStack.popHead()
        if (uniaryInstruction is StackPermittedDataType.BooleanWrapper) {
            val res: Boolean = uniaryInstruction.value
            instructionStack.pushHead(processBooleanInstruction(res, false, instruction))
        } else if (uniaryInstruction is StackPermittedDataType.IntegerWrapper) {
            val res: Int = uniaryInstruction.i.value
            if (instruction == "not") {
                instructionStack.pushHead(bitWiseNot16Bit(uniaryInstruction.i.to16BitArray()))
            } else {
                instructionStack.pushHead(processIntegerInstruction(res, -1, instruction))
            }
        }
    }

    private fun processIntegerInstruction(a: Int, b: Int, instruction: String): Int {
        return when (instruction) {
            "add" -> add(a, b)
            "sub" -> sub(a, b)
            "neg" -> neg(a)
            else -> {
                Logger.getLogger("StackArithmeticProcessor").log(Level.WARNING, "unrecognised command $instruction")
                -1
            }
        }
    }

    private fun processBooleanInstruction(a: Boolean, b: Boolean, instruction: String): Boolean {
        println("doing some boolean stuff")
        return when (instruction) {
            "eq" -> eq(a, b)
            "and" -> and(a, b)
            "or" -> or(a, b)
            "not" -> not(a)
            else -> {
                Logger.getLogger("StackArithmeticProcessor").log(Level.WARNING, "unrecognised command $instruction")
                false
            }
        }
    }

}