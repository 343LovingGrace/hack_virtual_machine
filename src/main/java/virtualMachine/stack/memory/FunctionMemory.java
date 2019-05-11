package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Memory;
import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;

public class FunctionMemory implements Memory, VmStack {

    private final VmStack workingStack = new LocalStack();
    //can try and be clever with not overusing memory later
    private final Word[] virtualRam = new Word[256];
    private final GlobalStack globalStack;
    private int globalStackPointer;
    private final Word[] programHeap;

    //THIS/THAT will access the SAME programHeap memory as the 'global' memory segment

    private final Map<MemorySegments, Integer> memorySegmentPointerMap =
            Map.of(
                    STATIC, 0,
                    LOCAL, 50,
                    ARGUMENT, 100,
                    TEMP, 150,
                    POINTER, 200);

    FunctionMemory(Word[] arguments, Word[] staticVariables, GlobalStack globalStack, Word[] programHeap) {
        initMemorySegment(arguments, ARGUMENT);
        initMemorySegment(staticVariables, STATIC);
        this.globalStack = globalStack;
        this.programHeap = programHeap;
    }

    private void initMemorySegment(Word[] variables, MemorySegments memorySegment) {
        if (variables == null) return;

        if (variables.length > 50) {
            throw new RuntimeException("Too many arguments (>50) provided aborting...");
        }
        for (int i = 0; i < 50; i++) {
            loadIntoMemory(variables[i], memorySegmentPointerMap.get(memorySegment) + i, memorySegment);
        }
    }

    @Override
    public void loadIntoMemory(Word variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //THIS/THAT kind of special cases - may look at refactoring this whole memory access approach ...
        if (segment == THIS || segment == THAT) {
            address += getThisThatOffset(segment);
            programHeap[address] = variable;
        } else {
            address += memorySegmentPointerMap.get(segment);
            virtualRam[address] = variable;
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
            return programHeap[address];
        }

        address += memorySegmentPointerMap.get(segment);
        return virtualRam[address];
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

}
