package virtualMachine.stack.datawrappers.instruction;

public class Instruction {
    private final Commands command;
    private final String operand;
    private final Integer address;

    public Instruction(Commands command, String operand, Integer address) {
        this.command = command;
        this.operand = operand;
        this.address = address;
    }

    public Commands getCommand() {
        return command;
    }

    public String getOperand() {
        return operand;
    }

    public Integer getAddress() {
        return address;
    }
}
