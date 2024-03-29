<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="zh">
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
                <li class="current"><a href="<%=basePath %>/webpage/crm/download-crm.jsp">CRM下载</a></li>
                <li><a href="<%=basePath %>/webpage/crm/download-app">APP下载</a></li>
            </ul>
        </div>
    </div>

	<div class="container-fluid wrap crm">
		<div class="row">
            <div class="col-sm-7">
                <img class="img-responsive" src="images/crm.png" alt="">
            </div>
            <div class="col-sm-5">
                <ul class="crm-down">
                    <li class="title">司集加注站CRM管理系统</li>
                    <%--<li>
                        <span class="pull-right">2016-6-24</span>
                        16.5M
                    </li>--%>
                    <li>
                        <a href="http://download.sysongy.net/司集CRM安装包.exe" class="btn btn-block btn-primary btn-lg">下载</a>
                    </li>
                </ul>
            </div>
        </div>
	</div>
    <script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
    <script src="<%=basePath %>/webpage/crm/js/main.js"></script>
</body>
</html>