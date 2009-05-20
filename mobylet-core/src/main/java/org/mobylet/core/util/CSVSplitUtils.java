package org.mobylet.core.util;

import java.util.ArrayList;
import java.util.List;

public class CSVSplitUtils {

	public static List<String> splitLine(String line) {
		List<String> splitList = new ArrayList<String>();
		if (StringUtils.isEmpty(line)) {
			return splitList;
		}
		char[] lineChars = line.toCharArray();
		boolean isInnerQuote = false;
		int elementStartIndex = 0;
		int pos = 0;
		for (; pos<lineChars.length; pos++) {
			if (lineChars[pos] == ',') {
				if (!isInnerQuote) {
					if (pos == elementStartIndex) {
						splitList.add("");
					} else {
						splitList.add(new String(lineChars, elementStartIndex, pos-elementStartIndex));
					}
					elementStartIndex = pos + 1;
				}
			} else if (lineChars[pos] == '"') {
				if (pos == 0 ||
						pos == lineChars.length-1 ||
						lineChars[pos-1] == ',' ||
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
