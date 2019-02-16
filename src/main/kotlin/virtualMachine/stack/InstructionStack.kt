package virtualMachine.stack

import virtualMachine.datawrappers.SixteenBitInteger
import virtualMachine.datawrappers.StackPermittedDataType
import java.util.*

class InstructionStack {

    private val instructionStack: Deque<StackPermittedDataType> = ArrayDeque()

    fun popHead(): StackPermittedDataType = instructionStack.pop()

    fun pushHead(value: Int) {
        instructionStack.push(StackPermittedDataType.IntegerWrapper(SixteenBitInteger(value)))
    }

    fun pushHead(value: Boolean) = instructionStack.push(StackPermittedDataType.BooleanWrapper(value))

    fun peekHead() : StackPermittedDataType = instructionStack.peekFirst()

    fun peekSecond() : StackPermittedDataType {
        val head = instructionStack.pop()
        val second = instructionStack.peekFirst()
        instructionStack.addFirst(head)
        return second
    }

    fun print() {
        instructionStack.forEach {
            if (it is StackPermittedDataType.IntegerWrapper) {
                println("stack val is " + it.i.value)
            } else if (it is StackPermittedDataType.BooleanWrapper) {
                println("stack bool valu is " + it.value)
            }
        }
        println()
    }

    fun equals(instructionStack2: InstructionStack) : Boolean {
        return instructionStack2.instructionStack.equals(instructionStack)
    }

    fun copy() : InstructionStack = this
}