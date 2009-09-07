package org.mobylet.t2gae.page;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.mobylet.t2gae.dto.MessageDto;
import org.mobylet.t2gae.util.PMF;
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
@Page("/top")
public class IndexPage {

	@Default
	@SuppressWarnings("unchecked")
	public Navigation index(WebContext context) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query query = pm.newQuery(MessageDto.class);
		query.setOrdering("postDate desc");
		query.setRange(0, 20);
		List<MessageDto> messageList = (List<MessageDto>)query.execute();
		List<String> list = new ArrayList<String>();
		for (MessageDto message : messageList) {
			list.add(message.getMessage());
		}
		context.getRequest().setAttribute("list", list);

		return Forward.to("/WEB-INF/pages/show.jsp");
	}

}
