<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="pdp product">
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">出发地:</div>
				<div class="col-key">陕西省西安市中心产业园</div>
			</div>
			<div class="row address-end">
				<div class="col-value">目的地:</div>
				<div class="col-key">陕西省西安市中心产业园</div>
			</div>
		</div>
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">货物类型:</div>
				<div class="col-key">煤炭</div>
			</div>
			<div class="row">
				<div class="col-value">货物报价:</div>
				<div class="col-key">600元/顿</div>
			</div>
		</div>
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">货物说明:</div>
				<div class="col-key product-explain">陕西省西安市中心产业园 陕西省西安市中心产业园 陕西省西安市中心产业园</div>
			</div>
		</div>
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">联系人:</div>
				<div class="col-key">张三丰</div>
			</div>
			<div class="row">
				<div class="col-value">联系电话:</div>
				<div class="col-key">15000045778</div>
			</div>
		</div>
	</div>
</body>
</html>