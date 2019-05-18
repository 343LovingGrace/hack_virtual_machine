package virtualMachine.stack.datawrappers;

public class VmFunction {
    private final String name;
    private final int calledFrom;

    public VmFunction(String name, int locCalledFrom) {
        this.name = name;
        this.calledFrom = locCalledFrom;
    }

    public String getName() {
        return name;
    }

    public int getCalledFrom() {
        return calledFrom;
    }
}
