<html>
<head>
<title>mobylet-example - Hello World!</title>
</head>
<body>
<div>Hello World!</div>
<form action="hello.jsp" method="get">
input-&gt;<m:wrap istyle="4"><input type="text" name="txt" size="10" /></m:wrap><br />
<input type="submit" name="go" value="GO!" /><br />
</form>
output-&gt;${param.txt}<br />
<hr />
<c:import url="include.jsp">
	<c:param name="pmm" value="PMM">
	</c:param>
</c:import>
</body>
</html>