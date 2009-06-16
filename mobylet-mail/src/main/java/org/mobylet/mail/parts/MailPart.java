package org.mobylet.mail.parts;

import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;

public class MailPart {

	protected DataHandler handler;

	protected Map<String, String> headerMap;


	public MailPart(DataHandler handler) {
		this.handler = handler;
		this.headerMap = new HashMap<String, String>();
	}

	public void addHeader(String key, String value) {
		headerMap.put(key, value);
	}

	public void removeHeader(String key) {
		this.headerMap.remove(key);
	}

	public void setHandler(DataHandler handler) {
		this.handler = handler;
	}

	public DataHandler getHandler() {
		return this.handler;
	}

}
