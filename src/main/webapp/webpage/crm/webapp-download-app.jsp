<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="white" lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>应用下载-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<ul id="menu" class="download-app-fd">
	    <li data-menuanchor="download" class="active"><a href="#download"><span class="icon-arrow-up"></span></a></li>
	    <li data-menuanchor="intro"><a href="#intro"><span class="icon-arrow-down"></span></a></li>
	</ul>

	<div id="download-fullpage">
		<div class="section">
			<div class="share">
				<div class="download-app-hd">
					<p>1.0.0.13 ( build 6 ) </p>
					<p>更新于 2016-09-22</p>
				</div>
				<div class="download-app-bd" onclick="statistics(${version})">
					<a class="btn-app-primary logic-download-app" href="http://www.sysongy.net/workspace/apk/sysongy-website-release-1.1.1.apk"><span class="icon-android"></span>下载安卓</a>
				</div>
			</div>
		</div>
		<div class="section">
			<div class="share">
				<div class="app-intro-bd">APP介绍：司集APP，货车出行必备神器。</br></br>
					APP摘要：司集APP，全国三千万货车司机出行必备神器，提供最贴心的全国油气站导航、实时路况查询、加油加气优惠活动服务。</br></br>
					APP特点：</br>
					一、精准地图导航</br>
					二、实时路况上报</br>
					三、优惠活动</br>
					四、账户安全管理</br>
				</div>
				<form class="app-intro-form" action="<%=basePath %>/portal/crm/help/suggest" method="post" id="appIntroFrom">
					<div class="item">
						<input id="title" name="title" tabIndex="1" class="txt required" type="text" class="txt" placeholder="Email / QQ / 微信 / 电话">
					</div>
					<div class="item">
						<textarea id="info" name="info" tabIndex="1" class="txt required" name="" id="" class="txt" placeholder="反馈内容..."></textarea>
					</div>
					<button type="submit" class="btn-app-primary">提交信息</button>
				</form>
			</div>
		</div>
	</div>

<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
<script src="<%=basePath %>/webpage/crm/js/jquery.fullpage.min.js"></script>
<script src="<%=basePath %>/webpage/crm/js/jquery.easings.min.js"></script>
<script src="<%=basePath %>/assets/js/jquery.validate.js"></script>
<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
<script>
var appVersion;
function statistics(version){
	$.ajax({
		url:"../../portal/crm/help/app/down",
		data:{
			appVersion:version
		},
		async:false,
		type: "POST",
		success: function(data){
			console.log("APP下载统计成功！");
		}
	})
}
</script>
</body>
</html>