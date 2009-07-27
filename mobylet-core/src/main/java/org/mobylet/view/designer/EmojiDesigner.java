package org.mobylet.view.designer;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.util.SingletonUtils;

public class EmojiDesigner extends SingletonDesigner {

	public String get(String name) {
		return get(name, Carrier.DOCOMO);
	}

	public String get(String name, Carrier carrier) {
		return new String(
				SingletonUtils.get(EmojiPoolFamily.class)
				.getEmojiPool(carrier)
				.get(name)
				.getCodes());
	}

}
