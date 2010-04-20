/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.config.MailConfig;
import org.mobylet.mail.detector.MailCarrierDetector;
import org.mobylet.mail.message.MobyletMessage;

public class MobyletMailer {

	public static MobyletMessage createMessage(String to) {
		//Config
		MailConfig config = SingletonUtils.get(MailConfig.class);
		Session session = config.createSession();
		//Carrier
		MailCarrierDetector carrierDetector =
			SingletonUtils.get(MailCarrierDetector.class);
		Carrier carrier = carrierDetector.getCarrierByAddress(to);
		//Message
		MobyletMessage message = new MobyletMessage(carrier, session);
		return message.to(to);
	}

	public static MobyletMessage send(MobyletMessage message) {
		message = message.construct();
		try {
			message.saveChanges();
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("メッセージ再編中に例外発生", e);
		}
		Session session = message.getSession();
		Transport transport = null;
		try {
			transport = session.getTransport("smtp");
			if (MailConstants.TRUE.equals(
					session.getProperty(MailConfig.MAIL_SMTP_AUTH))) {
				transport.connect(
						session.getProperty(MailConfig.MAIL_SMTP_HOST),
						session.getProperty(MailConfig.MAIL_SMTP_USER),
						session.getProperty(MailConfig.MAIL_SMTP_PASS));
			} else {
				transport.connect();
			}
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					//NOP
				}
			}
		}
		return message;
	}

}
