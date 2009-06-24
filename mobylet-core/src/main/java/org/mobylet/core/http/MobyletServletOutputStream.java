package org.mobylet.core.http;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class MobyletServletOutputStream extends ServletOutputStream {

	protected OutputStream os;

	protected int length;


	public MobyletServletOutputStream(OutputStream os) {
		this.os = os;
		this.length = 0;
	}

	@Override
	public void write(int b) throws IOException {
		os.write(b);
		length++;
	}

	@Override
	public void flush() throws IOException {
		super.flush();
	}

	public int getLength() {
		return length;
	}

}
