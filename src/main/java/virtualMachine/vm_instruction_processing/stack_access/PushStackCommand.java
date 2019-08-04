package virtualMachine.vm_instruction_processing.stack_access;

import virtualMachine.memory.FunctionStack;
import virtualMachine.memory.MemorySegments;
import virtualMachine.types.Word;
import virtualMachine.types.instruction.Instruction;

import java.util.Objects;

import static virtualMachine.memory.MemorySegments.getFromName;

public class PushStackCommand implements StackAccessProcessor {

    public void processInstruction(Instruction instruction, FunctionStack functionStack) {
        int memoryAddress = instruction.getNumericValue();
        MemorySegments segment = getFromName(instruction.getOperand());

        Word loadedVariable = functionStack.getFromMemory(memoryAddress, segment);
        Objects.requireNonNull(loadedVariable, "variable cannot be null. Instruction: " + instruction.toString());

        functionStack.push(loadedVariable);
    }

}
