<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script src="<%=basePath %>/dist/js/integral/manageIntegralHistory.js"></script>
<!-- /section:basics/content.breadcrumbs -->
<div class="">
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<form class="form-horizontal"  id="integralHistoryform">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="page-header">
						<h1>
							积分历史查询
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
							<label>积分类型：</label>
							<select class="chosen-select" name="integral_type">
								<s:option flag="true" gcode="INTEGRAL_TYPE" form="integralHistory" field="integral_type" />
							</select>
						</div>
						<div class="item">
							<label>会员账号/手机号码：</label>
							<input type="text" name="full_name" placeholder="会员账号/手机号码"  maxlength="32" value="${integralHistory.full_name}"/>
						</div>
						<div class="item">
							<label>积分数量：</label>
								<input type="text" name="integral_num_less" class="number" style="width:60px" size="4" value="${integralHistory.integral_num_less}"/>
									<span class="">
										<i class="fa fa-exchange"></i>
									</span>
							<input type="text" name="integral_num_more" class="number" style="width:60px" size="4" value="${integralHistory.integral_num_more}"/>
						</div>
						<div class="item">
							<label>时间：</label>
							<input type="text" class="date-picker" name="created_date_after" id="created_date_after" style="width:150px" value="${integralHistory.created_date_after}"  readonly="readonly" data-date-format="yyyy-mm-dd"/>
							<span class="">
								<i class="fa fa-exchange"></i>
							</span>
							<input type="text" class="date-picker" name="created_date_before" id="created_date_before"  style="width:150px" value="${integralHistory.created_date_before}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
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
					<div class="table-header">积分历史查询列表</div>
					<!-- div.table-responsive -->
					<!-- div.dataTables_borderWrap -->
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th style="width:8%" onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">会员账号</th>
									<th style="width:8%" onclick="orderBy(this,'full_name');commitForm();" id="full_name_order">认证姓名</th>
									<th style="width:10%" onclick="orderBy(this,'station_id');commitForm();" id="station_id_order">注册工作站编号</th>
									<th style="width:15%" onclick="orderBy(this,'regis_source');commitForm();" id="regis_source_order">注册工作站名称</th>
									<th style="width:10%" onclick="orderBy(this,'integral_type');commitForm();" id="integral_type_order">积分类别</th>
									<th style="width:10%;text-align:center;" onclick="orderBy(this,'integral_num');commitForm();" id="integral_num_order">积分奖励</th>
									<th style="width:10%;text-align:center;" onclick="orderBy(this,'integral_total');commitForm();" id="integral_total_order">积分总数</th>
									<th style="width:8%" onclick="orderBy(this,'create_time');commitForm();" class="td-w2" id="create_time_order"><i id="created_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480">创建时间</i></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
									<tr>
									    <td>${list.user_name}</td>
										<td>${list.full_name}</td>
										<td>${list.station_id}</td>
										<td>${list.regis_source}</td>
										<td><s:Code2Name mcode="${list.integral_type}" gcode="INTEGRAL_TYPE"></s:Code2Name></td>
										<td style="text-align:center;">${list.integral_num}</td>
										<td style="text-align:center;">${list.integral_total}</td>
										<td>${list.createdTimeStr}</td>
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
									<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#integralHistoryform');">
										<span aria-hidden="true">上一页</span>
									</a>
								</li>
								<li id="next">
									<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#integralHistoryform');">
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