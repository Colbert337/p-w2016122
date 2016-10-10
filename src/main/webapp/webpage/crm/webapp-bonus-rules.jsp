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


	<div class="bonus-rules-hd" align="center">
		<img alt="" src="<%=basePath %>/webpage/crm/images/fxz.png" style="width: 20%;">
		</br>
		<span style="font-size:30px;font-family:微软雅黑;color:#669f31;font-weight: lighter;">使用司集APP</span>
		</br>
		<img alt="" src="<%=basePath %>/webpage/crm/images/fxgz.png" style="width: 100%;">
	</div>
	
	<div class="bonus-rules-hd" align="center">
		<c:forEach items="${aliPayCashBack}" var="cashBack">
			<div style="text-align:center;font-family: 微软雅黑 Light; font-weight:lighter; font-size: 18px; color: #545252; padding:20px; ">充值<span style="font-size:24px; font-weight:bold;margin-left:10px; margin-right:10px;">${cashBack.threshold_min_value} - ${cashBack.threshold_max_value}</span>元<br>
			用户即可获得<span style="font-size:36px; font-weight:lighter; color:#669f31; margin-left:15px; margin-right:15px;"><fmt:formatNumber pattern="000" value="${cashBack.cash_per*100}" type="percent"/>%</span>的返现
			</div>
		</c:forEach>
	</div>
	
</body>
</html>