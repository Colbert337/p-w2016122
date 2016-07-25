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

	<div class="container-fluid wrap crm">
		<div class="row">
            <div class="col-sm-7">
                <img class="img-responsive" src="images/crm.jpg" alt="">
            </div>
            <div class="col-sm-5">
                <ul class="crm-down">
                    <li class="title">司集CRM V1.3.0</li>
                    <li>
                        <span class="pull-right">2016-6-24</span>
                        16.5M
                    </li>
                    <li>
                        <a href="" class="btn btn-block btn-primary btn-lg">下载</a>
                    </li>
                </ul>
            </div>
        </div>
	</div>
    <script src="js/jquery.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>