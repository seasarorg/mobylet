package org.mobylet.t2gae.page;

import java.util.Date;

import javax.jdo.PersistenceManager;

import org.mobylet.t2gae.dto.MessageDto;
import org.mobylet.t2gae.util.PMF;
import org.t2framework.commons.annotation.composite.SingletonScope;
import org.t2framework.t2.annotation.core.Default;
import org.t2framework.t2.annotation.core.Page;
import org.t2framework.t2.contexts.Request;
import org.t2framework.t2.contexts.WebContext;
import org.t2framework.t2.navigation.Redirect;
import org.t2framework.t2.spi.Navigation;

/**
 *
 */
@SingletonScope
@Page("/mailpost")
public class MailPostPage {

	@Default
	public Navigation index(WebContext context) {
		Request request = context.getRequest();
		String comment = request.getParameter("pBody");
		if (comment == null || comment.length() == 0) {
			return Redirect.to("/top/");
		}
		MessageDto message = new MessageDto();
		message.setMessage(comment);
		message.setPostDate(new Date());

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(message);
		} finally {
			pm.close();
		}
		return Redirect.to("/top/");
	}

}
