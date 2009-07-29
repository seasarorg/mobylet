package org.mobylet.core.selector.impl;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mobylet.charset.MobyletCharsetPool;
import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.selector.CharsetSelector;
import org.mobylet.core.util.SingletonUtils;

public class MobyletCharsetSelector implements CharsetSelector {

	protected boolean isCharsetInstalled = false;

	protected Map<Carrier, Charset> charsetMap;


	public MobyletCharsetSelector() {
		initialize();
	}

	@Override
	public Charset getCharset(Carrier carrier) {
		if (isCharsetInstalled) {
			return charsetMap.get(carrier);
		} else {
			return charsetMap.get(Carrier.OTHER);
		}
	}

	@Override
	public String getCharsetName(Carrier carrier) {
		return getCharset(carrier).name();
	}

	@Override
	public boolean isCharsetInstalled() {
		return isCharsetInstalled;
	}

	protected void initialize() {
		charsetMap = new HashMap<Carrier, Charset>();
		Charset defCharset = SingletonUtils.get(Charset.class);
		if (defCharset == null) {
			defCharset = Charset.forName(DefCharset.WIN31J);
		}
		charsetMap.put(Carrier.OTHER, defCharset);
		try {
			charsetMap.put(Carrier.DOCOMO,
					MobyletCharsetPool.getInstance().charsetForName(DefCharset.DOCOMO));
			charsetMap.put(Carrier.AU,
					MobyletCharsetPool.getInstance().charsetForName(DefCharset.AU));
			charsetMap.put(Carrier.SOFTBANK,
					Charset.forName(DefCharset.UTF8));
			isCharsetInstalled = true;
		} catch (Throwable t) {
			Logger logger = Logger.getLogger(this.getClass().getName());
			logger.log(Level.WARNING, "CHARSET IS NOT INSTALLED", t);
			isCharsetInstalled = false;
		}
	}
}
