package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Word;

import java.util.Arrays;

class PseudoMemory {

    private Word[] memory;

    PseudoMemory(int size) {
        this.memory = new Word[size];
    }

    void setAddress(int address, Word variable) {
        resizeMemory(address);
        memory[address] = variable;
    }

    Word getAddress(int address) {
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
