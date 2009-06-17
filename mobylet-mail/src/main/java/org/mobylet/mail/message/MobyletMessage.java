package org.mobylet.mail.message;

import java.util.Set;
import java.util.Map.Entry;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.mail.parts.MailPart;
import org.mobylet.mail.selector.MailCharsetSelector;
import org.mobylet.mail.util.MailHeaderUtils;

public class MobyletMessage extends MimeMessage {

	protected Carrier carrier;

	protected String encodingCharset;

	protected String notifyCharset;

	protected Session session;


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
			headerMessage = MailHeaderUtils.encodeHeaderString(
					string, encodingCharset, notifyCharset);
			super.setSubject(headerMessage);
			return this;
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"SUBJECTの設定に失敗 string = " + string, e);
		}
	}

	public MobyletMessage setBodyPart(MailPart part) {
		try {
			this.setDataHandler(part.getHandler());
			Set<Entry<String, String>> entrySet = part.getHeaderMap().entrySet();
			if (entrySet != null || entrySet.size() > 0) {
				for (Entry<String, String> entry : entrySet) {
					this.addHeader(entry.getKey(), entry.getValue());
				}
			}
			return this;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}
}
