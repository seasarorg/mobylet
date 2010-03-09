package org.mobylet.mail.config;

import javax.mail.Session;

public interface MailConfig {

	public static final String MAIL_HOST = "mail.host";

	public static final String MAIL_SMTP_HOST = "mail.smtp.host";

	public static final String MAIL_SMTP_LOCALHOST = "mail.smtp.localhost";

	public static final String MAIL_SMTP_PORT = "mail.smtp.port";

	public static final String MAIL_SMTP_USER = "mail.smtp.user";

	public static final String MAIL_SMTP_PASS = "mail.smtp.pass";

	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";


	public String getHost();

	public String getPort();

	public String getUser();

	public String getPassword();

	public Session createSession();
}
