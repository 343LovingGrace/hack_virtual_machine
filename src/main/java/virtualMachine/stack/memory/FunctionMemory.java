package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Memory;
import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;

public class FunctionMemory implements Memory, VmStack {

    private final VmStack workingStack = new LocalStack();
    private final GlobalStack globalStack;
    private final PseudoMemory programHeap;

    private final int locFunctionCalledFrom;
    private final String name;

    private final Map<MemorySegments, PseudoMemory> memorySegmentMemoryMap =
            Map.of(
                    STATIC, new PseudoMemory(16),
                    LOCAL, new PseudoMemory(16),
                    ARGUMENT, new PseudoMemory(16),
                    TEMP, new PseudoMemory(16),
                    POINTER, new PseudoMemory(2));

    FunctionMemory(GlobalStack globalStack, PseudoMemory programHeap, int locCalledFrom, String name) {
        this.globalStack = globalStack;
        this.programHeap = programHeap;
        this.locFunctionCalledFrom = locCalledFrom;
        this.name = name;
    }

    FunctionMemory(PseudoMemory arguments, PseudoMemory staticVariables, GlobalStack globalStack, PseudoMemory programHeap,
                   int locCalledFrom, String name) {
        initMemorySegment(arguments, ARGUMENT);
        initMemorySegment(staticVariables, STATIC);
        this.globalStack = globalStack;
        this.programHeap = programHeap;
        this.locFunctionCalledFrom = locCalledFrom;
        this.name = name;
    }

    private void initMemorySegment(PseudoMemory variables, MemorySegments memorySegment) {
        if (variables == null) return;

        if (variables.getLength() > 50) {
            throw new RuntimeException("Too many arguments (>50) provided aborting...");
        }
        for (int i = 0; i < variables.getLength(); i++) {
            loadIntoMemory(variables.getAddress(i), i, memorySegment);
        }
    }

    @Override
    public void loadIntoMemory(Word variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //THIS/THAT kind of special cases - may look at refactoring this whole memory access approach ...
        if (segment == THIS || segment == THAT) {
            address += getThisThatOffset(segment);
            programHeap.setAddress(address, variable);
        } else {
            var segmentMemory = memorySegmentMemoryMap.get(segment);
            segmentMemory.setAddress(address, variable);
        }

    }

    @Override
    public Word getFromMemory(int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //constants are not loaded into memory
        if (segment == CONSTANT) {
            return new Word(address);
        }

        if (segment == THIS || segment == THAT) {
            address += getThisThatOffset(segment);
            return programHeap.getAddress(address);
        }

        var segmentMemory = memorySegmentMemoryMap.get(segment);
        return segmentMemory.getAddress(address);
    }


    private void checkPointerInBounds(int address, MemorySegments segment) {
        if (segment == POINTER && (address < 0 || address > 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getThisThatOffset(MemorySegments segment) {
        if (segment == THIS) {
            return getFromMemory(0, POINTER).convertToInteger();
        } else {
            return getFromMemory(1, POINTER).convertToInteger();
        }
    }

    @Override
    public void push(Word variable) {
        workingStack.push(variable);
        globalStack.push(variable);
    }

    @Override
    public Word pop() {
        globalStack.decrementStackPointer();
        return workingStack.pop();
    }

    int getLocFunctionCalledFrom() {
        return locFunctionCalledFrom;
    }

    private class LocalStack implements VmStack {

        private final Deque<Word> localStack = new ArrayDeque<>();

        @Override
        public void push(Word variable) {
            localStack.push(variable);
        }

        @Override
        public Word pop() {
            return localStack.pop();
        }
    }

}
