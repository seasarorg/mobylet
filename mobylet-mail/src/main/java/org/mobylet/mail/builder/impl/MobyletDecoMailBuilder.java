package org.mobylet.mail.builder.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.builder.HtmlPart;
import org.mobylet.mail.builder.MobyletMailBuilder;
import org.mobylet.mail.message.MobyletMessage;
import org.mobylet.mail.message.MessageBody.Attach;
import org.mobylet.mail.util.DataHandlerUtils;
import org.mobylet.mail.util.PartUtils;

public class MobyletDecoMailBuilder implements MobyletMailBuilder, MailConstants {

	@Override
	public MobyletMessage build(MobyletMessage message) {
		MimeMultipart decoMultipart = buildDecoBaseMultipart(message);
		MimeMultipart topMultipart = decoMultipart;
		//Docomo-Mode
		if (message.getCarrier() == Carrier.DOCOMO) {
			topMultipart = new MimeMultipart();
			try {
				topMultipart.setSubType(MIXED);
				MimeBodyPart subBodyPart = new MimeBodyPart();
				subBodyPart.setContent(decoMultipart);
				topMultipart.addBodyPart(subBodyPart);
			} catch (MessagingException e) {
				throw new MobyletRuntimeException("メッセージ構築時に例外発生", e);
			}
		}
		//Attach
		if (message.getBody().getAttaches().size() > 0) {
			try {
				for (Attach attach : message.getBody().getAttaches()) {
					topMultipart.addBodyPart(
							PartUtils.buildAttachPart(
									message.getCarrier(),
									attach)
						);
				}
			} catch (MessagingException e) {
				throw new MobyletRuntimeException("メッセージ構築時に例外発生", e);
			}
		}
		try {
			message.setContent(topMultipart);
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("メッセージ構築時に例外発生", e);
		}
		return message;

	}

	public MimeMultipart buildDecoBaseMultipart(MobyletMessage message) {
		MimeMultipart overMultipart = new MimeMultipart();
		MimeMultipart innerMultipart = new MimeMultipart();
		try {
			if (message.getCarrier() == Carrier.AU) {
				overMultipart.setSubType(MIXED);
			} else {
				overMultipart.setSubType(RELATED);
			}
			innerMultipart.setSubType(ALTERNATIVE);
			//TextPart
			MobyletTextMailBuilder textBuilder =
				SingletonUtils.get(MobyletTextMailBuilder.class);
			MimeBodyPart textBodyPart = textBuilder.buildTextPart(
					new MimeBodyPart(),
					message.getCarrier(),
					message.getBody().getText(),
					message.getNotifyCharset());
			innerMultipart.addBodyPart(textBodyPart);
			//HtmlPart
			HtmlPart htmlPart = PartUtils.buildHtmlPart(
					message.getCarrier(),
					message.getBody().getHtml());
			MimeBodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setDataHandler(
					DataHandlerUtils.getDataHandler(
							message.getCarrier(),
							htmlPart.getSource()));
			htmlBodyPart.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			htmlBodyPart.addHeader(CONTENT_TYPE,
					TEXT_HTML + "; charset=\"" + message.getNotifyCharset() + "\"");
			innerMultipart.addBodyPart(htmlBodyPart);
			if (htmlPart.getInlineParts() != null && htmlPart.getInlineParts().size() > 0) {
				for (MimeBodyPart inlinePart : htmlPart.getInlineParts()) {
					overMultipart.addBodyPart(inlinePart);
				}
			}
			//Wrap-Multipart
			MimeBodyPart innerBodyPart = new MimeBodyPart();
			innerBodyPart.setContent(innerMultipart);
			overMultipart.addBodyPart(innerBodyPart);
			return overMultipart;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}
}
