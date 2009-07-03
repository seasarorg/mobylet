<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="m" uri="http://taglibs.mobylet.org/" %>
<html>
<head>
<title>Resize</title>
</head>
<body>
<div>Resize!</div>
<div>リサイズ画像1.0 FW<br/><m:img src="images/resize.jpg" magniWidth="0.5"/></div>
<div>リサイズ画像1.0 IS<br/><m:img src="images/resize.jpg" magniWidth="0.5" scaleType="INSQUARE"/></div>
<div>リサイズ画像1.0 CS<br/><m:img src="images/resize.jpg" magniWidth="0.5" scaleType="CLIPSQUARE"/></div>
</body>
</html>