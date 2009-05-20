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
package org.mobylet.core.util;

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
		int elementStartIndex = 0;
		int pos = 0;
		for (; pos<lineChars.length; pos++) {
			if (lineChars[pos] == ',') {
				if (!isInnerQuote) {
					if (pos == elementStartIndex) {
						splitList.add("");
					} else {
						String elem = new String(lineChars, elementStartIndex, pos-elementStartIndex);
						if (isQuoted && elem.contains(BACKSLASH)) {
							splitList.add(elem
									.replaceAll(BACKSLASH + W_QUOTE, W_QUOTE)
									.replaceAll(W_BACKSLASH, BACKSLASH));
						} else {
							splitList.add(elem);
						}
					}
					elementStartIndex = pos + 1;
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
