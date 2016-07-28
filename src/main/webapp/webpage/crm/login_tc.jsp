<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="zh" class="login-html">
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
<body class="login">
<jsp:include page="common/header.jsp"></jsp:include>

	<div class="login-wrap">
		<div class="wrap">
			<div class="login-box">
				<div class="hd">运输公司</div>
				<form action="<%=basePath%>/web/login" method="post" name="login" id="login" autocomplete="off">
				<ul class="bd">
					<li>
						<span class="icon icon-user"></span>
						<input id="userName" name="userName" class="txt form-control required user" tabIndex="1" placeholder="用户名" type="text">
						<div class="form-item-error"></div>
					</li>
					<li>
						<span class="icon icon-lock-open-empty"></span>
						<input id="password" name="password" class="txt form-control required" placeholder="密码" tabIndex="2" type="password">
						<div class="form-item-error"></div>
					</li>
					<%--<li>
						<input class="txt code" placeholder="验证码" type="text">
						<span class="code-img">code img</span>
					</li>
					<li>
						<span class="fp"><a href="">忘记密码？</a></span>
						<a href="">商家注册</a>
					</li>--%>
					<li>
						<button class="btn btn-block btn-primary" type="submit" tabIndex="3">登录</button>
					</li>
				</ul>
				</form>
			</div>
		</div>
	</div>
	<footer>
		陕西司集能源科技有限公司
	</footer>
	<script src="js/jquery.min.js"></script>
	<script type='text/javascript' src='<%=basePath%>/assets/js/jquery.validate.js'></script>
	<script src="js/main.js"></script>
</body>
</html>