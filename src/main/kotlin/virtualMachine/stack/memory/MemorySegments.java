package virtualMachine.stack.memory;

import java.util.Arrays;

public enum MemorySegments {
    THIS("this", 0),
    THAT("that", 1),
    STATIC("static"),
    LOCAL("local"),
    POINTER("pointer"),
    ARGUMENT("argument"),
    GLOBAL("global"),
    CONSTANT("constant");

    private final String name;
    private int pointerToSelf = -1;

    MemorySegments(String input) {
        this.name = input;
    }

    MemorySegments(String input, int pointerToSelf) {
        this.name = input;
        this.pointerToSelf = pointerToSelf;
    }

    public String getName() {
        return name;
    }

    public int getPointerToSelf() {
        return pointerToSelf;
    }

    public boolean hasPointerToSelf() {
        return pointerToSelf != -1;
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
