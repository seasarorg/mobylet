package org.mobylet.core.http;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Writer;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.Emoji;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletPrintWriter extends PrintWriter {

	protected Carrier outCarrier;

	protected EmojiPoolFamily family;


	public MobyletPrintWriter(Writer out, Carrier outCarrier) {
		super(out);
		this.outCarrier = outCarrier;
		family = SingletonUtils.get(EmojiPoolFamily.class);
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(int)
	 */
	@Override
	public void write(int c) {
		if (family != null) {
			char ch = (char)c;
			Emoji e = family.getEmoji(ch);
			if (e == null) {
				super.write(c);
			} else {
				Emoji related = e.getRelated(outCarrier);
				if (related == null) {
					super.write(c);
				} else {
					char[] codes = related.getCodes();
					if (codes != null) {
						for (char code : codes) {
							super.write(code);
						}
					}
				}
			}
		} else {
			super.write(c);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(char[], int, int)
	 */
	@Override
	public void write(char buf[], int off, int len) {
		if (family != null) {
			CharArrayWriter caw = new CharArrayWriter(len);
			for (int i=off; i<off+len; i++) {
				Emoji e = family.getEmoji(buf[i]);
				if (e == null) {
					caw.append(buf[i]);
				} else {
					Emoji related = e.getRelated(outCarrier);
					if (related == null) {
						caw.append(buf[i]);
					} else {
						char[] codes = related.getCodes();
						if (codes != null) {
							for (char code : codes) {
								caw.append(code);
							}
						}
					}
				}
			}
			super.write(caw.toCharArray());
		} else {
			super.write(buf, off, len);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(java.lang.String, int, int)
	 */
	@Override
	public void write(String s, int off, int len) {
		if (StringUtils.isNotEmpty(s)) {
			super.write(s.toCharArray(), off, len);
		}
	}

}
