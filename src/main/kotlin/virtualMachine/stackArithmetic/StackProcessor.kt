package virtualMachine.stackArithmetic

import virtualMachine.datawrappers.StackPermittedDataType
import virtualMachine.datawrappers.getValue
import virtualMachine.stack.InstructionStack
import virtualMachine.stack.StackMemory
import virtualMachine.stack.StaticVariables
import virtualMachine.stack.THIS
import virtualMachine.util.bitWiseNot16Bit
import virtualMachine.util.bitWiseOp16Bit
import java.util.logging.Level
import java.util.logging.Logger

//todo: doing too much refactor
class StackProcessor {

    private val staticVariables = StaticVariables()
    private val stackMemory = StackMemory()

    private val allBinaryInstructions: Set<String> = setOf("add", "sub", "eq", "gt", "lt", "and", "or")
    private val allUnaryInstructions: Set<String> = setOf("neg", "not")

    fun processInstruction(instructionStack: InstructionStack, instruction: String): InstructionStack {
        when {
            instruction.contains("pop") -> {
                val head : StackPermittedDataType = instructionStack.popHead()
                val headValue : Int = getValue(head) as Int
                if (instruction.contains("pointer")) {
                    val pointerOffset = 3
                    val addressToSet: String = getValueFromCommand(instruction)
                    stackMemory.setAddress(pointerOffset + addressToSet.toInt(), headValue)
                }
            }
            instruction.contains("push") -> processPushCommand(instruction, instructionStack)
            allUnaryInstructions.contains(instruction) -> processUnaryInstruction(instructionStack, instruction)
            allBinaryInstructions.contains(instruction) -> processBinaryInstruction(instructionStack, instruction)
            else -> Logger.getLogger("StackProcessor").log(Level.SEVERE, "invalid message provided" + instruction)
        }
        return instructionStack
    }

    //interface with classes constructed for each command
    private fun processPushCommand(instruction: String, instructionStack: InstructionStack) {
        val constant: String = getValueFromCommand(instruction)

        if (instruction.contains("constant")) {
            instructionStack.pushHead(constant.toInt())
        } else if (instruction.contains("static")) {
            staticVariables.addVariable(constant.toInt())
        }
    }

    private fun getValueFromCommand(instruction: String) : String {
        val splitCommand: List<String> = instruction.split(" ")
        return splitCommand[splitCommand.size - 1]
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
                Logger.getLogger("StackProcessor").log(Level.WARNING, "unrecognised command $instruction")
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
                Logger.getLogger("StackProcessor").log(Level.WARNING, "unrecognised command $instruction")
                false
            }
        }
    }

    fun getThis() : Int {
        return stackMemory.getAddress(THIS)
    }

}