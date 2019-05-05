package virtualMachine.stack.vm_instruction_parsing;

import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl.*;

class AllInstructionProcessors {

    private final BinaryInstructionProcessor binaryInstructionProcessor = new BinaryInstructionProcessor();
    private final PopInstructionProcessor popInstructionProcessor = new PopInstructionProcessor();
    private final ProgramFlowProcessor programFlowProcessor = new ProgramFlowProcessor();
    private final PushInstructionProcessor pushInstructionProcessor = new PushInstructionProcessor();
    private final UnaryInstructionProcessor unaryInstructionProcessor = new UnaryInstructionProcessor();

    BinaryInstructionProcessor getBinaryInstructionProcessor() {
        return binaryInstructionProcessor;
    }

    PopInstructionProcessor getPopInstructionProcessor() {
        return popInstructionProcessor;
    }

    public ProgramFlowProcessor getProgramFlowProcessor() {
        return programFlowProcessor;
    }

    PushInstructionProcessor getPushInstructionProcessor() {
        return pushInstructionProcessor;
    }

    UnaryInstructionProcessor getUnaryInstructionProcessor() {
        return unaryInstructionProcessor;
    }
}
