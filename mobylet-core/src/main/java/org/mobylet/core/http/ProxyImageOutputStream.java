package org.mobylet.core.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;

public class ProxyImageOutputStream extends ServletOutputStream {

	protected ByteArrayOutputStream outStream;


	public ProxyImageOutputStream() {
		outStream = new ByteArrayOutputStream(8192);
	}

	@Override
	public void write(int b) throws IOException {
		outStream.write(b);
	}

	public InputStream getWrittenBytesInputStream() {
		return new ByteArrayInputStream(outStream.toByteArray());
	}

}
