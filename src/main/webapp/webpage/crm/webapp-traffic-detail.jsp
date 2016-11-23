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
	<div >
			<div class="row">
				<div class="col-xs-12">
					<div class="col-sm-5"><span style="font-size: large;font-weight: bold;color: red;">${conditionType}</span></div>
					<div class="col-sm-7">发布时间：<fmt:formatDate value="${roadCondition.publisherTime}" pattern="yyyy-MM-dd-HH:mm:ss" /></div>
				</div>
			</div>
			<div style="border-bottom: 1px solid #ccc;"></div>
			<div class="row">
				<div class="col-xs-12">
					<div class="form-group">
						<label class="col-sm-1" > 位置信息： </label>
						<div class="col-sm-3">${roadCondition.address}</div>
					</div>
					<div style="clear:both;"></div>
					<div class="form-group">
						<label class="col-sm-1" > 行驶方向： </label>
						<div class="col-sm-3">${direction}</div>
					</div>
					<div style="clear:both;"></div>
					<div class="form-group">
						<label class="col-sm-2 " > 路况详情： </label>
						<div class="col-sm-5">${conditionMsg}</div>
					</div>
					<div style="clear:both;"></div>
					<div class="form-group">
						<label class="col-sm-2" > 路况发布人： </label>
						<div class="col-sm-5">${name}</div>
					</div>
					<div style="clear:both;"></div>
					<%--<div>行驶方向：${direction}</div>
					<div>路况详情：${conditionMsg}</div>
					<div>路况发布人：${name}</div>--%>
					<c:if test="${conditionImg != null}" >
						<div class="row">
							<div class="form-group">
								<div class="img col-sm-7">
									<img class="img-responsive" src="${conditionImg}" alt="">
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
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