package org.mobylet.core.http;

import java.io.PrintWriter;
import java.io.Writer;

import org.mobylet.core.Carrier;

public class MobyletPrintWriter extends PrintWriter {

	protected Carrier carrier;


	public MobyletPrintWriter(Writer out, Carrier carrier) {
		super(out);
		this.carrier = carrier;
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(int)
	 */
	@Override
	public void write(int c) {
		super.write(c);
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(char[], int, int)
	 */
	@Override
	public void write(char buf[], int off, int len) {
		super.write(buf, off, len);
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(java.lang.String, int, int)
	 */
	@Override
	public void write(String s, int off, int len) {
		super.write(s, off, len);
	}

}
