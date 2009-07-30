package org.mobylet.core.session.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.holder.impl.MobyletSessionHolder;
import org.mobylet.core.session.MobyletSession;
import org.mobylet.core.util.RequestUtils;

public class MobyletSessionImpl implements MobyletSession {

	public static final String KEY_HOLDER = SessionHolder.class.getName();

	protected Class<? extends SessionHolder> holderClass = MobyletSessionHolder.class;


	@Override
	public boolean exist() {
		HttpServletRequest request = RequestUtils.get();
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object holder = session.getAttribute(KEY_HOLDER);
			if (holder != null &&
					holder instanceof SessionHolder) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		if (clazz != null) {
			return (T)get(clazz.getName());
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		HttpServletRequest request = RequestUtils.get();
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object holder = session.getAttribute(KEY_HOLDER);
			if (holder != null &&
					holder instanceof SessionHolder) {
				return (T)SessionHolder.class.cast(holder).get(key);
			}
		}
		return null;
	}

	@Override
	public void invalidate() {
		HttpServletRequest request = RequestUtils.get();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T remove(Class<T> clazz) {
		if (clazz != null) {
			return (T)remove(clazz.getName());
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T remove(String key) {
		HttpServletRequest request = RequestUtils.get();
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object holder = session.getAttribute(KEY_HOLDER);
			if (holder != null &&
					holder instanceof SessionHolder) {
				return (T)SessionHolder.class.cast(holder).remove(key);
			}
		}
		return null;
	}

	@Override
	public <T> void set(T obj) {
		if (obj != null) {
			set(obj.getClass().getName(), obj);
		}
	}

	@Override
	public <T> void set(String key, T obj) {
		HttpServletRequest request = RequestUtils.get();
		HttpSession session = request.getSession(true);
		Object holder = session.getAttribute(KEY_HOLDER);
		if (holder == null ||
				!(holder instanceof SessionHolder)) {
			try {
				holder = holderClass.newInstance();
			} catch (Exception e) {
				throw new MobyletRuntimeException(
						"SessionHolderが生成出来ません holder = "
						+ holderClass.getName(), e);
			}
		}
		SessionHolder.class.cast(holder).set(key, obj);
		session.setAttribute(KEY_HOLDER, holder);
	}

	@Override
	public void substitute() {
		if (exist()) {
			HttpServletRequest request = RequestUtils.get();
			HttpSession session = request.getSession(false);
			Object holder = session.getAttribute(KEY_HOLDER);
			invalidate();
			session = request.getSession(true);
			session.setAttribute(KEY_HOLDER, holder);
		}
	}


}
