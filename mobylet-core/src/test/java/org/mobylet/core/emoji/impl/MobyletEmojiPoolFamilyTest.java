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
package org.mobylet.core.emoji.impl;

import junit.framework.TestCase;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.util.SingletonUtils;

public class MobyletEmojiPoolFamilyTest extends TestCase {

	public void test_initialize() {
		//## ARRANGE ##
		SingletonUtils.put(new MobyletEmojiPoolReader());
		SingletonUtils.put(new MobyletEmojiPoolFamily());

		//## ACT ##
		EmojiPoolFamily family = SingletonUtils.get(EmojiPoolFamily.class);
		family.initialize();

		//## ASSERT ##
		char emojiChar = (char)0xE63E;
		assertTrue(
				family.getEmojiPool(Carrier.DOCOMO).get(emojiChar)
				.getRelated(Carrier.AU)
				.getRelated(Carrier.SOFTBANK)
				.getRelated(Carrier.DOCOMO)
				.getCodes()[0] == emojiChar);
		assertTrue(
				family.getEmojiPool(Carrier.DOCOMO).get(emojiChar)
				.getRelated(Carrier.OTHER)
				.getCodes()[0] == '〓');
	}
}
