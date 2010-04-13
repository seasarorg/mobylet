<m:xmlheader/>
<html>
<head>
<title>mobylet-example Profile</title>
<m:css src="test.css"/>
</head>
<body>
<div>端末情報</div>
<div>
ｷｬﾘｱ：${mobylet.carrier}<br/>
機種名：${mobylet.device.deviceProfile["機種名"] }<br/>
ﾌﾞﾗｳｻﾞｻｲｽﾞ：横${mobylet.device.deviceDisplay.width}×縦${mobylet.device.deviceDisplay.height}<br/>
UID：${mobylet.uid}<br/>
GUID：${mobylet.guid}<br/>
</div>
</body>
</html>

