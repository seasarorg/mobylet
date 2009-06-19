<%@page import="org.mobylet.mail.MobyletMailer"%>
<%@page import="org.mobylet.mail.message.*"%>
<html>
<head>
<title>mobylet-example - Hello World!</title>
</head>
<body>
<div>Mail-Sended</div>
<%
	MobyletMessage message = MobyletMailer.createMessage(request.getParameter("address"));
	MessageBody body = new MessageBody();
	body.setHtml("<html><body>テストHTML</body></html>");
	body.setText("テストテキスト");
	message.from("info@shin-takeuchi.com")
		.subject(request.getParameter("subject"))
		.setBody(body);
	MobyletMailer.send(message);
%>
</body>
</html>