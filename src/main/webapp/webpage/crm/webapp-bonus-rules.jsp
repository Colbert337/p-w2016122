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
	<title>返现规则-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="bonus-rules-hd">
		微信返现规则
	</div>
	<ul class="bonus-rules-bd">
		<c:forEach items="${wechatCashBack}" var="cashBack">
			<li>
				${cashBack.threshold_min_value} < ${cashBack.threshold_max_value},返点系数${cashBack.cash_per}
			</li>
		</c:forEach>
	</ul>
	<div class="bonus-rules-hd">
		支付宝返现规则
	</div>
	<ul class="bonus-rules-bd">
		<c:forEach items="${aliPayCashBack}" var="cashBack">
			<li>
				${cashBack.threshold_min_value} < ${cashBack.threshold_max_value},返点系数${cashBack.cash_per}
			</li>
		</c:forEach>
	</ul>
</body>
</html>