<%@page import="org.mobylet.mail.SimpleMailSender"%>
<html>
<head>
<title>mobylet-example - Hello World!</title>
</head>
<body>
<div>Mail-Sended</div>
<%
	SimpleMailSender.send(
				request.getParameter("address"),
				"info@shin-takeuchi.com",
				request.getParameter("subject"),
				request.getParameter("body"));
%>
</body>
</html>