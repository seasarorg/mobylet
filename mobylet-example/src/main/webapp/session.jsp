<m:xmlheader/>
<%@page import="org.mobylet.core.util.SessionUtils"%>
<html>
<head>
<title>mobylet-example Session</title>
<m:css src="test.css"/>
</head>
<body>
<div>Hello World!</div>
<form action="session.jsp" method="get">
input-&gt;<m:wrap istyle="4"><input type="text" name="txt" size="10" /></m:wrap><br />
<input type="submit" name="go" value="GO!" /><br />
</form>
<%= SessionUtils.get(String.class) %>
<%
if (request.getParameter("txt") != null) {
	SessionUtils.set(request.getParameter("txt"));
}
%>
<br />
</body>
</html>