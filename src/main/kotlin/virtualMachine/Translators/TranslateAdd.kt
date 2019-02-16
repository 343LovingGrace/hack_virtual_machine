package virtualMachine.Translators

import virtualMachine.stack.InstructionStack

class TranslateAdd : Translator {

    override fun translate(stack: InstructionStack) : String {
        val x = stack.peekHead()
        val y = stack.peekSecond()
        return """
            @StackPointer
            M=$x //loads value of x as variable into M (memory register)
            D=M // sets Data register with x's value
            @$y //loads y into A register
            D=D+A //adds them then loads value into D
        """.trimMargin()
    }

}