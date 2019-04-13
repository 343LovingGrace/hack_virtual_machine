package virtualMachine.hack_translation.translators

import virtualMachine.stack.vm_instruction_parsing.InstructionStack

class TranslateAdd : Translator {

    override fun translate(stack: InstructionStack) : String {
        val x = stack.pop()
        val y = stack.pop()
        return """
            @StackPointer
            M=${x.convertToInteger()} //loads value of x as variable into M (memory register)
            D=M // sets Data register with x's value
            @${y.convertToInteger()} //loads y into A register
            D=D+A //adds them then loads value into D
        """.trimMargin()
    }

}