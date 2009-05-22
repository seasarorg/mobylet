package org.mobylet.core.selector.impl;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.selector.CharsetSelector;

public class MobyletCharsetSelector implements CharsetSelector {

	protected boolean isInstalledCharset = false;

	protected Map<Carrier, Charset> charsetMap;


	public MobyletCharsetSelector() {
		initialize();
	}

	@Override
	public Charset getCharset(Carrier carrier) {
		if (isInstalledCharset) {
			return charsetMap.get(carrier);
		} else {
			return charsetMap.get(Carrier.OTHER);
		}
	}

	@Override
	public String getCharsetName(Carrier carrier) {
		return getCharset(carrier).name();
	}

	protected void initialize() {
		charsetMap = new HashMap<Carrier, Charset>();
		charsetMap.put(Carrier.OTHER,
				Charset.forName(DefCharset.UTF8));
		try {
			charsetMap.put(Carrier.DOCOMO,
					Charset.forName(DefCharset.DOCOMO));
			charsetMap.put(Carrier.AU,
					Charset.forName(DefCharset.AU));
			charsetMap.put(Carrier.SOFTBANK,
					Charset.forName(DefCharset.UTF8));
			isInstalledCharset = true;
		} catch (Throwable t) {
			isInstalledCharset = false;
		}
	}
}
