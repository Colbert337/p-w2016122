<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>${pd.SYSNAME}</title>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<link rel="stylesheet" href="<%=basePath%>/assets/css/login.css" />
</head>
<body>

	<form action="<%=basePath%>/web/login" method="post" name="login" id="login" autocomplete="off">
		<div class="login">
			<ul>
				<li>
					<div class="form-item">
						<input id="userName" name="userName" class="form-control required user" type="text">
						<div class="form-item-error"></div>
					</div>
				</li>
				<li>
					<div class="form-item">
						<input id="password" name="password" class="form-control required pw" type="password">
						<div class="form-item-error"></div>
					</div>
				</li>
				<li>
					<div class="form-item">
						<button class="btn" type="submit">btn</button>
						<div class="rememberpw">
							<input name="" id="rememberpw" type="checkbox">
							<label for="rememberpw">记住密码</label>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>


	<script type='text/javascript' src='<%=basePath%>/assets/js/jquery-1.9.1.min.js'></script>
	<script type='text/javascript' src='<%=basePath%>/assets/js/jquery.validate.js'></script>
	<script type="text/javascript" src="<%=basePath%>/assets/js/jquery.cookie.js"></script>
	<script type='text/javascript' src='<%=basePath%>/assets/js/jquery.login.js'></script>
</body>

</html>