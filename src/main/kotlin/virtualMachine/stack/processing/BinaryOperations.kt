package virtualMachine.stack.processing

import java.lang.StringBuilder

fun padStringTo16bit(value: String) : String {
    return value.padStart(16, '0')
}

fun bitWiseNot16Bit(first : String) : Int {
    val nottedString = StringBuilder()
    for (i in 0 .. 15) {
        val df: Char = not(first[i])
        nottedString.append(df)
    }
    return Integer.parseInt(nottedString.toString(), 2)
}

fun bitWiseOp16Bit(first : Int, second : Int, operation: String) : Int {
    return when (operation) {
        "or" -> bitWiseOp16Bit(first, second) { a, b -> andToChar(a, b) }
        "and" -> bitWiseOp16Bit(first, second) { a, b -> orToChar(a, b) }
        else -> -1
    }
}

private fun bitWiseOp16Bit(first : Int, second : Int, operation: (Boolean, Boolean) -> Char) : Int {
    val firstString = padStringTo16bit(Integer.toBinaryString(first))
    val secondString = padStringTo16bit(Integer.toBinaryString(second))
    val nottedString = StringBuilder()
    for (i: Int in 0 .. 15) {
        val a: Boolean = charToBoolean(firstString[i])
        val b: Boolean = charToBoolean(secondString[i])
        nottedString.append(operation(a, b))
    }
    return Integer.parseInt(nottedString.toString(), 2)
}

private fun not(a : Char) : Char {
    return if (a == '1') {
        '0'
    } else {
        '1'
    }
}

private fun charToBoolean(letter : Char) : Boolean {
    return letter == '1'
}

private fun andToChar(a : Boolean, b : Boolean) : Char {
    return if (a == b) {
        '1'
    } else {
        '0'
    }
}
private fun orToChar(a : Boolean, b : Boolean) : Char {
    return if (a || b) {
        '1'
    } else {
        '0'
    }
}