package virtualMachine.vm_instruction_processing;

import virtualMachine.stack.memory.VirtualMemory;
import virtualMachine.stack.types.instruction.Instruction;

public interface InstructionProcessor {
    /**
     * processes a command and interacts with memory
     * @param instruction e.g. push constant 1234
     * @param virtualMemory stores a functions local variables, stack and global 'heap' memory
     */
    void processInstruction(Instruction instruction, VirtualMemory virtualMemory);
}
