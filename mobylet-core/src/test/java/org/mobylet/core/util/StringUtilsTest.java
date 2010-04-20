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

import junit.framework.TestCase;


public class StringUtilsTest extends TestCase {

	public void test_zen2han(){
		assertEquals("本日はｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝです",
						StringUtils.toHan("本日はアイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンです"));
		assertEquals("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞ",
						StringUtils.toHan("ガギグゲゴザジズゼゾダヂヅデドバビブベボ"));
		assertEquals("ｧｨｩｪｫｯｬｭｮ", StringUtils.toHan("ァィゥェォッャュョ"));
		assertEquals("ｲｴﾜｳﾞｶｹ", StringUtils.toHan("ヰヱヮヴヵヶ"));
		assertEquals("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
						StringUtils.toHan("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
		assertEquals("0123456789", StringUtils.toHan("０１２３４５６７８９"));
		assertEquals("/---ﾞﾟ･",
						StringUtils.toHan("／－ー―゛゜・"));
	}

}
