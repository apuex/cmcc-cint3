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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 值的数组
 */
public class TATDArray implements Serializable {
	private static final long serialVersionUID = 1L;

	public TATDArray() {
		
	}
	
	public TATDArray(List<TATD> l) {
		this.values.addAll(l);
	}

  @Override
  public boolean equals(Object o) {
  	TATDArray r = null;
      if(o instanceof TATDArray) {
          r = (TATDArray) o;
      } else {
          return false;
      }

      boolean result =
          ( this.values.equals(r.values)
          );

      return result;
  }

  @Override
  public String toString() {
      StringBuilder builder = new StringBuilder();
      builder
          .append("TATDArray { ")
          .append("values=").append(this.values)
          .append(" }");

      return builder.toString();
  }

	public List<TATD> values = new LinkedList<TATD>();
}
