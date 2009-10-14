package org.mobylet.core.http.util;

import org.mobylet.core.util.RequestUtils;

public class ForceWrapUtils {

	public static boolean isForceWrapRequest() {
		return RequestUtils.getMobyletContext().get(ForceWrapObject.class) != null;
	}
	
	public static void setForceWrapRequest() {
		RequestUtils.getMobyletContext().set(new ForceWrapObject());
	}
	
	public static void removeForceWrapRequest() {
		RequestUtils.getMobyletContext().remove(ForceWrapObject.class);
	}
	
	public static class ForceWrapObject {
		//NOP
	}
}
