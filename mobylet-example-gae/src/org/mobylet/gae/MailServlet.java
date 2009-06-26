package org.mobylet.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.mail.MobyletMailer;
import org.mobylet.mail.message.MessageBody;
import org.mobylet.mail.message.MobyletMessage;

public class MailServlet extends HttpServlet {

	private static final long serialVersionUID = 7746699883183565156L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
    	MobyletMessage message = MobyletMailer.createMessage(req.getParameter("address"));
    	MessageBody body = new MessageBody();
    	body.setText(req.getParameter("body"));
    	message.from("s.takeuchi.leihauoli@gmail.com")
    		.subject(req.getParameter("subject"))
    		.setBody(body);
    	MobyletMailer.send(message);
    	/*
    	InternetAddress address = new InternetAddress("s.takeuchi.leihauoli@gmail.com", "フロム", "iso-2022-jp");  // （1）
		InternetAddress addressTo = new InternetAddress("s.takeuchi@leihauoli.com", "トゥー", "iso-2022-jp");  // （1）
		Session session = Session.getDefaultInstance(new Properties());

		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(address);
			message.addRecipient(Message.RecipientType.TO, addressTo);  // （3）

			message.setSubject("Google App Engineからのメールです", "ISO-2022-JP");  // （4）
			message.setText("連載記事「Google App Engine for Javaを使ってみよう！」をよろしく！");  // （5）
			Transport.send(message);  // （6）
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		*/
		resp.setContentType("text/html; charset=\"shift_jis\"");
		resp.getWriter().write("<html><body>test</body></html>");
		resp.flushBuffer();
	}
}
