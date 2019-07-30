package virtualMachine.stack.types.instruction;

import virtualMachine.vm_instruction_processing.InstructionProcessor;
import virtualMachine.vm_instruction_processing.impl.FunctionProcessor;
import virtualMachine.vm_instruction_processing.impl.BinaryInstructionProcessor;
import virtualMachine.vm_instruction_processing.impl.PopInstructionProcessor;
import virtualMachine.vm_instruction_processing.impl.PushInstructionProcessor;
import virtualMachine.vm_instruction_processing.impl.UnaryInstructionProcessor;
import virtualMachine.vm_instruction_processing.impl.ProgramFlowProcessor;

public enum Commands {
    PUSH("push", new PushInstructionProcessor()),
    POP("pop", new PopInstructionProcessor()),

    ADD("add", new BinaryInstructionProcessor()),
    SUBTRACT("sub", new BinaryInstructionProcessor()),
    LESS_THAN("lt", new BinaryInstructionProcessor()),
    GREATER_THAN("gt", new BinaryInstructionProcessor()),
    BITWISE_OR("or", new BinaryInstructionProcessor()),
    BITWISE_AND("and", new BinaryInstructionProcessor()),
    BITWISE_EQUALS("eq", new BinaryInstructionProcessor()),

    NOT("not", new UnaryInstructionProcessor()),
    NEGATIVE("neg", new UnaryInstructionProcessor()),

    LABEL("label", new ProgramFlowProcessor()),
    GOTO("goto", new ProgramFlowProcessor()),
    IF_GOTO("if-goto", new ProgramFlowProcessor()),

    FUNCTION("function", new FunctionProcessor()),
    CALL("call", new FunctionProcessor()),
    RETURN("return", new FunctionProcessor());

    private final String name;
    private final InstructionProcessor instructionProcessor;

    Commands(String name, InstructionProcessor instructionProcessor) {
        this.name = name;
        this.instructionProcessor = instructionProcessor;
    }

    public String getName() {
        return name;
    }

    public InstructionProcessor getInstructionProcessor() {
        return instructionProcessor;
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
