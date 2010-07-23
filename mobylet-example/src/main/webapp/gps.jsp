<%@page import="org.mobylet.core.Mobylet"%>
<%@page import="org.mobylet.core.MobyletFactory"%>
<%@page import="org.mobylet.core.gps.Gps"%>
<%@page import="org.mobylet.core.gps.Geo"%>
<%@page import="org.mobylet.core.gps.GeoConverter"%>
<%@page import="org.mobylet.core.util.SingletonUtils"%>
<m:xmlheader/>
<html>
<head>
<title>mobylet-example GPS</title>
<m:css src="test.css"/>
</head>
<body>
<div>GPS</div>
<%
	Mobylet mobylet = MobyletFactory.getInstance();
	GeoConverter converter = SingletonUtils.get(GeoConverter.class);
	Gps gps = mobylet.getGps();
	Gps gpsTokyo = converter.toTokyo(gps);
	Double lat = null;
	Double lon = null;
	Double latTokyo = null;
	Double lonTokyo = null;

	String strLat = null;
	String strLon = null;
	String strLatTokyo = null;
	String strLonTokyo = null;
	if(gps != null){
		lat = gps.getLat();
		lon = gps.getLon();
		strLat = gps.getStrLat();
		strLon = gps.getStrLon();
		latTokyo = gpsTokyo.getLat();
		lonTokyo = gpsTokyo.getLon();
		strLatTokyo = gpsTokyo.getStrLat();
		strLonTokyo = gpsTokyo.getStrLon();
	}
%>
<div>
■世界測地系(度)<br/>
緯度：<%= lat %><br/>
経度：<%= lon %><br/>
■世界測地系(度分秒)<br/>
緯度：<%= strLat %><br/>
経度：<%= strLon %><br/>
<br/>
■日本測地系(度)<br/>
緯度：<%= latTokyo %><br/>
経度：<%= lonTokyo %><br/>
■日本測地系(度分秒)<br/>
緯度：<%= strLatTokyo %><br/>
経度：<%= strLonTokyo %>
</div>
<% if(lat != null && lon != null){ %>
<m:googlemap key="ABQIAAAAPYgXdQyUkL002WfUhRqsFxSCNA_NVtbBIrIBbfef5W7r3469IRSmYo0djw2uXw79MXwwZacmUqYReQ" markerColor="BLUE" markerAlphaNumericCharacter="1">
</m:googlemap>
<% } %>
</body>
</html>