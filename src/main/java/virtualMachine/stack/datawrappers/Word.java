package virtualMachine.stack.datawrappers;

import java.util.Arrays;

public class Word {

    private static final byte SIZE = 16;
    private static final byte SIGN_LOC = 0;
    private static final byte NUMBER_START_LOC = 1;
    private final boolean[] wordBits;

    public Word(boolean[] wordBits) {
        this.wordBits = wordBits;
    }

    public Word(boolean[] wordBits, boolean isNegative) {
        this.wordBits = wordBits;
        setSign(isNegative);
    }

    public Word(int operand) {
        wordBits = new boolean[SIZE];
        if (operand < 0) {
            setSign(true);
        }
        operand = Math.abs(operand);

        long bitShift = 1 << (SIZE - NUMBER_START_LOC);
        if (operand > bitShift) {
            throw new RuntimeException("Input out of range");
        }

        for (int i = NUMBER_START_LOC; i < SIZE; i++) {
            bitShift = bitShift >> 1;
            long powerOfTwo = bitShift;
            if (operand / powerOfTwo > 0) {
                wordBits[i] = true;
                operand -= powerOfTwo;
            }
        }
    }

    public Word(boolean operand) {
        wordBits = new boolean[SIZE];
        if (operand) {
            for (int i = 0; i < SIZE; i++) {
                wordBits[i] = true;
            }
        }
    }

    public Word bitWiseNot() {
        boolean[] nottedInt = new boolean[SIZE];
        for (int i = NUMBER_START_LOC; i < SIZE; i++) {
            nottedInt[i] = !wordBits[i];
        }
        return new Word(nottedInt);
    }

    public Word bitwizeAnd(Word operand) {
        boolean[] andArray = new boolean[SIZE];
        boolean[] operandCopy = operand.getCopiedSixteenBit();
        for (int i = NUMBER_START_LOC; i < SIZE; i++) {
            andArray[i] = wordBits[i] && operandCopy[i];
        }
        return new Word(andArray);
    }

    public Word bitwiseOr(Word operand) {
        boolean[] orArray = new boolean[SIZE];
        boolean[] operandCopy = operand.getCopiedSixteenBit();
        for (int i = NUMBER_START_LOC; i < SIZE; i++) {
            orArray[i] = wordBits[i] || operandCopy[i];
        }
        return new Word(orArray);
    }

    //could optimise these methods
    public Word lessThan(Word opeand) {
        int thisValue = this.convertToInteger();
        int thatValue = opeand.convertToInteger();
        return new Word(thisValue < thatValue);
    }

    public Word greaterThan(Word opeand) {
        int thisValue = this.convertToInteger();
        int thatValue = opeand.convertToInteger();
        return new Word(thisValue > thatValue);
    }

    public int convertToInteger() {
        int res = 0;
        for (int i = NUMBER_START_LOC; i < SIZE; i++) {
            if (wordBits[i]) {
                res += 1 << (SIZE - 1 - i);
            }
        }
        if (isNegative()) {
            return -res;
        }
        return res;
    }

    private boolean[] getCopiedSixteenBit() {
        return Arrays.copyOf(wordBits, SIZE);
    }

    public Word add16Bit(Word operand) {
        boolean[] additionProduct = new boolean[SIZE];
        boolean overFlowBit = false;

        if ((this.isNegative() && operand.isNegative())
            || (!this.isNegative() && !operand.isNegative())) {
            for (byte i = SIZE - 1; i >= NUMBER_START_LOC; i--) {

                boolean thisVal = wordBits[i];
                boolean opVal = operand.get(i);

                if (overFlowBit) {
                    if (thisVal && opVal) {
                        additionProduct[i] = true;
                        overFlowBit = true;
                    } else if (thisVal || opVal) {
                        overFlowBit = true;
                    } else {
                        additionProduct[i] = true;
                        overFlowBit = false;
                    }
                } else {
                    if (thisVal && opVal) {
                        overFlowBit = true;
                    } else if (thisVal || opVal) {
                        additionProduct[i] = true;
                    }
                }
            }
            if (this.isNegative() && operand.isNegative()) {
                return new Word(additionProduct, true);
            }
            return new Word(additionProduct);

        } else {
            //copout
            return new Word(this.convertToInteger() + operand.convertToInteger());
        }

    }

    //todo: implement bitwise subtract
    public Word subtract(Word toBeSubtracted) {
        int thisVal = this.convertToInteger();
        int operandValue = toBeSubtracted.convertToInteger();
        return new Word(thisVal - operandValue);
    }

    public boolean get(byte index) {
        return wordBits[index];
    }

    private void setSign(boolean isNegative) {
        this.wordBits[SIGN_LOC] = isNegative;
    }

    private boolean isNegative() {
        return wordBits[SIGN_LOC];
    }

    @Override
    public String toString() {
        StringBuilder numericRepresentation = new StringBuilder(80);
        for (int i = NUMBER_START_LOC; i < SIZE; i++) {
            if (wordBits[i]) {
                numericRepresentation.append('1');
            } else {
                numericRepresentation.append('0');
            }
        }
        if (this.isNegative()) {
            return "[-" + numericRepresentation.toString() + ']';
        }
        return "[" + numericRepresentation.toString() + ']';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word that = (Word) o;
        return Arrays.equals(wordBits, that.wordBits);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(wordBits);
    }
}
