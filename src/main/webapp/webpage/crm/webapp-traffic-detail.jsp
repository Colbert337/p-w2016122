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
	<title>司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="share">
		<div class="traffic-detail-hd">
			缓行 路况详情标题
		</div>
		<div class="traffic-detail-bd">
			<div class="date">
				发布时间：2016-08-18 08:08:08
			</div>
			<div class="info">
				“2010年3月公安部公布的货车保有量为1368
			</div>
			<div class="img">
				<img class="img-responsive" src="images/ys1.jpg" alt="">
			</div>
			<a href="" class="btn-app-primary">下载司集APP给您提供更多优质服务</a>
		</div>
	</div>
</body>
</html>