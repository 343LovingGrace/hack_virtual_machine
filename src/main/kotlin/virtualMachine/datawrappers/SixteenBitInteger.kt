package virtualMachine.datawrappers

import virtualMachine.util.padStringTo16bit

class SixteenBitInteger(val value: Int) {

    fun to16BitArray() : String {
        return padStringTo16bit(Integer.toBinaryString(value))
    }

}