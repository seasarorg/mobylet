package org.mobylet.core.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.mobylet.core.dialect.MobyletDialect;

public class MobyletResponse extends HttpServletResponseWrapper {

	protected MobyletDialect dialect;

	protected PrintWriter printWriter;

	protected ServletOutputStream outputStream;


	public MobyletResponse(HttpServletResponse response, MobyletDialect dialect) {
		super(response);
		this.dialect = dialect;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (printWriter == null) {
			printWriter = new MobyletPrintWriter(
					new OutputStreamWriter(getOutputStream(),
							Charset.forName(dialect.getCharsetName())),
						dialect.getCarrier());
		}
		return printWriter;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (outputStream == null) {
			outputStream = super.getOutputStream();
		}
		return outputStream;
	}

	public void flush() throws IOException {
		if (printWriter != null) {
			printWriter.flush();
		} else if (outputStream != null) {
			outputStream.flush();
		}
	}
}
