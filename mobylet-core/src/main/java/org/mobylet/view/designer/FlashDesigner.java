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
