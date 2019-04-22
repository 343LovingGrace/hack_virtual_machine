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
    private int fixedAddress = -1;

    MemorySegments(String input) {
        this.name = input;
    }

    MemorySegments(String input, int fixedAddress) {
        this.name = input;
        this.fixedAddress = fixedAddress;
    }

    public String getName() {
        return name;
    }

    public int getFixedAddress() {
        return fixedAddress;
    }

    public boolean hasPointerToSelf() {
        return fixedAddress != -1;
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
