package virtualMachine.stack.vm_instruction_parsing;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.instruction.Commands;
import virtualMachine.stack.types.instruction.Instruction;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

import java.util.List;

import static virtualMachine.stack.types.instruction.Commands.FUNCTION;
import static virtualMachine.stack.types.instruction.Commands.LABEL;

public class VmParser {

    private final List<Instruction> vmInstructions;
    private final VirtualMemory virtualMemory = new VirtualMemory();

    public VmParser(List<Instruction> vmInstructions) {
        this.vmInstructions = vmInstructions;

        initializeFunctions(vmInstructions);
    }

    public void processInstruction(Instruction instruction) {
        var instructionProcessor = getInstructionProcessorFromCommand(instruction);
        instructionProcessor.processInstruction(instruction, virtualMemory);
    }

    public void executeVmInstructions() {
        final var totalInstructions = vmInstructions.size();

        while (virtualMemory.getControlFlow().hasNextInstruction(totalInstructions)) {
            int instructionPointer = virtualMemory.getControlFlow().nextInstruction();

            Instruction instruction = vmInstructions.get(instructionPointer);
            System.out.println(instruction.toString());
            var instructionProcessor = getInstructionProcessorFromCommand(instruction);
            instructionProcessor.processInstruction(instruction, virtualMemory);
        }

    }

    private InstructionProcessor getInstructionProcessorFromCommand(Instruction instruction) {
        Commands command = instruction.getCommand();
        return command.getInstructionProcessor();
    }

    /**
     * Only used in tests (candidate for refactoring)
     * @return mutable virtual memory
     */
    public VirtualMemory getVirtualMemory() {
        return virtualMemory;
    }


    private void initializeFunctions(List<Instruction> vmInstructions) {
        for (int i = 0; i < vmInstructions.size(); i++) {
            var instruction = vmInstructions.get(i);
            if (instruction.getCommand() == FUNCTION
                    || instruction.getCommand() == LABEL) {

                virtualMemory.getControlFlow().addJumpLocation(instruction.getOperand(), i);
            }
        }
    }
}
