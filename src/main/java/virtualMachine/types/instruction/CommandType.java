package virtualMachine.types.instruction;

public enum CommandType {
    PUSH_STACK("push_stack"),
    POP_STACK("pop_stack"),
    ARITHMETIC("arithmetic"),
    NEGATION("negation"),
    MEMORY_ACCESS("memory_access"),
    CONTROL_FLOW_ACCESS("control_flow_access");

    private final String token;

    CommandType(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
