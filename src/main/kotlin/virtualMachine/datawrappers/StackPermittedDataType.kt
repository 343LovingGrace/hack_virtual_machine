package virtualMachine.datawrappers

sealed class StackPermittedDataType {
    class BooleanWrapper(val value: Boolean) : StackPermittedDataType()
    class IntegerWrapper(val i: SixteenBitInteger) : StackPermittedDataType()
}
fun getValue(dataType: StackPermittedDataType) : Any {
    return when (dataType) {
        is StackPermittedDataType.BooleanWrapper -> dataType.value
        is StackPermittedDataType.IntegerWrapper -> dataType.i.value
    }
}