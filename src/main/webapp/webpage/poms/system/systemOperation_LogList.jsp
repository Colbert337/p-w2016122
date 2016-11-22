<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/sysparam/systemOperation_LogList.js"></script>
<!-- /section:basics/content.breadcrumbs -->
<div class="">
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<form class="form-horizontal"  id="sysOperationLog">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="page-header">
						<h1>
							系统日志查询
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
							<label>用户类型：</label>
							<select class="chosen-select" name="log_platform" >
								<s:option flag="true" gcode="LOG_PLATFORM" form="sysOperationLog" field="log_platform" />
							</select>
						</div>
						<div class="item">
							<label>操作类型：</label>
							<select class="chosen-select" name="operation_type" >
								<s:option flag="true" gcode="OPERATION_TYPE" form="sysOperationLog" field="operation_type" />
							</select>
						</div>
						<div class="item">
							<label>日志内容：</label>
							<input type="text" name="log_content" placeholder="优惠卷名称"  maxlength="32" value="${sysOperationLog.log_content}"/>
						</div>
						<div class="item">
							<label>时间：</label>
								<input type="text" class="date-picker" name="created_date_after" id="created_date_after" style="width:150px" value="${sysOperationLog.created_date_after}"  readonly="readonly" data-date-format="yyyy-mm-dd"/>
									<span class="">
										<i class="fa fa-exchange"></i>
									</span>
								<input type="text" class="date-picker" name="created_date_before" id="created_date_before"  style="width:150px" value="${sysOperationLog.created_date_before}" readonly="readonly" data-date-format="yyyy-mm-dd"/>
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
					<div class="table-header">系统日志信息列表</div>
					<!-- div.table-responsive -->
					<!-- div.dataTables_borderWrap -->
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th style="width:8%" onclick="orderBy(this,'log_platform');commitForm();" id="log_platform_order">用户类型</th>
									<th style="width:8%" onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">操作人</th>
									<th style="width:8%" onclick="orderBy(this,'operation_type');commitForm();" id="operation_type_order">操作类型</th>
									<th style="width:15%" onclick="orderBy(this,'log_content');commitForm();" id="log_content_order">日志内容</th>
									<th style="width:8%" onclick="orderBy(this,'created_date');commitForm();" class="td-w2" id="created_date_order"><i id="created_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480">创建时间</i></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
									<tr>
										<td><s:Code2Name mcode="${list.log_platform}" gcode="LOG_PLATFORM"></s:Code2Name></td>
										<td>${list.user_name}</td>
										<td><s:Code2Name mcode="${list.operation_type}" gcode="OPERATION_TYPE"></s:Code2Name></td>
										<td>${list.log_content}</td>
										<td>${list.createdDateStr}</td>
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
									<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#sysOperationLog');">
										<span aria-hidden="true">上一页</span>
									</a>
								</li>
								<li id="next">
									<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#sysOperationLog');">
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