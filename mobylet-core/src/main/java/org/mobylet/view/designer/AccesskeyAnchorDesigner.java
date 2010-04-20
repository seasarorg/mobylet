/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.view.config.TransitionConfig;
import org.mobylet.view.design.AccesskeyAnchorDesign;
import org.mobylet.view.xhtml.TagAttribute;

public class AccesskeyAnchorDesigner extends TransitionDesigner {


	public AccesskeyAnchorDesign getAccesskeyAnchorDesign(
			String url, Integer accesskey) {
		return getAccesskeyAnchorDesign(url, accesskey, config);
	}

	public AccesskeyAnchorDesign getAccesskeyAnchorDesign(
			String url, Integer accesskey, TransitionConfig config) {
		AccesskeyAnchorDesign design =
			new AccesskeyAnchorDesign(constructUrl(url, config));
		String accesskeyString = "";
		if (accesskey != null) {
			accesskeyString = String.valueOf(accesskey);
		} else {
			return design;
		}
		TagAttribute tagAttribute =
			new TagAttribute("accesskey", accesskeyString);
		design.setTagAttribute(tagAttribute);
		Mobylet m = MobyletFactory.getInstance();
		if (accesskey < 10) {
			if (m.getCarrier() != Carrier.SOFTBANK) {
				design.setEmojiString(
						SingletonDesigner.getDesigner(EmojiDesigner.class)
						.get(accesskeyString));
			}
		} else {
			design.setEmojiString("["+accesskeyString+"]");
		}
		return design;
	}

}
