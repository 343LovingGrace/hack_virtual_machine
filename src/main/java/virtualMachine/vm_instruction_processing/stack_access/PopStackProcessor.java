package virtualMachine.vm_instruction_processing.stack_access;

import virtualMachine.stack.memory.FunctionStack;
import virtualMachine.stack.types.instruction.Instruction;

import static virtualMachine.stack.memory.MemorySegments.getFromName;

public class PopStackProcessor implements StackAccessProcessor {

    public void processInstruction(Instruction instruction, FunctionStack functionStack) {
        var head = functionStack.pop();
        var reference = instruction.getNumericValue();
        var segment = getFromName(instruction.getOperand());

        functionStack.loadIntoMemory(head, reference, segment);
    }
}
