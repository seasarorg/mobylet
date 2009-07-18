package org.mobylet.charset;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MobyletCharsetPool {

	protected static MobyletCharsetPool singleton = new MobyletCharsetPool();

	protected Map<String, Charset> charsets = null;


	public static MobyletCharsetPool getInstance() {
		return singleton;
	}

	protected MobyletCharsetPool() {
		this.charsets = new HashMap<String, Charset>();
		this.charsets.put("X-MOBYLET-DOCOMO", new MobyletDocomoCharset());
		this.charsets.put("X-MOBYLET-AU", new MobyletAuCharset());
		this.charsets.put("X-MOBYLET-MAIL-SJIS-AU", new MobyletAuMailSjisCharset());
	}

	public Charset charsetForName(String key) {
		return (Charset)this.charsets.get(key.toUpperCase());
	}

	public Iterator<Charset> charsets() {
		return this.charsets.values().iterator();
	}

}
