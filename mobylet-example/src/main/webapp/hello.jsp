<html>
<head>
<title>mobylet-example - Hello World!</title>
</head>
<body>
<div>Hello World!</div>
<form action="hello.jsp" method="get">
input-&gt;<input type="text" name="txt" size="10" /><br />
<input type="submit" name="go" value="GO!" /><br />
</form>
output-&gt;<%= request.getParameter("txt") %>
</body>
</html>