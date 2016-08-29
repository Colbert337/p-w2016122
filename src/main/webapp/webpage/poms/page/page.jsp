<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="index" lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>司集能源</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="activ-detail-hd">
		<h2>${page.pageTitle}</h2>
		<div class="date">
			<span>${page.pageBody}</span>
			<span>${page.pageCreator}</span>
			<span><fmt:formatDate value="${page.pageCreatedTime}" type="both"/></span>
		</div>
	</div>
	<div class="activ-detail-bd">
		${page.pageHtml}
	</div>
</body>
</html>