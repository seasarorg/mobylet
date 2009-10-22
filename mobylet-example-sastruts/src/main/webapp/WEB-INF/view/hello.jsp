<html>
<head>
<title>mobylet-example - Hello World!</title>
</head>
<body>
<div>Hello World!</div>
<s:form action="/hello/" method="post">
input-&gt;<html:checkbox property="txt" value="10"/><input type="hidden" name="txt" value=""/><br />
<input type="hidden" name="tmp" value="TEMP" />
<input type="submit" name="go" value="GO!" /><br />
</s:form>
output-&gt;${txt}
</body>
</html>