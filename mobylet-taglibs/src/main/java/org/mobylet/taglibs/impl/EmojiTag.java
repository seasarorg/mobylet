package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;

public class EmojiTag extends SimpleTagSupport {

	protected String name;

	protected Carrier carrier = Carrier.DOCOMO;


	@Override
	public void doTag() throws JspException, IOException {
		JspWriterUtils.write(
				getJspContext().getOut(),
				new String(
						SingletonUtils.get(EmojiPoolFamily.class)
						.getEmojiPool(carrier)
						.get(name)
						.getCodes())
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
