package virtualMachine.vm_instruction_processing.impl;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.instruction.Commands;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.vm_instruction_processing.InstructionProcessor;

public class UnaryInstructionProcessor implements InstructionProcessor {

    @Override
    public void processInstruction(Instruction instruction, VirtualMemory virtualMemory) {
        var head = virtualMemory.pop();

        if (instruction.getCommand() == Commands.NOT) {
            virtualMemory.push(head.bitWiseNot());
        } else if (instruction.getCommand() == Commands.NEGATIVE) {
            virtualMemory.push(head.negate());
        }
    }
}
