package org.mobylet.core.config;

import java.util.ArrayList;
import java.util.List;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.enums.SessionKey;
import org.mobylet.core.session.MobyletSessionAdapter;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.XmlUtils;
import org.mobylet.core.xml.Xml;
import org.mobylet.core.xml.XmlNode;

public class MobyletSessionConfig {

	protected SessionKey key;

	protected int timeout;

	protected MobyletSessionAdapter adapter;

	protected Distribution distribution;


	public MobyletSessionConfig() {
		initialize();
	}

	protected void initialize() {
		Xml mobyletSessionXml = new Xml("mobyletSession.xml");
		XmlNode root = mobyletSessionXml.getRootNode();
		//GlobalTags
		key = SessionKey.valueOf(XmlUtils.getValue(root, "key"));
		timeout = Integer.parseInt(XmlUtils.getValue(root, "timeout"));
		try {
			adapter = MobyletSessionAdapter.class.cast(
					Class.forName(XmlUtils.getValue(root, "adapter")).newInstance());
			SingletonUtils.put(adapter);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//DistributionTags
		if (root.getNodeListByXPath("distribution").size() == 1) {
			distribution = new Distribution();
			distribution.setProtocol(XmlUtils.getValue(root, "distribution/protocol"));
			distribution.setPath(XmlUtils.getValue(root, "distribution/path"));
			distribution.setMethod(XmlUtils.getValue(root, "distribution/method"));
			//ParametersTags
			if (root.getNodeListByXPath("distribution/parameters").size() == 1) {
				Parameters parameters = new Parameters(
						XmlUtils.getValue(root, "distribution/parameters/sessionKey"),
						XmlUtils.getValue(root, "distribution/parameters/objectData"),
						XmlUtils.getValue(root, "distribution/parameters/invokeType"));
				distribution.setParameters(parameters);
			}
			//ReceiveHostsTags
			if (root.getNodeListByXPath("distribution/receiveHosts").size() == 1) {
				List<XmlNode> hosts = root.getNodeListByXPath("distribution/receiveHosts/host");
				if (hosts != null) {
					for (XmlNode node : hosts) {
						distribution.addReceiveHost(
								node.getAttributes().get("name"), node.getValue());
					}
				}
			}
			//AllowIpsTags
			if (root.getNodeListByXPath("distribution/allowIps").size() == 1) {
				List<XmlNode> ips = root.getNodeListByXPath("distribution/allowIps/ip");
				if (ips != null) {
					for (XmlNode node : ips) {
						distribution.addAllowIp(node.getValue());
					}
				}
			}
		}
	}

	public SessionKey getKey() {
		return key;
	}

	public int getTimeout() {
		return timeout;
	}

	public MobyletSessionAdapter getAdapter() {
		return adapter;
	}

	public Distribution getDistribution() {
		return distribution;
	}


	public static class Distribution {

		protected String protocol;

		protected String path;

		protected String method;

		protected Parameters parameters;

		protected List<Host> receiveHosts;

		protected List<Ip> allowIps;


		public Distribution() {
			receiveHosts = new ArrayList<Host>();
			allowIps = new ArrayList<Ip>();
		}

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public void setParameters(Parameters parameters) {
			this.parameters = parameters;
		}

		public Parameters getParameters() {
			return parameters;
		}

		public List<Host> getReceiveHosts() {
			return receiveHosts;
		}

		public void addReceiveHost(String name, String host) {
			receiveHosts.add(new Host(name, host));
		}

		public List<Ip> getAllowIps() {
			return allowIps;
		}

		public void addAllowIp(String ip) {
			allowIps.add(new Ip(ip));
		}

		public boolean isAllowIp(String ip) {
			int ipInteger = Ip.getIpInteger(ip);
			for (Ip allowIp : allowIps) {
				if (allowIp.isAllow(ipInteger)) {
					return true;
				}
			}
			return false;
		}

	}

	public static class Parameters {

		protected String sessionKey;

		protected String objectDataKey;

		protected String invokeTypeKey;

		public Parameters(String sessionKey, String objectDataKey, String invokeTypeKey) {
			this.sessionKey = sessionKey;
			this.objectDataKey = objectDataKey;
			this.invokeTypeKey = invokeTypeKey;
		}

		public String getSessionKey() {
			return sessionKey;
		}

		public String getObjectDataKey() {
			return objectDataKey;
		}

		public String getInvokeTypeKey() {
			return invokeTypeKey;
		}

	}

	public static class Host {

		protected String name;

		protected String host;

		public Host(String name, String host) {
			this.name = name;
			this.host = host;
		}

		public String getName() {
			return name;
		}

		public String getHost() {
			return host;
		}

	}

	public static class Ip {

		protected String ipString;

		protected int ipStart;

		protected int ipEnd;

		protected int mask;

		public Ip(String ip) {
			ipString = ip;
			int maskIndex = 0;
			if ((maskIndex = ip.indexOf('/')) > 0) {
				mask = Integer.parseInt(ip.substring(maskIndex + 1));
				ipString = ip.substring(0, maskIndex);
			}
			int ipInteger = getIpInteger(ipString);
			if (mask != 0) {
				ipStart = ipInteger & (-1 << (32-mask));
				ipEnd = ipInteger | (-1 >>> mask);
			} else {
				ipStart = ipInteger;
				ipEnd = ipInteger;
			}
		}

		public boolean isAllow(String ip) {
			return isAllow(getIpInteger(ip));
		}

		public boolean isAllow(int ipInteger) {
			return (ipStart <= ipInteger) && (ipInteger <= ipEnd);
		}

		public static int getIpInteger(String ip) {
			String[] ipElements = ip.split("[.]");
			if (ipElements.length != 4) {
				throw new MobyletRuntimeException("IPアドレスの形式が間違っています = " + ip, null);
			}
			int ipInteger = 0x0000;
			ipInteger = Integer.parseInt(ipElements[0]) << 24;
			ipInteger += Integer.parseInt(ipElements[1]) << 16;
			ipInteger += Integer.parseInt(ipElements[2]) << 8;
			ipInteger += Integer.parseInt(ipElements[3]);
			return ipInteger;
		}
	}
}
