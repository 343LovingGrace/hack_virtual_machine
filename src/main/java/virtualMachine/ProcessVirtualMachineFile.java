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

    //to support gotos
    private final List<Instruction> vmInstructions = new ArrayList<>();

    public VMInstructionParser processVmFile(String pathToInput, List<Instruction> initSteps) {
        var vmParser = new VMInstructionParser();
        
        if (initSteps != null && !initSteps.isEmpty()) {
            for (var instruction: initSteps) {
                vmParser.processInstruction(instruction);
            }
        }

        readInput(pathToInput, vmParser);

        return vmParser;
    }

    private void readInput(String pathToInput, VMInstructionParser vmParser) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToInput))) {

            List<String> instructionFile = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                instructionFile.add(line);

                Instruction instruction = getInstructionFromRawInput(line);

                vmParser.processInstruction(instruction);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
