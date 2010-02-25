package org.mobylet.core.config;

import java.util.ArrayList;
import java.util.List;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.enums.SessionKey;
import org.mobylet.core.session.MobyletSessionAdapter;

public class MobyletSessionConfig {

	protected SessionKey key;

	protected int timeout;

	protected MobyletSessionAdapter adapter;

	protected Distribution distribution;


	public MobyletSessionConfig() {
		initialize();
	}

	protected void initialize() {
		//Xml mobyletSessionXml = new Xml("mobyletSession.xml");
		//XmlNode root = mobyletSessionXml.getRootNode();
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

	}

	public static class Parameters {

		protected String objectDataKey;

		protected String invokeTypeKey;

		public Parameters(String objectDataKey, String invokeTypeKey) {
			this.objectDataKey = objectDataKey;
			this.invokeTypeKey = invokeTypeKey;
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

		protected byte[] b;

		protected int mask;

		public Ip(String ip) {
			ipString = ip;
			int maskIndex = 0;
			if ((maskIndex = ip.indexOf('/')) > 0) {
				mask = Integer.parseInt(ip.substring(maskIndex + 1));
				ipString = ip.substring(0, maskIndex);
			}
			String[] ipElements = ipString.split("[.]");
			if (ipElements.length != 4) {
				throw new MobyletRuntimeException("IPアドレスの形式が間違っています = " + ip, null);
			}
			for (int i=0; i<ipElements.length; i++) {
				b[i] = Byte.parseByte(ipElements[i]);
			}
		}

	}
}
