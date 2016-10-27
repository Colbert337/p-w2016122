<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/coupon/manageCouponGroup.js"></script>
<!-- /section:basics/content.breadcrumbs -->
<div class="">
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<form class="form-horizontal"  id="formCouponGroup">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="page-header">
						<h1>
							优惠卷组管理
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
							<label>优惠组编号：</label>
							<input type="text" name="coupongroup_no" placeholder="输入优惠卷组编号"  maxlength="32" value="${couponGroup.coupongroup_no}"/>
						</div>
						<div class="item">
							<label>优惠组名称：</label>
							<input type="text" name="coupongroup_title" placeholder="输入优惠卷组名称"  maxlength="32" value="${couponGroup.coupongroup_title}"/>
						</div>
						<div class="item">
						<label>发放类型：</label>
							<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox"  <c:if test="${fn:contains(couponGroup.issued_type,'1')}">checked="checked"</c:if> name="issued_type" value="1"/><s:Code2Name mcode="1" gcode="ISSUED_TYPE"></s:Code2Name></label>
							<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox"  <c:if test="${fn:contains(couponGroup.issued_type,'2')}">checked="checked"</c:if> name="issued_type" value="2" /><s:Code2Name mcode="2" gcode="ISSUED_TYPE"></s:Code2Name></label>
							<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox"  <c:if test="${fn:contains(couponGroup.issued_type,'3')}">checked="checked"</c:if> name="issued_type" value="3" /><s:Code2Name mcode="3" gcode="ISSUED_TYPE"></s:Code2Name></label>
							<label class="checkbox inline font-size"  style="padding-left:25px"><input type="checkbox"  <c:if test="${fn:contains(couponGroup.issued_type,'4')}">checked="checked"</c:if> name="issued_type" value="4" /><s:Code2Name mcode="4" gcode="ISSUED_TYPE"></s:Code2Name></label>
						</div>
						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/coupon/addCouponGroup.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125">新建</i>
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125">查询</i>

							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								<i class="ace-icon fa fa-flask align-top bigger-125">重置</i>
							</button>
						</div>
					</div>
					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					<div class="table-header">优惠组详细信息列表</div>
					<!-- div.table-responsive -->
					<!-- div.dataTables_borderWrap -->
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<%--<th style="width:3%" class="center td-w1">--%>
										<%--<label class="pos-rel">--%>
											<%--<input type="checkbox" class="ace" id="pks" onclick="checkedAllRows(this);" />--%>
											<%--<span class="lbl"></span>--%>
										<%--</label>--%>
									<%--</th>--%>
									<th style="width:10%" onclick="orderBy(this,'coupongroup_no');commitForm();" id="coupongroup_no_order">优惠组编号</th>
									<th style="width:10%" onclick="orderBy(this,'coupongroup_title');commitForm();" id="coupongroup_title_order">优惠组名称</th>
									<th style="width:40%" onclick="orderBy(this,'issued_type');commitForm();" id="issued_type_order">发放类型</th>
									<th style="width:5%" class="text-center td-w3">更多操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
									<tr>
										<%--<td style="width:3%" class="center">--%>
											<%--<label class="pos-rel">--%>
												<%--<input type="checkbox" class="ace" name="checkbox_id"  value="${list.coupongroup_id}"/>--%>
												<%--<span class="lbl"></span>--%>
											<%--</label>--%>
										<%--</td>--%>
										<td>${list.coupongroup_no}</td>
										<td>${list.coupongroup_title}</td>
										<td>
									<c:set value="${ fn:split(list.issued_type, ',') }" var="issued_type" />
									<c:forEach var="item" items="${issued_type}" varStatus="status">
										<s:Code2Name mcode="${item}" gcode="ISSUED_TYPE"></s:Code2Name>
										<c:if test="${!status.last}">
											，
										</c:if>
									</c:forEach>
									</td>
										<td>
											<div class="text-center">
												<a class="option-btn-m" href="javascript:void(0);" title="导入优惠名单" data-rel="tooltip">
													<i class="ace-icon fa fa-cloud-download  bigger-130" onclick="importUserCouponGroup('${list.coupongroup_id}');"></i>
												</a>
												<a class="option-btn-m" href="javascript:void(0);" title="修改" data-rel="tooltip">
													<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate('${list.coupongroup_id}');"></i>
												</a>
												<a class="option-btn-m" href="javascript:deleteCoupon('${list.coupongroup_id}');" title="删除" data-rel="tooltip">
													<span class="ace-icon fa fa-trash-o bigger-130"></span>
												</a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
					</div>
					<div class="col-sm-6">
						<nav>
							<ul id="ulhandle" class="pagination pull-right no-margin">
								<li id="previous">
									<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formCoupon');">
										<span aria-hidden="true">上一页</span>
									</a>
								</li>
								<li id="next">
									<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formCoupon');">
										<span aria-hidden="true">下一页</span>
									</a>
								</li>
							</ul>
						</nav>
					</div>
				</div>
				<jsp:include page="/common/message.jsp"></jsp:include>
			<!-- PAGE CONTENT ENDS -->
			</form>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->


<div id="userCouponModel" class="modal fade" role="dialog" aria-labelledby="conponModalLabel" data-backdrop="static" tabindex="-1">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editBanner">享优惠卷人员列表</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid" id="content"></div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="closeDialog('userCouponModel')">关闭</button>
			</div>
		</div>
	<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>