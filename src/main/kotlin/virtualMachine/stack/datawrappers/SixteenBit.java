package virtualMachine.stack.datawrappers;

import java.util.Arrays;

public class SixteenBit {

    private final boolean[] sixteenBit;

    public SixteenBit(boolean[] sixteenBit) {
        this.sixteenBit = sixteenBit;
    }

    public SixteenBit(int operand) {
        if (operand > 1 << 17) {
            throw new IllegalArgumentException("The operand is too big, must be 16 bit");
        }
        sixteenBit = new boolean[16];
        for (int i = 15; i >= 0; i--) {
            int powerOfTwo = 1 << i;
            if (operand / powerOfTwo > 0) {
                sixteenBit[i] = true;
                operand -= powerOfTwo;
            }
        }
    }

    /**
     * returns 0000 0000 0000 0000 as false
     * and     1111 1111 1111 1111 as true
     * @param operand boolean value to make 16 bit
     */
    public SixteenBit(boolean operand) {
        sixteenBit = new boolean[16];
        if (operand) {
            for (int i = 0; i < 16; i++) {
                sixteenBit[i] = true;
            }
        }
    }

    public SixteenBit not16Bit() {
        boolean[] nottedInt = new boolean[16];
        for (int i = 0; i < 16; i++) {
            nottedInt[i] = !sixteenBit[i];
        }
        return new SixteenBit(nottedInt);
    }

    public SixteenBit and16Bit(SixteenBit operand) {
        boolean[] andArray = new boolean[16];
        boolean[] operandCopy = operand.getCopiedSixteenBit();
        for (int i = 0; i < 16; i++) {
            andArray[i] = sixteenBit[i] && operandCopy[i];
        }
        return new SixteenBit(andArray);
    }

    public SixteenBit or16Bit(SixteenBit operand) {
        boolean[] orArray = new boolean[16];
        boolean[] operandCopy = operand.getCopiedSixteenBit();
        for (int i = 0; i < 16; i++) {
            orArray[i] = sixteenBit[i] || operandCopy[i];
        }
        return new SixteenBit(orArray);
    }

    //could optimise these methods
    public SixteenBit lessThan16Bit(SixteenBit opeand) {
        int thisValue = this.convertToInteger();
        int thatValue = opeand.convertToInteger();
        return new SixteenBit(thisValue < thatValue);
    }

    public SixteenBit greaterThan16Bit(SixteenBit opeand) {
        int thisValue = this.convertToInteger();
        int thatValue = opeand.convertToInteger();
        return new SixteenBit(thisValue > thatValue);
    }

    public int convertToInteger() {
        int res = 0;
        for (int i = 0; i < 16; i++) {
            if (sixteenBit[i]) {
                res += 1 << i;
            }
        }
        return res;
    }

    public boolean[] getCopiedSixteenBit() {
        return Arrays.copyOf(sixteenBit, 16);
    }

    public SixteenBit equals(SixteenBit operand) {
        boolean isEqual = true;
        for (byte i = 0; i < 16; i++) {
            if (operand.get(i) != sixteenBit[i]) {
                isEqual = false;
                break;
            }
        }
        return new SixteenBit(isEqual);
    }

    public SixteenBit add16Bit(SixteenBit operand) {
        boolean[] additionProduct = new boolean[16];
        boolean overFlowBit = false;

        System.out.println("        " + this.toString());
        System.out.println("        " + operand.toString());

        for (byte i = 0; i < 16; i++) {

            boolean thisVal = sixteenBit[i];
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

        return new SixteenBit(additionProduct);
    }

    public boolean get(byte index) {
        return sixteenBit[index];
    }

    public byte size() {
        return 16;
    }

    @Override
    public String toString() {
        StringBuilder dfs = new StringBuilder(40);
        for (int i = 15; i >= 0; i--) {
            if (sixteenBit[i]) {
                dfs.append('1');
            } else {
                dfs.append('0');
            }
        }
        return '[' + dfs.toString() + ']';
    }
}
