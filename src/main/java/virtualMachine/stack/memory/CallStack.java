package virtualMachine.stack.memory;

import java.util.ArrayDeque;

public class CallStack extends ArrayDeque<FunctionStack> {

    public CallStack(PseudoAddressSpaceMemory virtualRam, ControlFlow controlFlow) {
        push(new FunctionStack(virtualRam, controlFlow.getInstructionPointer(), "Sys.init"));
    }

}
