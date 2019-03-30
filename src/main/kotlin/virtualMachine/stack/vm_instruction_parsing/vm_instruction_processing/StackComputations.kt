package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing

import java.util.logging.Level
import java.util.logging.Logger

abstract class StackComputations {

    protected fun calculateIntegerInstruction(a: Int, b: Int, instruction: String): Int {
        return when (instruction) {
            "add" -> a + b
            "sub" -> a - b
            "neg" -> -a
            else -> {
                Logger.getLogger("VMInstructionParser").log(Level.WARNING, "unrecognised command $instruction")
                -1
            }
        }
    }

    protected fun calculateBooleanInstruction(a: Boolean, b: Boolean, instruction: String): Boolean {
        return when (instruction) {
            "eq" -> a == b
            "and" -> a && b
            "or" ->  a || b
            "not" -> !a
            else -> {
                Logger.getLogger("VMInstructionParser").log(Level.WARNING, "unrecognised command $instruction")
                false
            }
        }
    }
}