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
package org.mobylet.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.mobylet.core.MobyletRuntimeException;

public class InputToOutputStreamUtils {

	public static void writeAll(InputStream in, OutputStream out) {
		int c = -1;
		try {
			while ((c = in.read()) >= 0) {
				out.write(c);
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"入力ストリーム情報を出力ストリームに書き込み中に例外発生", e);
		}
	}

}
