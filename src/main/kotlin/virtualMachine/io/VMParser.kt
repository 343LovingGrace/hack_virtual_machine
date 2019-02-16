package virtualMachine.io

import virtualMachine.Translators.HackAssemblyTranslation
import virtualMachine.Translators.TranslateVMToHack
import virtualMachine.stack.InstructionStack
import virtualMachine.stackArithmetic.StackArithmeticProcessor

class VMParser {

    private val instructionStack = InstructionStack()
    private val hackAssemblyTranslation = TranslateVMToHack()
    private val processor = StackArithmeticProcessor()
    private val translator = HackAssemblyTranslation()


    fun readLine(line: String) {
        //todo: CANNOT modify stack here (pointless double parsing of line -> first generates hack code, second
        //actually computes instruction
        translator.addToAssembly(hackAssemblyTranslation.translateInstructionToHack(line, instructionStack))
        processor.processArthimeticOrLogicalInstruction(instructionStack, line)
    }

    fun getInstructionStack() : InstructionStack = instructionStack.copy()
}