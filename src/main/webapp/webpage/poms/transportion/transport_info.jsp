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

<div class="gastation-infomation">
	<div class="gastation-infomation-hd">
		基础信息
	</div>
	<div class="gastation-infomation-items">
		<div class="item">
			<div class="row">
				<div class="col-sm-2 text-right">
					公司名称:
				</div>
				<div class="col-sm-10">
					<span class="value">${transportion.transportion_name}</span>
				</div>
			</div>
		</div>
		<div class="item">
			<div class="row">
				<div class="col-sm-2 text-right">
					公司地址:
				</div>
				<div class="col-sm-9">
					<span class="value">${transportion.address}</span>
				</div>
			</div>
		</div>
		<div class="item">
			<div class="row">
				<div class="col-sm-2 text-right">
					联系人:
				</div>
				<div class="col-sm-9">
					<span class="value">${transportion.station_manager}</span>
				</div>
			</div>
		</div>
		<div class="item">
			<div class="row">
				<div class="col-sm-2 text-right">
					联系电话:
				</div>
				<div class="col-sm-9">
					<span class="value">${transportion.contact_phone}</span>
				</div>
			</div>
		</div>
		<div class="item">
			<div class="row">
				<div class="col-sm-2 text-right">
					注册邮箱:
				</div>
				<div class="col-sm-9">
					<span class="value">${transportion.email}</span>
				</div>
			</div>
		</div>
		<div class="item">
			<div class="row">
				<div class="col-sm-2 text-right">
					平台有效期:
				</div>
				<div class="col-sm-9">
					<span class="value"><fmt:formatDate value="${transportion.expiry_date}" type="both" pattern="yyyy-MM-dd HH:mm"/></span>
				</div>
			</div>
		</div>
	</div>

	<div class="gastation-infomation-hd">
		证件信息
	</div>
	<div class="gastation-infomation-cert">
		<div class="row">
			<div class="col-sm-6 item">
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
			<div class="col-sm-6 item">
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
	</div>
</div>