package virtualMachine.memory;

import virtualMachine.types.Word;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static virtualMachine.memory.MemorySegments.*;

/**
 * Stores variables in a working stack, for addition etc. and also stores persistent variables, e.g. function arguments
 * in pseudo memory, and has references to the main vm heap
 */
public class FunctionStack implements Memory, VmStack {

    private final VmStack workingStack = new LocalStack();
    private final PseudoAddressSpaceMemory programHeap;

    private final int locFunctionCalledFrom;
    private final String name;

    private final Map<MemorySegments, PseudoAddressSpaceMemory> memorySegmentMemoryMap =
            Map.of(
                    STATIC, new PseudoAddressSpaceMemory(16),
                    LOCAL, new PseudoAddressSpaceMemory(16),
                    ARGUMENT, new PseudoAddressSpaceMemory(16),
                    TEMP, new PseudoAddressSpaceMemory(16),
                    POINTER, new PseudoAddressSpaceMemory(2));

    public FunctionStack(PseudoAddressSpaceMemory programHeap, int locCalledFrom, String name) {
        this.programHeap = programHeap;
        this.locFunctionCalledFrom = locCalledFrom;
        this.name = name;
    }

    public FunctionStack(PseudoAddressSpaceMemory arguments, PseudoAddressSpaceMemory staticVariables, PseudoAddressSpaceMemory programHeap,
                  int locCalledFrom, String name) {
        initMemorySegment(arguments, ARGUMENT);
        initMemorySegment(staticVariables, STATIC);
        this.programHeap = programHeap;
        this.locFunctionCalledFrom = locCalledFrom;
        this.name = name;
    }

    private void initMemorySegment(PseudoAddressSpaceMemory variables, MemorySegments memorySegment) {
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

        PseudoAddressSpaceMemory segmentMemory = memorySegmentMemoryMap.get(segment);
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
    }

    @Override
    public Word pop() {
        return workingStack.pop();
    }

    public int getLocFunctionCalledFrom() {
        return locFunctionCalledFrom;
    }

    public String getName() {
        return name;
    }

    private static class LocalStack implements VmStack {

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
