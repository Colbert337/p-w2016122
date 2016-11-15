<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/transportion/transportion_rechargereport.js"></script>
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
	<form id="formgastation" action="<%=basePath%>/web/transportion/consumeReport">
	<input type="hidden" name="downloadreport" value="true"/>

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							运输公司消费报表
						</h1>
					</div>
					
					<div class="search-types">
						<div class="item">
						    <label>运输公司编号：</label>
							<input type="text" name="creditAccount" placeholder="请输入运输公司编号"  maxlength="20" value="${sysOrder.creditAccount}"/>
						</div>
						<div class="item">
							<label>订单编号：</label>
							<input type="text" name="orderNumber" placeholder="订单编号/交易流水号"  maxlength="20" value="${sysOrder.orderNumber}"/>
						</div>
						<div class="item">
							<label>交易类型：</label>
							<select id="is_discharge" name="is_discharge" maxlength="20">
								<option value="">全部</option>
								<option value="0">消费</option>
								<option value="1">冲红</option>
							</select>
						</div>
						<div class="item">
							<label>车牌号：</label>
							<input type="text" name="sysDriver.plateNumber" placeholder="车牌号"  maxlength="20" value="${sysOrder.sysDriver.plateNumber}"/>
						</div>
						<div class="item">

								<label>交易时间:</label>
								<input type="text"  style="width:140px;" id="startDate" name="startDate" value="${sysOrder.startDate}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" style="width:140px;" id="endDate" name="endDate" value="${sysOrder.endDate}" readonly="readonly"/>

						</div>
						
						<%--<div class="item">
						    <label>充值渠道：</label>
							<input type="text" name="deal_number" placeholder="充值渠道"  maxlength="20" value="${order.channel}"/>
						</div>--%>

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
					
					<div class="table-header">个人报表列表</div>

					<div>
						<div class="alert alert-info alert-mt">
							<span class="bigger-120">消费总金额：${totalCash}元</span>
						</div>
						<div class="sjny-table-responsive">
							<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center" style="display:none">
										<label class="pos-rel">
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>
									<th onclick="orderBy(this,'order_number');commitForm();" id="order_number_order">订单编号</th>
									<th onclick="orderBy(this,'order_type');commitForm();" id="order_type_order">订单类型</th>
									<th onclick="orderBy(this,'deal_number');commitForm();" id="deal_number_order">交易流水号</th>
									<th onclick="orderBy(this,'order_date');commitForm();" id="order_date_order"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>交易时间</th>
									<th onclick="orderBy(this,'is_discharge');commitForm();" id="is_discharge_order">交易类型</th>
									<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">实收金额</th>
									<th onclick="orderBy(this,'should_payment');commitForm();" id="should_payment_order">订单金额</th>
									<th onclick="orderBy(this,'spend_type');commitForm();" id="spend_type_order">支付方式</th>
									<th onclick="orderBy(this,'credit_account');commitForm();" id="credit_account_order">运输公司编号</th>
									<th onclick="orderBy(this,'transportion_name');commitForm();" id="transportion_name_order">运输公司名称</th>
									<th onclick="orderBy(this,'channel');commitForm();" id="channel_order">加注站名称</th>
									<th onclick="orderBy(this,'plates_number');commitForm();" id="plates_number_order">车牌号</th>
									<th onclick="orderBy(this,'remark');commitForm();" id="remark_order">备注</th>
									<th onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">操作人</th>
									<th>商品详情</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj" <c:if test="${list.is_discharge == 1}">style="color: #A60000;" </c:if> >
									<td class="center" style="display:none">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.order_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.order_number}</td>
									<td><s:Code2Name mcode="${list.order_type}" gcode="ORDER_TYPE"></s:Code2Name></td>
									<td>${list.deal_number}</td>
									<td><fmt:formatDate value="${list.order_date}" type="both"/></td>
									<td>
										<c:if test="${list.is_discharge == 0}">消费</c:if>
										<c:if test="${list.is_discharge == 1}">冲红</c:if>
									</td>
									<td>${list.cash}</td>
									<td>${list.should_payment}</td>
									<td><s:Code2Name mcode="${list.spend_type}" gcode="SPEND_TYPE"></s:Code2Name></td>
									<td>${list.creditAccount}</td>
									<td>${list.transportion_name}</td>
									<td>${list.channel}</td>
									<td>${list.plates_number}</td>
									<td>${list.remark}</td>
									<td>${list.user_name}</td>
									<td class="text-center">
										<a class="" href="javascript:void(0);" title="查看明细" data-rel="tooltip">
											<i class="ace-icon fa fa-search-plus bigger-130" onclick="showDetail('${list.order_id}','${list.order_type}','${list.cash}',
												'${sysOrder.creditAccount}',
													'${sysOrder.orderNumber}',
													'${sysOrder.is_discharge}',
													'${sysOrder.sysDriver.plateNumber}',
													'${sysOrder.startDate}',
													'${sysOrder.endDate}',
													'${pageInfo.pageNum}',
													'${pageInfo.pageSize}',
													'${pageInfo.orderBy}');"></i>
										</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						</div>
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

<script type="text/javascript">
	var is_discharge = '${sysOrder.is_discharge}';
	$("#is_discharge").find("option[value='"+is_discharge+"']").attr("selected",true);
</script>