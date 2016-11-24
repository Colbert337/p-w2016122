<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="white" lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>路况详情-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/bootstrap.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div style="max-width: 500px;margin: 0 auto;padding: 15px;" >
			<div class="row">
				<div class="">
					<label style="font-size: large;font-weight: bold;color: red;" >${conditionType}</label>
					<span style="float: right;padding-right: 20px;">发布时间：<fmt:formatDate value="${roadCondition.publisherTime}" pattern="yyyy-MM-dd-HH:mm:ss" /></span>
				</div>
			</div>
			<%--<div style="border-bottom: 1px solid #ccc;"></div>--%>
			<div class="row">
				<div class="col-md-8">
					<table class="table">
						<tbody>
						<tr>
							<th>路况详情：</th>
							<td><div id="" name="show">${conditionMsg}</div></td>
						</tr>
						<tr>
							<th>行驶方向：</th>
							<td><div id="" name="show">${direction}</div></td>
						</tr>
						<tr>
							<th width="28%">位置信息：</th>
							<td><div  name="show">${roadCondition.address}</div></td>
						</tr>
						<tr>
							<th>发布人：</th>
							<td><div  name="show">${name}</div></td>
						<tr>
						<c:if test="${conditionImg != null}" >
							<tr>
								<td colspan="2">
									<div class="img col-sm-7">
										<img class="img-responsive" src="${conditionImg}" alt="">
									</div>
								</td>
							<tr>
						</c:if>
						</tbody>
					</table>
				<%--<div class="col-xs-12">

					<div class="row">
						<label class="col-sm-1" > 位置信息： </label>
						<div class="col-sm-3">${roadCondition.address}</div>
					</div>

					<div style="clear:both;"></div>
					<div class="row">
						<label class="col-sm-1" > 行驶方向： </label>
						<div class="col-sm-3">${direction}</div>
					</div>
					<div style="clear:both;"></div>
					<div class="row">
						<label class="col-sm-2 " > 路况详情： </label>
						<div class="col-sm-5">${conditionMsg}</div>
					</div>
					<div style="clear:both;"></div>
					<div class="row">
						<label class="col-sm-2" > 路况发布人： </label>
						<div class="col-sm-5">${name}</div>
					</div>
					<div style="clear:both;"></div>
					&lt;%&ndash;<div>行驶方向：${direction}</div>
					<div>路况详情：${conditionMsg}</div>
					<div>路况发布人：${name}</div>&ndash;%&gt;
					<c:if test="${conditionImg != null}" >
						<div class="row">
							<div class="img col-sm-7">
								<img class="img-responsive" src="${conditionImg}" alt="">
							</div>
						</div>
					</c:if>
				</div>
			</div>--%>
			<%--<div class="date">
				发布时间：
				<fmt:formatDate value="${roadCondition.publisherTime}" pattern="yyyy-MM-dd-HH:mm:ss" />
			</div>
			<div class="info">
				${conditionType}
				${name}于<fmt:formatDate value="${roadCondition.publisherTime}" pattern="yyyy-MM-dd-HH:mm" />前提供了${roadCondition.address}附近的${direction}的${conditionType}路况，请注意路况信息变化！
				<c:if test="${conditionMsg != null}" ><br>路况详情如下：<br>${conditionMsg}</c:if>
				<c:if test="${roadCondition.memo ne null && roadCondition.memo ne ''}" ><br>备注：<br>${roadCondition.memo}</c:if>
			</div>--%>
	</div>

	<%--<div class="btn-more-info">
		<div class="inner">
			&lt;%&ndash;<span class="icon-sy">&ndash;%&gt;
			<a href="" class="btn-app-primary logic-download-app"></span>下载司集APP给您提供更多优质服务</a>
		</div>
	</div>--%>

	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>