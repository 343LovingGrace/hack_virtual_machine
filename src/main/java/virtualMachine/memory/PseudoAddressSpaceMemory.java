package virtualMachine.memory;

import virtualMachine.types.Word;

import java.util.Arrays;

public class PseudoAddressSpaceMemory {

    private Word[] memory;

    public PseudoAddressSpaceMemory(int size) {
        this.memory = new Word[size];
    }

    public void setAddress(int address, Word variable) {
        resizeMemory(address);
        memory[address] = variable;
    }

    public Word getAddress(int address) {
        if (address >= memory.length) {
            throw new RuntimeException("Segmentation fault");
        }
        return memory[address];
    }

    int getLength() {
        return memory.length;
    }

    private void resizeMemory(int address) {
        while (address >= memory.length) {
            memory = Arrays.copyOf(memory, memory.length * 2);
        }
    }
}
