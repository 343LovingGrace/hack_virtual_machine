package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl;

import org.jetbrains.annotations.NotNull;
import virtualMachine.stack.datawrappers.instruction.Instruction;
import virtualMachine.stack.memory.GlobalVirtualMemory;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

/**
 * function f n -> start of code named f with n local variables
 * call f m -> call function f with m stack values as arguments
 * return -> returns the calling function
 */
public class FunctionProcessor implements InstructionProcessor {

    @Override
    public void processInstruction(@NotNull Instruction instruction, @NotNull GlobalVirtualMemory virtualMemory) {
        var command = instruction.getCommand();

        switch (command) {
            case FUNCTION:

                virtualMemory.pushToCallStack(instruction.getOperand());
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
