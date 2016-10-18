<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script src="<%=basePath %>/dist/js/coupon/modifyCouponGroup.js"></script>
	<div class="">
		<!-- /section:settings.box -->
		<div class="page-header">
			<h1>
			修改优惠卷组
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal" id="coupongroupform">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
				<div class="form-group">
					<label for="coupon_no"  class="col-sm-3 control-label no-padding-right">编号：</label>
					<div class="col-sm-4" style="margin-top:7px">
						${couponGroup.coupongroup_no}
					</div>
				</div>
				<div class="form-group">
					<label for="couponGroup_title"  class="col-sm-3 control-label no-padding-right">名称：</label>
					<div class="col-sm-4">
						<input type="hidden" name="coupongroup_id" value="${couponGroup.coupongroup_id}"/>
						<input type="text" name="couponGroup_title" placeholder="输入优惠卷组名称" class="form-control" maxlength="20" value="${couponGroup.coupongroup_title}" required/>
					</div>
				</div>
				<div class="form-group">
					<label for="couponGroup_detail" class="col-sm-3 control-label no-padding-right" >详情：</label>
					<div class="col-sm-4">
						<textarea type="text" name="couponGroup_detail" placeholder="输入优惠卷组详情"  class="form-control" maxlength="150">${couponGroup.coupon_detail}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">额定时间：</label>
					<div class="col-sm-4 datepicker-noicon">
						<input type="text" class="date-picker" name="start_rated_time" id="start_rated_time" value="${couponGroup.start_rated_time}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
						<span class="">
							<i class="fa fa-exchange"></i>
						</span>
						<input type="text" class="date-picker" name="end_rated_time" id="end_rated_time" value="${couponGroup.end_rated_time}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
					</div>
				</div>
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="button" onclick="save();">
							<i class="ace-icon fa fa-check bigger-110"></i>保存
						</button>
						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="reset" onclick="resetform();">
							<i class="ace-icon fa fa-repeat bigger-110"></i>重置
						</button>
						&nbsp; &nbsp; &nbsp;
						<button class="btn btn-success" type="button" onclick="returnpage();">
							<i class="ace-icon fa fa-undo bigger-110"></i>返回
						</button>
					</div>
				</div>
				<jsp:include page="/common/message.jsp"></jsp:include>
				</form>
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content -->