package virtualMachine.types.instruction;

import virtualMachine.vm_instruction_processing.stack_access.*;

import java.util.Map;
import java.util.Set;

import static virtualMachine.types.instruction.CommandType.*;

public class StackAccessProcessorLookup {

    private static final PushStackCommand push = new PushStackCommand();
    private static final PopStackProcessor POP_STACK_PROCESSOR = new PopStackProcessor();
    private static final ArithmeticProcessor ARITHMETIC_PROCESSOR = new ArithmeticProcessor();
    private static final NegationProcessor NEGATION_PROCESSOR = new NegationProcessor();

    private static final Map<CommandType, StackAccessProcessor> processors = Map.of(
            PUSH_STACK, push,
            POP_STACK, POP_STACK_PROCESSOR,
            ARITHMETIC, ARITHMETIC_PROCESSOR,
            NEGATION, NEGATION_PROCESSOR
    );

    public static Set<CommandType> stackAccessors() {
        return processors.keySet();
    }

    public static StackAccessProcessor getProcessor(CommandType commandType) {
        return processors.get(commandType);
    }
}
