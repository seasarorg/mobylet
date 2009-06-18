package org.mobylet.mail.config;

import javax.mail.Session;

public interface MailConfig {

	public String getHost();

	public String getPort();

	public String getUser();

	public String getPassword();

	public Session createSession();
}
