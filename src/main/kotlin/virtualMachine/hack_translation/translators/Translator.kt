package virtualMachine.hack_translation.translators

import virtualMachine.stack.vm_instruction_parsing.InstructionStack

interface Translator {
    fun translate(stack: InstructionStack) : String
}