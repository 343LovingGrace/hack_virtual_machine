package virtualMachine.stack.types.instruction;

import static virtualMachine.stack.types.instruction.CommandType.*;

public enum Commands {
    PUSH("push", PUSH_STACK),
    POP("pop", POP_STACK),

    ADD("add", ARITHMETIC),
    SUBTRACT("sub", ARITHMETIC),
    LESS_THAN("lt", ARITHMETIC),
    GREATER_THAN("gt", ARITHMETIC),
    BITWISE_OR("or", ARITHMETIC),
    BITWISE_AND("and", ARITHMETIC),
    BITWISE_EQUALS("eq", ARITHMETIC),

    NOT("not", NEGATION),
    NEGATIVE("neg", NEGATION),

    LABEL("label", CONTROL_FLOW_ACCESS),
    GOTO("goto", CONTROL_FLOW_ACCESS),
    IF_GOTO("if-goto", CONTROL_FLOW_ACCESS),

    FUNCTION("function", MEMORY_ACCESS),
    CALL("call", MEMORY_ACCESS),
    RETURN("return", MEMORY_ACCESS);

    private final String name;
    private final CommandType commandType;

    Commands(String name, CommandType commandType) {
        this.name = name;
        this.commandType = commandType;
    }

    public String getName() {
        return name;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public static Commands getCommandFromValue(String value) {
        for (var command : Commands.values()) {
            if (command.getName().equals(value)) {
                return command;
            }
        }
        throw new RuntimeException("Value not recongnised " + value);
    }
}
