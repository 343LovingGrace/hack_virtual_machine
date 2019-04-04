package virtualMachine;

import org.junit.Assert;
import org.junit.Test;
import virtualMachine.stack.datawrappers.SixteenBit;

public class SixteenBitRepresentation {

    @Test
    public void integerConversion() {
        boolean[] twentyThree = new boolean[16];
        twentyThree[4] = true;
        twentyThree[2] = true;
        twentyThree[1] = true;
        twentyThree[0] = true;

        SixteenBit sixteenBit = new SixteenBit(twentyThree);

        Assert.assertEquals(23, sixteenBit.convertToInteger());
    }

    @Test
    public void shouldConvertIntToBooleanArray() {
        SixteenBit sixteenBit = new SixteenBit(5);

        Assert.assertEquals("[0000000000000101]", sixteenBit.toString());
    }
}
