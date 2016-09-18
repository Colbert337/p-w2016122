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
					<p>1.0.0.6 ( build 6 ) </p>
					<p>更新于 2016-09-18</p>
				</div>
				<div class="download-app-bd">
					<a class="btn-app-primary logic-download-app"><span class="icon-android"></span>下载安卓</a>
				</div>
			</div>

			<div class="download-app-help">
				<img class="img" src="images/download-app-help.png" alt="">
				<div class="transparent"></div>
			</div>
		</div>
		<div class="section">
			<div class="share">
				<div class="app-intro-hd">介绍</div>
				<div class="app-intro-bd">因为司集，所以司集！</div>
				<form class="app-intro-form" action="" method="post" id="appIntroFrom">
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

</body>
</html>