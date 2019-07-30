package virtualMachine.stack.memory;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

//TODO : move to VirtualMachine
public class ControlFlow {

    private int instructionPointer = 0;
    private final Map<String, Integer> jumpLocations = new HashMap<>();

    public int nextInstruction() {
        return instructionPointer++;
    }

    public boolean hasNextInstruction(int allInstructions) {
        return instructionPointer < allInstructions ;
    }

    public void addJumpLocation(String labelName, int pointer) {
        if (jumpLocations.containsKey(labelName)) {
            int existingPointer = jumpLocations.get(labelName);
            if (existingPointer == pointer) {
                throw new RuntimeException("Cannot have a function with 2 addresses");
            }
        }
        jumpLocations.put(labelName, pointer);
    }

    public void setInstructionPointerToJumpAddress(String label) {
        if (jumpLocations.containsKey(label)) {
            instructionPointer = jumpLocations.get(label);
        } else {
            throw new RuntimeException("Label not in map of labelLocations (not been added) " + label);
        }
    }

    void processReturn(Deque<FunctionMemory> callStack) {
        var function = callStack.pop();
        instructionPointer = function.getLocFunctionCalledFrom();
    }

    public Integer getJumpLocation(String label) {
        if (jumpLocations.containsKey(label)) {
            return jumpLocations.get(label);
        }
        throw new RuntimeException("Label not in map of labelLocations (not been added) " + label);
    }

    int getInstructionPointer() {
        return instructionPointer;
    }
}
