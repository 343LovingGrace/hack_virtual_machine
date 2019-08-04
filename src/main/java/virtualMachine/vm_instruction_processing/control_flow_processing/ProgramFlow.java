package virtualMachine.vm_instruction_processing.control_flow_processing;

import virtualMachine.memory.FunctionStack;
import virtualMachine.types.Word;
import virtualMachine.types.instruction.Instruction;

import static virtualMachine.types.instruction.Commands.*;

public class ProgramFlow {

    public static void processInstruction(Instruction instruction, ControlFlow controlFlow, FunctionStack functionStack) {

        var command = instruction.getCommand();

        if (command == GOTO) {
            controlFlow.setInstructionPointerToJumpAddress(instruction.getOperand());
        } else if (command == IF_GOTO) {
            //set next instruction set to be executed to be the one next one in list if false, one in label otherwise
            Word topValue = functionStack.pop();
            if (!topValue.isFalse()) {
                controlFlow.setInstructionPointerToJumpAddress(instruction.getOperand());
            }
        } else {
            throw new RuntimeException("Command not recognised " + command);
        }
        //label locations already added to control flow
    }

}
