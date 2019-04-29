package virtualMachine.stack.memory;

import virtualMachine.stack.datawrappers.Word;

import java.util.Map;

import static virtualMachine.stack.memory.MemorySegments.*;

public class FunctionMemory {

    private final Map<MemorySegments, Word[]> memory;

    public FunctionMemory(Word[] staticVariables, Word[] arguments) {
        this.memory = Map.of(
                THIS, new Word[100],
                THAT, new Word[100],
                STATIC, staticVariables,
                LOCAL, new Word[100],
                ARGUMENT, arguments,
                POINTER, new Word[2]
        );
    }



}
