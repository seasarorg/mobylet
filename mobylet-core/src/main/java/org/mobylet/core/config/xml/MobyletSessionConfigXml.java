package org.mobylet.core.config.xml;

public interface MobyletSessionConfigXml {

	public static final String FILEPATH = "mobyletSession.xml";

	public static final String SL = "/";

	public static final String X_NAME = "name";

	public static final String X_KEY = "key";

	public static final String X_TIMEOUT = "timeout";

	public static final String X_ADAPTER = "adapter";

	public static final String X_DISTRIBUTION = "distribution";

	public static final String X_DTB_PROTOCOL = X_DISTRIBUTION + SL + "protocol";

	public static final String X_DTB_PATH = X_DISTRIBUTION + SL + "path";

	public static final String X_DTB_METHOD = X_DISTRIBUTION + SL + "method";

	public static final String X_DTB_PARAMETERS = X_DISTRIBUTION + SL + "parameters";

	public static final String X_DTB_PM_SESSIONKEY = X_DTB_PARAMETERS + SL + "sessionKey";

	public static final String X_DTB_PM_OBJECTDATA = X_DTB_PARAMETERS + SL + "objectData";

	public static final String X_DTB_PM_INVOKETYPE = X_DTB_PARAMETERS + SL + "invokeType";

	public static final String X_DTB_RECEIVEHOSTS = X_DISTRIBUTION + SL + "receiveHosts";

	public static final String X_DTB_RH_HOST = X_DTB_RECEIVEHOSTS + SL + "host";

	public static final String X_DTB_ALLOWIPS = X_DISTRIBUTION + SL + "allowIps";

	public static final String X_DTB_AL_IP = X_DTB_ALLOWIPS + SL + "ip";

}
