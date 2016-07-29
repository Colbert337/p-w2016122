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
<div class="subnav">
    <div class="inner">
        <ul>
            <li class="home"><a href="index.jsp">首页</a></li>
            <li class="current"><a href="<%=basePath %>/portal/crm/help/list/all">常见问题</a></li>
            <li><a href="hp_service.jsp">客服咨询</a></li>
            <li><a href="<%=basePath %>/portal/crm/help/list/notice">服务公告</a></li>
        </ul>
    </div>
</div>

	<div class="container-fluid wrap">
		<div class="help-container">
            <span class="icon-cs"></span>
            <div class="title">
                <div>司集帮助中心</div>
            </div>
            <div class="search">
                <div class="inner">
                    <input class="txt" type="search" id="search" placeholder="请输入关键字">
                    <button class="button">搜 索</button>
                    <span class="icon icon-search-1"></span>
                </div>
            </div>
            <table class="search-list" id="table">
                <tr>
                    <td>
                        <div class="item">
                            <a class="tit" href="">司集帮助中心司集帮助中心司集帮助中心111</a>
                            <div class="info">
                                司集帮助中心司集帮助中心司集帮助中心, <br>司集帮助中心司集帮助中心司<br>集帮助中心,司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司<br>集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心司集帮助中心司<br>集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心,司集帮助<br>中心司集帮助中心司集帮助中心
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                        <a class="tit" href="">司集帮助中心司集帮助中心司集帮助中心222</a>
                        <div class="info">
                            司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心
                        </div>
                    </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                        <a class="tit" href="">司集帮助中心司集333</a>
                        <div class="info">
                            司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心,司集帮助中心司集帮助中心司集帮助中心
                        </div>
                    </div>
                    </td>
                </tr>
            </table>
        </div>
	</div>
    <script src="js/jquery.min.js"></script>
    <script src="js/jquery.searchable-1.0.0.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>