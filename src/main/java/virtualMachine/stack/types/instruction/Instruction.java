package virtualMachine.stack.types.instruction;

public class Instruction {
    private final Commands command;
    private final String operand;
    private final Integer numericValue;

    public Instruction(Commands command, String operand, Integer numericValue) {
        this.command = command;
        this.operand = operand;
        this.numericValue = numericValue;
    }

    public Commands getCommand() {
        return command;
    }

    public String getOperand() {
        return operand;
    }

    public Integer getNumericValue() {
        return numericValue;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "command=" + command +
                ", operand='" + operand + '\'' +
                ", numericValue=" + numericValue +
                '}';
    }
}
