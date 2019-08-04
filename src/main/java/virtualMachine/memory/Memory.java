package virtualMachine.memory;

import virtualMachine.types.Word;

public interface Memory {

    void loadIntoMemory(Word variable, int address, MemorySegments segment);

    Word getFromMemory(int address, MemorySegments segment);

}
