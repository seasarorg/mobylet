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
package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.FlashDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class FlashTag extends SimpleTagSupport {

	protected String src;

	protected String width;

	protected String height;

	protected String loop;

	protected String bgcolor;

	protected String quality;

	protected String copyright;


	@Override
	public void doTag() throws JspException, IOException {
		try {
			FlashDesigner designer =
				SingletonDesigner.getDesigner(FlashDesigner.class);
			JspWriterUtils.write(
					getJspContext().getOut(),
					designer.getInlineFlash(src, width, height, bgcolor, loop, quality, copyright)
					);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}


	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLoop() {
		return loop;
	}

	public void setLoop(String loop) {
		this.loop = loop;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

}
