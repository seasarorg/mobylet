package org.mobylet.core.config.xml;

public interface MobyletSessionConfigXml {

	public static final String SL = "/";

	public static final String X_KEY = "key";

	public static final String X_TIMEOUT = "timeout";

	public static final String X_ADAPTER = "adapter";

	public static final String X_DISTRIBUTION = "distribution";

	public static final String X_DTB_PATH = X_DISTRIBUTION + SL + "path";

	public static final String X_DTB_METHOD = X_DISTRIBUTION + SL + "method";

	public static final String X_DTB_PARAMETER = X_DISTRIBUTION + SL + "parameter";

	public static final String X_DTB_RECEIVEHOSTS = X_DISTRIBUTION + SL + "receiveHosts";

	public static final String X_DTB_RH_HOST = X_DTB_RECEIVEHOSTS + SL + "host";

	public static final String X_DTB_ALLOWIPS = X_DISTRIBUTION + SL + "allowIps";

	public static final String X_DTB_AL_IP = X_DTB_ALLOWIPS + SL + "ip";

}
