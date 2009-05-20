/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.core.emoji;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefChar;
import org.mobylet.core.util.StringUtils;

public class Emoji {

	protected Carrier carrier;

	protected char[] c = new char[]{ DefChar.MIN_CHAR };

	protected char[] relatedChars;

	protected Map<Carrier, Emoji> map;


	public Emoji(Carrier carrier, char c) {
		this(carrier, null);
		this.c[0] = c;
	}

	public Emoji(Carrier carrier, String relatedString) {
		this.carrier = carrier;
		if (StringUtils.isNotEmpty(relatedString)) {
			this.relatedChars = relatedString.toCharArray();
		}
		map = new HashMap<Carrier, Emoji>();
		relate(this.carrier, this);
	}

	public void relate(Carrier carrier, Emoji emoji) {
		map.put(carrier, emoji);
	}

	public Emoji getRelated(Carrier carrier) {
		return map.get(carrier);
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public char[] getCodes() {
		if (c[0] != DefChar.MIN_CHAR) {
			return c;
		} else {
			return relatedChars;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Emoji) {
			return this.carrier == ((Emoji)obj).carrier
				&& this.c == ((Emoji)obj).c;
		} else {
			return super.equals(obj);
		}
	}
}
