package virtualMachine.stack.memory;

import java.util.Arrays;

public enum MemorySegments {
    THIS("this"),
    THAT("that"),
    STATIC("static"),
    LOCAL("local"),
    POINTER("pointer"),
    ARGUMENT("argument"),
    GLOBAL("global"),
    CONSTANT("constant");

    final String name;

    MemorySegments(String input) {
        this.name = input;
    }

    public String getName() {
        return name;
    }

    public static MemorySegments getFromName(String name) {
        for (var segment : MemorySegments.values()) {
            if (name.equals(segment.getName())) {
                return segment;
            }
        }
        throw new RuntimeException("Unrecognised segment name " + name + ", use one of" +
                Arrays.toString(MemorySegments.values()));
    }
}
