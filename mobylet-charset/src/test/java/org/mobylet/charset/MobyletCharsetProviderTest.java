package org.mobylet.charset;

import java.nio.charset.Charset;
import java.util.Iterator;

import junit.framework.TestCase;

public class MobyletCharsetProviderTest extends TestCase {

	public void test_iterator() {
		//## ARRANGE ##
		boolean hasDocomo = false;
		boolean hasAu = false;
		MobyletCharsetProvider provider = new MobyletCharsetProvider();

		//## ACT ##
		Iterator<Charset> charsets = provider.charsets();
		while (charsets.hasNext()) {
			Charset charset = charsets.next();
			if (charset instanceof MobyletDocomoCharset) {
				hasDocomo = true;
			}
			if (charset instanceof MobyletAuCharset) {
				hasAu = true;
			}
		}

		//## ASSERT ##
		assertTrue(hasDocomo);
		assertTrue(hasAu);
	}

	public void test_charsetForName() {
		//## ARRANGE ##
		MobyletCharsetProvider provider = new MobyletCharsetProvider();

		//## ACT ##
		Charset docomo = provider.charsetForName("x-mobylet-docomo");
		Charset au = provider.charsetForName("x-mobylet-au");

		//## ASSERT ##
		assertTrue(docomo instanceof MobyletDocomoCharset);
		assertTrue(au instanceof MobyletAuCharset);
	}
}
