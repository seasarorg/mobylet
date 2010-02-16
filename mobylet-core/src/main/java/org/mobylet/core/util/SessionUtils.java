package org.mobylet.core.util;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.config.enums.SessionKey;
import org.mobylet.core.holder.SessionHolder;

public class SessionUtils {

	public static <T> T get(Class<T> clazz) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.get(getKey(), clazz);
	}

	public static void invalidate() {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.invalidate(getKey());
	}

	public static <T> T remove(Class<T> clazz) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.remove(getKey(), clazz);
	}

	public static <T> void set(T obj) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.set(getKey(), obj);
	}

	public static String getKey() {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		Mobylet m = MobyletFactory.getInstance();
		if (m != null) {
			if (config.getSessionKey() == SessionKey.GUID) {
				return m.getGuid();
			} else {
				return m.getUid();
			}
		} else {
			return null;
		}
	}

}
