package virtualMachine.stack.vm_instruction_parsing

import virtualMachine.stack.datawrappers.SixteenBit
import java.util.*

class InstructionStack {

    private val instructionStack : Deque<SixteenBit> = ArrayDeque()

    fun pop() : SixteenBit = instructionStack.pop()

    fun push(sixteenBit: SixteenBit) = instructionStack.push(sixteenBit)
}