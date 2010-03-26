package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.utils.JspWriterUtils;

public class HiddenUidTag extends MobyletDynamicSimpleTagSupport {

	public static final String TAG = "input type=\"hidden\"";

	@Override
	public void doTag() throws JspException, IOException {
		try {
			Mobylet m = MobyletFactory.getInstance();
			if (m.getCarrier() == Carrier.DOCOMO) {
				addAttribute("name", "uid");
				String uid = m.getUid();
				addAttribute("value", uid != null ? uid : "NULLGWDOCOMO");
				JspWriterUtils.write(
						getJspContext().getOut(),
						STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG
						);
			}
			recycle();
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

}
