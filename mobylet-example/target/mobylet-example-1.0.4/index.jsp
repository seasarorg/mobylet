<html>
<head>
<title>mobylet-example</title>
<m:css src="test.css"/>
</head>
<body>
<div>mobylet-example</div>
<div>
<a href="hello.jsp">Hello World</a><br/>
<m:a href="/profile.jsp" isAdditionalContext="true">端末情報</m:a><br/>
<a href="resize.jsp">画像リサイズ</a><br/>
<a href="resize2.jsp">画像リサイズ2</a><br/>
<a href="session.jsp">セッション</a><br/>
<m:gps kickBackUrl="/mobylet/gps.jsp">位置情報(GPS)</m:gps>
</div>
</body>
</html>