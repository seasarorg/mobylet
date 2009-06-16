<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="m" uri="http://taglibs.mobylet.org/" %>
<html>
<head>
<title>mobylet-example</title>
</head>
<body>
<div>mobylet-example</div>
<div>
<a href="hello.jsp">Hello World</a><br/>
<a href="profile.jsp">端末情報</a><br/>
<a href="resize.jsp">画像リサイズ</a><br/>
<m:gps kickBackUrl="http://mobylet-example-gae.appspot.com/gps.jsp">位置情報(GPS)</m:gps><br />

</div>
</body>
</html>