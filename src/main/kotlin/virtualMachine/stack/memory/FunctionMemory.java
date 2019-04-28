package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.SixteenBit;

import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;
import static virtualMachine.stack.memory.MemorySegments.GLOBAL_STACK;

public class FunctionMemory {

    private final Map<MemorySegments, SixteenBit[]> memory;

    public FunctionMemory(SixteenBit[] staticVariables, SixteenBit[] arguments) {
        this.memory = Map.of(
                THIS, new SixteenBit[100],
                THAT, new SixteenBit[100],
                STATIC, staticVariables,
                LOCAL, new SixteenBit[100],
                ARGUMENT, arguments,
                POINTER, new SixteenBit[2]
        );
    }



}
