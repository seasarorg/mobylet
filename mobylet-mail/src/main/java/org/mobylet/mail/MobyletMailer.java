package org.mobylet.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.mobylet.core.Carrier;
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
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}

}
