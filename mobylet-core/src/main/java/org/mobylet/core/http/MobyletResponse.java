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
package org.mobylet.core.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.type.ContentType;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.SingletonUtils;

public class MobyletResponse extends HttpServletResponseWrapper {

	protected HttpServletResponse response;

	protected MobyletDialect dialect;

	protected PrintWriter printWriter;

	protected ServletOutputStream outputStream;

	protected boolean hasContentType;


	public MobyletResponse(HttpServletResponse response, MobyletDialect dialect) {
		super(response);
		this.dialect = dialect;
		this.response = response;
		this.hasContentType = false;
	}

	@Override
	public void setContentType(String type) {
		super.setContentType(type);
		if (!hasContentType()) {
			hasContentType = true;
		}
	}

	public boolean hasContentType() {
		return hasContentType;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (printWriter == null) {
			printWriter = new MobyletPrintWriter(
					new OutputStreamWriter(getOutputStream(),
							dialect.getCharset()),
						dialect.getCarrier());
			Mobylet m = MobyletFactory.getInstance();
			if (m != null &&
					m.getContentType() == ContentType.XHTML) {
				response.setContentType(dialect.getXContentTypeString());
			} else {
				response.setContentType(dialect.getContentTypeString());
			}
		}
		return printWriter;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (outputStream == null) {
			String imgContentType = null;
			if (dialect.getCarrier() != Carrier.OTHER &&
					!hasContentType() &&
					(imgContentType =
						ImageUtils.getContentTypeStringByRequestURI()) != null) {
				setContentType(imgContentType);
				if (ImageUtils.isAutoScale()) {
					outputStream = new ProxyImageOutputStream();
				} else {
					outputStream = super.getOutputStream();
				}
			} else {
				outputStream = super.getOutputStream();
			}
		}
		return outputStream;
	}

	public void flush() throws IOException {
		if (printWriter != null) {
			printWriter.flush();
		} else if (outputStream != null) {
			if (outputStream instanceof ProxyImageOutputStream) {
				MobyletServletOutputStream outStream =
					new MobyletServletOutputStream(super.getOutputStream());
				SingletonUtils.get(ImageScaler.class).scale(
						ProxyImageOutputStream.class.cast(outputStream)
							.getWrittenBytesInputStream(),
						outStream,
						ImageUtils.getImageCodec(),
						ImageUtils.getScaledWidth(),
						ImageUtils.getScaleType());
				setContentLength(outStream.getLength());
			}
			outputStream.flush();
		}
	}
}
