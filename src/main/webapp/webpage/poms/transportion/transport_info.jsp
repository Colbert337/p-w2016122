<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
</script>
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
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form">
			<!-- #section:elements.form -->
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 公司名称： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.transportion_name}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 公司地址： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.address}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 联系人： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.station_manager}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 联系电话： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.contact_phone}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 注册邮箱： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.email}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 平台有效期： </label>
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

