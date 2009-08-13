package org.mobylet.view.designer;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.StringUtils;


public class XmlHeaderDesigner extends SingletonDesigner {

	public String get(String iversion) {
		Mobylet m = MobyletFactory.getInstance();
		String header =
			"<?xml version=\"1.0\" encoding=\""
			+ m.getDialect().getContentCharsetName()
			+ "\"?>\r\n";
		switch (m.getCarrier()) {
		case DOCOMO:
			return header +
			"<!DOCTYPE html PUBLIC \"-//i-mode group (ja)//DTD XHTML i-XHTML" +
			"(Locale/Ver.=ja/" +
			(StringUtils.isEmpty(iversion) ? "1.0" : iversion) +
			") 1.0//EN\" \"i-xhtml_4ja_10.dtd\">";
		case AU:
			return header +
			"<!DOCTYPE html PUBLIC \"-//OPENWAVE//DTD XHTML 1.0//EN\" " +
			"\"http://www.openwave.com/DTD/xhtml-basic.dtd\">";
		case SOFTBANK:
			return header +
			"<!DOCTYPE html PUBLIC \"-//J-PHONE//DTD XHTML Basic 1.0 Plus//EN\" " +
			"\"xhtml-basic10-plus.dtd\">";
		default:
			return header +
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
			"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		}
	}

}
