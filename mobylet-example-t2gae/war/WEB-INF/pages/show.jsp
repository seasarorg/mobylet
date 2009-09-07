<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="m" uri="http://taglibs.mobylet.org/" %>
<html>
<head>
<title>mobylet-example</title>
</head>
<body>
<div>INDEX PAGE</div>
<div>
<form action="/post/" method="POST">
<input type="text" size="20" name="comment" />
<input type="submit" value="POST" />
</form>
</div>
<hr />
<c:forEach items="${list}" var="item">
	<div>${item}</div>
<hr />
</c:forEach>
</body>
</html>