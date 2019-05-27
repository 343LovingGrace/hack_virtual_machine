package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl;

import org.jetbrains.annotations.NotNull;
import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.Word;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

import static virtualMachine.stack.types.instruction.Commands.GOTO;
import static virtualMachine.stack.types.instruction.Commands.IF_GOTO;
import static virtualMachine.stack.types.instruction.Commands.LABEL;

/**
 * label _label_ labels the current location in the functions code (allows to goto to this location)
 * goto _label_ unconditional goto to location
 * if-goto _label_ conditional goto - top val in stack is popped if val is not false execution continues from label
 *                  else execution continues from next command in program
 */
public class ProgramFlowProcessor implements InstructionProcessor {

    @Override
    public void processInstruction(Instruction instruction, @NotNull VirtualMemory virtualMemory) {

        var command = instruction.getCommand();

        if (command == GOTO) {
            virtualMemory.getControlFlow()
                    .setInstructionPointerToJumpAddress(instruction.getOperand());
        } else if (command == IF_GOTO) {
            //set next instruction to be executed to be the one next one in list if false, one in label otherwise
            Word topValue = virtualMemory.pop();
            if (!topValue.isFalse()) {
                virtualMemory.getControlFlow()
                        .setInstructionPointerToJumpAddress(instruction.getOperand());
            }
        } else if (command == LABEL) {
            virtualMemory.getControlFlow()
                    .getJumpLocation(instruction.getOperand());
        } else {
            throw new RuntimeException("Command not recognised " + command);
        }

    }

}
