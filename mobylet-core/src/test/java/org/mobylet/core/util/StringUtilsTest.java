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
