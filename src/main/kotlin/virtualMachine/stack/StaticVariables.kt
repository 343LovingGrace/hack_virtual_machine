package virtualMachine.stack

import kotlin.collections.ArrayList

class StaticVariables {

    private val MAX_SIZE = 8
    private var size = 0
    private val variables : MutableList<Int> = ArrayList(8)

    fun addVariable(variable : Int) {
        if (size < MAX_SIZE) {
            variables.add(variable)
        }
        throw IndexOutOfBoundsException("There can only be 8 static variables per stack")
    }

    fun get(index: Int) : Int {
        return variables[index]
    }

}