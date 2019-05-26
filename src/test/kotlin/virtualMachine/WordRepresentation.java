package virtualMachine;

import org.junit.Assert;
import org.junit.Test;
import virtualMachine.stack.types.Word;

public class WordRepresentation {

    @Test
    public void integerConversion() {
        Word s7490 = convertStringToSixteenBit("0001 1101 0100 0010");
        Assert.assertEquals(7490, s7490.convertToInteger());

        String s3256 = "0000 1100 1011 1000";
        Word bit16_3256 = convertStringToSixteenBit(s3256);
        Assert.assertEquals(3256, bit16_3256.convertToInteger());
    }

    @Test
    public void shouldConvertIntToBooleanArray() {
        Word word = new Word(5);

        Assert.assertEquals(padToCorrectLength("101") + " | 5", word.toString());
    }

    @Test
    public void shouldConvert3256ToWord() {
        String correctResult = "000 1100 1011 1000";
        int toConvert = 3256;

        Word wordResult = new Word(toConvert);

        Assert.assertEquals(padToCorrectLength(correctResult) + " | 3256", wordResult.toString());
    }

    @Test
    public void shouldConvert7490ToWord() {
        String s7490 = "001 1101 0100 0010";
        int toConvert = 7490;

        Word wordResult = new Word(toConvert);

        Assert.assertEquals(padToCorrectLength(s7490) + " | 7490", wordResult.toString());
    }

    @Test
    public void shouldAdd16BitNumbersCorrectly() {
        int opA = 14;
        int opB = 8;

        Word opA16Bit = new Word(opA);
        Word opB16Bit = new Word(opB);

        Word result16Bit = opA16Bit.add16Bit(opB16Bit);
        int res = result16Bit.convertToInteger();

        Assert.assertEquals(opA + opB, res);
    }

    @Test
    public void shouldAddBigger16BitNumbersCorrectly() {
        int opA = 2343;
        int opB = 7438;

        Word opA16Bit = new Word(opA);
        Word opB16Bit = new Word(opB);

        Word result16Bit = opA16Bit.add16Bit(opB16Bit);
        int res = result16Bit.convertToInteger();

        Assert.assertEquals(opA + opB, res);
    }

    @Test
    public void shouldSubtract55And19Correctly() {
        Word op55 = new Word(55);
        Word op19 = new Word(19);

        int res = 36;
        Word calculatedResult = op55.subtract(op19);
        Assert.assertEquals(res, calculatedResult.convertToInteger());
    }

    @Test
    public void shouldSubtractToANegativeNumberCorrectly() {
        Word op12 = new Word(12);
        Word op42 = new Word(42);

        int res = -30;
        Word calculatedResult = op12.subtract(op42);
        Assert.assertEquals(res, calculatedResult.convertToInteger());
    }

    private Word convertStringToSixteenBit(String input) {
        char[] inp = input.replace(" ", "").toCharArray();
        boolean[] word = new boolean[16];
        for (int i = 0; i < word.length; i++) {
            if (inp[i] == '1') {
                word[i] = true;
            }
        }
        return new Word(word);
    }

    private String padToCorrectLength(String characterSoFar) {
        characterSoFar = characterSoFar.replace(" ", "");
        while (characterSoFar.length() < 15) { //0 is sign bit
            characterSoFar = String.format("%s%s", '0', characterSoFar);
        }
        return characterSoFar;
    }
}
