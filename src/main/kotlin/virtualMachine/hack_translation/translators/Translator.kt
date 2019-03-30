package virtualMachine.hack_translation.translators

import virtualMachine.stack.InstructionStack

interface Translator {
    fun translate(stack: InstructionStack) : String
}