package virtualMachine

import virtualMachine.hack_translation.translators.HackAssemblyTranslation
import virtualMachine.hack_translation.translators.TranslateVMToHack
import virtualMachine.stack.InstructionStack
import virtualMachine.stack.processing.StackProcessor

//todo: kind of a pointless class really remove
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