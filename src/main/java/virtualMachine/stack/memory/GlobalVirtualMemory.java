package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Word;

import java.util.Arrays;
import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;

public class GlobalVirtualMemory {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables dynamic and per function
    //argument - methods arguments
    // global - kind of a clunge

    //Important: everything shares same pseudo address space - should be fine to assume that they don't though

    private static final int SIXTEEN_BIT_LENGTH = 32768;
    private static final Word[] virtualRam = new Word[SIXTEEN_BIT_LENGTH];
    private static final int STACK_POINTER_OFFSET = 256;
    private int stackPointer = STACK_POINTER_OFFSET;

    private InstructionStack instructionStack = new InstructionStack();

    private final Map<MemorySegments, Word[]> memory =
            Map.of(
                    THIS, virtualRam,
                    THAT, virtualRam,
                    STATIC, new Word[10],
                    LOCAL, new Word[10],
                    ARGUMENT, new Word[15],
                    GLOBAL, new Word[10],
                    POINTER, new Word[] {new Word(0), new Word(1)},
                    GLOBAL_STACK, new Word[100]);


    public void loadIntoMemory(Word variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        Word[] memorySegment = memory.get(segment);
        while (address > memorySegment.length) {
            memorySegment = Arrays.copyOf(memorySegment, memorySegment.length * 2);
        }

        address += getThisThatOffset(segment);

        memorySegment[address] = variable;
    }


    private void checkPointerInBounds(int address, MemorySegments segment) {
        if (segment == POINTER && (address < 0 || address > 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public Word getFromMemory(int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //constants are not loaded into memory
        if (segment == CONSTANT) {
            return new Word(address);
        }

        address += getThisThatOffset(segment);

        var memorySegment = memory.get(segment);
        return memorySegment[address];
    }

    private void decremenetSp() {
        if (stackPointer > STACK_POINTER_OFFSET) {
            stackPointer--;
        }
    }

    public int getStackPointer() {
        return stackPointer;
    }

    public Word popStack() {
        decremenetSp();
        return instructionStack.pop();
    }

    public void pushToStack(Word value) {
        stackPointer++;
        instructionStack.push(value);
        memory.get(GLOBAL_STACK)[stackPointer - STACK_POINTER_OFFSET] = value;
    }

    private int getThisThatOffset(MemorySegments segment) {
        if (segment == THIS || segment == THAT) {
            return getFromMemory(segment.getFixedAddress(), POINTER).convertToInteger();
        }
        return 0;
    }

    public void printStack() {
        instructionStack.printStack();
    }

}
