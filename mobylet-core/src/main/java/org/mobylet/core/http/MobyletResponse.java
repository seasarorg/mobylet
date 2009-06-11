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
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.image.ImageScaleHelper;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;

public class MobyletResponse extends HttpServletResponseWrapper {

	protected HttpServletResponse response;

	protected MobyletDialect dialect;

	protected PrintWriter printWriter;

	protected ServletOutputStream outputStream;


	public MobyletResponse(HttpServletResponse response, MobyletDialect dialect) {
		super(response);
		this.dialect = dialect;
		this.response = response;
	}

	@Override
	public void setContentType(String type) {
		super.setContentType(type);
		RequestUtils.getMobyletContext().set(new MobyletContentType(type));
	}

	public boolean hasContentType() {
		return RequestUtils.getMobyletContext().get(MobyletContentType.class) != null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (printWriter == null) {
			printWriter = new MobyletPrintWriter(
					new OutputStreamWriter(getOutputStream(),
							Charset.forName(dialect.getCharsetName())),
						dialect.getCarrier());
			response.setContentType(dialect.getContentTypeString());
		}
		return printWriter;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (outputStream == null) {
			String imgContentType = null;
			if (!hasContentType() &&
					(imgContentType = MobyletContentType.getContentTypeStringByImageSuffix()) != null) {
				setContentType(imgContentType);
				if (SingletonUtils.get(ImageScaleHelper.class).isAutoScaleImage()) {
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
				SingletonUtils.get(ImageScaleHelper.class).autoScale(
						super.getOutputStream(),
						ProxyImageOutputStream.class.cast(outputStream)
						.getWrittenBytesInputStream());
			}
			outputStream.flush();
		}
	}
}
