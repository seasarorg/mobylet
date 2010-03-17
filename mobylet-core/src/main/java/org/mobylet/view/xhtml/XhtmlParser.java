package org.mobylet.view.xhtml;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mobylet.core.MobyletRuntimeException;

public class XhtmlParser {

	private static final Pattern REGEX_SPLIT_IN_TAG =
		Pattern.compile("[a-zA-Z0-9_?!-]+([\\s]?=[\\s]?[\"']?[^\"]+[\"']?)?");
	
	public void parse(char[] ch, XhtmlHandler handler) {
		CharArrayWriter writer = new CharArrayWriter(256);
		int pos = 0;
		int offset = 0;
		int length = 0;
		boolean inComment = false;
		boolean inQuot = false;
		boolean inWQuot = false;
		boolean inTag = false;
		int pre1 = 0x00;
		int pre2 = 0x00;
		int pre3 = 0x00;
		try {
			handler.startDocument();
			while (pos < ch.length) {
				char c = ch[pos];
				switch (c) {
				case '<':
					if (!inComment && !inWQuot && !inQuot) {
						if (pos+3 < ch.length &&
								ch[pos+1] == '!' &&
								ch[pos+2] == '-' &&
								ch[pos+3] == '-') {
							pos = pos+3;
							inComment = true;
							if (length != 0 && !inTag) {
								handler.characters(ch, offset, length);
							}
						} else {
							inTag = true;
							if (length != 0) {
								handler.characters(ch, offset, length);
							}
						}
						offset = pos + 1;
						length = 0;
					} else if (!inComment) {
						length++;
						if (inTag) {
							writer.write(c);
						}
					}
					break;
				case '>':
					if (inComment &&
							pre2 == '-' &&
							pre1 == '-') {
						inComment = false;
						offset = pos + 1;
					} else if (!inComment && !inWQuot && !inQuot) {
						if (length != 0) {
							boolean existsBody = true;
							boolean isEndTag = false;
							String inTagStr = writer.toString();
							if (inTagStr.endsWith("/")) {
								existsBody = false;
								inTagStr =inTagStr.substring(0, inTagStr.length()-1);
							} else if (inTagStr.startsWith("/")) {
								isEndTag = true;
								inTagStr = inTagStr.substring(1);
							}
							//String[] elements = inTagStr.trim().split("\\s");
							String[] elements = null;
							List<String> elemList = new ArrayList<String>();
							Matcher matcher = REGEX_SPLIT_IN_TAG.matcher(inTagStr);
							while (matcher.find()) {
								elemList.add(matcher.group());
							}
							elements = new String[elemList.size()];
							elemList.toArray(elements);
							if (elements.length > 0) {
								if (elements[0].equals("?xml") ||
										elements[0].equals("!DOCTYPE")) {
									//NOP
								} else {
									TagElement tagElement = new TagElement(elements[0]);
									for (int i=1; i<elements.length; i++) {
										int idxEq = elements[i].indexOf('=');
										if (idxEq < 0) {
											String key = elements[i].trim();
											tagElement.putAttribute(key, key);
										} else {
											String key = elements[i].substring(0, idxEq).trim();
											String val = elements[i].substring(idxEq+1).trim();
											if (val.startsWith("\"") ||
													val.startsWith("'")) {
												val = val.substring(1, val.length()-1);
											}
											tagElement.putAttribute(key, val);
										}
									}
									if (isEndTag) {
										handler.endElement(
												tagElement.getName());
									} else {
										handler.startElement(
												tagElement.getName(),
												new TagAttributes(tagElement.getAttMap()),
												existsBody);
									}
								}
							}
						}
						offset = pos + 1;
						length = 0;
						inTag = false;
						writer.reset();
					} else if (!inComment) {
						length++;
						if (inTag) {
							writer.write(c);
						}
					}
					break;
				case '-':
					if (!inComment && !inWQuot && !inQuot &&
							pre3 == '<' &&
							pre2 == '!' &&
							pre1 == '-') {
						inComment = true;
						length = 0;
					} else if (!inComment) {
						length++;
						if (inTag) {
							writer.write(c);
						}
					}
					break;
				case '"':
					if (!inComment && !inQuot) {
						if (inWQuot && pre1 == '\\') {
							//NOP
						} else {
							inWQuot = !inWQuot;
						}
						length++;
						if (inTag) {
							writer.write(c);
						}
					}
					break;
				case '\'':
					if (!inComment && !inWQuot) {
						if (inQuot && pre1 == '\\') {
							//NOP
						} else {
							inQuot = !inQuot;
						}
						length++;
						if (inTag) {
							writer.write(c);
						}
					}
					break;
				default:
					if (!inComment) {
						length++;
						if (inTag) {
							writer.write(c);
						}
					}
					break;
				}
				pre3 = pre2;
				pre2 = pre1;
				pre1 = c;
				pos++;
			}
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"XHTMLが正しく読めません", e);
		}
		handler.endDocument();
	}

}
