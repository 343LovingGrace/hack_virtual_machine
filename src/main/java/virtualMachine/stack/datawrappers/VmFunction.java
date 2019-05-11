package virtualMachine.stack.datawrappers;

public class VmFunction {
    private final String name;
    private final int arguments;

    public VmFunction(String name, int arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public int getArguments() {
        return arguments;
    }
}
