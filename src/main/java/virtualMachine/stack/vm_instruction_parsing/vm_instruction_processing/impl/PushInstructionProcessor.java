package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

import static virtualMachine.stack.memory.MemorySegments.getFromName;

public class PushInstructionProcessor implements InstructionProcessor {
    @Override
    public void processInstruction(Instruction instruction, VirtualMemory virtualMemory) {
        var memoryAddress = instruction.getNumericValue();
        var segment = getFromName(instruction.getOperand());

        var loadedVariable = virtualMemory.getFromMemory(memoryAddress, segment);
        virtualMemory.push(loadedVariable);
    }
}
