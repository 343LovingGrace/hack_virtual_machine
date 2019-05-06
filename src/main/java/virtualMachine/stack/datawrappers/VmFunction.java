package virtualMachine.stack.datawrappers;

public class VmFunction {
    private final String name;
    private final byte arguments;

    public VmFunction(String name, byte arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public byte getArguments() {
        return arguments;
    }
}
