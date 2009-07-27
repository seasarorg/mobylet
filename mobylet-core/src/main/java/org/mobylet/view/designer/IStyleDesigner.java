package org.mobylet.view.designer;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.type.ContentType;
import org.mobylet.core.util.StringUtils;


public class IStyleDesigner extends SingletonDesigner {


	public static final String ATT_STYLE_PREFIX = "style=";


	public String addIStyle(String inputTag, int istyle) {
		if (StringUtils.isEmpty(inputTag)) {
			return inputTag;
		}
		Mobylet m = MobyletFactory.getInstance();
		String att = getIStyle(m.getCarrier(), m.getContentType(), istyle);
		if (StringUtils.isEmpty(att)) {
			return inputTag;
		}
		int sTagLastIndex = inputTag.indexOf('>');
		if (inputTag.charAt(sTagLastIndex - 1) == '/') {
			sTagLastIndex = sTagLastIndex - 1;
		}
		if (att.startsWith(ATT_STYLE_PREFIX)) {
			String sTag = inputTag.substring(0, sTagLastIndex);
			if (sTag.contains(ATT_STYLE_PREFIX)) {
				int index = sTag.indexOf(ATT_STYLE_PREFIX);
				return inputTag.substring(0, index)
						+ att.substring(7, att.length()-1) + ";"
						+ inputTag.substring(index+7);
			}
		}
		return inputTag.substring(0, sTagLastIndex)
					+ " " + att
					+ inputTag.substring(sTagLastIndex);
	}

	public String getIStyle(
			Carrier carrier, ContentType contentType, int istyle) {
		String att = null;
		switch (carrier) {
		case DOCOMO:
			if (contentType == ContentType.HTML) {
				att = "istyle=";
				switch (istyle) {
				case 1:
					//全角かな
					return att + "\""+ 1 + "\"";
				case 2:
					//半角カナ
					return att + "\""+ 2 + "\"";
				case 3:
					//英字
					return att + "\""+ 3 + "\"";
				case 4:
					//数字
					return att + "\""+ 4 + "\"";
				}
			} else if (contentType == ContentType.XHTML){
				att = "style=";
				switch (istyle) {
				case 1:
					//全角かな
					return att + "\"-wap-input-format:&quot;*&lt;ja:h&gt;&quot;\"";
				case 2:
					//半角カナ
					return att + "\"-wap-input-format:&quot;*&lt;ja:hk&gt;&quot;\"";
				case 3:
					//英字
					return att + "\"-wap-input-format:&quot;*&lt;ja:en&gt;&quot;\"";
				case 4:
					//数字
					return att + "\"-wap-input-format:&quot;*&lt;ja:n&gt;&quot;\"";
				}
			}
		case AU:
			att = "istyle=";
			switch (istyle) {
			case 1:
				//全角かな
				return att + "\""+ 1 + "\"";
			case 2:
				//半角カナ
				return att + "\""+ 2 + "\"";
			case 3:
				//英字
				return att + "\""+ 3 + "\"";
			case 4:
				//数字
				return att + "\""+ 4 + "\"";
			}
		case SOFTBANK:
			att = "mode=";
			switch (istyle) {
			case 1:
				//全角かな
				return att + "\"hiragana\"";
			case 2:
				//半角カナ
				return att + "\"hankakukana\"";
			case 3:
				//英字
				return att + "\"alphabet\"";
			case 4:
				//数字
				return att + "\"numeric\"";
			}
		case OTHER:
			return null;
		}
		return null;
	}



}

