<m:xmlheader/>
<%@page import="org.mobylet.core.util.SessionUtils"%>
<html>
<head>
<title>mobylet-example Session</title>
<m:css src="test.css"/>
</head>
<body>
<div>Hello World!</div>
<%
	SessionUtils.invalidate();
%>
Invalidated.
<br />
</body>
</html>