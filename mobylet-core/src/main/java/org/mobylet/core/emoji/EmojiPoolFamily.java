package org.mobylet.core.emoji;

import org.mobylet.core.Carrier;


public interface EmojiPoolFamily {

	public EmojiPool getEmojiStocker(Carrier carrier);

	public Emoji getEmoji(char c);

}
