package virtualMachine.stack.memory;

import virtualMachine.stack.types.Word;

public interface Memory {

    void loadIntoMemory(Word variable, int address, MemorySegments segment);

    Word getFromMemory(int address, MemorySegments segment);

}
