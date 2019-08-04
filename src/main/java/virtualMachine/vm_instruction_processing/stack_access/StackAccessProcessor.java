package virtualMachine.vm_instruction_processing.stack_access;

import virtualMachine.stack.memory.FunctionStack;
import virtualMachine.stack.types.instruction.Instruction;

public interface StackAccessProcessor {

    void processInstruction(Instruction instruction, FunctionStack functionStack);
}
