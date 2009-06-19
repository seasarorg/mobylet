package org.mobylet.mail.builder;

import java.util.List;

import javax.mail.internet.MimeBodyPart;

public class HtmlPart {

	protected String source;

	protected List<MimeBodyPart> inlineParts;


	public HtmlPart(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<MimeBodyPart> getInlineParts() {
		return inlineParts;
	}

	public void setInlineParts(List<MimeBodyPart> inlineParts) {
		this.inlineParts = inlineParts;
	}

}
