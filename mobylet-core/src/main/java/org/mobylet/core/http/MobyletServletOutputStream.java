package org.mobylet.core.http;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class MobyletServletOutputStream extends ServletOutputStream {

	protected ServletOutputStream sos;

	protected int length;


	public MobyletServletOutputStream(ServletOutputStream sos) {
		this.sos = sos;
		this.length = 0;
	}

	@Override
	public void write(int b) throws IOException {
		sos.write(b);
		b++;
	}

	@Override
	public void flush() throws IOException {
		super.flush();
	}

	public int getLength() {
		return length;
	}

}
