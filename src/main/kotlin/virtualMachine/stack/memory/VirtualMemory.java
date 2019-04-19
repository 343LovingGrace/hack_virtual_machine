package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.SixteenBit;

import java.util.Arrays;
import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;

public class VirtualMemory {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables
    //argument - methods arguments
    // global - kind of a clunge

    private final Map<MemorySegments, SixteenBit[]> memory =
            Map.of(
                    THIS, new SixteenBit[100],
                    THAT, new SixteenBit[100],
                    STATIC, new SixteenBit[100],
                    LOCAL, new SixteenBit[100],
                    ARGUMENT, new SixteenBit[100],
                    GLOBAL, new SixteenBit[10],
                    POINTER, new SixteenBit[2]);


    public void loadIntoMemory(SixteenBit variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        var memorySegment = memory.get(segment);
        if (address > memorySegment.length) {
            memorySegment = Arrays.copyOf(memorySegment, memorySegment.length * 2);
        }

        memorySegment[address] = variable;
    }

    private void checkPointerInBounds(int address, MemorySegments segment) {
        if (segment == POINTER && (address < 0 || address > 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public SixteenBit getFromMemory(int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //constants are not loaded into memory
        if (segment == CONSTANT) {
            return new SixteenBit(address);
        }

        var memorySegment = memory.get(segment);
        return memorySegment[address];
    }
}
