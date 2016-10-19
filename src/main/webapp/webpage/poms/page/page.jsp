<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html class="index" lang="zh-CN">
<head>
<title>${page.pageTitle}</title>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="<%=basePath%>/webpage/crm/css/webapp.css">
<link rel="stylesheet" href="<%=basePath%>/webpage/crm/css/fontello.css">
<link rel="stylesheet" href="<%=basePath%>/webpage/crm/css/fontello.css">
<script type="text/javascript"
	src="<%=basePath%>/webpage/crm/js/jquery.min.js">
	
</script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
	<c:if test="${page.show_title eq '1'}">
		<div class="activ-detail-hd">
			<h2>${page.pageTitle}</h2>
			<div class="date">
				<span>${page.pageBody}</span> <span>${page.pageCreator}</span> <span><fmt:formatDate
						value="${page.pageCreatedTime}" pattern="yyyy-MM-dd-HH:mm:ss" /></span>
			</div>
		</div>
	</c:if>

	<div class="activ-detail-bd">${page.pageHtml}</div>
	<c:if test="${page.show_download_button eq '1'}">
		<div class="btn-more-info">
			<div class="inner">
				<a href="<%=basePath%>/webpage/crm/webapp-download-app"
					class="btn-app-primary logic-download-app"><span
					class="icon-sy"></span>ä¸è½½å¸éAPPç»æ¨æä¾æ´å¤ä¼è´¨æå¡</a>
			</div>
		</div>
	</c:if>
</body>
</html>