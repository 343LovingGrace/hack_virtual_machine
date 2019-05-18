package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Memory;
import virtualMachine.stack.datawrappers.VmFunction;
import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static virtualMachine.stack.memory.MemorySegments.*;

public final class GlobalVirtualMemory implements Memory, VmStack {

    private final PseudoMemory virtualRam = new PseudoMemory(8192);
    private final GlobalStack globalStack = new GlobalStack();
    private final Deque<VmFunction> callStack = new ArrayDeque<>();
    private final ControlFlow controlFlow = new ControlFlow();

    private final Map<String, FunctionMemory> functions = new HashMap<>();

    //should be specified in normal vm files but if not, supply an empty function
    public GlobalVirtualMemory() {
        functions.put("sys.init", new FunctionMemory(null, null, globalStack, virtualRam));
        callStack.push(new VmFunction("sys.init", 0));
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
        globalStack.push(variable);
    }

    @Override
    public Word pop() {
        globalStack.decrementStackPointer();
        FunctionMemory function = getFunctionMemory();
        return function.pop();
    }


    public void callFunction(String functionName, int numberArguments) {
        var arguments = new PseudoMemory(numberArguments);
        var function = getFunctionMemory();

        for (int i = 0; i < numberArguments; i++) {
            arguments.setAddress(i, function.pop());
        }

        final int stackPointer = getGlobalStackPointer();
        saveStateOnGlobalStack(function, stackPointer);

        functions.put(functionName, new FunctionMemory(arguments, null, globalStack, virtualRam));
        callStack.push(new VmFunction(functionName, controlFlow.getInstructionPointer()));

        controlFlow.setInstructionPointerToJumpAddress(functionName);
    }

    private void saveStateOnGlobalStack(FunctionMemory function, int returnAddress) {
        globalStack.push(new Word(returnAddress));
        //save this and that to global stack
        Word thisPointer = function.getFromMemory(0, POINTER);
        Word thatPointer = function.getFromMemory(1, POINTER);

        globalStack.push(thisPointer == null ? new Word(0) : thisPointer);
        globalStack.push(thatPointer == null ? new Word(0) : thatPointer);
    }

    public void returnFromFunction() {
        var returnValue = pop();
        controlFlow.processReturn(callStack);
        //need to actual return a result and set it as an argument (from local stack)
        push(returnValue);
    }

    public ControlFlow getControlFlow() {
        return controlFlow;
    }

    private int getGlobalStackPointer() {
        return globalStack.getGlobalStackPointer();
    }

    public Word popGlobalStack() {
        return globalStack.pop();
    }

    public void pushToCallStack(VmFunction functionName) {
        callStack.add(functionName);
    }


    private FunctionMemory getFunctionMemory() {
        var currentFunction = callStack.peek();
        requireNonNull(currentFunction, "Invalid state reached: there must be a currently called function");

        return functions.get(currentFunction.getName());
    }
}
