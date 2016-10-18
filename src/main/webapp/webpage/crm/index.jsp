<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="index" lang="zh">
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

	<div class="container-fluid">
		<div class="main">
			<div class="banner">
				<h1>司集云平台</h1>
				<h2>专注打造中国互联网清洁能源平台</h2>
			</div>
		</div>
	</div>
	
	<div class="my-selected my-selected-index">
		<div class="container-fluid">
			<div class="wrap">
				<div class="inner">
					<div class="row">
						<div class="col-sm-4">
							<div class="key-item">
								<span class="icon icon-lng"></span>
								<a href="<%=basePath %>/webpage/crm/project_gs.jsp">
									我是加注站
								</a>
							</div>
							<a class="key-item-xs" href="<%=basePath %>/webpage/crm/project_gs.jsp">
								<span class="icon icon-lng"></span>
								我是加注站
							</a>
						</div>
						<div class="col-sm-4">
							<div class="key-item">
								<span class="icon icon-sun"></span>
								<a href="<%=basePath %>/webpage/crm/project_tc.jsp">
									我是运输公司
								</a>
							</div>
							<a class="key-item-xs" href="<%=basePath %>/webpage/crm/project_tc.jsp">
								<span class="icon icon-sun"></span>
								我是运输公司
							</a>
						</div>
						<div class="col-sm-4">
							<div class="key-item">
								<span class="icon icon-car"></span>
								<a href="<%=basePath %>/webpage/crm/project_app.jsp">
									我是个人司机
								</a>
							</div>
							<a class="key-item-xs" href="<%=basePath %>/webpage/crm/project_app.jsp">
								<span class="icon icon-car"></span>
								我是个人司机
							</a>
						</div>
					</div>
					<a href="" class="key-close"><span class="icon-cancel"></span></a>
				</div>
				<div class="bg-opacity"></div>
			</div>
		</div>
	</div>

	<footer>
		陕西司集能源科技有限公司
	</footer>

	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/main.js"></script>
</body>
</html>