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
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/bootstrap.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="subnav">
    <div class="inner">
        <ul>
            <li class="home"><a href="<%=basePath %>/webpage/crm/index.jsp">首页</a></li>
            <li><a href="<%=basePath %>/portal/crm/help/list/all">常见问题</a></li>
            <li class="current"><a href="<%=basePath %>/webpage/crm/hp_service.jsp">客服咨询</a></li>
            <li><a href="<%=basePath %>/portal/crm/help/list/notice">服务公告</a></li>
        </ul>
    </div>
</div>

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
    <script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
    <script src="<%=basePath %>/webpage/crm/js/main.js"></script>
</body>
</html>