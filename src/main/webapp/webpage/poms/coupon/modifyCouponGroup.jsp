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
	<style type="text/css">
	.input-group-btn  button.btn{
	padding: 2px 10px;
	}
	#dynamic-table   input.btn{
	padding: 0px 2px;
	}
	</style>
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
						<input type="text" name="coupongroup_title" placeholder="输入优惠卷组名称" class="form-control" maxlength="20" value="${couponGroup.coupongroup_title}" required/>
					</div>
				</div>
				<div class="form-group">
					<label for="couponGroup_detail" class="col-sm-3 control-label no-padding-right" >详情：</label>
					<div class="col-sm-4">
						<textarea type="text" name="coupongroup_detail" placeholder="输入优惠卷组详情"  class="form-control" maxlength="150">${couponGroup.coupongroup_detail}</textarea>
					</div>
				</div>
				<div class="form-group">
				<label for="coupon_ids" class="col-sm-3 control-label no-padding-right">优惠卷：</label>
				<div class="col-sm-4">
					<input type="hidden" name="coupon_ids"/>
					<%--<input type="hidden" name="coupon_nos"/>--%>
					<%--<div class="myOwnDdl">--%>
					<%--<select multiple="multiple" class="form-control" id="coupon_ids" name="coupon_ids">--%>
					<%--</select>--%>
					<%--</div>--%>
					<div class="sjny-table-responsive">
						<div id="dynamic-table_div">
							<table id="dynamic-table" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th style="text-align:center"><input type="checkbox" id="checkboxAll" /></th>
										<th id="coupon_title_order">优惠名称</th>
										<th id="coupon_kind_order">优惠类型</th>
										<th style="text-align:center">数量</th>
									</tr>
								</thead>
								<tbody id="coupon">
								</tbody>
						</table>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="issued_type" class="col-sm-3 control-label no-padding-right">发放类型：</label>
				<div class="col-sm-4" align="left" valign="middle">
					<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox" onclick="changeissuedtype()" name="issued_type" <c:if test="${fn:contains(couponGroup.issued_type,'1')}">checked="checked"</c:if> value="1"/><s:Code2Name mcode="1" gcode="ISSUED_TYPE"></s:Code2Name></label>
					<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox" onclick="changeissuedtype()" name="issued_type" <c:if test="${fn:contains(couponGroup.issued_type,'2')}">checked="checked"</c:if> value="2" /><s:Code2Name mcode="2" gcode="ISSUED_TYPE"></s:Code2Name></label>
					<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox" onclick="changeissuedtype()" name="issued_type" <c:if test="${fn:contains(couponGroup.issued_type,'3')}">checked="checked"</c:if> value="3" /><s:Code2Name mcode="3" gcode="ISSUED_TYPE"></s:Code2Name></label>
					<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox" onclick="changeissuedtype()" name="issued_type" <c:if test="${fn:contains(couponGroup.issued_type,'4')}">checked="checked"</c:if> value="4" /><s:Code2Name mcode="4" gcode="ISSUED_TYPE"></s:Code2Name></label>
					<br/>
					<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox" onclick="changeissuedtype()" name="issued_type" <c:if test="${fn:contains(couponGroup.issued_type,'5')}">checked="checked"</c:if> value="5" /><s:Code2Name mcode="5" gcode="ISSUED_TYPE"></s:Code2Name></label>
					<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox" onclick="changeissuedtype()" name="issued_type" <c:if test="${fn:contains(couponGroup.issued_type,'6')}">checked="checked"</c:if> value="6" /><s:Code2Name mcode="6" gcode="ISSUED_TYPE"></s:Code2Name></label>
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