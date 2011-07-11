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

import java.nio.charset.Charset;

import junit.framework.TestCase;

public class UrlDecoderTest extends TestCase {

	public void test_decode(){
		String en = "ABC%81%a6%81j2009%2f12%2f16(%90%85)%82%dc%82%c5%82%cc%8a" +
				"%fa%8a%d4%8c%c0%92%e8%83N%83G%83X%83g%82%cc%97l%82%c5%82" +
				"%b7%81B%0d%0a%0d%0a%82%bb%82%cc%8el%81w%93%60%90%e0%82%cc" +
				"%96%d8%81x%0d%0a%0d%0a%97%f6%82%b7%82%e9%92j%8eq%90%b6%93k" +
				"%82%cc%8d%90%94%92%82%f0%8e%e8%93%60%82%a4%83N%83G%83X%83g" +
				"%82%c5%82%b7%81B%0d%0a'09%82%cc11%8c%8e%83K%83%60%83%83%83A" +
				"%83C%83e%83%80%82%f0%8eg%82%a4%82%c6%83X%83e%81%5b%83%5e%83" +
				"XUP%82%b7%82%e9%8e%96%82%aa%82%c5%82%ab%83N%83G%83X%83g%90" +
				"%ac%8c%f7%81B%0d%0a%83A%83C%83e%83%80%82%c9%82%e6%82%c1%82" +
				"%c4%82%cd%8d%90%94%92%82%c9%8e%b8%94s%82%b7%82%e9%82%b1%82" +
				"%c6%82%e0%82%a0%82%e9%82%aa%81A%83N%83G%83X%83g%82%cd%90%ac" +
				"%8c%f7%82%c8%82%cc%82%c5%83X%83e%81%5b%0d%0a%83%5e%83XUP%82" +
				"%cd%82%b7%82%e9%81B%0d%0a%83A%83C%83e%83%80%82%aa%82%c8%82" +
				"%a2%82%c6%8d%90%94%92%82%b7%82%e9%83%5e%83C%83~%83%93%83O%82" +
				"%c5%83%82%83%93%83X%83%5e%81%5b%82%aa%8fP%82%c1%82%c4%82%ab%82" +
				"%c4%90%ed%93%ac%81A%8f%9f%82%c1%82%c4%82%e0%0d%0a%83N%83G%83X" +
				"%83g%8e%b8%94s%82%c9%82%c8%82%e8%82%dc%82%b7%81B%0d%0a%0d%0a%89" +
				"%5e%96%bd%82%cc%8b%c8%82%aa%82%e8%8ap%81%a8%8a%ed%97p%2b1%81EHP" +
				"%2b5%0d%0a%93%60%90%e0%82%cc%96%d8%81%40%81%40%81%40%81%a8%98r%97" +
				"%cd%2b1%81E%8d%aa%90%ab%2b1%0d%0a%83~%83%89%8aw%8f%97%8eq%90%a7" +
				"%95%9e%81%40%81%a8%96%82%97%cd%2b1%81EMP%2b2%0d%0a%83~%83%89%8aw" +
				"%92j%8eq%90%a7%95%9e%81%40%81%a8%98r%97%cd%2b1%81EHP%2b2%0d%0a%8b" +
				"%8c%8dZ%8e%c9%82%cc%8c%ae%81%40%81%40%81%a8%8dZ%93%e0%90V%95%b7vol.7" +
				"%0d%0aBADEND%81%40%81%40%81%40%81%40%81%a8%8d%aa%90%ab%2b1%0d%0a" +
				"%89%b3%8f%97%82%cc%83%89%83u%83%8c%83%5e%81%5b%81%40%81%a8%96%82" +
				"%97%cd%2b1%0d%0a%8f%5c%88%ea%8c%8e%95a%81%40%81%40%81%40%81%a8%83" +
				"%8f%81E%83C%83n%81%5b%82%cc%89H%0d%0a%8eg%97p%82%b7%82%e9%82%c6%83A" +
				"%83C%83e%83%80%82%cd%82%c8%82%ad%82%c8%82%e8%82%dc%82%b7%81B%0d%0a%81%8 ABC";

		assertTrue(UrlDecoder.decode(en, Charset.forName("windows-31j")).length() > 0);
	}

