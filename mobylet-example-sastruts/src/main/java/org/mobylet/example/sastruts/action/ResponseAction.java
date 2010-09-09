package org.mobylet.example.sastruts.action;

import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.ResponseUtil;

public class ResponseAction {

	@Execute(validator = false)
	public String index() {
		ResponseUtil.write("OK");
		return null;
	}

}
