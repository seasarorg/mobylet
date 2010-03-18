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
