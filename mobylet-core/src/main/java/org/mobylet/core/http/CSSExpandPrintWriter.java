/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.mobylet.core.http;

import java.io.PrintWriter;

import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.view.css.CSSCondContainer;
import org.mobylet.view.css.CSSExpandHandler;
import org.mobylet.view.xhtml.XhtmlParser;

public class CSSExpandPrintWriter extends PrintWriter {

	protected StringBuilder buf;

	protected PrintWriter writer;

	public CSSExpandPrintWriter(PrintWriter out) {
		super(out);
		writer = out;
		buf = new StringBuilder(1024);
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(int)
	 */
	@Override
	public void write(int c) {
		buf.append(c);
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(char[], int, int)
	 */
	@Override
	public void write(char buf[], int off, int len) {
		this.buf.append(buf, off, len);
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(java.lang.String, int, int)
	 */
	@Override
	public void write(String s, int off, int len) {
		if (StringUtils.isNotEmpty(s)) {
			buf.append(s.toCharArray(), off, len);
		}
	}

	public void flushByMobylet() {
		int index = buf.indexOf("<html");
		if (index < 0) {
			index = 0;
		}
		CSSCondContainer container = null;
		if (RequestUtils.get() != null &&
				RequestUtils.getMobyletContext() != null) {
			container =
				RequestUtils.getMobyletContext().get(CSSCondContainer.class);
		}
		CSSExpandHandler handler = new CSSExpandHandler(container);
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		handler.setRemovedClass(config.isCSSExpandRemovedClass());
		SingletonUtils.get(XhtmlParser.class).parse(buf.toString().toCharArray(), handler);
		if (index > 0) {
			writer.write(buf.substring(0, index));
		}
		writer.write(handler.toString());
	}

}
