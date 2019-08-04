package virtualMachine;

import virtualMachine.memory.*;
import virtualMachine.types.Word;
import virtualMachine.types.instruction.Commands;
import virtualMachine.types.instruction.Instruction;
import virtualMachine.types.instruction.StackAccessProcessorLookup;
import virtualMachine.vm_instruction_processing.control_flow_processing.ControlFlow;
import virtualMachine.vm_instruction_processing.memory_access.CallStack;
import virtualMachine.vm_instruction_processing.memory_access.Function;
import virtualMachine.vm_instruction_processing.control_flow_processing.ProgramFlow;
import virtualMachine.vm_instruction_processing.stack_access.StackAccessProcessor;

import java.util.List;

import static virtualMachine.types.instruction.CommandType.CONTROL_FLOW_ACCESS;
import static virtualMachine.types.instruction.CommandType.MEMORY_ACCESS;
import static virtualMachine.types.instruction.Commands.FUNCTION;
import static virtualMachine.types.instruction.Commands.LABEL;

public class VirtualMachine {

    private final List<Instruction> vmInstructions;
    private final PseudoAddressSpaceMemory virtualRam = new PseudoAddressSpaceMemory(8192);
    private final ControlFlow controlFlow = new ControlFlow();
    private final CallStack callStack = new CallStack(virtualRam, controlFlow);

    public VirtualMachine(List<Instruction> vmInstructions) {
        this.vmInstructions = vmInstructions;

        initializeFunctions(vmInstructions);
    }

    public void processInstruction(Instruction instruction) {
        Commands command = instruction.getCommand();

        if (StackAccessProcessorLookup.stackAccessors().contains(command.getCommandType())) {
            StackAccessProcessor stackAccessProcessor = StackAccessProcessorLookup.getProcessor(command.getCommandType());
            stackAccessProcessor.processInstruction(instruction, callStack.peekFirst());

        } else if (MEMORY_ACCESS == command.getCommandType()) {
            Function.processInstruction(instruction, controlFlow, callStack, virtualRam);
        } else if (CONTROL_FLOW_ACCESS == command.getCommandType()) {
            ProgramFlow.processInstruction(instruction, controlFlow, callStack.peekFirst());
        }
    }

    public void executeVmInstructions() {
        final var totalInstructions = vmInstructions.size();

        while (controlFlow.hasNextInstruction(totalInstructions)) {
            int instructionPointer = controlFlow.nextInstruction();

            Instruction instruction = vmInstructions.get(instructionPointer);
            System.out.println(instruction.toString());
            processInstruction(instruction);
        }

    }

    public Word getFromMemory(int address, MemorySegments memorySegments) {
        return callStack.peekFirst().getFromMemory(address, memorySegments);
    }

    public Word pop() {
        return callStack.peekFirst().pop();
    }

    private void initializeFunctions(List<Instruction> vmInstructions) {
        for (int i = 0; i < vmInstructions.size(); i++) {
            var instruction = vmInstructions.get(i);
            if (instruction.getCommand() == FUNCTION
                    || instruction.getCommand() == LABEL) {

                controlFlow.addJumpLocation(instruction.getOperand(), i);
            }
        }
    }
}
