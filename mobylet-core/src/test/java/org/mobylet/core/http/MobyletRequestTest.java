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
package org.mobylet.core.http;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlDecoder;

public class MobyletRequestTest extends TestCase {

	public void test_init() {
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
		mergeParametersString(en);
	}

	@SuppressWarnings("unchecked")
	protected void mergeParametersString(String q) {
		Map<String, Object> mergeMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(q)) {
			return;
		}
		Charset orgCharset = Charset.forName("windows-31j");
		String[] keyValues = q.split("[&]");
		for (String keyValue : keyValues) {
			int index = 0;
			if ((index = keyValue.indexOf('=')) > 0) {
				String key = UrlDecoder.decode(
						keyValue.substring(0, index), orgCharset);
				String val = UrlDecoder.decode(
						keyValue.substring(index + 1), orgCharset);
				if (mergeMap.containsKey(key)) {
					Object valueSet = mergeMap.get(key);
					if (valueSet instanceof Set) {
						((Set<String>)valueSet).add(val);
					} else if (valueSet instanceof String[]) {
						String[] varList = (String[])valueSet;
						Set<String> tmpSet = new LinkedHashSet<String>();
						for (String var : varList) {
							tmpSet.add(var);
						}
						tmpSet.add(val);
						valueSet = tmpSet;
					}
					mergeMap.put(key, valueSet);
				} else {
					Set<String> valSet = new LinkedHashSet<String>();
					valSet.add(val);
					mergeMap.put(key, valSet);
				}
			}
		}
	}
}
