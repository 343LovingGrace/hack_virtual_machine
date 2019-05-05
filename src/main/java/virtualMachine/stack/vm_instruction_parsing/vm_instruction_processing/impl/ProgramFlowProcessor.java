package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl;

import org.jetbrains.annotations.NotNull;
import virtualMachine.stack.datawrappers.Word;
import virtualMachine.stack.datawrappers.instruction.Commands;
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
    public void processInstruction(Instruction instruction, GlobalVirtualMemory virtualMemory) {

        //todo: going to need to change how ProcessVirtual machine file operates to support jumping

        var command = instruction.getCommand();

        if (command == LABEL) {

        } else if (command == GOTO) {

        } else if (command == IF_GOTO) {
            Word topValue = virtualMemory.popStack();
            if (topValue.convertToInteger() != 0) {

            } else {

            }
        } else {
            throw new RuntimeException("Command not recognised " + command);
        }

    }

}
