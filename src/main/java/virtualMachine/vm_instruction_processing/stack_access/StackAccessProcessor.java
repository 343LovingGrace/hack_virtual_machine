package virtualMachine.vm_instruction_processing.stack_access;

import virtualMachine.memory.FunctionStack;
import virtualMachine.types.instruction.Instruction;

public interface StackAccessProcessor {

    void processInstruction(Instruction instruction, FunctionStack functionStack);
}
