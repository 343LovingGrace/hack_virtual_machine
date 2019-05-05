package virtualMachine.stack.vm_instruction_parsing;

import virtualMachine.stack.datawrappers.instruction.Commands;
import virtualMachine.stack.datawrappers.instruction.Instruction;
import virtualMachine.stack.memory.GlobalVirtualMemory;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.BinaryInstructionProcessor;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.PopInstructionProcessor;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.PushInstructionProcessor;
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.UnaryInstructionProcessor;

import java.util.List;

import static virtualMachine.stack.datawrappers.instruction.Commands.POP;
import static virtualMachine.stack.datawrappers.instruction.Commands.PUSH;

public class VMInstructionParser {

    private final List<Instruction> vmInstructions;
    private final GlobalVirtualMemory globalVirtualMemory = new GlobalVirtualMemory();

    public VMInstructionParser(List<Instruction> vmInstructions) {
        this.vmInstructions = vmInstructions;
    }

    public void processInstruction(Instruction instruction) {
        var instructionProcessor = getInstructionProcessorFromCommand(instruction);
        instructionProcessor.processInstruction(instruction, globalVirtualMemory);
    }

    public void processInstructions() {
        for (var instruction: vmInstructions) {
            var instructionProcessor = getInstructionProcessorFromCommand(instruction);
            instructionProcessor.processInstruction(instruction, globalVirtualMemory);
        }
    }

    private InstructionProcessor getInstructionProcessorFromCommand(Instruction instruction) {
        Commands command = instruction.getCommand();

        if (command == POP) {
            return new PopInstructionProcessor();
        } else if (command == PUSH) {
            return new PushInstructionProcessor();
        } else if (Commands.allBinaryCommands().contains(command)) {
            return new BinaryInstructionProcessor();
        } else if (Commands.allUnaryCommands().contains(command)) {
            return new UnaryInstructionProcessor();
        } else {
            throw new RuntimeException("Unknown instruction: " + instruction);
        }
    }

    public GlobalVirtualMemory getVirtualMemory() {
        return globalVirtualMemory;
    }
}
