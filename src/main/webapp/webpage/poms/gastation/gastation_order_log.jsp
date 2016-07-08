<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/gastation/gastation_order_log.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							预存款充值记录
						</h1>
					</div>
					
					<input type="hidden" class="" name="order_number" value="${order.order_number}" readonly="readonly"/>
					
					<div class="search-types">
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>交易时间:</label>
								<input type="text" class="" name="deal_date_after" value="${order.deal_date_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="deal_date_before" value="${order.deal_date_before}" readonly="readonly"/>
							</div>			
						</div>
						
						<div class="item">
						    <label>订单流水号：</label>
							<input type="text" name="deal_number" placeholder="输入订单流水号"  maxlength="20" value="${order.deal_number}"/>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								重置
							</button>
							<button class="btn btn-sm" type="button" onclick="returnpage();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								返回
							</button>
						</div>
					</div>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					<div class="table-header">预存款充值记录订单列表</div>

					<div>
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>
									<!-- <th onclick="orderBy(this,'order_id');commitForm();" id="order_id_order">订单编号</th> -->
									<th onclick="orderBy(this,'deal_number');commitForm();" id="deal_number_order">订单流水号</th>
									<th onclick="orderBy(this,'charge_type');commitForm();" id="charge_type_order">交易类型</th>
									<th onclick="orderBy(this,'operator');commitForm();" id="operator_order">操作员</th>
									<th onclick="orderBy(this,'run_success');commitForm();" id="run_success_order">交易结果</th>
									<th onclick="orderBy(this,'deal_date');commitForm();" id="deal_date_order"><i id="deal_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>交易时间</th>
									<th onclick="orderBy(this,'remark');commitForm();" id="remark_order">备注信息</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.order_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<%-- <td>${list.order_id}</td> --%>
									<td>${list.deal_number}</td>
								 	<td><s:Code2Name mcode="${list.order_type}" gcode="ORDER_TYPE"></s:Code2Name></td> 
									<td>${list.operator}</td>
									<td>${list.run_success}</td>
									<td><fmt:formatDate value="${list.deal_date}" type="both"/></td>
									<td>${list.remark}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
			

			<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条|共 ${pageInfo.total} 条|共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formcard');">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formcard');">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>  
						</ul>
					</nav>
				</div>
			</div>

			<jsp:include page="/common/message.jsp"></jsp:include>


			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>
