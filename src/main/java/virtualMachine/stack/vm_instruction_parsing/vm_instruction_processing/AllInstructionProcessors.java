package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing;

import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.*;

public class AllInstructionProcessors {

    private final BinaryInstructionProcessor binaryInstructionProcessor = new BinaryInstructionProcessor();
    private final PopInstructionProcessor popInstructionProcessor = new PopInstructionProcessor();
    private final ProgramFlowProcessor programFlowProcessor = new ProgramFlowProcessor();
    private final PushInstructionProcessor pushInstructionProcessor = new PushInstructionProcessor();
    private final UnaryInstructionProcessor unaryInstructionProcessor = new UnaryInstructionProcessor();
    private final FunctionProcessor functionProcessor = new FunctionProcessor();

    public BinaryInstructionProcessor getBinaryInstructionProcessor() {
        return binaryInstructionProcessor;
    }

    public PopInstructionProcessor getPopInstructionProcessor() {
        return popInstructionProcessor;
    }

    public ProgramFlowProcessor getProgramFlowProcessor() {
        return programFlowProcessor;
    }

    public PushInstructionProcessor getPushInstructionProcessor() {
        return pushInstructionProcessor;
    }

    public UnaryInstructionProcessor getUnaryInstructionProcessor() {
        return unaryInstructionProcessor;
    }

    public FunctionProcessor getFunctionProcessor() {
        return functionProcessor;
    }
}
