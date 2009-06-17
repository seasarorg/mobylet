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
				"s.takeuchi@leihauoli.com",
				request.getParameter("address"),
				request.getParameter("body"));
%>
</body>
</html>