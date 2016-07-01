<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>
			<c:if test="${sessionScope.currUser.user.userType == 1}">
				陕西司集能源气站管理系统
			</c:if>
			<c:if test="${sessionScope.currUser.user.userType == 2}">
				陕西司集能源运输公司管理系统
			</c:if>
			<c:if test="${sessionScope.currUser.user.userType == 4}">
				陕西司集能源气站集团管理系统
			</c:if>
			<c:if test="${sessionScope.currUser.user.userType == 5}">
				陕西司集能源运维管理平台
			</c:if>
		</title>

		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<link type="image/x-icon" href="<%=basePath %>/common/favicon.ico" rel="shortcut icon" />
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.css" />

		<!-- page specific plugin styles -->

		<!-- text fonts -->
		<link rel="stylesheet" href="<%=basePath %>/assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="<%=basePath %>/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="<%=basePath %>/assets/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="<%=basePath %>/assets/css/ace-ie.css" />
		<![endif]-->
		
		<link rel="stylesheet" href="<%=basePath %>/common/css/comm.css" />
		<!-- 弹层样式及插件 -->
		<link type="text/css" href="<%=basePath %>/assets/artDialog/css/ui-dialog.css" rel="stylesheet" />
		<!-- 日期控件 -->
		<link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap-datepicker3.css" />

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-duallistbox.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-switch.min.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-multiselect.css" />

		<link rel="stylesheet" href="<%=basePath %>/assets/css/colorbox.css" />
		<link rel="stylesheet" href="<%=basePath %>/common/css/fileinput.css" />
		<link rel="stylesheet" href="<%=basePath%>/assets/css/select2.css" />
	</head>

	<body class="no-skin">