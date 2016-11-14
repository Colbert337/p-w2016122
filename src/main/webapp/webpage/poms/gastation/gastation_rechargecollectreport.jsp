<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/gastation/gastation_rechargecollectreport.js"></script>
<script type="text/javascript"
		src="<%=basePath%>/assets/js/global/jedate.js"></script>
<script type="text/javascript">

	jeDate({
		dateCell : "#startDate",
		format : "YYYY-MM-DD hh:mm:ss",
		isTime : true,
		festival : true,
		minDate : "2014-09-19 00:00:00"
	})
	jeDate({
		dateCell : "#endDate",
		format : "YYYY-MM-DD hh:mm:ss",
		isTime : true,
		festival : true,
		minDate : "2014-09-19 00:00:00"
	})

</script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation" action="<%=basePath%>/web/gastation/gastationRechargeReport/import">
	<input type="hidden" name="downloadreport" value="true"/>

	<jsp:include page="/common/page_param.jsp"></jsp:include>	

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							加注站充值汇总
						</h1>
					</div>
					
					<div class="search-types">
						<c:if test="${sessionScope.currUser.user.userType == 5}">
							<div class="item">
								<label>加注站编号：</label>
								<input type="text" name="channelNumber" placeholder="请输入加注站编号" maxlength="10" value="${sysOrder.channelNumber}"/>
							</div>
						</c:if>
						<div class="item">

								<label>交易时间:</label>
								<input type="text" style="width:140px;" name="startDate" id="startDate" value="${order.startDate}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" style="width:140px;" name="endDate" id="endDate" value="${order.endDate}" readonly="readonly"/>

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
							<div class="item"></div>
							<button class="btn btn-sm btn-primary" type="button" onclick="importReport()">导出报表</button>
						</div>
					</div>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					<div class="table-header">加注站充值汇总</div>

					<div>
						<div class="alert alert-info alert-mt">
							<span class="bigger-120">充值总金额：${totalCash}元</span>
						</div>
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center" style="display:none">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>
									<th onclick="orderBy(this,'sys_gas_station_id');commitForm();" id="sys_gas_station_id_order">加注站编号</th>
									<th onclick="orderBy(this,'gas_station_name');commitForm();" id="gas_station_name_order">加注站名称</th>
									<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">充值金额(包含冲红)</th>
									<th onclick="orderBy(this,'hedgefund');commitForm();" id="hedgefund_order">冲红金额</th>
 									<th onclick="orderBy(this,'operations_name');commitForm();" id="operations_name_order">运营人员</th>
 									<th onclick="orderBy(this,'salesmen_name');commitForm();" id="salesmen_name_order">销售人员</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center" style="display:none">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.sys_gas_station_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.sys_gas_station_id}</td>
									<td>${list.gas_station_name}</td>
									<td>${list.cash}</td>
									<td>${list.hedgefund}</td>
									<td>${list.operations_name}</td>
									<td>${list.salesmen_name}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>

					<%--分页start--%>
					<div class="row">
						<div class="col-sm-6">
							<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
						</div>
						<div class="col-sm-6">
							<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
								<ul id="ulhandle" class="pagination">
									<li id="previous" class="paginate_button previous" aria-controls="dynamic-table" tabindex="0">
										<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formgastation');">
											<span aria-hidden="true">上一页</span>
										</a>
									</li>
									<li id="next" class="paginate_button next" aria-controls="dynamic-table" tabindex="0">
										<a id="nexthandle" href="javascript:nextpage('#formgastation');" aria-label="Next" >
											<span aria-hidden="true">下一页</span>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<%--分页 end--%>
					<jsp:include page="/common/message.jsp"></jsp:include>


			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>