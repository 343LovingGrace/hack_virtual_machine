package virtualMachine.stack.datawrappers;

public interface VmStack {
    void push(Word variable);

    Word pop();
}
