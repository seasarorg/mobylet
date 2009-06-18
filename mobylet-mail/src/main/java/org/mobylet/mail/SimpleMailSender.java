package org.mobylet.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.mobylet.core.Carrier;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.config.MailConfig;
import org.mobylet.mail.detector.MailCarrierDetector;
import org.mobylet.mail.message.MobyletMessage;
import org.mobylet.mail.parts.builder.PartsBuilder;
import org.mobylet.mail.parts.builder.TextPartsBuilder;

public class SimpleMailSender {

	public static void send(String to, String from, String subject, String body) {
		MailConfig config = SingletonUtils.get(MailConfig.class);
		Session session = config.createSession();

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
