package virtualMachine.stack

const val SP = 0
const val LCL = 1
const val ARG = 2
const val THIS = 3
const val THAT = 4
class StackMemory {

    private val pseudoRam : IntArray = IntArray(4096)

    fun setAddress(address : Int, value : Int) {
        pseudoRam[address] = value
    }

    fun getAddress(address : Int) : Int {
        return pseudoRam[address]
    }
}