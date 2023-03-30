package com.github.apuex.cmcc.cint3.utility;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Assert;
import org.junit.Test;

import com.github.apuex.cmcc.cint3.Message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class NettyByteBufTest {

	@Test
	public void testRewind() {
		ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(8);
		byteBuf.writeIntLE(Message.MESSAGE_HEADER);
		byteBuf.writeIntLE(0);
		byte[] array = new byte[byteBuf.readableBytes()];
		byteBuf.getBytes(0, array, 0, array.length);
		ByteBuffer buf = ByteBuffer.wrap(array);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		final int header1 = buf.getInt();
		final int length1 = buf.getInt();
		buf.position(0);
		final int header2 = buf.getInt();
		final int length2 = buf.getInt();
		Assert.assertEquals(header1, header2);
		Assert.assertEquals(length1, length2);
	}
}
