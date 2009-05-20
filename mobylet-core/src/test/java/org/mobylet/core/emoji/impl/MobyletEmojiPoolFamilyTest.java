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
