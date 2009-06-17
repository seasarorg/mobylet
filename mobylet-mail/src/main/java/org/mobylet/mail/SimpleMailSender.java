package org.mobylet.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.mobylet.core.Carrier;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.detector.MailCarrierDetector;
import org.mobylet.mail.message.MobyletMessage;
import org.mobylet.mail.parts.builder.PartsBuilder;
import org.mobylet.mail.parts.builder.TextPartsBuilder;

public class SimpleMailSender {

	public static void send(String to, String from, String subject, String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MailCarrierDetector carrierDetector =
			SingletonUtils.get(MailCarrierDetector.class);
		Carrier carrier = carrierDetector.getCarrierByAddress(to);

		PartsBuilder builder = new TextPartsBuilder();
		MobyletMessage message = new MobyletMessage(carrier, session);
		message.from(from)
				.to(to)
				.subject(subject)
				.setBodyPart(builder.buildPart(carrier, body));

		try {
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
