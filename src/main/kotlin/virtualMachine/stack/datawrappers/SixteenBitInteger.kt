package virtualMachine.stack.datawrappers

import virtualMachine.stack.vm_instruction_parsing.padStringTo16bit

class SixteenBitInteger(val value: Int) {

    fun to16BitArray() : String {
        return padStringTo16bit(Integer.toBinaryString(value))
    }

}