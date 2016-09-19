<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="index" lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>活动详情-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="activ-detail-hd">
		<h2>${mbBanner.title}</h2>
		<div class="date">
			<span><fmt:formatDate value="${mbBanner.createdDate}" type="both"/> </span>
			<span>司集能源</span>
			<span>阅读 11</span>
		</div>
	</div>
	<div class="activ-detail-bd">
		他们每年上亿的过路费，油费，罚款，每笔支出都在为国家财政做贡献。<br>
		“2010年3月公安部公布的货车保有量为1368.59万辆。如果按照每辆货车每天被罚款100元，全年按照300天计算，即每车年罚款3万元，即4105.77亿元，这只是按2009年公布的车辆算2010年的罚款数字。”（数据来自网络）卡车司机才是交警、路政以及13亿人的衣食父母。<br>
		他们每年上亿的过路费，油费，罚款，每笔支出都在为国家财政做贡献。<br>
		“2010年3月公安部公布的货车保有量为1368.59万辆。如果按照每辆货车每天被罚款100元，全年按照300天计算，即每车年罚款3万元，即4105.77亿元，这只是按2009年公布的车辆算2010年的罚款数字。”（数据来自网络）卡车司机才是交警、路政以及13亿人的衣食父母。<br>
		他们每年上亿的过路费，油费，罚款，每笔支出都在为国家财政做贡献。<br>
		“2010年3月公安部公布的货车保有量为1368.59万辆。如果按照每辆货车每天被罚款100元，全年按照300天计算，即每车年罚款3万元，即4105.77亿元，这只是按2009年公布的车辆算2010年的罚款数字。”（数据来自网络）卡车司机才是交警、路政以及13亿人的衣食父母。<br>
		他们每年上亿的过路费，油费，罚款，每笔支出都在为国家财政做贡献。<br>
		“2010年3月公安部公布的货车保有量为1368.59万辆。如果按照每辆货车每天被罚款100元，全年按照300天计算，即每车年罚款3万元，即4105.77亿元，这只是按2009年公布的车辆算2010年的罚款数字。”（数据来自网络）卡车司机才是交警、路政以及13亿人的衣食父母。<br>
		他们每年上亿的过路费，油费，罚款，每笔支出都在为国家财政做贡献。<br>
		“2010年3月公安部公布的货车保有量为1368.59万辆。如果按照每辆货车每天被罚款100元，全年按照300天计算，即每车年罚款3万元，即4105.77亿元，这只是按2009年公布的车辆算2010年的罚款数字。”（数据来自网络）卡车司机才是交警、路政以及13亿人的衣食父母。<br>
	</div>

	<div class="btn-more-info">
		<div class="inner">
			<a href="" class="btn-app-primary logic-download-app"><span class="icon-sy"></span>下载司集APP给您提供更多优质服务</a>
		</div>
	</div>

	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>