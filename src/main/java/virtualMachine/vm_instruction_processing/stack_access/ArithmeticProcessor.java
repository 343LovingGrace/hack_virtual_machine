package virtualMachine.vm_instruction_processing.stack_access;

import virtualMachine.stack.memory.FunctionStack;
import virtualMachine.stack.types.Word;
import virtualMachine.stack.types.instruction.Commands;
import virtualMachine.stack.types.instruction.Instruction;

public class ArithmeticProcessor implements StackAccessProcessor {

    /**
     * Process arithmetic operation of the 2 top values of a current function's stack
     */
    @Override
    public void processInstruction(Instruction instruction, FunctionStack functionStack) {
        var head = functionStack.pop();
        var secondFromHead = functionStack.pop();

        Word result = doComputation(head, secondFromHead, instruction.getCommand());
        functionStack.push(result);
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
