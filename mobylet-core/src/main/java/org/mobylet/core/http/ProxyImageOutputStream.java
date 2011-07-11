/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
