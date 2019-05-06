package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.VmFunction;
import virtualMachine.stack.datawrappers.Word;
import virtualMachine.stack.datawrappers.instruction.Instruction;

import java.util.*;

import static virtualMachine.stack.memory.MemorySegments.*;

//TODO: becoming a god class watch out
public class GlobalVirtualMemory {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables dynamic and per function
    //argument - methods arguments
    // global - kind of a clunge

    //Important: everything shares same pseudo address space - should be fine to assume that they don't though

    private static final int RESERVED_MEMORY = 28000;
    private int stackPointer = 0;
    private int instructionPointer = 0;

    private final Word[] virtualRam = new Word[32768];
    private final Word[] globalStack = new Word[2048];
    private final WorkingStack workingStack = new WorkingStack();
    private final Deque<VmFunction> callStack = new ArrayDeque<>();
    private final Map<String, Integer> labelLocations = new HashMap<>();
    private final Map<String, Integer> functionLocations = new HashMap<>(8);

    private final Map<MemorySegments, Integer> memorySegmentPointerMap =
            Map.of(
                    STATIC, RESERVED_MEMORY,
                    LOCAL, RESERVED_MEMORY + 500,
                    ARGUMENT, RESERVED_MEMORY + 1000,
                    GLOBAL, RESERVED_MEMORY + 1500,
                    POINTER, virtualRam.length - 2);


    public void loadIntoMemory(Word variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //THIS/THAT kind of special cases - may look at refactoring this whole memory access approach ...
        if (segment == THIS || segment == THAT) {
            address += getThisThatOffset(segment);
            if (address > RESERVED_MEMORY) {
                throw new RuntimeException("Heap memory overwriting reserved memory");
            }
        } else {
            address += memorySegmentPointerMap.get(segment);
        }

        virtualRam[address] = variable;
    }


    public Word getFromMemory(int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //constants are not loaded into memory
        if (segment == CONSTANT) {
            return new Word(address);
        }

        if (segment == THIS || segment == THAT) {
            address += getThisThatOffset(segment);
        } else {
            address += memorySegmentPointerMap.get(segment);
        }

        return virtualRam[address];
    }


    private void checkPointerInBounds(int address, MemorySegments segment) {
        if (segment == POINTER && (address < 0 || address > 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void decrementStackPointer() {
        if (stackPointer > 0) {
            stackPointer--;
        }
    }

    public int getStackPointer() {
        return stackPointer;
    }

    public Word popStack() {
        decrementStackPointer();
        return workingStack.pop();
    }

    public void pushToStack(Word value) {
        stackPointer++;
        workingStack.push(value);
        globalStack[stackPointer] = value;
    }

    private int getThisThatOffset(MemorySegments segment) {
        if (segment == THIS) {
            return getFromMemory(0, POINTER).convertToInteger();
        } else {
            return getFromMemory(1, POINTER).convertToInteger();
        }
    }

    public void pushToGlobalStack(Word toAdd) {
        globalStack[stackPointer] = toAdd;
    }

    public Word getFromGlobalStack(int addressToPop) {
        return globalStack[addressToPop];
    }
    
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

    public void pushToCallStack(VmFunction functionName) {
        callStack.add(functionName);
    }

    public VmFunction popCallStack() {
        return callStack.pop();
    }
}
