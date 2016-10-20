<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script src="<%=basePath %>/dist/js/coupon/addCoupon.js"></script>
	<div class="">
		<!-- /section:settings.box -->
		<div class="page-header">
			<h1>
				新建优惠卷
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<form class="form-horizontal" id="couponform">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="form-group">
						<label for="coupon_type" class="col-sm-3 control-label no-padding-right">优惠方式：</label>
						<div class="col-sm-4">
							<div class="radio">
								<label class="radio-inline">
									<input name="coupon_type"  type="radio" class="ace" value="1" checked="checked" onclick="changeCouponType()">
									<span class="lbl"><s:Code2Name mcode="1" gcode="COUPON_TYPE"></s:Code2Name></span>
								</label>
								<%--<label class="radio-inline">--%>
									<%--<input name="coupon_type"  type="radio" class="ace" value="2" onclick="changeCouponType()">--%>
									<%--<span class="lbl"><s:Code2Name mcode="2" gcode="COUPON_TYPE"></s:Code2Name></span>--%>
								<%--</label>--%>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="coupon_kind" class="col-sm-3 control-label no-padding-right">优惠类型：</label>
						<div class="col-sm-4">
							<div class="radio">
								<label class="radio-inline">
									<input name="coupon_kind"  type="radio" class="ace" value="1" checked="checked" onclick="changecouponkind()">
									<span class="lbl"><s:Code2Name mcode="1" gcode="COUPON_KIND"></s:Code2Name></span>
								</label>
								<label class="radio-inline">
									<input name="coupon_kind"  type="radio" class="ace" value="2" onclick="changecouponkind()">
									<span class="lbl"><s:Code2Name mcode="2" gcode="COUPON_KIND"></s:Code2Name></span>
								</label>
							</div>
						</div>
					</div>
					<div class="form-group" id="station_name" style="display:none">
						<label for="sys_gas_station_id" class="col-sm-3 control-label no-padding-right">优惠气站：</label>
						<div class="col-sm-4">
							<select class="form-control" id="gas_station" name="sys_gas_station_id">
							</select>
						</div>
					</div>
					<div class="form-group" id="money">
						<label for="preferential_money" class="col-sm-3 control-label no-padding-right">优惠金额：</label>
						<div class="col-sm-4">
							<select class="form-control" name="preferential_money">
								<s:option flag="true" gcode="DISCOUNT_CODE" form="couponform" field="preferential_money" />
							</select>
						</div>
					</div>
					<div class="form-group" id="discount" style="display:none">
						<label for="preferential_discount" class="col-sm-3 control-label no-padding-right">优惠折扣：</label>
						<div class="col-sm-4">
							<input type="text" name="preferential_discount"  style="width:50px;display:inline" maxlength="3"/> 折优惠
						</div>
					</div>
					<div class="form-group">
						<label for="use_condition" class="col-sm-3 control-label no-padding-right">使用条件：</label>
						<div class="col-sm-4">
								<label class="radio-inline">
									<input name="use_condition"  type="radio" class="ace" value="1" checked="checked" onclick="changeUseCondition()">
									<span class="lbl">满<input type="text" name="limit_money" maxlength="8" style="width:80px;"/> 元使用</span>
								</label>
								<label class="radio-inline">
									<input name="use_condition"  type="radio" class="ace" value="2" onclick="changeUseCondition()">
									<span class="lbl">无条件使用</span>
								</label>
						</div>
					</div>
					<div class="form-group" id="time">
						<label class="col-sm-3 control-label no-padding-right">优惠时间：</label>
						<div class="col-sm-4 datepicker-noicon">
							<input type="text" class="date-picker" name="start_coupon_time" id="start_coupon_time"  readonly="readonly" data-date-format="yyyy-mm-dd"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
							<input type="text" class="date-picker" name="end_coupon_time" id="end_coupon_time"  readonly="readonly" data-date-format="yyyy-mm-dd"/>
						</div>
					</div>
					<div class="form-group">
						<label for="issuance_type" class="col-sm-3 control-label no-padding-right">消费方式：</label>
						<div class="col-sm-4">
							<div class="radio">
								<label class="radio-inline">
									<input name="consume_type"  type="radio" class="ace" value="1" checked="checked">
									<span class="lbl"><s:Code2Name mcode="1" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
									<input name="consume_type"  type="radio" class="ace" value="2">
									<span class="lbl"><s:Code2Name mcode="2" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
									<input name="consume_type"  type="radio" class="ace" value="3">
									<span class="lbl"><s:Code2Name mcode="3" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="issuance_type" class="col-sm-3 control-label no-padding-right">发放方式：</label>
						<div class="col-sm-4">
							<div class="radio">
							<label class="radio-inline">
								<input name="issuance_type"  type="radio" class="ace" value="1" checked="checked">
								<span class="lbl"><s:Code2Name mcode="1" gcode="ISSUANCE_TYPE"></s:Code2Name></span>
							</label>
							<label class="radio-inline" style="display:none">
								<input name="issuance_type"  type="radio" class="ace" value="2">
								<span class="lbl"><s:Code2Name mcode="2" gcode="ISSUANCE_TYPE"></s:Code2Name></span>
							</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="status" class="col-sm-3 control-label no-padding-right">状态：</label>
						<div class="col-sm-4">
						<div class="radio">
							<label class="radio-inline">
								<input name="status"  type="radio" class="ace" value="1" checked="checked">
								<span class="lbl"><s:Code2Name mcode="1" gcode="COUPON_STATUS"></s:Code2Name></span>
							</label>
							<label class="radio-inline">
								<input name="status"  type="radio" class="ace" value="2">
								<span class="lbl"><s:Code2Name mcode="2" gcode="COUPON_STATUS"></s:Code2Name></span>
							</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="coupon_detail" class="col-sm-3 control-label no-padding-right" >详情：</label>
						<div class="col-sm-4">
							<textarea type="text" name="coupon_detail" placeholder="输入优惠详情" class="form-control" maxlength="150"></textarea>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">

							<button class="btn btn-info" type="button" onclick="save();">
								<i class="ace-icon fa fa-check bigger-110"></i>
								保存
							</button>
							&nbsp; &nbsp; &nbsp;

							<button class="btn" type="reset" onclick="resetform();">
								<i class="ace-icon fa fa-repeat bigger-110"></i>
								重置
							</button>
							&nbsp; &nbsp; &nbsp;

							<button class="btn btn-success" type="button" onclick="returnpage();">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								返回
							</button>
						</div>
					</div>
					<jsp:include page="/common/message.jsp"></jsp:include>
				</form>
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content -->