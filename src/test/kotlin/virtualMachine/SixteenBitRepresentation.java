package virtualMachine;

import org.junit.Assert;
import org.junit.Test;
import virtualMachine.stack.datawrappers.SixteenBit;

public class SixteenBitRepresentation {

    @Test
    public void integerConversion() {
        SixteenBit s7490 = convertStringToSixteenBit("0001110101000010");
        System.out.println(s7490.toString());
        Assert.assertEquals(7490, s7490.convertToInteger());

        String s3256 = "0000110010111000";
        SixteenBit bit16_3256 = convertStringToSixteenBit(s3256);
        Assert.assertEquals(3256, bit16_3256.convertToInteger());
    }

    @Test
    public void shouldConvertIntToBooleanArray() {
        SixteenBit sixteenBit = new SixteenBit(5);

        Assert.assertEquals("[0000000000000101]", sixteenBit.toString());
    }

    @Test
    public void shouldConvertIntegerToBin() {
        String correctResult = "[0000110010111000]";
        int toConvert = 3256;

        SixteenBit sixteenBitResult = new SixteenBit(toConvert);

        Assert.assertEquals(correctResult, sixteenBitResult.toString());

        String s7490 = "[0001110101000010]";
        toConvert = 7490;

        sixteenBitResult = new SixteenBit(toConvert);

        Assert.assertEquals(s7490, sixteenBitResult.toString());
    }

    //more tests here

    @Test
    public void shouldAdd16BitNumbersCorrectly() {
        int opA = 14;
        int opB = 8;

        SixteenBit opA16Bit = new SixteenBit(opA);
        SixteenBit opB16Bit = new SixteenBit(opB);

        SixteenBit result16Bit = opA16Bit.add16Bit(opB16Bit);
        int res = result16Bit.convertToInteger();
        System.out.println(result16Bit.toString());

        Assert.assertEquals(opA + opB, res);
    }

    @Test
    public void shouldAddBigger16BitNumbersCorrectly() {
        int opA = 2343;
        int opB = 7438;

        SixteenBit opA16Bit = new SixteenBit(opA);
        SixteenBit opB16Bit = new SixteenBit(opB);

        SixteenBit result16Bit = opA16Bit.add16Bit(opB16Bit);
        SixteenBit correctResult = new SixteenBit(opA + opB);
        int res = result16Bit.convertToInteger();

        System.out.println();
        System.out.println("correct " + correctResult.toString());
        System.out.println("attempt " + result16Bit.toString());

        Assert.assertEquals(opA + opB, res);
    }

    private SixteenBit convertStringToSixteenBit(String input) {
        boolean[] sixteenBit = new boolean[16];
        for (int i = 0; i < sixteenBit.length; i++) {
            if (input.charAt(i) == '1') {
                sixteenBit[15 - i] = true;
            }
        }
        return new SixteenBit(sixteenBit);
    }
}
