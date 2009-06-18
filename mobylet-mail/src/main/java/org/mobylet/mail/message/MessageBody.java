package org.mobylet.mail.message;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mobylet.core.util.ResourceUtils;

public class MessageBody {

	protected String text;

	protected String html;

	protected List<InputStream> attaches;


	public MessageBody() {
		this.attaches = new ArrayList<InputStream>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public void addAttachPath(String path) {
		this.attaches.add(
				ResourceUtils.getResourceFileOrInputStream(path));
	}

	public void addAttachStream(InputStream is) {
		this.attaches.add(is);
	}

	public List<InputStream> getAttaches() {
		return attaches;
	}

}
