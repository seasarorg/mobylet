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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.http.util.ForceWrapUtils;

public class ForceWrapMobyletResponse extends MobyletResponse {


	public ForceWrapMobyletResponse(
			HttpServletResponse response, MobyletDialect dialect) {
		super(response, dialect);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (outputStream == null) {
			if (ForceWrapUtils.isForceWrapRequest()) {
				ServletOutputStream wrapStream = new ForceWrapServletOutputStream(this);
				outputStream = wrapStream;
			} else {
				return super.getOutputStream();
			}
		}
		return outputStream;
	}

	@Override
	protected ServletOutputStream getMobyletOutputStream() throws IOException {
		return super.getOutputStream();
	}

}
