package virtualMachine.stack.memory;

import virtualMachine.stack.types.Word;

public interface VmStack {

    void push(Word variable);

    Word pop();
}
