package org.mobylet.core.holder.impl;

import java.util.HashMap;
import java.util.LinkedList;

import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.UidSession;
import org.mobylet.core.session.UidSessionTouchListener;
import org.mobylet.core.util.SingletonUtils;

public class MobyletSessionHolder implements SessionHolder, UidSessionTouchListener {

	protected HashMap<String, UidSession> holder = new HashMap<String, UidSession>();

	protected LinkedList<UidSession> linkedSession = new LinkedList<UidSession>();

	
	@Override
	public <T> T get(String uid, Class<T> clazz) {
		UidSession session = getSession(uid);
		if (session != null) {
			return session.get(clazz);
		}
		return null;
	}

	@Override
	public <T> T remove(String uid, Class<T> clazz) {
		UidSession session = getSession(uid);
		if (session != null) {
			return session.remove(clazz);
		}
		return null;
	}

	@Override
	public <T> void set(String uid, T obj) {
		if (uid != null) {
			UidSession session = getSession(uid);
			if (session == null) {
				session = new UidSession(uid);
				session.setTouchListener(this);
				session.touch();
				holder.put(uid, session);
			}
			session.put(obj);
		}
	}

	@Override
	public void invalidate(String uid) {
		UidSession session = holder.remove(uid);
		linkedSession.remove(session);
	}
	
	protected void processClean() {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		Long expiredTime = System.currentTimeMillis() - (config.getSessionTimeout() * 60000);
		UidSession session = null;
		while (((session = linkedSession.peek()) != null) &&
				(session.getTouchTime() < expiredTime)) {
			linkedSession.poll();
		}
	}

	protected UidSession getSession(String uid) {
		processClean();
		if (uid != null) {
			UidSession session = holder.remove(uid);
			if (session == null) {
				return null;
			}
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			Long expiredTime = System.currentTimeMillis() - (config.getSessionTimeout() * 60000);
			if (session.getTouchTime() < expiredTime) {
				linkedSession.remove(session);
				return null;
			} else {
				session.touch();
				holder.put(uid, session);
				return session;
			}
		}
		return null;
	}

	@Override
	public void actionTouch(UidSession session) {
		if (linkedSession.remove(session)) {
			linkedSession.add(session);
		}
	}

}
