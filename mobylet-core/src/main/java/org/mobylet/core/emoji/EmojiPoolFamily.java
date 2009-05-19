package org.mobylet.core.emoji;

import org.mobylet.core.Carrier;


public interface EmojiPoolFamily {

	public EmojiPool getEmojiPool(Carrier carrier);

	public Emoji getEmoji(char c);

	public boolean isEmoji(char c);

}
