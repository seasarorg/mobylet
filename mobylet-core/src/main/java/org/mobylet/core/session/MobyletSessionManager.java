package org.mobylet.core.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MobyletSessionManager {

	boolean isManaged(HttpServletRequest request);
	
	void invoke(HttpServletRequest request, HttpServletResponse response);
	
}
