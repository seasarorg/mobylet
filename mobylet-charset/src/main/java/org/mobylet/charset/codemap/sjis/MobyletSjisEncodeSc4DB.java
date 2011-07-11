/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.charset.codemap.sjis;

public class MobyletSjisEncodeSc4DB {

	private static int[] c2bsc4;

	static {
		//1byte Unicode
		c2bsc4 = new int[0xFA2D - 0xF929 + 1];
		c2bsc4[0xF929 - 0xF929] = 0xFAE0;
		c2bsc4[0xF9DC - 0xF929] = 0xFBE9;
		c2bsc4[0xFA0E - 0xF929] = 0xFA90;
		c2bsc4[0xFA0F - 0xF929] = 0xFA9B;
		c2bsc4[0xFA10 - 0xF929] = 0xFA9C;
		c2bsc4[0xFA11 - 0xF929] = 0xFAB1;
		c2bsc4[0xFA12 - 0xF929] = 0xFAD8;
		c2bsc4[0xFA13 - 0xF929] = 0xFAE8;
		c2bsc4[0xFA14 - 0xF929] = 0xFAEA;
		c2bsc4[0xFA15 - 0xF929] = 0xFB58;
		c2bsc4[0xFA16 - 0xF929] = 0xFB5E;
		c2bsc4[0xFA17 - 0xF929] = 0xFB75;
		c2bsc4[0xFA18 - 0xF929] = 0xFB7D;
		c2bsc4[0xFA19 - 0xF929] = 0xFB7E;
		c2bsc4[0xFA1A - 0xF929] = 0xFB80;
		c2bsc4[0xFA1B - 0xF929] = 0xFB82;
		c2bsc4[0xFA1C - 0xF929] = 0xFB86;
		c2bsc4[0xFA1D - 0xF929] = 0xFB89;
		c2bsc4[0xFA1E - 0xF929] = 0xFB92;
		c2bsc4[0xFA1F - 0xF929] = 0xFB9D;
		c2bsc4[0xFA20 - 0xF929] = 0xFB9F;
		c2bsc4[0xFA21 - 0xF929] = 0xFBA0;
		c2bsc4[0xFA22 - 0xF929] = 0xFBA9;
		c2bsc4[0xFA23 - 0xF929] = 0xFBB1;
		c2bsc4[0xFA24 - 0xF929] = 0xFBB3;
		c2bsc4[0xFA25 - 0xF929] = 0xFBB4;
		c2bsc4[0xFA26 - 0xF929] = 0xFBB7;
		c2bsc4[0xFA27 - 0xF929] = 0xFBD3;
		c2bsc4[0xFA28 - 0xF929] = 0xFBDA;
		c2bsc4[0xFA29 - 0xF929] = 0xFBEA;
		c2bsc4[0xFA2A - 0xF929] = 0xFBF6;
		c2bsc4[0xFA2B - 0xF929] = 0xFBF7;
		c2bsc4[0xFA2C - 0xF929] = 0xFBF9;
		c2bsc4[0xFA2D - 0xF929] = 0xFC49;
		//NORMALIZE
		for (int i=0; i<c2bsc4.length; i++) {
			if (c2bsc4[i] == 0) {
				c2bsc4[i] = 0x3F3F;
			}
		}
	}

	public static int toByte(int c) {
		return c2bsc4[c - 0xF929];
	}
}
