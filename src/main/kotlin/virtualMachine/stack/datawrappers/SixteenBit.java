package virtualMachine.stack.datawrappers;

import java.util.Arrays;

public class SixteenBit {

    private final boolean[] sixteenBit;

    public SixteenBit(boolean[] sixteenBit) {
        this.sixteenBit = sixteenBit;
    }

    public SixteenBit(int operand) {
        sixteenBit = new boolean[16];
        for (int i = 15; i >= 0; i--) {
            int powerOfTwo = 1 << i;
            if (operand / powerOfTwo > 0) {
                sixteenBit[i] = true;
                operand -= powerOfTwo;
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

    public int convertToInteger() {
        int res = 0;
        for (int i = 15; i >= 0; i--) {
            if (sixteenBit[i]) {
                res += Math.pow(2, i);
            }
        }
        return res;
    }

    public boolean[] getCopiedSixteenBit() {
        return Arrays.copyOf(sixteenBit, 16);
    }

    private boolean[] convertIntTo16Bit(int operand) {
        boolean[] bits = new boolean[16];

        for (int i = 15; i >= 0; i--) {
            double power = Math.pow(2, i);
            int divisor = (int) (operand / Math.pow(2, i));
            if (divisor == 1) {
                operand -= power;
            }
        }

        return bits;
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
