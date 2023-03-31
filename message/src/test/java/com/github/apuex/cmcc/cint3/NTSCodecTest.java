package com.github.apuex.cmcc.cint3;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

public class NTSCodecTest {

	@Test
	public void testCodec() {
		final String expected = "0123456789     \0";
		final String input = "0123456789";
		final int maxLength = 16;
		
		byte[] bytes = new byte[16];
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		Util.encodeStringNTS(buf, input, maxLength);
		buf.flip();
		final String actual = Util.decodeStringUnTrimmed(buf, maxLength);
		Assert.assertEquals(expected, actual);
	}
}
