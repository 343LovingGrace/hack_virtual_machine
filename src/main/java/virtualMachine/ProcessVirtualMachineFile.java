package virtualMachine;

import virtualMachine.stack.datawrappers.instruction.Commands;
import virtualMachine.stack.datawrappers.instruction.Instruction;
import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessVirtualMachineFile {

    public VMInstructionParser processVmFile(String pathToInput, List<Instruction> initSteps) {

        List<Instruction> vmInstructions = processRawInputToArrayOfInstructions(pathToInput);

        var vmParser = new VMInstructionParser(vmInstructions);

        if (initSteps != null && !initSteps.isEmpty()) {
            for (var instruction: initSteps) {
                vmParser.processInstruction(instruction);
            }
        }

        vmParser.executeVmInstructions();

        return vmParser;
    }

    private List<Instruction> processRawInputToArrayOfInstructions(String pathToInput) {
        final List<Instruction> vmInstructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToInput))) {

            String line;
            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                vmInstructions.add(getInstructionFromRawInput(line));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vmInstructions;
    }

    public static Instruction getInstructionFromRawInput(String line) {
        Instruction instruction;
        String[] instructionLine = line.split(" ");
        if (instructionLine.length == 1) {
            instruction = new Instruction(Commands.getCommandFromValue(instructionLine[0]), null, null);
        } else if (instructionLine.length == 2) {
            instruction = new Instruction(Commands.getCommandFromValue(instructionLine[0]), instructionLine[1], null);
        } else if (instructionLine.length == 3) {
            instruction = new Instruction(Commands.getCommandFromValue(instructionLine[0]), instructionLine[1], Integer.valueOf(instructionLine[2]));
        } else {
            throw new RuntimeException("Unrecognised instruction format: " + line);
        }
        return instruction;
    }

}
