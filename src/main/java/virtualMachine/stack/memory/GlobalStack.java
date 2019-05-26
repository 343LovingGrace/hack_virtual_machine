package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

public class GlobalStack implements VmStack {
    private int globalStackPointer = 0;
    private final PseudoMemory globalStack = new PseudoMemory(2056);

    @Override
    public void push(Word variable) {
        globalStack.setAddress(globalStackPointer, variable);
        incrementStackPointer();
    }

    @Override
    public Word pop() {
        var toReturn = globalStack.getAddress(globalStackPointer);
        decrementStackPointer();
        return toReturn;
    }

    int getGlobalStackPointer() {
        return globalStackPointer;
    }

    void decrementStackPointer() {
        if (globalStackPointer > 0) {
            globalStackPointer--;
        }
    }

    private void incrementStackPointer() {
        globalStackPointer++;
    }

}
