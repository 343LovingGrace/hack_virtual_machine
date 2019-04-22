package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.SixteenBit;

import java.util.Arrays;
import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;

public class VirtualMemory {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables dynamic and per function
    //argument - methods arguments
    // global - kind of a clunge

    private static final int SIXTEEN_BIT_LENGTH = 32768;
    private static final SixteenBit[] virtualRam = new SixteenBit[SIXTEEN_BIT_LENGTH];

    private final Map<MemorySegments, SixteenBit[]> memory =
            Map.of(
                    THIS, virtualRam,
                    THAT, virtualRam,
                    STATIC, new SixteenBit[100],
                    LOCAL, new SixteenBit[100],
                    ARGUMENT, new SixteenBit[15],
                    GLOBAL, new SixteenBit[10],
                    POINTER, new SixteenBit[2]);


    public void loadIntoMemory(SixteenBit variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        SixteenBit[] memorySegment = memory.get(segment);
        while (address > memorySegment.length) {
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

        if (segment == THIS || segment == THAT) {
            int offset = getFromMemory(segment.getFixedAddress(), POINTER).convertToInteger();
            address += offset;
        }

        var memorySegment = memory.get(segment);
        return memorySegment[address];
    }

}
