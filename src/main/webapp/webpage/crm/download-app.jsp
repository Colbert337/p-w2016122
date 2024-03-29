<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title>司集云平台</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link type="image/x-icon" href="<%=basePath%>/common/favicon.ico"
	rel="shortcut icon" />
<link rel="stylesheet"
	href="<%=basePath%>/webpage/crm/css/bootstrap.css">
<link rel="stylesheet"
	href="<%=basePath%>/webpage/crm/css/fontello.css">
<link rel="stylesheet" href="<%=basePath%>/webpage/crm/css/style.css">
</head>
<body>
	<jsp:include page="common/header.jsp"></jsp:include>
	<div class="subnav">
		<div class="inner">
			<ul>
				<li class="home"><a href="<%=basePath%>/webpage/crm/index.jsp">首页</a></li>
				<li><a href="<%=basePath%>/webpage/crm/download-crm.jsp">CRM下载</a></li>
				<li class="current"><a
					href="<%=basePath%>/webpage/crm/download-app">APP下载</a></li>
			</ul>
		</div>
	</div>

	<div class="container-fluid app" style="margin: 0 auto; position: relative;">
		<img class="img-responsive" src="${img}" alt="">
		<span onclick="statistics(${version})"><div class="fl-r down-style" onclick="window.location.href='<%=basePath %>${url}'"></div></span>
	</div>
	<script src="<%=basePath%>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath%>/webpage/crm/js/main.js"></script>
	<script>
		var appVersion;
		function statistics(version) {
			$.ajax({
				url : "../../portal/crm/help/app/down",
				data : {
					appVersion : version
				},
				async : false,
				type : "POST",
				success : function(data) {
					console.log("APP下载统计成功！");
				}
			})
		}
	</script>
</body>
</html>