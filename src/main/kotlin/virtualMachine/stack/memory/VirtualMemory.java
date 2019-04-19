package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.SixteenBit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;

public class VirtualMemory {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables

    private final Map<MemorySegments, List<SixteenBit>> memory =
            Map.of(
                    THIS, new ArrayList<>(),
                    THAT, new ArrayList<>(),
                    STATIC, new ArrayList<>(),
                    LOCAL, new ArrayList<>(),
                    POINTER, new ArrayList<>(2));


    public void loadIntoMemory(SixteenBit variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        var memorySegment = memory.get(segment);
        memorySegment.add(address, variable);
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
        return memorySegment.get(address);
    }
}
