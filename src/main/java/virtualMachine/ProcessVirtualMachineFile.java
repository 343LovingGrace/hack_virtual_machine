package virtualMachine;

import virtualMachine.stack.vm_instruction_parsing.VMInstructionParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ProcessVirtualMachineFile {

    public VMInstructionParser processVmFile(String pathToInput, List<String> initSteps) {
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
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                vmParser.processInstruction(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
