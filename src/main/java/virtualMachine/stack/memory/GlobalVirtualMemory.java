package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Memory;
import virtualMachine.stack.datawrappers.VmFunction;
import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

import java.util.*;

import static virtualMachine.stack.memory.MemorySegments.*;

//TODO: becoming a god class watch out
public final class GlobalVirtualMemory implements Memory {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables dynamic and per function
    //argument - methods arguments
    // temp - kind of a clunge

    //Important: everything shares same pseudo address space - should be fine to assume that they don't though

    //TODO: when calling a function need to store variables on global stack
    //TODO: when returning from a function need to restore local variables

    private static final int RESERVED_MEMORY = 28000;
//    private int globalStackPointer = 0;

    private final Word[] virtualRam = new Word[32768];
    private final GlobalStack globalStack = new GlobalStack();
    private final VmStack workingStack = new LocalStack();
    private final Deque<VmFunction> callStack = new ArrayDeque<>();
    private final ControlFlow controlFlow = new ControlFlow();

    private final Map<MemorySegments, Integer> memorySegmentPointerMap =
            Map.of(
                    STATIC, RESERVED_MEMORY,
                    LOCAL, RESERVED_MEMORY + 500,
                    ARGUMENT, RESERVED_MEMORY + 1000,
                    TEMP, RESERVED_MEMORY + 1500,
                    POINTER, virtualRam.length - 2);


    public void loadIntoMemory(Word variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //THIS/THAT kind of special cases - may look at refactoring this whole memory access approach ...
        if (segment == THIS || segment == THAT) {
            address += getThisThatOffset(segment);
            if (address > RESERVED_MEMORY) {
                throw new RuntimeException("Heap memory overwriting reserved memory");
            }
        } else {
            address += memorySegmentPointerMap.get(segment);
        }

        virtualRam[address] = variable;
    }


    public Word getFromMemory(int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //constants are not loaded into memory
        if (segment == CONSTANT) {
            return new Word(address);
        }

        if (segment == THIS || segment == THAT) {
            address += getThisThatOffset(segment);
        } else {
            address += memorySegmentPointerMap.get(segment);
        }

        return virtualRam[address];
    }


    private void checkPointerInBounds(int address, MemorySegments segment) {
        if (segment == POINTER && (address < 0 || address > 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public Word popStack() {
        globalStack.decrementStackPointer();
        return workingStack.pop();
    }

    public void pushToStack(Word value) {
        workingStack.push(value);
        globalStack.push(value);
    }

    private int getThisThatOffset(MemorySegments segment) {
        if (segment == THIS) {
            return getFromMemory(0, POINTER).convertToInteger();
        } else {
            return getFromMemory(1, POINTER).convertToInteger();
        }
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
}
