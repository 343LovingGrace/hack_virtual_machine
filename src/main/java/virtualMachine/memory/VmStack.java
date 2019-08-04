package virtualMachine.memory;

import virtualMachine.types.Word;

public interface VmStack {

    void push(Word variable);

    Word pop();
}
