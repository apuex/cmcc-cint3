/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package com.github.apuex.cmcc.cint3;

import java.nio.ByteBuffer;

/**
 * 值的数组
 */
public class TATDArrayCodec {

	public void encode(ByteBuffer buf, TATDArray v) {
		buf.putInt(v.values.size());
		for (TATD e : v.values) {
			codec.encode(buf, e);
		}
	}

	public TATDArray decode(ByteBuffer buf) {
		TATDArray v = new TATDArray();
		final int size = buf.getInt();
		for (int i = 0; i != size; ++i) {
			v.values.add(codec.decode(buf));
		}
		return v;
	}

	private TATDCodec codec = new TATDCodec();
}
