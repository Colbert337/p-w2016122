<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>司集云平台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/fontello.css">
	<link rel="stylesheet" href="css/style.css">
</head>
<body class="login">
<jsp:include page="common/header.jsp"></jsp:include>

	<div class="login-wrap cng">
		<div class="wrap">
			<div class="login-box">
				<div class="hd">加注站</div>
				<ul class="bd">
					<li>
						<span class="icon icon-user"></span>
						<input class="txt" placeholder="用户名" type="text">
					</li>
					<li>
						<span class="icon icon-lock-open-empty"></span>
						<input class="txt" placeholder="密码" type="password">
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
						<button class="btn btn-block btn-primary">登录</button>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<footer>
		陕西司集能源公司
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>