package org.mobylet.view.designer;

import org.mobylet.core.Carrier;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.emoji.Emoji;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.util.SingletonUtils;

public class EmojiDesigner extends SingletonDesigner {

	public static final String PREFIX_IMG = "<img src=\"";

	public static final String SUFIX_IMG = "\" />";


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

	public String getImageEmoji(Emoji emoji) {
		char[] codes = emoji.getCodes();
		if (codes != null && codes.length == 1) {
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			String imagePath = config.getEmojiImagePath();
			if (imagePath == null) {
				imagePath = "";
			}
			if (!imagePath.endsWith("/") && imagePath.length() > 0) {
				imagePath = imagePath + "/";
			}
			return PREFIX_IMG +
					imagePath +
					Integer.toHexString((int)codes[0]).toUpperCase() + ".gif" +
					SUFIX_IMG;
		}
		return null;
	}

}
