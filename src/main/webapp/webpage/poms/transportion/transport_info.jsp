<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
</script>
<div class="page-header">
	<h1>
		公司信息
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			信息详情 &amp; 页面
		</small>
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
					<label class="pad-top-10">${transportion.transportionName}</label>
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
					<label class="pad-top-10">${transportion.adminUsername}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 联系电话： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.contactPhone}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right"> 平台有效期： </label>
				<div class="col-sm-8">
					<label class="pad-top-10">${transportion.expiryDate}</label>
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