	public void test_decode2() {
		String en = "town=%D4%D1%A5%B8%DE%AF%C1&kind=%B5%BD%BD%D2" +
				"&SMsg=%82%B1%82%CC%93X%82%CC%97%D7%82%CD%81A%97%BF%92%E0%82%C8" +
				"%82%F1%82%C5%82%B7%82%E6%81B%82%C8%82%A9%82%C8%82%A9%8D%82%8B%89" +
				"%82%C8%93X%82%C5%82%A0%82%E8%82%C8%82%AA%82%E7%81A%82%A8%8Bq%82" +
				"%B3%82%F1%82%CC%93%FC%82%E8%82%CD%8F%E3%81X%81B%82%A4%82%E7%82%E2" +
				"%82%DC%82%B5%82%A2%8E%96%82%C5%82%B7%82%C8%81B%0D%0A%82%BB%82" +
				"%EA%82%AA%8FW%8Bq%97%A6%82%AA%8D%82%82%B7%82%AC%82%C4%81A%89%DF" +
				"%98J%82%C5%93%7C%82%EA%82%C4%82%B5%82%DC%82%C1%82%BD%82%C6%82%A9" +
				"%81B&place=%8E%F0%8F%EA%97%D7%82%CC%97%BF%92%E0&" +
				"name=%BB%DE%B7%DE%DD%82%C5%82%E0%81A%91%E5%90l%8BC%82%BE%82%E6%83t%83O%97%BF%97%9D&" +
				"getitem=&wantitem=%88%A4%82%CC%8E%B5%82%C2%93%B9%8B%EF&move=%A6%AF%B6%A5%B1%CF%81A%D4%D1%A5%B8%DE%AF%C1" +
				"&boss=&bossexp=&bossmoney=&zako=&zakoexpmin=&zakoexpmax=&zakomoneymin=&zakomoneymax=&paymoney=800" +
				"&payexp=200&zennin=6&timei=10&syougou=&level=%81%9A&comment=%96%83%E1%83%82%F0%92%BC%82%B7%82%BD%82%DF%A4" +
				"%89%F1%95%9C%BD%B7%D9%28%91m%97%B5%A5%90%B9%90%ED%8Em%82%C5%8Am%94F%8D%CF%82%DD%A1%8AY%93%96%82%B7%82%E9%BD" +
				"%B7%D9%82%C8%82%B5%82%CC%8F%EA%8D%87%82%CD%95s%96%BE%29%82%A9%A2%88%A4%82%CC%8E%B5%82%C2%93%B9%8B%EF%A3%82" +
				"%AA%82%A0%82%EA%82%CE%A4%88%DA%93%AE%82%C8%82%B5%82%C5%89%F0%8C%88%A1%0D%0A%B1%B2%C3%D1%82%CD%8E%9D%82%C1%82" +
				"%C4%82%A2%82%E9%82%BE%82%AF%82%C5OK%82%C5%A4%8E%C0%8D%DB%82%C9%82%CD%8F%C1%94%EF%82%B5%82%C8%82%A2%A1%0D%0A%95" +
				"%F1%8FV%82%CDEXP200%A5M%24800%A1%0D%0A%0D%0A%B1%B2%C3%D1%82%E0%BD%B7%D9%82%E0%82%C8%82%A2%8F%EA%8D%87%A4%94%83" +
				"%82%C1%82%C4%97%88%82%E9%95K%97v%82%A0%82%E8%A1%8D%C5%8A%F1%82%E8%82%CD%A6%AF%B6%A5%B1%CF%A1%0D%0A%82%B1%82%CC" +
				"%8F%EA%8D%87%A4%95%F1%8FV%82%CDEXP50%A5M%24300%A1%0D%0A%0D%0A%88%A4%82%CC%8E%B5%82%C2%93%B9%8B%EF%A5%89%F1%95%9C" +
				"%BD%B7%D9%97%BC%95%FB%8F%8A%8E%9D%82%CC%8F%EA%8D%87%BD%B7%D9%97D%90%E6%82%C9%82%C8%82%E9%A1%0D%0A%82%B1%82%CC%8F" +
				"%EA%8D%87%82%CC%95%F1%8FV%82%E0EXP50%A5M%24300%A1&seigen=&map=&username=&password=&id=578&submit=1.%8FC%90%B3";
		assertTrue(UrlDecoder.decode(en, Charset.forName("windows-31j")).length() > 0);
	}
	
	public void test_decode3() {
		String a = "+";
		Charset charset = Charset.forName("UTF-8");
		assertTrue(a.equals(UrlDecoder.decode(UrlEncoder.encode(a, charset), charset)));
	}

}
