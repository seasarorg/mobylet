/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
