package virtualMachine.vm_instruction_processing.impl;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.vm_instruction_processing.InstructionProcessor;

import static virtualMachine.stack.memory.MemorySegments.getFromName;

public class PopInstructionProcessor implements InstructionProcessor {

    @Override
    public void processInstruction(Instruction instruction, VirtualMemory virtualMemory) {
        var head = virtualMemory.pop();
        var reference = instruction.getNumericValue();
        var segment = getFromName(instruction.getOperand());

        virtualMemory.loadIntoMemory(head, reference, segment);
    }
}
