package virtualMachine.vm_instruction_processing.stack_access;

import virtualMachine.memory.FunctionStack;
import virtualMachine.types.instruction.Commands;
import virtualMachine.types.instruction.Instruction;

public class NegationProcessor implements StackAccessProcessor {

    @Override
    public void processInstruction(Instruction instruction, FunctionStack functionStack) {
        var head = functionStack.pop();

        if (instruction.getCommand() == Commands.NOT) {
            functionStack.push(head.bitWiseNot());
        } else if (instruction.getCommand() == Commands.NEGATIVE) {
            functionStack.push(head.negate());
        }
    }

}
