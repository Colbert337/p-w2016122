<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>站点信息-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
</head>
<body>
	<div class="pdp">
		<div class="pdp-title clearfix">
			<span class="type"><c:if test="${gastation.type == '0'}">联盟站</c:if></span>
			<div class="name">${gastation.gas_station_name}</div>
		</div>
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">气站地址:</div>
				<div class="col-key">
					<%--<a href="<%=basePath %>/webpage/crm/webapp-download-app.jsp" class="station-icon"><span class="icon-direction"></span></a>--%>
					${gastation.address}
				</div>
			</div>
			<div class="row address-end">
				<div class="col-value">提供服务:</div>
				<div class="col-key">${gastation.gas_server}</div>
			</div>
		</div>
		<div class="pdp-item">
			<div class="row">
				<div class="col-value">电话:</div>
				<div class="col-key">
					<a href="<%=basePath %>/webpage/crm/webapp-download-app.jsp" class="station-icon"><span style="color: #669F31;" class="icon-phone-1"></span></a>
					${gastation.contact_phone}
				</div>
			</div>
			<c:if test="${!empty gastation.station_telephone}">
				<div class="row">
					<div class="col-value">&nbsp;&nbsp;&nbsp;</div>
					<div class="col-key">
						<a href="tel:${gastation.station_telephone}" class="station-icon"><span
							style="color: #669F31;" class="icon-phone-1"></span></a>
						${gastation.station_telephone}
					</div>
				</div>
			</c:if>
		</div>
		<div class="pdp-item">
			<div class="pdp-item-title">
				商品信息：
			</div>
			<div class="pdp-item-content">
				<c:forEach items="${priceList}" var="price">
					<div class="row">
						<div class="col-value">${price.priceName}</div>
							<%--<div class="col-key">${price.gasPrice}元/${price.unit}</div>--%>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="pdp-item">
			<div class="pdp-item-title">
				优惠活动：
			</div>
			<ul class="station-special">
				<li>${gastation.promotions}</li>
			</ul>
		</div>
		<%--<div class="station-special-more">
			<a class="btn">查看更多优惠</a>
		</div>--%>
	</div>

	<div class="btn-more-info">
		<div class="inner">
			<a href="<%=basePath %>/webpage/crm/webapp-download-app" class="btn-app-primary logic-download-app"></span>下载司集APP给您提供更多优质服务</a>
		</div>
	</div>

	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>