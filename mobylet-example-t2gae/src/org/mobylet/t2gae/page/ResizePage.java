package org.mobylet.t2gae.page;

import org.t2framework.commons.annotation.composite.SingletonScope;
import org.t2framework.t2.annotation.core.Default;
import org.t2framework.t2.annotation.core.Page;
import org.t2framework.t2.contexts.WebContext;
import org.t2framework.t2.navigation.Forward;
import org.t2framework.t2.spi.Navigation;

/**
 *
 */
@SingletonScope
@Page("resize")
public class ResizePage {

	@Default
	public Navigation index(WebContext context) {
		return Forward.to("/pages/resize2.jsp");
	}
}
