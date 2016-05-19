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
		<title>海贝运维支撑系统</title>

		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

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

		<!-- inline styles related to this page -->
		<!-- basic scripts -->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="<%=basePath %>/assets/js/jquery-1.9.1.min.js"></script>
		<!-- ace settings handler -->
		<script src="<%=basePath %>/assets/js/ace-extra.js"></script>
		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="<%=basePath %>/assets/js/html5shiv.js"></script>
		<script src="<%=basePath %>/assets/js/respond.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" href="<%=basePath %>/comm/css/comm.css" />
		<!-- 弹层样式及插件 -->
		<link type="text/css" href="<%=basePath %>/assets/artDialog/css/ui-dialog.css" rel="stylesheet" />
		<script type="text/javascript"  src="<%=basePath %>/assets/artDialog/dist/dialog-plus-min.js"></script>
		<!-- JqueryValidationEngine表单验证  -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/assets/jQueryVE/css/validationEngine.jquery.css" />
		<script type="text/javascript" src="<%=basePath %>/assets/jQueryVE/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath %>/assets/jQueryVE/js/jquery.validationEngine.min.js"></script>
	</head>

	<body class="no-skin">