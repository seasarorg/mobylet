<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="m" uri="http://taglibs.mobylet.org/" %>
<m:xmlheader/>
<html>
<head>
<title>mobylet-example Resize2</title>
<m:css src="test.css"/>
</head>
<body>
<div>Resize!</div>
<div>リサイズ画像0.5 FW<br/><m:img src="images/resize.jpg" magniWidth="0.5"/></div>
<div>リサイズ画像0.5 IS<br/><m:img src="images/resizeV.jpg" magniWidth="0.5" scaleType="INSQUARE"/></div>
<div>リサイズ画像0.5 CS<br/><m:img src="images/resize.jpg" magniWidth="0.5" scaleType="CLIPSQUARE"/></div>
</body>
</html>