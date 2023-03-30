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
 * TID数组
 */
public class NodeIDArrayCodec {
	
	public void encode(ByteBuffer buf, NodeIDArray v) {
		buf.putInt(v.values.size());
		for (int e : v.values) {
			buf.putInt(e);
		}
	}

	public NodeIDArray decode(ByteBuffer buf) {
		NodeIDArray v = new NodeIDArray();
		final int size = buf.getInt();
		for (int i = 0; i != size; ++i) {
			v.values.add(buf.getInt());
		}
		return v;
	}
}
