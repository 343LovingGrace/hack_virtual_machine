package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.VmFunction;
import virtualMachine.stack.datawrappers.Word;
import virtualMachine.stack.datawrappers.instruction.Instruction;

import java.util.*;

import static virtualMachine.stack.memory.MemorySegments.*;

public class GlobalVirtualMemory {

    //Pointer -> a 2 entry segment that holds the addresses of the this and that segments
    //this, that pseudo heap memory
    //static => constants shared across all vm files (i.e. classes)
    //local => stores a functions local variables dynamic and per function
    //argument - methods arguments
    // global - kind of a clunge

    //Important: everything shares same pseudo address space - should be fine to assume that they don't though

    private static final Word[] virtualRam = new Word[32768];
    private int stackPointer = 0;
    private int instructionPointer = 0;
    private Map<String, Integer> labels = new HashMap<>();

    private Word[] globalStack = new Word[2048]; //todo
    private InstructionStack instructionStack = new InstructionStack();
    private Deque<VmFunction> callStack = new ArrayDeque<>();

    //pointers are never updated!
    private final Map<MemorySegments, Integer> memorySegmentPointerMap =
            Map.of(
                    THIS, 0,
                    THAT, 0,
                    STATIC, 4000,
                    LOCAL, 5000,
                    ARGUMENT, 6000,
                    GLOBAL, 7000,
                    POINTER, 32766);


    public void loadIntoMemory(Word variable, int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        address += memorySegmentPointerMap.get(segment);
        address += getThisThatOffset(segment);

        virtualRam[address] = variable;
    }

    private void checkPointerInBounds(int address, MemorySegments segment) {
        if (segment == POINTER && (address < 0 || address > 1)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public Word getFromMemory(int address, MemorySegments segment) {
        checkPointerInBounds(address, segment);

        //constants are not loaded into memory
        if (segment == CONSTANT) {
            return new Word(address);
        }

        address += getThisThatOffset(segment);
        address += memorySegmentPointerMap.get(segment);
        return virtualRam[address];
    }

    private void decremenetSp() {
        if (stackPointer > 0) {
            stackPointer--;
        }
    }

    public int getStackPointer() {
        return stackPointer;
    }

    public Word popStack() {
        decremenetSp();
        return instructionStack.pop();
    }

    public void pushToStack(Word value) {
        stackPointer++;
        instructionStack.push(value);
        globalStack[stackPointer] = value;
    }

    private int getThisThatOffset(MemorySegments segment) {
        if (segment == THIS) {
            return getFromMemory(0, POINTER).convertToInteger();
        } else if (segment == THAT) {
            return getFromMemory(1, POINTER).convertToInteger();
        }
        return 0;
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

    public void addLabel(String label) {
        labels.put(label, instructionPointer);
    }

    public void setInstructionPointerToLabelAddress(String label) {
        if (labels.containsKey(label)) {
            instructionPointer = labels.get(label);
        } else {
            throw new RuntimeException("Label not in map of labels (not been added) " + label);
        }
    }

    public Integer getLabel(String label) {
        if (labels.containsKey(label)) {
            return labels.get(label);
        }
        throw new RuntimeException("Label not in map of labels (not been added) " + label);
    }

    public void pushToCallStack(VmFunction functionName) {
        callStack.add(functionName);
    }

    public VmFunction popCallStack() {
        return callStack.pop();
    }
}
