package org.mobylet.mail.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.builder.HtmlPart;
import org.mobylet.mail.config.MailMimeConfig;
import org.mobylet.mail.message.MessageBody.Attach;

public class PartUtils implements MailConstants {

	public static final Pattern PATTERN_IMGTAG =
		Pattern.compile("<(img|IMG)[ ]+.+?>");

	public static final Pattern PATTERN_INLINEIMAGESRC =
		Pattern.compile("src[ ]*=[ ]*[\"]?[0-9a-zA-Z._/:\\\\-]+[\"]?");


	public static MimeBodyPart buildAttachPart(Carrier carrier, Attach attach) {
		MimeBodyPart part = new MimeBodyPart();
		if (attach == null ||
				StringUtils.isEmpty(attach.getRealPath()) ||
				StringUtils.isEmpty(attach.getNickname()) ||
				attach.getInputStream() == null) {
			return part;
		}
		String realPath = attach.getRealPath();
		String extension = null;
		if (realPath.indexOf('.') >= 0) {
			extension = realPath.substring(realPath.lastIndexOf('.') + 1);
		}
		//Mime
		String mimeType = APPLICATION_OCTETSTREAM;
		if (StringUtils.isNotEmpty(extension)) {
			String defMime =
				SingletonUtils.get(MailMimeConfig.class)
				.getMimeProperties().getProperty(extension);
			if (StringUtils.isNotEmpty(defMime)) {
				mimeType = defMime;
			}
		}
		//DataBody
		DataSource byteSource = null;
		try {
			byteSource = new ByteArrayDataSource(
					attach.getInputStream(), APPLICATION_OCTETSTREAM
			);
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"入力ストリームからDataSourceが作成出来ません", e);
		}
		try {
			part.setDataHandler(new DataHandler(byteSource));
			part.setHeader(TRANSFER_ENCODING, ENCODING_BASE64);
			if (carrier == Carrier.AU) {
				part.addHeader(
						CONTENT_DISPOSITION, DISPOSITION_ATTACHMENT +
						"; filename=\"" + getRealFileName(attach) + "\"");
			}
			if (attach.isInline()) {
				part.setHeader(CONTENT_ID, "<" + attach.getNickname() + ">");
			}
			if (StringUtils.isEmpty(attach.getRealPath())) {
				part.addHeader(CONTENT_TYPE, mimeType + ";");
			} else {
				part.addHeader(
						CONTENT_TYPE, mimeType + "; name=\"" +
						MailHeaderUtils.encodeHeaderString(
								carrier,
								getRealFileName(attach) + "\"")
				);
			}
		} catch (MessagingException e) {
			throw new MobyletRuntimeException(
					"添付ファイルパートを作成中に例外発生", e);
		}
		return part;
	}

	public static HtmlPart buildHtmlPart(Carrier carrier, String body) {
		List<MimeBodyPart> inlineParts = new ArrayList<MimeBodyPart>();
		StringBuffer buf = new StringBuffer("");
		List<Attach> inlines = new ArrayList<Attach>();
		//ReBuild-Html
		Matcher imgTagMatcher = PATTERN_IMGTAG.matcher(body);
		int cidindex = 0;
		while (imgTagMatcher.find()) {
			String cid = cidindex + "@mobylet";
			String imgTagString = imgTagMatcher.group();
			Matcher srcAttMatcher =
				PATTERN_INLINEIMAGESRC.matcher(imgTagString);
			if (srcAttMatcher.find()) {
				String srcString = srcAttMatcher.group();
				String srcValue = srcString.substring(srcString.indexOf("=") + 1).trim();
				if (srcValue.startsWith("\"") && srcValue.endsWith("\"")) {
					srcValue = srcValue.substring(1, srcValue.length()-1);
				}
				if (srcValue.startsWith("\'") && srcValue.endsWith("\'")) {
					srcValue = srcValue.substring(1, srcValue.length()-1);
				}
				Attach inline =
					new Attach(srcValue,
							cid,
							ResourceUtils.getResourceFileOrInputStream(srcValue));
				inline.setInline(true);
				inlines.add(inline);
			}
			imgTagMatcher.appendReplacement(buf, "<img src=\"cid:"+cid+"\" />");
			cidindex++;
		}
		imgTagMatcher.appendTail(buf);
		HtmlPart htmlPart = new HtmlPart(buf.toString());
		//Build-InlineBody
		if (inlines.size() != 0) {
			for (Attach inline : inlines) {
				inlineParts.add(buildAttachPart(carrier, inline));
			}
		}
		htmlPart.setInlineParts(inlineParts);
		return htmlPart;
	}


	public static String getRealFileName(Attach attach) {
		if (attach != null &&
				attach.getRealPath() != null) {
			String realPath = attach.getRealPath();
			int index = -1;
			if ((index = realPath.lastIndexOf(File.separatorChar)) >= 0 ||
					(index = realPath.lastIndexOf('/')) >= 0) {
				return realPath.substring(index + 1);
			} else {
				return realPath;
			}
		}
		return "EMPTYNAME";
	}

}
