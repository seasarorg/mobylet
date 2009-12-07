package org.mobylet.core.http;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.Writer;

import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.XmlUtils;
import org.mobylet.view.css.CSSCondContainer;
import org.mobylet.view.css.CSSInjectionHandler;
import org.xml.sax.InputSource;

public class CSSInjectionPrintWriter extends PrintWriter {

	protected StringBuilder buf;


	public CSSInjectionPrintWriter(Writer out) {
		super(out);
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
		InputSource inputSource = new InputSource(new StringReader(buf.toString()));
		CSSCondContainer container = null;
		if (RequestUtils.get() != null &&
				RequestUtils.getMobyletContext() != null) {
			container =
				RequestUtils.getMobyletContext().get(CSSCondContainer.class);
		}
		CSSInjectionHandler handler = new CSSInjectionHandler(container);
		XmlUtils.parseSax(inputSource, handler);
		super.write(handler.toString());
		super.flush();
	}

}
