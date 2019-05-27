package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

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
            case CALL:

                virtualMemory.callFunction(instruction.getOperand(), instruction.getNumericValue());
                break;
            case RETURN:

                virtualMemory.returnFromFunction();
                break;
        }
    }

}
