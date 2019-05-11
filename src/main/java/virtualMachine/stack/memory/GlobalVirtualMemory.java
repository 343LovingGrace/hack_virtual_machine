package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Memory;
import virtualMachine.stack.datawrappers.VmFunction;
import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

import java.util.*;

import static virtualMachine.stack.memory.MemorySegments.*;

public final class GlobalVirtualMemory implements Memory, VmStack {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables dynamic and per function
    //argument - methods arguments
    // temp - kind of a clunge

    //Important: everything shares same pseudo address space - should be fine to assume that they don't though

    //TODO: when calling a function need to store variables on global stack
    //TODO: when returning from a function need to restore local variables

    private final Word[] virtualRam = new Word[16384];
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
        var function = getFunctionMemory();
        function.push(variable);
        globalStack.push(variable);
    }

    @Override
    public Word pop() {
        globalStack.decrementStackPointer();
        var function = getFunctionMemory();
        return function.pop();
    }


    public void callFunction(String functionName, int numberArguments) {
        var arguments = new Word[numberArguments];
        int argumentAddress = 0;
        var function = getFunctionMemory();

        while (argumentAddress < numberArguments) {
            arguments[0] = function.pop();
            argumentAddress++;
        }

        if (controlFlow.getFunctionLocation(functionName) == -1) {
            throw new RuntimeException("Called function does not exist, aborting");
        }

        functions.put(functionName, new FunctionMemory(arguments, null, globalStack, virtualRam));
    }

    public ControlFlow getControlFlow() {
        return controlFlow;
    }

    public int getGlobalStackPointer() {
        return globalStack.getGlobalStackPointer();
    }

    public Word popGlobalStack() {
        return globalStack.pop();
    }

    public void pushToCallStack(VmFunction functionName) {
        callStack.add(functionName);
    }

    public VmFunction popCallStack() {
        return callStack.pop();
    }

    private FunctionMemory getFunctionMemory() {
        var currentFunction = callStack.peek();
        Objects.requireNonNull(currentFunction, "Invalid state reached: there must be a currently called function");

        return functions.get(currentFunction.getName());
    }
}
