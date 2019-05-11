package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.VmStack;
import virtualMachine.stack.datawrappers.Word;

import java.util.ArrayDeque;
import java.util.Deque;

public class LocalStack implements VmStack {
    private final Deque<Word> localStack = new ArrayDeque<>();

    @Override
    public void push(Word variable) {
        localStack.push(variable);
    }

    @Override
    public Word pop() {
        return localStack.pop();
    }
}
