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
