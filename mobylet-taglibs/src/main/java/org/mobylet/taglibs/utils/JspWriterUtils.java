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
package org.mobylet.taglibs.utils;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import org.mobylet.taglibs.MobyletRenderingException;

public class JspWriterUtils {

	public static void write(JspWriter writer, String str) {
		try {
			writer.print(str);
		} catch (IOException e) {
			throw new MobyletRenderingException(
					"レスポンス出力中にIO例外が発生しました", e);
		}
	}
}
