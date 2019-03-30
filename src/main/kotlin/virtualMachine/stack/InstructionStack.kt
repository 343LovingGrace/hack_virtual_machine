package virtualMachine.stack

import virtualMachine.stack.datawrappers.SixteenBitInteger
import virtualMachine.stack.datawrappers.StackPermittedDataType
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class InstructionStack {

    private val instructionStack: Deque<StackPermittedDataType> = ArrayDeque()

    fun popHead(): StackPermittedDataType = instructionStack.pop()

    fun pushHead(value: Any) {
        if (value is Int) {
            instructionStack.push(StackPermittedDataType.IntegerWrapper(SixteenBitInteger(value)))
        } else if (value is Boolean) {
            instructionStack.push(StackPermittedDataType.BooleanWrapper(value))
        } else {
            Logger.getLogger(this.javaClass.name).log(Level.WARNING,
                    "The stack can only contain integers or boolean values not $value")
        }
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
        return instructionStack2.instructionStack == instructionStack
    }

    fun copy() : InstructionStack = this

    fun isEmpty() : Boolean = instructionStack.isEmpty()
}