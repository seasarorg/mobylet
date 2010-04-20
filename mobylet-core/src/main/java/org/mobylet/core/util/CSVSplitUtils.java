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
package org.mobylet.core.util;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVSplitUtils {

	public static final String BACKSLASH = "\\";

	public static final String W_BACKSLASH = "\\\\";

	public static final String W_QUOTE = "\"";


	public static List<String> splitLine(String line) {
		List<String> splitList = new ArrayList<String>();
		if (StringUtils.isEmpty(line)) {
			return splitList;
		}
		char[] lineChars = line.toCharArray();
		boolean isInnerQuote = false;
		boolean isQuoted = false;
		int elemStartIndex = 0;
		int pos = 0;
		for (; pos<lineChars.length; pos++) {
			if (lineChars[pos] == ',' || pos == lineChars.length - 1) {
				if (!isInnerQuote) {
					if (pos == elemStartIndex) {
						splitList.add("");
					} else {
						String elem =
							(pos == lineChars.length - 1 && lineChars[pos] != ',' ?
									new String(lineChars, elemStartIndex, pos-elemStartIndex+1) :
										new String(lineChars, elemStartIndex, pos-elemStartIndex));
						if (isQuoted && elem.contains(BACKSLASH)) {
							CharArrayWriter caw = new CharArrayWriter(128);
							char[] elemChars = elem.toCharArray();
							for (int i=0; i<elemChars.length; i++) {
								if (elemChars[i] == '\\' && i > 1) {
									if (elemChars[i-1] == '\\') {
										caw.append(elemChars[i]);
									}
								} else if (elemChars[i] == '"' && i > 1) {
									if (elemChars[i-1] == '\\') {
										caw.append(elemChars[i]);
									}
								} else {
									caw.append(elemChars[i]);
								}
							}
							splitList.add(caw.toString());
						} else {
							splitList.add(elem);
						}
					}
					elemStartIndex = pos + 1;
					isQuoted = false;
				}
			} else if (lineChars[pos] == '"') {
				if (pos == 0 ||
						lineChars[pos-1] == ',') {
					isInnerQuote = !isInnerQuote;
					isQuoted = true;
				} else if (pos == lineChars.length-1 ||
						lineChars[pos+1] == ',') {
					isInnerQuote = !isInnerQuote;
				}
			}
		}
		return splitList;
	}

	public static String[] splitLineToArray(String line) {
		List<String> list = splitLine(line);
		String[] array = new String[list.size()];
		return list.toArray(array);
	}
}
