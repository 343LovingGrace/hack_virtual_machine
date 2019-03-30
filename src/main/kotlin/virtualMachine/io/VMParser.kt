package virtualMachine.io

import virtualMachine.Translators.HackAssemblyTranslation
import virtualMachine.Translators.TranslateVMToHack
import virtualMachine.stack.InstructionStack
import virtualMachine.stackArithmetic.StackProcessor

//todo: kind of a pointless class really
class VMParser {

    private val instructionStack = InstructionStack()
    private val hackAssemblyTranslation = TranslateVMToHack()
    private val processor = StackProcessor()
    private val translator = HackAssemblyTranslation()


    fun readLine(line: String) {
        //probably wont actually generate any hack assembly
//        translator.addToAssembly(hackAssemblyTranslation.translateInstructionToHack(line, instructionStack))
        processor.processInstruction(instructionStack, line)
    }

    fun getInstructionStack() : InstructionStack = instructionStack.copy()

}