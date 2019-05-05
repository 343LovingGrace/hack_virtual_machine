package virtualMachine.stack.memory

import virtualMachine.stack.datawrappers.Word
import java.util.*

class InstructionStack {

    private val instructionStack : Deque<Word> = ArrayDeque()

    fun pop() : Word {
        return instructionStack.pop()
    }

    fun push(word: Word) {
        instructionStack.push(word)
    }

    fun printStack() {
        instructionStack.forEach { println(it.convertToInteger()) }
        println()
    }
}