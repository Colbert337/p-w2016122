<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/gastation/gastation_rechargereport.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation" action="<%=basePath%>/web/gastation/reChargeReport">
	<input type="hidden" name="downloadreport" value="true"/>

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							${page}充值报表
							<input type="hidden" name="page" value="${page}"/>
						</h1>
					</div>
					
					<div class="search-types">
						<div class="item">
						    <label>会员账号：</label>
							<input type="text" name="sysDriver.userName" placeholder="请输入会员账号"  maxlength="20" value="${sysOrder.sysDriver.userName}"/>
						</div>
						<div class="item">
							<label>订单编号：</label>
							<input type="text" name="orderNumber" placeholder="订单编号/交易流水号"  maxlength="20" value="${sysOrder.orderNumber}"/>
						</div>
						<div class="item">
							<label>加注站编号：</label>
							<input type="text" name="channelNumber" placeholder="请输入加注站编号" maxlength="10" value="${sysOrder.channelNumber}"/>
						</div>
						<div class="item">
							<label>交易类型：</label>
							<select id="is_discharge" name="is_discharge">
								<option value="">全部</option>
								<option value="0">消费</option>
								<option value="1">冲红</option>
							</select>
						</div>	
						<%-- <div class="item">
							<label>支付方式：</label>
							<select name="chargeType">
								<s:option gcode="CHARGE_TYPE" flag="true" form="sysOrder" field="chargeType"></s:option>
							</select>
						</div>	 --%>
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>交易时间:</label>
								<input type="text" class="" name="startDate" value="${sysOrder.startDate}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="endDate" value="${sysOrder.endDate}" readonly="readonly"/>
							</div>			
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
							<span class="bigger-120">充值总金额：${totalCash}元</span>
						</div>
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
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
									<th onclick="orderBy(this,'deal_type');commitForm();" id="deal_type_order">订单类型</th>
									<th onclick="orderBy(this,'channel_number');commitForm();" id="channel_number_order">加注站编号</th>
									<th onclick="orderBy(this,'channel');commitForm();" id="channel_order">加注站名称</th>
									<th onclick="orderBy(this,'full_name');commitForm();" id="full_name_order">客户姓名</th>
									<th onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">会员账号</th>
									<th onclick="orderBy(this,'charge_type');commitForm();" id="charge_type_order">支付方式</th>
									<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">充值金额</th>
									<!-- <th onclick="orderBy(this,'cash_back_per');commitForm();" id="cash_back_per_order">返现系数</th> -->
									<th onclick="orderBy(this,'cash_back');commitForm();" id="cash_back_order">返现金额</th>
									<th onclick="orderBy(this,'operator');commitForm();" id="operator_order">操作人</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj" <c:if test="${list.is_discharge == 1}">style="color: #A60000;" </c:if> >
									<td class="center">
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
										<c:if test="${list.is_discharge == 0}">充值</c:if>
										<c:if test="${list.is_discharge == 1}">冲红</c:if>
									</td>
									<td><s:Code2Name mcode="${list.deal_type}" gcode="ORDER_DEAL_TYPE"></s:Code2Name></td>
									<td>${list.channel_number}</td>
									<td>${list.channel}</td>
									<td>${list.full_name}</td>
									<td>${list.user_name}</td>
									<td><s:Code2Name mcode="${list.charge_type}" gcode="CASHBACK"></s:Code2Name></td>
									<td>${list.cash}</td>
									<%-- <td>${list.cash_back_per}</td> --%>
									<td>${list.cash_back}</td>
									<td>${list.operator}</td>
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

<script type="text/javascript">
	var is_discharge = '${sysOrder.is_discharge}';
	$("#is_discharge").find("option[value='"+is_discharge+"']").attr("selected",true);
</script>