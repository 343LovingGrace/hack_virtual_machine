package virtualMachine.stack.datawrappers;

import virtualMachine.stack.memory.MemorySegments;

public interface Memory {

    void loadIntoMemory(Word variable, int address, MemorySegments segment);


    Word getFromMemory(int address, MemorySegments segment);

}
