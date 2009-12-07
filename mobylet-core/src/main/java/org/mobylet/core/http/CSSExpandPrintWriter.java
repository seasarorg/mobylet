package org.mobylet.core.http;

import java.io.PrintWriter;
import java.io.StringReader;

import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.XmlUtils;
import org.mobylet.view.css.CSSCondContainer;
import org.mobylet.view.css.CSSExpandHandler;
import org.xml.sax.InputSource;

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
		InputSource inputSource = new InputSource(new StringReader(buf.substring(index)));
		CSSCondContainer container = null;
		if (RequestUtils.get() != null &&
				RequestUtils.getMobyletContext() != null) {
			container =
				RequestUtils.getMobyletContext().get(CSSCondContainer.class);
		}
		CSSExpandHandler handler = new CSSExpandHandler(container);
		XmlUtils.parseSax(inputSource, handler);
		if (index > 0) {
			writer.write(buf.substring(0, index));
		}
		writer.write(handler.toString());
		writer.flush();
	}

}
