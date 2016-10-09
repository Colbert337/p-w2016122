<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="index" lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>活动详情-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="activ-detail-hd">
		<h2>${mbBanner.title}</h2>
		<div class="date">
			<span><fmt:formatDate value="${mbBanner.createdDate}" type="both"/> </span>
			<span>司集能源</span>
			<span>阅读  ${mbBanner.viewCount}</span>
		</div>
	</div>
	<div class="activ-detail-bd">
		${mbBanner.content}
	</div>

	<div class="btn-more-info">
		<div class="inner">
			<a href="<%=basePath %>/webpage/crm/webapp-download-app.jsp" class="btn-app-primary logic-download-app"></span>下载司集APP给您提供更多优质服务</a>
		</div>
	</div>

	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>