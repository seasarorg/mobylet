/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
		this.charsets.put("X-MOBYLET-MAIL-SJIS-AU", new MobyletAuMailSjisCharset());
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
