package virtualMachine.Translators

import virtualMachine.stack.InstructionStack

class TranslateVMToHack {

    fun translateInstructionToHack(line: String, stack: InstructionStack) : String {
        val translator : Translator = getCorrectTranslator(line)
        return translator.translate(stack)
    }

    private fun getCorrectTranslator(line: String) : Translator {
        return when {
            line.contains("add") -> TranslateAdd()

            //todo: proper exception handling
            else -> throw Exception("Cannae do it capn")
        }
    }

}