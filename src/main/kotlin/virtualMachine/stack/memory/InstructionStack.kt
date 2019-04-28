package virtualMachine.stack.memory

import virtualMachine.stack.datawrappers.SixteenBit
import java.util.*

class InstructionStack {

    private val instructionStack : Deque<SixteenBit> = ArrayDeque()

    fun pop() : SixteenBit {
        return instructionStack.pop()
    }

    fun push(sixteenBit: SixteenBit) {
        instructionStack.push(sixteenBit)
    }
}