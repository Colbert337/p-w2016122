<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/coupon/manageCouponUser.js"></script>
<!-- /section:basics/content.breadcrumbs -->
<div class="">
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<form class="form-horizontal"  id="userCoupon">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="page-header">
						<h1>
							优惠人员管理
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
							<label>会员账号/手机号码：</label>
							<input type="text" name="full_name" placeholder="会员账号/手机号码"  maxlength="32" value="${userCoupon.full_name}"/>
						</div>
						<div class="item">
							<label>优惠编号：</label>
							<input type="text" name="coupon_no" placeholder="优惠卷编号"  maxlength="32" value="${userCoupon.coupon_no}"/>
						</div>
						<div class="item">
							<label>优惠名称：</label>
							<input type="text" name="coupon_title" placeholder="优惠卷名称"  maxlength="32" value="${userCoupon.coupon_title}"/>
						</div>
						<div class="item">
							<label>优惠类型：</label>
							<select class="chosen-select" name="coupon_kind" >
								<s:option flag="true" gcode="COUPON_KIND" form="userCoupon" field="coupon_kind" />
							</select>
						</div>
						<div class="item">
							<label>优惠卷使用状态：</label>
							<select class="chosen-select" name="isuse" >
							<s:option flag="true" gcode="COUPON_ISUSE" form="userCoupon" field="isuse" />
							</select>
						</div>
						<div class="item">
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
					<div class="table-header">优惠人员详细信息列表</div>
					<!-- div.table-responsive -->
					<!-- div.dataTables_borderWrap -->
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th style="width:8%" onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">会员账号</th>
									<th style="width:8%" onclick="orderBy(this,'full_name');commitForm();" id="full_name_order">认证姓名</th>
									<th style="width:8%" onclick="orderBy(this,'station_id');commitForm();" id="station_id_order">注册工作站编号</th>
									<th style="width:15%" onclick="orderBy(this,'regis_source');commitForm();" id="regis_source_order">注册工作站名称</th>
									<th style="width:8%" onclick="orderBy(this,'coupon_no');commitForm();" id="coupon_no_order">优惠编号</th>
									<th style="width:8%" onclick="orderBy(this,'coupon_title');commitForm();" id="coupon_title_order">优惠名称</th>
									<th style="width:15%"onclick="orderBy(this,'coupon_kind');commitForm();" id="coupon_kind_order">优惠类型</th>
									<th style="text-align:center;width:8%" onclick="orderBy(this,'isuse');commitForm();" id="isuse_order">优惠状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
									<tr>
										<td>${list.user_name}</td>
										<td>${list.full_name}</td>
										<td>${list.station_id}</td>
										<td>${list.regis_source}</td>
										<td>${list.coupon_no}</td>
										<td>${list.coupon_title}</td>
										<td><c:if test="${list.coupon_kind=='1'}"><s:Code2Name mcode="1" gcode="COUPON_KIND"></s:Code2Name></c:if><c:if test="${list.coupon_kind=='2'}"><s:Code2Name mcode="2" gcode="COUPON_KIND"></s:Code2Name>，优惠气站：${list.gas_station_name}</c:if> </td>
										<td style="text-align:center;"><s:Code2Name mcode="${list.isuse}" gcode="COUPON_ISUSE"></s:Code2Name></td>
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
									<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#userCoupon');">
										<span aria-hidden="true">上一页</span>
									</a>
								</li>
								<li id="next">
									<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#userCoupon');">
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