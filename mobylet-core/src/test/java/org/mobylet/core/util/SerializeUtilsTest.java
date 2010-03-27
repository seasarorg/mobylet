package org.mobylet.core.util;

import java.io.Serializable;
import java.nio.charset.Charset;

import junit.framework.TestCase;


public class SerializeUtilsTest extends TestCase {

	public void test_testClass1(){
		TestClass1 testClass1 = new TestClass1();
		testClass1.abc = "abc";
		testClass1.bcd = 3;
		testClass1.cccc = "cccc";

		String base64String = SerializeUtils.serialize2Base64String(testClass1);
		TestClass1 newClass1 = (TestClass1)SerializeUtils.deserialize(base64String);

		assertEquals(testClass1.abc, newClass1.abc);
		assertEquals(testClass1.bcd, newClass1.bcd);
		assertEquals(testClass1.cccc, newClass1.cccc);
	}

	public void test_wish(){
		TestSessionDto testClass1 = new TestSessionDto();

		String base64String = SerializeUtils.serialize2Base64String(testClass1);
		TestSessionDto newClass1 = (TestSessionDto)SerializeUtils.deserialize(base64String);

		assertEquals(testClass1.content, newClass1.content);
	}

	public void test_wish2(){
		TestSessionDto testClass1 = new TestSessionDto();
		testClass1.content = "ABCD";

		String base64String = SerializeUtils.serialize2Base64String(testClass1);
		String urlEnc = UrlEncoder.encode(base64String, Charset.forName("UTF-8"));
		String urlDec = UrlDecoder.decode(urlEnc, Charset.forName("UTF-8"));
		TestSessionDto newClass1 = (TestSessionDto)SerializeUtils.deserialize(urlDec);

		assertEquals(testClass1.content, newClass1.content);
	}

	public static class TestClass1 implements Serializable {
		private static final long serialVersionUID = 4629473694231538033L;
		public String abc;
		public Integer bcd;
		public String cccc;
	}

	public static class TestSessionDto implements Serializable {
		private static final long serialVersionUID = 1L;
		protected String content;
		protected String sa;
		protected String df;
		protected String wg;
		protected String wf;
		protected String my;
		protected String ma;
		protected String year;
		protected String month;
		protected String day;
		protected String manzokuCd;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getSa() {
			return sa;
		}
		public void setSa(String sa) {
			this.sa = sa;
		}
		public String getDf() {
			return df;
		}
		public void setDf(String df) {
			this.df = df;
		}
		public String getWg() {
			return wg;
		}
		public void setWg(String wg) {
			this.wg = wg;
		}
		public String getWf() {
			return wf;
		}
		public void setWf(String wf) {
			this.wf = wf;
		}
		public String getMy() {
			return my;
		}
		public void setMy(String my) {
			this.my = my;
		}
		public String getMa() {
			return ma;
		}
		public void setMa(String ma) {
			this.ma = ma;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public String getManzokuCd() {
			return manzokuCd;
		}
		public void setManzokuCd(String manzokuCd) {
			this.manzokuCd = manzokuCd;
		}
	}

}
