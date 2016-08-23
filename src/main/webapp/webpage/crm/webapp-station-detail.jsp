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
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
</head>
<body>
	<div class="pdp">
		<div class="pdp-title">
			<span class="type">加盟气站</span>
			<div class="name">陕西汽车站</div>
		</div>
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">气站地址:</div>
				<div class="col-key">
					<a href="navigation://sysongy.com" class="station-icon"><span class="icon-direction"></span></a>
					陕西省西安市中心产业园
				</div>
			</div>
			<div class="row address-end">
				<div class="col-value">提供服务:</div>
				<div class="col-key">住宿，加气，餐饮</div>
			</div>
		</div>
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">电话:</div>
				<div class="col-key">
					<a href="tel:15000045778" class="station-icon"><span class="icon-phone-1"></span></a>
					15000045778
				</div>
			</div>
		</div>
		<div class="pdp-item">
			<div class="pdp-item-title">
				商品信息：
			</div>
			<div class="pdp-item-content">
				<div class="row">
					<div class="col-value">LNG单价:</div>
					<div class="col-key">15元/kg</div>
				</div>
				<div class="row">
					<div class="col-value">LNG单价:</div>
					<div class="col-key">15元/kg</div>
				</div>
			</div>
		</div>
		<div class="pdp-item">
			<div class="pdp-item-title">
				优惠活动：
			</div>
			<ul class="station-special">
				<li>
					陕西省西安市中心产业园 陕西省西安市中心产业园 陕西省西
				</li>
				<li>
					陕西省西安市中心产业园 陕西省西安市中心产业园 陕西省西
				</li>
				<li>
					陕西省西安市中心产业园 陕西省西安市中心产业园 陕西省西
				</li>
			</ul>
		</div>
		<div class="station-special-more">
			<a class="btn">查看更多优惠</a>
		</div>
	</div>

	<script src="<%=basePath %>/webpage/crm/js/zepto.min.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>