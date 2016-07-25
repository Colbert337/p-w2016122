<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>司集云平台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link type="image/x-icon" href="<%=basePath %>/common/favicon.ico" rel="shortcut icon" />
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/fontello.css">
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="common/header.jsp"></jsp:include>
    <jsp:include page="common/download.jsp"></jsp:include>

	<div class="container-fluid app">
		<img class="img-responsive" src="images/app.jpg" alt="">
	</div>

    <script src="js/jquery.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>