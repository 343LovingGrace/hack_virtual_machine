package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl;

import org.jetbrains.annotations.NotNull;
import virtualMachine.stack.datawrappers.Word;
import virtualMachine.stack.datawrappers.instruction.Instruction;
import virtualMachine.stack.memory.GlobalVirtualMemory;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

import static virtualMachine.stack.datawrappers.instruction.Commands.*;

/**
 * label _label_ labels the current location in the functions code (allows to goto to this location)
 * goto _label_ unconditional goto to location
 * if-goto _label_ conditional goto - top val in stack is popped if val > 0 execution continues from label
 *                  else execution continues from next command in program
 */
public class ProgramFlowProcessor implements InstructionProcessor {

    @Override
    public void processInstruction(Instruction instruction, @NotNull GlobalVirtualMemory virtualMemory) {

        //todo: going to need to change how ProcessVirtual machine file operates to support jumping

        var command = instruction.getCommand();

        if (command == LABEL) {
            //TODO: add label to some sort of map
        } else if (command == GOTO) {
            //todo: set next instruction to be executed to be the one in the label map
        } else if (command == IF_GOTO) {
            ////todo: set next instruction to be executed to be the one in the label map (if true)
            Word topValue = virtualMemory.popStack();
            if (topValue.convertToInteger() != 0) {

            } else {

            }
        } else {
            throw new RuntimeException("Command not recognised " + command);
        }

    }

}
