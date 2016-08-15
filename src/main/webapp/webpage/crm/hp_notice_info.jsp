<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="help service" lang="zh">
<head>
	<meta charset="UTF-8">
	<title>司集云平台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
    <link type="image/x-icon" href="<%=basePath %>/common/favicon.ico" rel="shortcut icon" />
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/bootstrap.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="subnav">
    <div class="inner">
        <ul>
            <li class="home"><a href="<%=basePath %>/webpage/crm/index.jsp">首页</a></li>
            <li><a href="<%=basePath %>/portal/crm/help/list/all">常见问题</a></li>
            <li><a href="<%=basePath %>/webpage/crm/hp_service.jsp">客服咨询</a></li>
            <li class="current"><a href="<%=basePath %>/portal/crm/help/list/notice">服务公告</a></li>
        </ul>
    </div>
</div>

	<div class="container-fluid wrap">
        <div class="service-notice-info">
            <c:forEach items="${noticeInfoList}" var="list">
            <div class="hd">${list.title}</div>
            <div class="bd">${list.answer}</div>
            <div class="fd"><fmt:formatDate value="${list.createdDate}" type="both" pattern="yyyy-MM-dd"/></div>
            </c:forEach>
        </div>
	</div>
    <script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
    <script src="<%=basePath %>/webpage/crm/js/main.js"></script>
</body>
</html>