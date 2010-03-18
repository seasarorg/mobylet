package org.mobylet.view.designer;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.StringUtils;

public class FlashDesigner extends SingletonDesigner {

	public String getInlineFlash(String src, String width, String height, String bgcolor, String loop, String quality, String copyright) {
		Mobylet m = MobyletFactory.getInstance();
		StringBuilder buf = new StringBuilder();
		//ObjectTag
		buf.append("<object data=\"" + src + "\" type=\"application/x-shockwave-flash\"");
		if (StringUtils.isNotEmpty(width)) {
			buf.append(" width=\"" + width + "\"");
		}
		if (StringUtils.isNotEmpty(height)) {
			buf.append(" height=\"" + height + "\"");
		}
		//IfAU
		if (m.getCarrier() == Carrier.AU &&
				StringUtils.isNotEmpty(copyright)) {
			buf.append(" copyright=\"" + copyright + "\"");
		}
		buf.append(">");
		//ParamTag
		if (StringUtils.isNotEmpty(loop)) {
			buf.append("<param name=\"loop\" value=\"" + loop + "\" />");
		}
		switch (m.getCarrier()) {
		case DOCOMO:
			if (StringUtils.isNotEmpty(bgcolor)) {
				buf.append("<param name=\"bgcolor\" value=\"" + bgcolor + "\" />");
			}
			if (StringUtils.isNotEmpty(quality)) {
				buf.append("<param name=\"quality\" value=\"" + quality + "\" />");
			}
			break;
		case SOFTBANK:
			if (StringUtils.isNotEmpty(bgcolor)) {
				buf.append("<param name=\"bgcolor\" value=\"" + bgcolor + "\" />");
			}
			if (StringUtils.isNotEmpty(quality)) {
				buf.append("<param name=\"quality\" value=\"" + quality + "\" />");
			}
			break;
		}
		buf.append("</object>");
		return buf.toString();
	}

}
