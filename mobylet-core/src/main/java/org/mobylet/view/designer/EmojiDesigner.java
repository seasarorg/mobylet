package org.mobylet.view.designer;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.util.SingletonUtils;

public class EmojiDesigner {

	public static EmojiDesigner getDesigner() {
		EmojiDesigner designer = null;
		try {
			designer = SingletonUtils.get(EmojiDesigner.class);
		} catch (Exception e) {
			//NOP
		}
		if (designer == null) {
			SingletonUtils.put(new EmojiDesigner());
			return getDesigner();
		}
		return designer;
	}

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
