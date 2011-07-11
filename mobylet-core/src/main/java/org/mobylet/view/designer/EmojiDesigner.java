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
