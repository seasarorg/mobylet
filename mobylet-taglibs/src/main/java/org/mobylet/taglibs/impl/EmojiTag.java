package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.Carrier;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.EmojiDesigner;

public class EmojiTag extends SimpleTagSupport {

	protected String name;

	protected Carrier carrier = Carrier.DOCOMO;


	@Override
	public void doTag() throws JspException, IOException {
		EmojiDesigner designer = EmojiDesigner.getDesigner();
		JspWriterUtils.write(
				getJspContext().getOut(),
				designer.get(name, carrier)
				);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCarrier() {
		if (carrier == null) {
			carrier = Carrier.OTHER;
		}
		return carrier.name();
	}

	public void setCarrier(String carrier) {
		this.carrier = Carrier.valueOf(carrier);
	}

}
