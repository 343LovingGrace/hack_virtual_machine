package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.Word;
import virtualMachine.stack.types.instruction.Commands;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

public class BinaryInstructionProcessor implements InstructionProcessor {
    @Override
    public void processInstruction(Instruction instruction, VirtualMemory virtualMemory) {
        var head = virtualMemory.pop();
        var secondFromHead = virtualMemory.pop();

        Word result = doComputation(head, secondFromHead, instruction.getCommand());
        virtualMemory.push(result);
    }

    private Word doComputation(Word head, Word second, Commands command) {
        switch (command) {
            case BITWISE_AND:
                return head.bitwizeAnd(second);
            case BITWISE_EQUALS:
                return new Word(head.equals(second));
            case BITWISE_OR:
                return head.bitwiseOr(second);
            case GREATER_THAN:
                return second.greaterThan(head);
            case LESS_THAN:
                return second.lessThan(head);
            case ADD:
                return second.add16Bit(head);
            case SUBTRACT:
                return second.subtract(head);
            default:
                throw new RuntimeException("Unrecognised command: " + command.getName());
        }
    }
}
