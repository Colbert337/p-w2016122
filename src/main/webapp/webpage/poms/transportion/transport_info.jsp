<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String imagePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<script src="<%=basePath %>/dist/js/transportion/transportion_info.js"></script>
<div class="page-header">
	<h1>
		公司信息
		<%--<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			信息详情 &amp; 页面
		</small>--%>
	</h1>
</div><!-- /.page-header -->
<%--单行表单 开始--%>
<div class="row">
	<div class="col-sm-1"></div>
	<div class="col-xs-9">
		<h3 class="row header smaller lighter blue col-sm-12">
			<span class="col-sm-7" style="font-size: 15px;">
				基础信息
			</span>
		</h3>
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form">
			<!-- #section:elements.form -->
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right"> 公司名称： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.transportion_name}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right"> 公司地址： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.address}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right"> 联系人： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.station_manager}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right"> 联系电话： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.contact_phone}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right"> 注册邮箱： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.email}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label no-padding-right"> 平台有效期： </label>
				<div class="col-sm-8">
					<label class="pad-top-10"><fmt:formatDate value="${transportion.expiry_date}" type="both" pattern="yyyy-MM-dd HH:mm"/></label>
				</div>
			</div>
			<%--<div class="form-group">
				<div class="col-md-offset-5 col-md-8">
					<button class="btn btn-md btn-primary" type="button">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						返回
					</button>
				</div>
			</div>--%>
		</form>
	</div><!-- /.col -->
</div><!-- /.row -->
<%--单行表单结束--%>

<div class="row">
	<div class="col-sm-1"></div>
	<div class="col-xs-9">
		<h3 class="row header smaller lighter blue col-sm-12">
			<span class="col-sm-7" style="font-size: 15px;">
				证件信息
			</span>
		</h3>
		<div class="row">
			<div class="col-sm-6">
				<c:choose>
					<c:when test="${transportion.indu_com_certif == null || transportion.indu_com_certif == ''}">
						<img class="col-sm-4" src="../common/images/default_productBig.jpg">
					</c:when>
					<c:otherwise>
						<a class="gastation-log-colorbox" href="${transportion.indu_com_certif}" data-rel="colorbox">
							<img class="img-responsive" src="${transportion.indu_com_certif}" alt="">
							<div class="title">工商注册证</div>
						</a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-sm-6">
				<c:choose>
					<c:when test="${transportion.tax_certif == null || transportion.tax_certif == ''}">
						<img class="col-sm-4" src="../common/images/default_productBig.jpg">
					</c:when>
					<c:otherwise>
						<a class="gastation-log-colorbox" href="${transportion.tax_certif}" data-rel="colorbox">
							<img class="img-responsive" src="${transportion.tax_certif}" alt="">
							<div class="title">税务登记证</div>
						</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div><!-- /.col -->
</div><!-- /.row -->
<%--单行表单结束--%>