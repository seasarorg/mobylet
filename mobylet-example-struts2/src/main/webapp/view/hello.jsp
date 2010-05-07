<m:xmlheader/>
<html>
<head>
<title>mobylet-example Hello, World!</title>
<m:css src="test.css"/>
</head>
<body>
<div>Hello World!</div>
<s:form action="hello" method="get">
input-&gt;<m:wrap istyle="4"><s:textfield name="message" size="10"></s:textfield></m:wrap><br />
<s:submit value="GO!" />
</s:form>
output-&gt;${message}<br />
<hr />
<c:import url="include.jsp">
	<c:param name="pmm" value="ほげ"></c:param>
</c:import>
</body>
</html>