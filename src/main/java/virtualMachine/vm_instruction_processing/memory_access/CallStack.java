package virtualMachine.vm_instruction_processing.memory_access;

import virtualMachine.vm_instruction_processing.control_flow_processing.ControlFlow;
import virtualMachine.memory.FunctionStack;
import virtualMachine.memory.PseudoAddressSpaceMemory;

import java.util.ArrayDeque;

public class CallStack extends ArrayDeque<FunctionStack> {

    public CallStack(PseudoAddressSpaceMemory virtualRam, ControlFlow controlFlow) {
        push(new FunctionStack(virtualRam, controlFlow.getInstructionPointer(), "Sys.init"));
    }

}
