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
package org.mobylet.view.css;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;

import org.mobylet.core.MobyletRuntimeException;

public class CSSParser {

	public CSSCondContainer parse(InputStream in) {
		CSSCondContainer container = new CSSCondContainer();
		if (in == null) {
			return container;
		}
		CharArrayWriter chars = new CharArrayWriter(128);
		String key = null;
		String value = null;
		boolean isComment = false;
		boolean isAnnotation = false;
		int c = 0x00;
		int pre = 0x00;
		try {
			while ((c = in.read()) != -1) {
				switch (c) {
				case '@':
					if (!isComment) {
						isAnnotation = true;
					}
					break;
				case ';':
					if (isAnnotation) {
						isAnnotation = false;
						chars.reset();
					} else {
						if (!isComment) {
							chars.append((char)c);
						}
					}
					break;
				case '/':
					if (isComment && pre == '*') {
						isComment = false;
					} else {
						if (!isComment) {
							chars.append((char)c);
						}
					}
					break;
				case '*':
					if (!isComment && pre == '/') {
						isComment = true;
					} else {
						if (!isComment) {
							chars.append((char)c);
						}
					}
					break;
				case '{':
					if (!isComment) {
						key = new String(chars.toCharArray()).trim();
						chars.reset();
					}
					break;
				case '}':
					if (!isComment) {
						value = new String(chars.toCharArray()).trim();
						chars.reset();
						String[] csvKeys = key.split("[,]");
						for (String csvKey : csvKeys) {
							String[] singleKeys = csvKey.split("\\s");
							CSSCond p = null;
							boolean isNextFirstChild = false;
							for (String singleKey : singleKeys) {
								//CHILD
								if (singleKey.contains(">")) {
									String[] directKeys = singleKey.split("[>]");
									if (directKeys.length == 0) {
										isNextFirstChild = true;
										continue;
									}
									else if (directKeys.length == 2 && singleKey.startsWith(">")) {
										CSSCond cond = new CSSCond(directKeys[1].trim());
										if (cond.getSelectorType() != SelectorType.FIRST_CHILD) {
											cond.setSelectorType(SelectorType.CHILD);
										}
										if (p != null) {
											cond.setParent(p);
										}
										p = cond;
									}
									else if (directKeys.length == 2 && singleKey.endsWith(">")) {
										CSSCond cond = new CSSCond(directKeys[0].trim());
										if (p != null) {
											cond.setParent(p);
										}
										p = cond;
										isNextFirstChild = true;
										continue;
									}
									else {
										for (int i=0; i<directKeys.length; i++) {
											CSSCond cond = new CSSCond(directKeys[i].trim());
											if (i > 0) {
												if (cond.getSelectorType() != SelectorType.FIRST_CHILD) {
													cond.setSelectorType(SelectorType.CHILD);
												}
											}
											if (p != null) {
												cond.setParent(p);
											}
											p = cond;
										}
									}
								} else {
									CSSCond cond = new CSSCond(singleKey.trim());
									if (p != null) {
										cond.setParent(p);
									}
									p = cond;
								}
								if (isNextFirstChild) {
									if (p.getSelectorType() != SelectorType.FIRST_CHILD) {
										p.setSelectorType(SelectorType.CHILD);
									}
								}
							}
							String[] entries = value.split("[;]");
							if (entries != null && entries.length > 0) {
								for (String entry : entries) {
									int di = entry.indexOf(':'); //FirstIndex
									if (di > 0) {
										p.putStyle(entry.substring(0,di), entry.substring(di+1));
									}
								}
							}
							container.put(p);
						}
					}
					break;
				default:
					if (!isComment) {
						chars.append((char)c);
					}
					break;
				}
				pre = c;
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"CSS読み込み中にIO例外が発生", e);
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"CSSが正しく読めません", e);
		}
		return container;
	}
}
