<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="white" lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>路况详情-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="share">
		<div class="traffic-detail-hd">
			${conditionType}
		</div>
		<div class="traffic-detail-bd">
			<div class="date">
				发布时间：
				<fmt:formatDate value="${roadCondition.publisherTime}" pattern="yyyy-MM-dd-HH:mm:ss" />
			</div>
			<div class="info">
				${conditionType}
				${name}于<fmt:formatDate value="${roadCondition.publisherTime}" pattern="yyyy-MM-dd-HH:mm" />前提供了${roadCondition.address}附近的${direction}的${conditionType}路况，请注意路况信息变化！
				<c:if test="${!roadCondition.memo eq null} " ><br>路况详情如下：<br>${roadCondition.memo}</c:if>
			</div>
			<div class="img">
				<img class="img-responsive" src="${conditionMsg}" alt="">
			</div>
		</div>
	</div>

	<%--<div class="btn-more-info">
		<div class="inner">
			&lt;%&ndash;<span class="icon-sy">&ndash;%&gt;
			<a href="" class="btn-app-primary logic-download-app"></span>下载司集APP给您提供更多优质服务</a>
		</div>
	</div>--%>

	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>