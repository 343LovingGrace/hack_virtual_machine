package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.instruction.Instruction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlFlow {

    private int instructionPointer = 0;
    private final Map<String, Integer> labelLocations = new HashMap<>();
    private final Map<String, Integer> functionLocations = new HashMap<>(8);

    public int nextInstruction() {
        return instructionPointer++;
    }

    public boolean hasNextInstruction(List<Instruction> allInstructions) {
        return instructionPointer < allInstructions.size();
    }

    public void addLabel(String label, int iP) {
        labelLocations.put(label, iP);
    }

    public void addFunctionInstructionLocation(String functionName, int pointer) {
        if (functionLocations.containsKey(functionName)) {
            throw new RuntimeException("Duplicate function name, terminating");
        }
        functionLocations.put(functionName, pointer);
    }

    int getFunctionLocation(String functionName) {
        if (functionName.contains(functionName)) {
            return functionLocations.get(functionName);
        }
        return -1;
    }

    public void setInstructionPointerToLabelAddress(String label) {
        if (labelLocations.containsKey(label)) {
            instructionPointer = labelLocations.get(label);
        } else {
            throw new RuntimeException("Label not in map of labelLocations (not been added) " + label);
        }
    }

    public Integer getLabelLocation(String label) {
        if (labelLocations.containsKey(label)) {
            return labelLocations.get(label);
        }
        throw new RuntimeException("Label not in map of labelLocations (not been added) " + label);
    }

}
