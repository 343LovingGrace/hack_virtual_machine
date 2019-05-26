package virtualMachine.stack.memory;

import java.util.HashMap;
import java.util.Map;

public class ControlFlow {

    private int instructionPointer = 0;
    private final Map<String, Integer> jumpLocations = new HashMap<>();

    public int nextInstruction() {
        return instructionPointer++;
    }

    public boolean hasNextInstruction(int allInstructions) {
        return instructionPointer < allInstructions ;
    }

    public void addJumpLocation(String functionName, int pointer) {
        if (jumpLocations.containsKey(functionName)) {
            int existingPointer = jumpLocations.get(functionName);
            if (existingPointer == pointer) {
                throw new RuntimeException("Cannot have a function with 2 addresses");
            }
        }
        jumpLocations.put(functionName, pointer);
    }

    public void setInstructionPointerToJumpAddress(String label) {
        if (jumpLocations.containsKey(label)) {
            instructionPointer = jumpLocations.get(label);
        } else {
            throw new RuntimeException("Label not in map of labelLocations (not been added) " + label);
        }
    }

    void processReturn(int jumpLocation) {
        instructionPointer = jumpLocation;
    }

    public Integer getJumpLocation(String label) {
        if (jumpLocations.containsKey(label)) {
            return jumpLocations.get(label);
        }
        throw new RuntimeException("Label not in map of labelLocations (not been added) " + label);
    }

    public int getInstructionPointer() {
        return instructionPointer;
    }
}
