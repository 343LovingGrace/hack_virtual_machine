package virtualMachine

import virtualMachine.hack_translation.translators.HackAssemblyTranslation
import virtualMachine.hack_translation.translators.TranslateVMToHack
import virtualMachine.stack.vm_instruction_parsing.InstructionStack
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser

//todo: kind of a pointless class really remove
class VMParser {

    private val instructionStack = InstructionStack()
    private val hackAssemblyTranslation = TranslateVMToHack()
    private val processor = VMInstructionParser()
    private val translator = HackAssemblyTranslation()


    fun readLine(line: String) {
        //probably wont actually generate any hack assembly
//        translator.addToAssembly(hackAssemblyTranslation.translateInstructionToHack(line, instructionStack))
        processor.processInstruction(instructionStack, line)
    }

    fun getInstructionStack() : InstructionStack = instructionStack

}