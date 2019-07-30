package virtualMachine.vm_instruction_processing.impl;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.vm_instruction_processing.InstructionProcessor;

import java.util.Objects;

import static virtualMachine.stack.memory.MemorySegments.getFromName;

public class PushInstructionProcessor implements InstructionProcessor {
    @Override
    public void processInstruction(Instruction instruction, VirtualMemory virtualMemory) {
        var memoryAddress = instruction.getNumericValue();
        var segment = getFromName(instruction.getOperand());

        var loadedVariable = virtualMemory.getFromMemory(memoryAddress, segment);
        Objects.requireNonNull(loadedVariable, "variable cannot be null. Instruction: " + instruction.toString());

        virtualMemory.push(loadedVariable);
    }
}
