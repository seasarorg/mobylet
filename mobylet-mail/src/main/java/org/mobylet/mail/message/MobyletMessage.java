package org.mobylet.mail.message;

import java.nio.charset.Charset;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.builder.MobyletMailBuilder;
import org.mobylet.mail.builder.impl.MobyletDecoMailBuilder;
import org.mobylet.mail.builder.impl.MobyletHtmlMailBuilder;
import org.mobylet.mail.builder.impl.MobyletTextMailBuilder;
import org.mobylet.mail.selector.MailCharsetSelector;
import org.mobylet.mail.util.DataHandlerUtils;
import org.mobylet.mail.util.MailHeaderUtils;

public class MobyletMessage extends MimeMessage implements MailConstants {

	protected Carrier carrier;

	protected Charset encodingCharset;

	protected String notifyCharset;

	protected Session mobyletSession;

	protected MessageBody body;

	protected boolean isConstructed;


	public MobyletMessage(Carrier toCarrier, Session session) {
		super(session);
		carrier = toCarrier;
		MailCharsetSelector charsetSelector =
			SingletonUtils.get(MailCharsetSelector.class);
		encodingCharset = charsetSelector.getEncodingCharset(carrier);
		notifyCharset = charsetSelector.getNotifyCharset(carrier);
	}

	public MobyletMessage from(String address) {
		return from(address, null);
	}

	public MobyletMessage from(String address, String principal) {
		try {
			Address adrs = StringUtils.isEmpty(principal) ?
					new InternetAddress(address) :
					new InternetAddress(address, principal, notifyCharset);
			this.setFrom(adrs);
			return this;
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"FROMの設定に失敗 address = " + address, e);
		}
	}

	public MobyletMessage to(String address) {
		return to(address, null);
	}

	public MobyletMessage to(String address, String principal) {
		try {
			return addRecipient(address, principal, RecipientType.TO);
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"TOの設定に失敗 address = " + address, e);
		}
	}

	public MobyletMessage cc(String address) {
		return cc(address, null);
	}

	public MobyletMessage cc(String address, String principal) {
		try {
			return addRecipient(address, principal, RecipientType.CC);
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"CCの設定に失敗 address = " + address, e);
		}
	}

	public MobyletMessage bcc(String address) {
		return bcc(address, null);
	}

	public MobyletMessage bcc(String address, String principal) {
		try {
			return addRecipient(address, principal, RecipientType.BCC);
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"BCCの設定に失敗 address = " + address, e);
		}
	}

	public MobyletMessage addRecipient(
			String address, String principal, javax.mail.Message.RecipientType type)
			throws Exception {
		Address adrs = StringUtils.isEmpty(principal) ?
				new InternetAddress(address) :
				new InternetAddress(address, principal, notifyCharset);
		this.addRecipient(type, adrs);
		return this;
	}

	@Override
	public void setSubject(String subject) throws MessagingException {
		subject(subject);
	}

	public MobyletMessage subject(String string) {
		String headerMessage = "";
		try {
			headerMessage = MailHeaderUtils.encodeHeaderString(carrier, string);
			super.setSubject(headerMessage);
			return this;
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"SUBJECTの設定に失敗 string = " + string, e);
		}
	}

	public MobyletMessage setBody(MessageBody body) {
		this.body = body;
		return this;
	}

	public MessageBody getBody() {
		return body;
	}

	public MobyletMessage construct() {
		if (isConstructed || body == null) {
			return this;
		}
		MobyletMailBuilder builder = null;
		//Text
		if (StringUtils.isNotEmpty(body.getText()) &&
				StringUtils.isEmpty(body.getHtml())) {
			builder = SingletonUtils.get(MobyletTextMailBuilder.class);
		}
		//Html
		else if (StringUtils.isEmpty(body.getText()) &&
				StringUtils.isNotEmpty(body.getHtml())) {
			builder = SingletonUtils.get(MobyletHtmlMailBuilder.class);
		}
		//DecoMail
		else {
			builder = SingletonUtils.get(MobyletDecoMailBuilder.class);
		}
		isConstructed = true;
		return builder.build(this);
	}


	public Carrier getCarrier() {
		return carrier;
	}

	public Charset getEncodingCharset() {
		return encodingCharset;
	}

	public String getNotifyCharset() {
		return notifyCharset;
	}

	public Session getSession() {
		return mobyletSession;
	}

	public MobyletMessage setTextBody(String source) {
		try {
			this.setDataHandler(DataHandlerUtils.getDataHandler(carrier, source));
			this.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			this.addHeader(CONTENT_TYPE,
					TEXT_PLAIN + "; charset=\"" + notifyCharset + "\"");
			return this;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}

	public MobyletMessage setHtmlBody(String source) {
		try {
			this.setDataHandler(DataHandlerUtils.getDataHandler(carrier, source));
			this.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			this.addHeader(CONTENT_TYPE,
					TEXT_HTML + "; charset=\"" + notifyCharset + "\"");
			return this;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}

}
