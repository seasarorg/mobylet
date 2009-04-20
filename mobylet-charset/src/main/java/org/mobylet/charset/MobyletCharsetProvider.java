package org.mobylet.charset;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * キャラセットプロバイダ.<br />
 *
 * @author takeuchi
 *
 */
public class MobyletCharsetProvider extends CharsetProvider {

	private Map<String, Charset> charsets = null;


	public MobyletCharsetProvider() {
		this.charsets = new HashMap<String, Charset>();
		this.charsets.put("X-MOBYLET-DOCOMO", new MobyletDocomoCharset());
		this.charsets.put("X-MOBYLET-AU", new MobyletAuCharset());
	}

	@Override
	public Charset charsetForName(String key) {
		return (Charset)this.charsets.get(key.toUpperCase());
	}

	@Override
	public Iterator<Charset> charsets() {
		return this.charsets.values().iterator();
	}

}
