package com.github.apuex.cmcc.cint3;

import org.junit.Assert;
import org.junit.Test;

public class BCDTest {
    @Test
    public void testFormatBCD()
    {
        final BCD bcd = new BCD(1,2,3);
        final String s = BCD.format(bcd);
        Assert.assertEquals("0001.02.003", s);
    }

    @Test
    public void testParseBCD()
    {
        final String s = "0001.02.003";
        final BCD bcd = BCD.parse(s);
        Assert.assertEquals(new BCD(1, 2, 3), bcd);
    }
}
