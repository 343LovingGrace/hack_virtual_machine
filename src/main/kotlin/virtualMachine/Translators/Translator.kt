package virtualMachine.Translators

import virtualMachine.stack.InstructionStack

interface Translator {
    fun translate(stack: InstructionStack) : String
}