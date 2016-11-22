<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script src="<%=basePath %>/dist/js/coupon/importUserCouponGroup.js"></script>
	<div class="">
		<!-- /section:settings.box -->
		<div class="page-header">
			<h1>
				导入优惠卷组名单
			</h1>
		</div><!-- /.page-header -->
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<form class="form-horizontal" id="importForm" enctype="multipart/form-data">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="shenhe-items-hd">优惠卷组信息</div>
					<table class="table table-striped">
						<tbody>
							<tr>
								<th width="15%" style="text-align:right">优惠卷组编号：</th>
								<td style="text-align:left">
									<span id="coupon_no">${couponGroup.coupongroup_no}</span>
								</td>
								<th width="15%" style="text-align:right">优惠卷组名称：</th>
								<td style="text-align:left">
									<input type="hidden" value="${couponGroup.coupongroup_id}" name="coupongroup_id"/>
									<input type="hidden" value="${couponGroup.coupon_ids}" name="coupon_ids"/>
									<input type="hidden" value="${couponGroup.coupon_nums}" name="coupon_nums"/>
									<span id="coupon_title">${couponGroup.coupongroup_title}</span>
								</td>
							</tr>
							<tr>
								<th width="15%" style="text-align:right">优惠卷组详情：</th>
								<td colspan="3" style="text-align:left">
									<span id="coupongroup_detail">
										${couponGroup.coupongroup_detail}
									</span>
								</td>
							</tr>
							<tr>
								<th width="15%" style="text-align:right">发放类型：</th>
								<td colspan="3" style="text-align:left">
									<span id="issued_type">
										<c:set value="${ fn:split(couponGroup.issued_type, ',') }" var="issued_type" />
										<c:forEach var="item" items="${issued_type}" varStatus="status">
											<s:Code2Name mcode="${item}" gcode="ISSUED_TYPE"></s:Code2Name>
											<c:if test="${!status.last}">
												，
											</c:if>
										</c:forEach>
									</span>
								</td>
							</tr>
							<tr id="import">
								<th width="15%" style="text-align:right">导入excel：</th>
								<td style="text-align:left">
									<input type="file"  id="file_import" name="fileImport" onchange="fileFormat()"/>
								</td>
								<td colspan="2" style="text-align:center">
									<a class="btn btn-sm btn-primary" href="<%=basePath %>/docs/template/userCoupon_temp.xls">下载模板</a>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="clearfix form-actions" id="importAction">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="button" onclick="importUserCouponGroup();">
								<i class="ace-icon fa fa-check bigger-110">导入</i>
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn btn-success" type="button" onclick="returnpage();">
								<i class="ace-icon fa fa-undo bigger-110">返回</i>
						</button>
						</div>
					</div>
					<div id="userCouponList" style="display:none">
						<p class="text-warning" id="info"></p>
						<p class="text-info">列表中的人员可以获得优惠卷，“点击保存”获得优惠卷，“点击取消”重新导入人员名单！<input type="hidden" name="sysDriverIds"/></p>
						<div class="sjny-table-responsive">
							<div id="dynamic-table_div">
								<table id="dynamic-table" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th id="user_name_order">会员账号</th>
											<th id="full_name_order">认证姓名</th>
											<th id="plate_number_order">车牌号</th>
											<th id="identity_card_order">身份证号</th>
											<th id="fuel_type_order">燃料类型</th>
											<th id="identity_card_order">注册工作站编号</th>
											<th id="fuel_type_order">注册工作站名称</th>
										</tr>
									</thead>
									<tbody id="driver">
									</tbody>
								</table>
							</div>
						</div>
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" id="importuserConpon"  type="button" onclick="saveUserCouponGroup();">
									<i class="ace-icon fa fa-check bigger-110">保存</i>
								</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn btn-success" type="button" onclick="cancel();">
									<i class="ace-icon fa fa-undo bigger-110">取消</i>
								</button>
							</div>
						</div>
					</div>
				</form>
		<jsp:include page="/common/message.jsp"></jsp:include>
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->


