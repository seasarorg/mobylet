<html>
<head>
<title>Hello World!</title>
</head>
<body>
<div>Hello World!</div>
<form action="." method="post">
input-&gt;<input type="text" name="txt" size="10" /><br />
<input type="submit" name="go" value="GO!" /><br />
</form>
output-&gt;<%= request.getParameter("txt") %>
</body>
</html>