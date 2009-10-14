package org.mobylet.core.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.MobyletRuntimeException;

public class ForceWrapServletOutputStream extends ServletOutputStream {

	protected PrintWriter writer;

	protected ByteArrayOutputStream baos;


	public ForceWrapServletOutputStream(MobyletResponse response) {
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"強制PrintWriter取得処理で例外発生", e);
		}
		this.baos = new ByteArrayOutputStream(8192);
	}

	@Override
	public void write(int b) throws IOException {
		baos.write(b);
	}

	@Override
	public void flush() throws IOException {
		if (baos.size() > 0) {
			writer.write(baos.toString("UTF-8").toCharArray());
		}
		super.flush();
	}

	public int getLength() {
		if (baos.size() > 0) {
			try {
				return baos.toString("UTF-8").getBytes(
						MobyletFactory.getInstance().getDialect().getCharset()).length;
			} catch (UnsupportedEncodingException e) {
				//NOP
				return 0;
			}
		}
		return 0;
	}

}
