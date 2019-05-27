package virtualMachine.stack.memory;

import virtualMachine.stack.types.Word;

import java.util.*;

import static java.util.Objects.requireNonNull;

public final class VirtualMemory implements Memory, VmStack {

    private final PseudoMemory virtualRam = new PseudoMemory(8192);
    //given how i've implemented this does is there any point having a global stack?!?!
    private final Deque<FunctionMemory> callStack = new ArrayDeque<>();
    private final ControlFlow controlFlow = new ControlFlow();

    //should be specified in normal vm files but if not, supply an empty function
    public VirtualMemory() {
        callStack.add(new FunctionMemory(virtualRam, controlFlow.getInstructionPointer(), "Sys.init"));
    }

    @Override
    public void loadIntoMemory(Word variable, int address, MemorySegments segment) {
        FunctionMemory functionMemory = getFunctionMemory();
        functionMemory.loadIntoMemory(variable, address, segment);
    }


    @Override
    public Word getFromMemory(int address, MemorySegments segment) {
        FunctionMemory functionMemory = getFunctionMemory();
        return functionMemory.getFromMemory(address, segment);
    }

    @Override
    public void push(Word variable) {
        FunctionMemory function = getFunctionMemory();
        function.push(variable);
    }

    @Override
    public Word pop() {
        FunctionMemory function = getFunctionMemory();
        return function.pop();
    }


    public void callFunction(String functionName, int numberArguments) {
        var arguments = new PseudoMemory(numberArguments);
        var function = getFunctionMemory();

        for (int i = 0; i < numberArguments; i++) {
            arguments.setAddress(i, function.pop());
        }

        callStack.push(new FunctionMemory(arguments, null, virtualRam, controlFlow.getInstructionPointer(), functionName));

        controlFlow.setInstructionPointerToJumpAddress(functionName);
    }

    public void returnFromFunction() {
        var returnValue = pop();
        controlFlow.setNextAddress(callStack);
        //need to actual return a result and set it as an argument (from local stack)
        push(returnValue);

        //TODO pop arguments and local vars back off the stack
    }

    public ControlFlow getControlFlow() {
        return controlFlow;
    }


    public void pushToCallStack(String functionName) {
        callStack.add(new FunctionMemory(virtualRam, controlFlow.getInstructionPointer(), functionName));
    }


    private FunctionMemory getFunctionMemory() {
        var currentFunction = callStack.peek();
        requireNonNull(currentFunction, "Invalid state reached: there must be a currently called function");

        return currentFunction;
    }
}
