package virtualMachine.Translators

class HackAssemblyTranslation {
    private val assemblyCode = StringBuilder()

    public fun addToAssembly(input: String) {
        assemblyCode.append(input)
    }

    fun getAssemblyCode() : String = assemblyCode.toString()
}