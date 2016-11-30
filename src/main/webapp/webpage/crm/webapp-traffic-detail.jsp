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
	<title>路况详情</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/bootstrap.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div style="max-width: 500px;margin: 0 auto;padding: 15px;" >
			<div class="row">
				<div style="padding-left: 20px;font-size: large;font-weight: bold;color: #669f31;" >${conditionType}</div>
				<div style="padding-left: 20px;">发布时间：<fmt:formatDate value="${roadCondition.publisherTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
			</div>
			<div class="row">
				<div class="col-md-8">
					<table class="table">
						<tbody>
						<tr>
							<th width="32%">路况详情：</th>
							<td><div id="" name="show">${conditionMsg}</div></td>
						</tr>
						<tr>
							<th>行驶方向：</th>
							<td><div id="" name="show">${direction}</div></td>
						</tr>
						<tr>
							<th>位置信息：</th>
							<td><div  name="show">${roadCondition.address}</div></td>
						</tr>
						<tr>
							<th>提供者：</th>
							<td><div  name="show">${name}</div></td>
						<tr>
						<c:if test="${conditionImg != null}" >
							<tr>
								<td colspan="2" align="center">
									<div class="img col-sm-7">
										<img class="img-responsive" src="${conditionImg}" alt="">
									</div>
								</td>
							<tr>
						</c:if>
						</tbody>
					</table>
				</div>
			</div>
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