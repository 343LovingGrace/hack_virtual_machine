package virtualMachine.stack.vm_instruction_parsing;

import virtualMachine.stack.datawrappers.instruction.Commands;
import virtualMachine.stack.datawrappers.instruction.Instruction;
import virtualMachine.stack.memory.GlobalVirtualMemory;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;

import java.util.List;

import static virtualMachine.stack.datawrappers.instruction.Commands.POP;
import static virtualMachine.stack.datawrappers.instruction.Commands.PUSH;

public class VMInstructionParser {

    private final List<Instruction> vmInstructions;
    private final GlobalVirtualMemory globalVirtualMemory = new GlobalVirtualMemory();
    private final AllInstructionProcessors instructionProcessors = new AllInstructionProcessors();

    public VMInstructionParser(List<Instruction> vmInstructions) {
        this.vmInstructions = vmInstructions;
    }

    public void processInstruction(Instruction instruction) {
        var instructionProcessor = getInstructionProcessorFromCommand(instruction);
        instructionProcessor.processInstruction(instruction, globalVirtualMemory);
    }

    public void processInstructions() {
        //TODO: eventually will allow jumping between instructions
        for (var instruction: vmInstructions) {
            var instructionProcessor = getInstructionProcessorFromCommand(instruction);
            instructionProcessor.processInstruction(instruction, globalVirtualMemory);
        }
    }

    private InstructionProcessor getInstructionProcessorFromCommand(Instruction instruction) {
        Commands command = instruction.getCommand();

        if (command == POP) {
            return instructionProcessors.getPopInstructionProcessor();
        } else if (command == PUSH) {
            return instructionProcessors.getPushInstructionProcessor();
        } else if (Commands.allBinaryCommands().contains(command)) {
            return instructionProcessors.getBinaryInstructionProcessor();
        } else if (Commands.allUnaryCommands().contains(command)) {
            return instructionProcessors.getUnaryInstructionProcessor();
        } else {
            throw new RuntimeException("Unknown instruction: " + instruction);
        }
    }

    public GlobalVirtualMemory getVirtualMemory() {
        return globalVirtualMemory;
    }
}
