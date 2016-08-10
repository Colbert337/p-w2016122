<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/advance/fleets_count_log.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation" action="<%=basePath%>/web/tcms/report/count/fleets/import">
		<input type="hidden" name="downloadreport" value="true"/>

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="page-header">
					<h1>
						车队消费汇总
					</h1>
				</div>

				<div class="search-types">
					<%--<div class="item">
						<label>运输公司编号：</label>
						<input type="text" name="stationId" placeholder="运输公司编号/运输公司名称" maxlength="20" value="${loger.stationId}"/>
					</div>--%>
					<div class="item">
						<div class="input-daterange top" id="j-input-daterange-top">
							<label>交易时间:</label>
							<input type="text" class="" name="startDate" value="${loger.startDate}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
							<input type="text" class="" name="endDate" value="${loger.endDate}" readonly="readonly"/>
						</div>
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

				<div class="table-header">车队消费汇总</div>

				<div>
					<div class="alert alert-info alert-mt">
						<span class="bigger-120">消费总金额：${totalCash}元</span>&emsp;<span class="bigger-120">冲红总金额：${chonghongCash}元</span>
					</div>
					<table id="dynamic-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<%--<th class="center">
								<label class="pos-rel">
									<input type="checkbox" class="ace" onclick="checkedAllRows(this);" />
									<span class="lbl"></span>
								</label>
							</th>--%>
							<%--<th onclick="orderBy(this,'sys_transportion_id');commitForm();" id="sys_transportion_id_order">运输公司编号</th>--%>
							<th onclick="orderBy(this,'tc_fleet_id');commitForm();" id="tc_fleet_id_order">车队编号</th>
							<th onclick="orderBy(this,'fleet_name');commitForm();" id="fleet_name_order">车队名称</th>
							<th onclick="orderBy(this,'channel');commitForm();" id="channel_order">加注站名称</th>
							<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">消费金额</th>
							<th onclick="orderBy(this,'hedgefund');commitForm();" id="hedgefund_order">冲红金额</th>
							<th onclick="orderBy(this,'summit');commitForm();" id="summit_order">消费量</th>
							<th onclick="orderBy(this,'consumecount');commitForm();" id="consumecount_order">消费次数</th>
						</tr>
						</thead>

						<tbody>

						<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
							<tr id="listobj">
								<%--<td class="center">
									<label class="pos-rel">
										<input type="checkbox" class="ace" id="pks" value="${list.order_id}"/>
										<span class="lbl"></span>
									</label>
								</td>--%>

								<%--<td>${list.sys_transportion_id}</td>--%>
								<td>${list.tc_fleet_id}</td>
								<td>${list.fleet_name}</td>
								<td>${list.channel}</td>
								<td>${list.cash}</td>
								<td>${list.hedgefund}</td>
								<td>${list.summit}</td>
								<td>${list.consumecount}</td>
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