package virtualMachine.vm_instruction_processing.memory_access;

import virtualMachine.memory.*;
import virtualMachine.types.Word;
import virtualMachine.types.instruction.Commands;
import virtualMachine.types.instruction.Instruction;
import virtualMachine.vm_instruction_processing.control_flow_processing.ControlFlow;

public class Function {

    public static void processInstruction(Instruction instruction, ControlFlow controlFlow,
                                          CallStack callStack, PseudoAddressSpaceMemory virtualRam) {
        Commands command = instruction.getCommand();
        FunctionStack functionStack = callStack.peekFirst();

        switch (command) {
            case FUNCTION:
                int numberOfArguments = instruction.getNumericValue();
                for (int i = 0; i <= numberOfArguments; i++) {
                    functionStack.loadIntoMemory(new Word(0), i, MemorySegments.LOCAL);
                }
                break;

            case CALL:
                callFunction(instruction, functionStack, controlFlow, callStack, virtualRam);

                break;

            case RETURN:
                processReturn(functionStack, controlFlow, callStack);
                break;
        }
    }

    private static void processReturn(FunctionStack functionStack, ControlFlow controlFlow, CallStack callStack) {
        Word returnValue = functionStack.pop();
        controlFlow.processReturn(callStack);
        functionStack.push(returnValue);
    }

    private static void callFunction(Instruction instruction, FunctionStack functionStack, ControlFlow controlFlow, CallStack callStack, PseudoAddressSpaceMemory virtualRam) {
        int numberOfFunctionArguments = instruction.getNumericValue();
        String functionName = instruction.getOperand();

        PseudoAddressSpaceMemory arguments = new PseudoAddressSpaceMemory(numberOfFunctionArguments);

        for (int i = 0; i < numberOfFunctionArguments; i++) {
            arguments.setAddress(i, functionStack.pop());
        }

        callStack.push(new FunctionStack(arguments, null, virtualRam, controlFlow.getInstructionPointer(), functionName));

        controlFlow.setInstructionPointerToJumpAddress(functionName);
    }

}
