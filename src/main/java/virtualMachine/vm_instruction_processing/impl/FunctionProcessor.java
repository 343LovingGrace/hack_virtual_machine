package virtualMachine.vm_instruction_processing.impl;

import virtualMachine.stack.memory.MemorySegments;
import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.Word;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.vm_instruction_processing.InstructionProcessor;

/**
 * function f n -> start of code named f with n local variables
 * call f m -> call function f with m stack values as arguments
 * return -> returns the calling function
 */
public class FunctionProcessor implements InstructionProcessor {

    @Override
    public void processInstruction(Instruction instruction, VirtualMemory virtualMemory) {
        var command = instruction.getCommand();

        switch (command) {
            case FUNCTION:
                int numberOfArguments = instruction.getNumericValue();
                for (int i = 0; i <= numberOfArguments; i++) {
                    virtualMemory.loadIntoMemory(new Word(0), i, MemorySegments.LOCAL);
                }
                break;

            case CALL:
                virtualMemory.callFunction(instruction.getOperand(), instruction.getNumericValue());
                break;
                
            case RETURN:
                virtualMemory.returnFromFunction();
                break;
        }
    }

}
