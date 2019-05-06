package virtualMachine.stack.memory

import virtualMachine.stack.datawrappers.Word
import java.util.*

class WorkingStack {

    private val instructionStack : Deque<Word> = ArrayDeque()

    fun pop() : Word {
        return instructionStack.pop()
    }

    fun push(word: Word) {
        instructionStack.push(word)
    }

}