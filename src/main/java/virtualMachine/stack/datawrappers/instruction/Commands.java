package virtualMachine.stack.datawrappers.instruction;

import java.util.Set;

public enum Commands {
    PUSH("push"),
    POP("pop"),

    ADD("add"),
    SUBTRACT("sub"),
    LESS_THAN("lt"),
    GREATER_THAN("gt"),
    BITWISE_OR("or"),
    BITWISE_AND("and"),
    BITWISE_EQUALS("eq"),
    NOT("not"),
    NEGATIVE("neg"),

    LABEL("label"),
    GOTO("goto"),
    IF_GOTO("if-goto"),

    FUNCTION("function"),
    CALL("call"),
    RETURN("return");

    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Commands getCommandFromValue(String value) {
        for (var command : Commands.values()) {
            if (command.getName().equals(value)) {
                return command;
            }
        }
        throw new RuntimeException("Value not recongnised " + value);
    }

    public static Set<Commands> allBinaryCommands() {
        return Set.of(ADD, SUBTRACT, BITWISE_EQUALS, GREATER_THAN, LESS_THAN, BITWISE_AND, BITWISE_OR);
    }

    public static Set<Commands> allUnaryCommands() {
        return Set.of(NEGATIVE, NOT);
    }

    public static Set<Commands> allFunctionCommands() {
        return Set.of(FUNCTION, CALL, RETURN);
    }

    public static Set<Commands> programFlowCommands() {
        return Set.of(LABEL, GOTO, IF_GOTO);
    }
}
