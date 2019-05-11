package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

import java.util.Arrays;

public class GlobalStack implements VmStack {
    private int globalStackPointer;
    private Word[] globalStack = new Word[2056];

    @Override
    public void push(Word variable) {
        globalStack[globalStackPointer] = variable;
        incrementStackPointer();
    }

    @Override
    public Word pop() {
        var toReturn = globalStack[globalStackPointer];
        decrementStackPointer();
        return toReturn;
    }

    public int getGlobalStackPointer() {
        return globalStackPointer;
    }

    public void decrementStackPointer() {
        if (globalStackPointer > 0) {
            globalStackPointer--;
        }
    }

    private void incrementStackPointer() {
        globalStackPointer++;
        if (globalStackPointer > globalStack.length) {
            globalStack = Arrays.copyOf(globalStack, globalStack.length * 2);
        }
    }

}
