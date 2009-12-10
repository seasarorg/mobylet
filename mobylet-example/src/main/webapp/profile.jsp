<%@page import="org.mobylet.core.Mobylet"%>
<%@page import="org.mobylet.core.MobyletFactory"%>
<%@page import="org.mobylet.core.device.Device"%>
<%@page import="org.mobylet.core.device.DeviceProfile"%>
<%@page import="org.mobylet.core.device.DeviceDisplay"%>
<m:xmlheader/>
<html>
<head>
<title>mobylet-example Profile</title>
<m:css src="test.css"/>
</head>
<body>
<div>端末情報</div>
<div>
<%
	Mobylet mobylet = MobyletFactory.getInstance();
	Device device = mobylet.getDevice();
	DeviceDisplay display = device.getDeviceDisplay();
	DeviceProfile profile = device.getDeviceProfile();
%>
ｷｬﾘｱ：<%= mobylet.getCarrier() %><br/>
機種名：<%= profile.get("機種名") %><br/>
ﾌﾞﾗｳｻﾞｻｲｽﾞ：横<%= display.getWidth() %>×縦<%= display.getHeight() %><br/>
GPS：<%= device.hasGps() ? "対応" : "非対応" %><br/>
</div>
</body>
</html>

