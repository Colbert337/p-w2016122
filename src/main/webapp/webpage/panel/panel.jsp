<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
</script>
<div class="page-header">
	<h1>
		总览
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			总览页面
		</small>
	</h1>
</div><!-- /.page-header -->
<div class="row">
	<div class="col-xs-12 panel-title">
		 <div class="space-32" style="margin-top: 10%;"></div>
		 <h1>
			 <c:if test="${sessionScope.currUser.user.userType == 1}">
				 欢迎登录司集加注站管理系统！
			 </c:if>
			 <c:if test="${sessionScope.currUser.user.userType == 2}">
				 欢迎登录司集运输公司管理系统！
			 </c:if>
			 <c:if test="${sessionScope.currUser.user.userType == 4}">
				 欢迎登录司集集团管理系统！
			 </c:if>
			 <c:if test="${sessionScope.currUser.user.userType == 5}">
				 欢迎登录司集运维管理平台！
			 </c:if>
		 </h1>
	</div>
</div>

