<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/coupon/manageCoupon.js"></script>
<!-- /section:basics/content.breadcrumbs -->
<div class="">
	<!-- /.page-header -->


	<div class="row">
		<div class="col-xs-12">
			<form class="form-horizontal"  id="formCoupon">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="page-header">
						<h1>
							优惠卷管理
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
							<label>优惠编号：</label>
							<input type="text" name="coupon_no" placeholder="输入优惠卷编号"  maxlength="32" value="${coupon.coupon_no}"/>
						</div>
						<div class="item">
							<label>优惠名称：</label>
							<input type="text" name="coupon_title" placeholder="输入优惠卷名称"  maxlength="32" value="${coupon.coupon_title}"/>
						</div>
						<%--<div class="item">--%>
						    <%--<label>优惠方式：</label>--%>
							<%--<select class="chosen-select" name="coupon_type" >--%>
								<%--<s:option flag="true" gcode="COUPON_TYPE" form="coupon" field="coupon_type" />--%>
							<%--</select>--%>
						<%--</div>--%>
						<div class="item">
						    <label>优惠类型：</label>
							<select class="chosen-select" name="coupon_kind" >
								<s:option flag="true" gcode="COUPON_KIND" form="coupon" field="coupon_kind" />
							</select>
						</div>
						<div class="item">
							<label>优惠状态：</label>
							<select class="chosen-select" name="status" >
								<s:option flag="true" gcode="COUPON_STATUS" form="coupon" field="status" />
							</select>
						</div>
						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/coupon/addCoupon.jsp');">
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
					<div class="table-header">优惠详细信息列表</div>
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
									<th style="width:10%" onclick="orderBy(this,'coupon_no');commitForm();" id="coupon_no_order">优惠编号</th>
									<th style="width:10%" onclick="orderBy(this,'coupon_title');commitForm();" id="coupon_title_order">优惠名称</th>
									<th style="width:30%"onclick="orderBy(this,'coupon_kind');commitForm();" id="coupon_kind_order">优惠类型</th>
									<%--<th style="text-align:center;width:10%" onclick="orderBy(this,'coupon_type');commitForm();" id="coupon_type_order">优惠方式</th>--%>
									<th style="width:10%" onclick="orderBy(this,'use_condition');commitForm();" id="use_condition_order">使用条件</th>
									<th style="text-align:center;width:10%" onclick="orderBy(this,'preferential_discount');commitForm();" id="preferential_discount_order">优惠额度</th>
									<th style="width:10%" onclick="orderBy(this,'coupon_detail');commitForm();" id="coupon_detail_order">优惠详情</th>
									<th style="text-align:center;width:10%" onclick="orderBy(this,'status');commitForm();" id="status_order">优惠状态</th>
									<th style="width:10%" onclick="orderBy(this,'statusinfo');commitForm();" id="statusinfo_order">优惠有效期</th>
									<th style="width:5%" class="text-center td-w3">更多操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
									<tr>
										<%--<td style="width:3%" class="center">--%>
											<%--<label class="pos-rel">--%>
												<%--<input type="checkbox" class="ace" name="checkbox_id"  value="${list.coupon_id}"/>--%>
												<%--<span class="lbl"></span>--%>
											<%--</label>--%>
										<%--</td>--%>
										<td>${list.coupon_no}</td>
										<td>${list.coupon_title}</td>
										<td style="display:none">${list.coupon_detail}</td>
										<td><c:if test="${list.coupon_kind=='1'}"><s:Code2Name mcode="1" gcode="COUPON_KIND"></s:Code2Name></c:if><c:if test="${list.coupon_kind=='2'}"><s:Code2Name mcode="2" gcode="COUPON_KIND"></s:Code2Name>，优惠气站：${list.gas_station_name}</c:if> </td>
										<%--<td style="text-align:center;"><s:Code2Name mcode="${list.coupon_type}" gcode="COUPON_TYPE"></s:Code2Name></td>--%>
										<td><c:if test="${list.use_condition=='1'}">满${list.limit_money}元使用</c:if><c:if test="${list.use_condition=='2'}">无条件使用</c:if></td>
										<td style="text-align:center;">${list.preferential_discount}<c:if test="${list.coupon_type=='1'}">元</c:if><c:if test="${list.coupon_type=='2'}">折</c:if></td>
										<td style="display:none;">${list.start_coupon_time}至${list.end_coupon_time}</td>
										<td title='${list.coupon_detail}'>
											<c:set var="subStr" value="${list.coupon_detail}"/>
											<c:choose>
												<c:when test="${fn:length(subStr) > 25}">
													<c:out value="${fn:substring(subStr, 0, 25)}..." />
												</c:when>
												<c:otherwise>
													<c:out value="${subStr}" />
												</c:otherwise>
											</c:choose>
										</td>
										<td style="text-align:center;"><s:Code2Name mcode="${list.status}" gcode="COUPON_STATUS"></s:Code2Name></td>
										<td>${list.statusinfo}</td>
										<td>
											<div class="text-center">

												<a class="option-btn-m" href="javascript:void(0);" title="查看详情" data-rel="tooltip">
													<i class="ace-icon fa fa-search-plus  bigger-130" onclick="showUserCoupon('${list.coupon_id}');"></i>
												</a>
												<c:if test="${list.status!='2'&&list.statusinfo!='已结束'}">
													<a class="option-btn-m" href="javascript:void(0);" title="导入优惠名单" data-rel="tooltip">
														<i class="ace-icon fa fa-cloud-download  bigger-130" onclick="importuserCoupon('${list.coupon_id}');"></i>
													</a>
												</c:if>
												<a class="option-btn-m" href="javascript:void(0);" title="修改" data-rel="tooltip">
													<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate('${list.coupon_id}');"></i>
												</a>
												<c:if test="${list.coupon_nums==0}">
													<a class="option-btn-m" href="javascript:deleteCoupon('${list.coupon_id}');" title="删除" data-rel="tooltip">
														<span class="ace-icon fa fa-trash-o bigger-130"></span>
													</a>
												</c:if>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">
							每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页
							&nbsp;&nbsp;转到第 <input type="text" name="convertPageNum" style="height:25px;width:45px" maxlength="4"/>  页
							<button type="button" class="btn btn-white btn-sm btn-primary" onclick="commitForm();">跳转</button>
						</div>
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
				<button class="btn btn-primary btn-sm"  data-dismiss="modal">关闭</button>
			</div>
		</div>
	<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>