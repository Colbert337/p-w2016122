<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="help" lang="zh">
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
<jsp:include page="common/help.jsp"></jsp:include>

	<div class="container-fluid wrap">
		<div class="help-container">
            <span class="icon-cs"></span>
            <div class="title">
                <div>司集客服咨询</div>
            </div>
            <div class="curstomer-service">
                <ul>
                    <li>
                        <span class="icon icon-phone-1"></span>
                        电话：029-88451872
                    </li>
                    <li>
                        <span class="icon icon-wechat"></span>
                        微信：admin@sysongy.com
                    </li>
                    <li>
                        <span class="icon icon-mail-alt"></span>
                        邮箱：admin@sysongy.com
                    </li>
                </ul>
            </div>
        </div>
	</div>
    <script src="js/jquery.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>