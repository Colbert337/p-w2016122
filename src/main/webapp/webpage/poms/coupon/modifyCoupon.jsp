<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script src="<%=basePath %>/dist/js/coupon/modifyCoupon.js"></script>
	<style type="text/css">
	#limit i.form-control-feedback{
		right: -28px;
	}
	</style>
	<div class="">
		<!-- /section:settings.box -->
		<div class="page-header">
			<h1>
			修改优惠卷
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal" id="couponform">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
				<div class="form-group">
					<label for="coupon_no"  class="col-sm-3 control-label no-padding-right">编号：</label>
					<div class="col-sm-4" style="margin-top:7px">
						${coupon.coupon_no} <input type="hidden" name="coupon_id" value="${coupon.coupon_id}"/>
						<input type="hidden" name="coupon_no" value="${coupon.coupon_no}"/>
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label for="coupon_type" class="col-sm-3 control-label no-padding-right">优惠方式：</label>--%>
					<%--<div class="col-sm-4">--%>
						<%--<div class="radio">--%>
							<%--<label class="radio-inline">--%>
								<%--<input name="coupon_type"  type="radio" class="ace" value="1" <c:if test="${coupon.coupon_type=='1'}">checked="checked"</c:if> onclick="changeCouponType()">--%>
								<%--<span class="lbl"><s:Code2Name mcode="1" gcode="COUPON_TYPE"></s:Code2Name></span>--%>
							<%--</label>--%>
							<%--<label class="radio-inline">--%>
								<%--<input name="coupon_type"  type="radio" class="ace" value="2" <c:if test="${coupon.coupon_type=='2'}">checked="checked"</c:if> onclick="changeCouponType()">--%>
								<%--<span class="lbl"><s:Code2Name mcode="2" gcode="COUPON_TYPE"></s:Code2Name></span>--%>
							<%--</label>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="form-group">
					<label for="coupon_kind" class="col-sm-3 control-label no-padding-right">优惠类型：</label>
					<div class="col-sm-4">
						<div class="radio">
							<input type="hidden" name="coupon_type" value="${coupon.coupon_type}"/>
								<c:if test="${coupon.coupon_nums==0}">
									<label class="radio-inline">
										<input name="coupon_kind"  type="radio" class="ace" value="1" <c:if test="${coupon.coupon_kind=='1'}">checked="checked"</c:if> onclick="changecouponkind()">
										<span class="lbl"><s:Code2Name mcode="1" gcode="COUPON_KIND"></s:Code2Name></span>
									</label>
									<label class="radio-inline">
										<input name="coupon_kind"  type="radio" class="ace" <c:if test="${coupon.coupon_kind=='2'}">checked="checked"</c:if> value="2" onclick="changecouponkind()">
										<span class="lbl"><s:Code2Name mcode="2" gcode="COUPON_KIND"></s:Code2Name></span>
									</label>
								</c:if>
								<c:if test="${coupon.coupon_nums>0}">
									<input type="hidden" name="coupon_kind" value="${coupon.coupon_kind}"/>
									<label class="radio-inline">
										<input name="coupon_kind"  type="radio" class="ace" value="1" <c:if test="${coupon.coupon_kind=='1'}">checked="checked"</c:if> disabled="disabled" onclick="changecouponkind()">
										<span class="lbl"><s:Code2Name mcode="1" gcode="COUPON_KIND"></s:Code2Name></span>
									</label>
									<label class="radio-inline">
										<input name="coupon_kind"  type="radio" class="ace" <c:if test="${coupon.coupon_kind=='2'}">checked="checked"</c:if> value="2" disabled="disabled" onclick="changecouponkind()">
										<span class="lbl"><s:Code2Name mcode="2" gcode="COUPON_KIND"></s:Code2Name></span>
									</label>
								</c:if>
						</div>
					</div>
				</div>
				<div class="form-group" id="station_name" style="display:none">
					<label for="sys_gas_station_id" class="col-sm-3 control-label no-padding-right">优惠气站：</label>
					<div class="col-sm-4">
						<c:if test="${coupon.coupon_nums==0}">
								<select class="form-control" id="gas_station" name="sys_gas_station_id" sys_gas_station_id="${coupon.sys_gas_station_id}">
								</select>
							</c:if>
						<c:if test="${coupon.coupon_nums>0}">
								<input type="hidden" name="sys_gas_station_id" value="${coupon.sys_gas_station_id}"/>
								<select class="form-control" id="gas_station" disabled="disabled" name="sys_gas_station_id" sys_gas_station_id="${coupon.sys_gas_station_id}">
								</select>
							</c:if>
					</div>
				</div>
				<div class="form-group" id="money">
					<label for="preferential_money" class="col-sm-3 control-label no-padding-right">优惠金额：</label>
					<div class="col-sm-4">
						<c:if test="${coupon.coupon_nums==0}">
							<select class="form-control" name="preferential_money">
								<s:option flag="true" gcode="DISCOUNT_CODE" form="coupon" field="preferential_money" />
							</select>
						</c:if>
						<c:if test="${coupon.coupon_nums>0}">
							<input type="hidden" name="preferential_money" value="${coupon.preferential_money}"/>
							<select class="form-control" disabled="disabled" name="preferential_money">
								<s:option flag="true" gcode="DISCOUNT_CODE" form="coupon" field="preferential_money" />
							</select>
						</c:if>
					</div>
				</div>
				<div class="form-group" id="discount" style="display:none">
					<label for="preferential_discount" class="col-sm-3 control-label no-padding-right">优惠折扣：</label>
					<div class="col-sm-4">
						<input type="text" name="preferential_discount" value="${coupon.preferential_discount}"  style="width:50px;display:inline" maxlength="3"/> 折优惠
					</div>
				</div>
				<div class="form-group">
					<label for="use_condition" class="col-sm-3 control-label no-padding-right">使用条件：</label>
					<div class="col-sm-4">
						<c:if test="${coupon.coupon_nums==0}">
							<label class="radio-inline">
								<input name="use_condition"  type="radio" class="ace" value="1" <c:if test="${coupon.use_condition=='1'}">checked="checked"</c:if>  onclick="changeUseCondition()">
								<span id="limit" class="lbl">满<input type="number" name="limit_money" <c:if test="${coupon.use_condition=='1'}">value="${coupon.limit_money}"</c:if>  maxlength="8" style="width:80px;"/>元使用</span>
							</label>
							<label class="radio-inline">
								<input name="use_condition"  type="radio" class="ace" <c:if test="${coupon.use_condition=='2'}">checked="checked"</c:if> value="2" onclick="changeUseCondition()">
								<span class="lbl">无条件使用</span>
							</label>
						</c:if>
						<c:if test="${coupon.coupon_nums>0}">
							<input type="hidden" name="use_condition" value="${coupon.use_condition}"/>
							<label class="radio-inline">
								<input name="use_condition" disabled="disabled"  type="radio" class="ace" value="1" <c:if test="${coupon.use_condition=='1'}">checked="checked"</c:if>  onclick="changeUseCondition()">
								<span class="lbl">满<input type="text" name="limit_money"  disabled="disabled" <c:if test="${coupon.use_condition=='1'}">value="${coupon.limit_money}"</c:if>  maxlength="8" style="width:80px;"/> 元使用</span>
							</label>
							<label class="radio-inline">
								<input name="use_condition"  type="radio"  disabled="disabled"   class="ace" <c:if test="${coupon.use_condition=='2'}">checked="checked"</c:if> value="2" onclick="changeUseCondition()">
								<span class="lbl">无条件使用</span>
							</label>
						</c:if>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">优惠时间：</label>
					<div class="col-sm-4 datepicker-noicon">
						<c:if test="${coupon.coupon_nums==0}">
							<input type="text" class="date-picker" name="start_coupon_time" id="start_coupon_time" value="${coupon.start_coupon_time}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
							<span class="">
								<i class="fa fa-exchange"></i>
							</span>
							<input type="text" class="date-picker" name="end_coupon_time" id="end_coupon_time" value="${coupon.end_coupon_time}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
						</c:if>
						<c:if test="${coupon.coupon_nums>0}">
							<input type="hidden" name="start_coupon_time" value="${coupon.start_coupon_time}"/>
							<input type="hidden" name="end_coupon_time" value="${coupon.end_coupon_time}"/>
							<input type="text" class="date-picker" disabled="disabled" name="start_coupon_time" id="start_coupon_time" value="${coupon.start_coupon_time}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
							<span class="">
								<i class="fa fa-exchange"></i>
							</span>
							<input type="text" class="date-picker"  disabled="disabled" name="end_coupon_time" id="end_coupon_time" value="${coupon.end_coupon_time}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
						</c:if>
					</div>
				</div>
				<div class="form-group">
					<label for="consume_type" class="col-sm-3 control-label no-padding-right">消费方式：</label>
					<div class="col-sm-4">
						<div class="radio">
							<c:if test="${coupon.coupon_nums==0}">
								<label class="radio-inline">
									<input name="consume_type"  type="radio" class="ace" value="1" <c:if test="${coupon.consume_type=='1'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="1" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
									<input name="consume_type"  type="radio" class="ace" value="2" <c:if test="${coupon.consume_type=='2'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="2" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
									<input name="consume_type"  type="radio" class="ace" value="3" <c:if test="${coupon.consume_type=='3'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="3" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
							</c:if>
							<c:if test="${coupon.coupon_nums>0}">
								<input type="hidden" name="consume_type" value="${coupon.consume_type}"/>
								<label class="radio-inline">
									<input name="consume_type" disabled="disabled"  type="radio" class="ace" value="1" <c:if test="${coupon.consume_type=='1'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="1" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
									<input name="consume_type" disabled="disabled"  type="radio" class="ace" value="2" <c:if test="${coupon.consume_type=='2'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="2" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
									<input name="consume_type" disabled="disabled"  type="radio" class="ace" value="3" <c:if test="${coupon.consume_type=='3'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="3" gcode="CONSUME_TYPE"></s:Code2Name></span>
								</label>
							</c:if>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="issuance_type" class="col-sm-3 control-label no-padding-right">发放方式：</label>
					<div class="col-sm-4">
						<div class="radio">
							<c:if test="${coupon.coupon_nums==0}">
								<label class="radio-inline">
								<input name="issuance_type"  type="radio" class="ace" value="1" <c:if test="${coupon.issuance_type=='1'}">checked="checked"</c:if>>
								<span class="lbl"><s:Code2Name mcode="1" gcode="ISSUANCE_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
								<input name="issuance_type"  type="radio" class="ace" value="2" <c:if test="${coupon.issuance_type=='2'}">checked="checked"</c:if>>
								<span class="lbl"><s:Code2Name mcode="2" gcode="ISSUANCE_TYPE"></s:Code2Name></span>
								</label>
							</c:if>
							<c:if test="${coupon.coupon_nums>0}">
								<input type="hidden" name="issuance_type" value="${coupon.issuance_type}"/>
								<label class="radio-inline">
									<input name="issuance_type" disabled="disabled"  type="radio" class="ace" value="1" <c:if test="${coupon.issuance_type=='1'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="1" gcode="ISSUANCE_TYPE"></s:Code2Name></span>
								</label>
								<label class="radio-inline" style="display:none">
									<input name="issuance_type"  disabled="disabled"  type="radio" class="ace" value="2" <c:if test="${coupon.issuance_type=='2'}">checked="checked"</c:if>>
									<span class="lbl"><s:Code2Name mcode="2" gcode="ISSUANCE_TYPE"></s:Code2Name></span>
								</label>
							</c:if>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="usetype" class="col-sm-3 control-label no-padding-right">状态：</label>
					<div class="col-sm-4">
						<div class="radio">
							<label class="radio-inline">
								<input name="status"  type="radio" class="ace" value="1" <c:if test="${coupon.status=='1'}">checked="checked"</c:if>>
								<span class="lbl"><s:Code2Name mcode="1" gcode="COUPON_STATUS"></s:Code2Name></span>
							</label>
							<label class="radio-inline">
								<input name="status"  type="radio" class="ace" value="2" <c:if test="${coupon.status=='2'}">checked="checked"</c:if>>
								<span class="lbl"><s:Code2Name mcode="2" gcode="COUPON_STATUS"></s:Code2Name></span>
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="coupon_detail" class="col-sm-3 control-label no-padding-right" >详情：</label>
					<div class="col-sm-4">
						<c:if test="${coupon.coupon_nums>0}">
							<input type="hidden" name="coupon_detail" value="${coupon.coupon_detail}"/>
							<textarea disabled="disabled" type="text" name="coupon_detail" placeholder="输入优惠详情"  class="form-control" maxlength="150">${coupon.coupon_detail}</textarea>
						</c:if>
						<c:if test="${coupon.coupon_nums==0}">
							<textarea type="text" name="coupon_detail" placeholder="输入优惠详情"  class="form-control" maxlength="150">${coupon.coupon_detail}</textarea>
						</c:if>
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