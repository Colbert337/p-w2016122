<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/advance/transfer_log.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							转账报表
						</h1>
					</div>
					
					<div class="search-types">
						<div class="item">
							<label>收款人/手机号码：</label>
							<input type="text" name="fullName" placeholder="收款人/手机号码"  maxlength="20" value="${transferAccount.fullName}"/>
						</div>
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>交易时间:</label>
								<input type="text" class="" name="startDate" value="${transferAccount.startDate}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="endDate" value="${transferAccount.endDate}" readonly="readonly"/>
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
						</div>
					</div>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					<div class="table-header">转账报表列表</div>

					<div>
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<%--<th class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>--%>
									<th onclick="orderBy(this,'order_number');commitForm();" id="order_number_order">订单编号</th>
									<%--<th onclick="orderBy(this,'deal_number');commitForm();" id="deal_number_order">交易流水号</th>--%>
									<th onclick="orderBy(this,'order_type');commitForm();" id="order_type_order">交易类型</th>
									<th onclick="orderBy(this,'full_name');commitForm();" id="full_name_order">收款人</th>
									<th onclick="orderBy(this,'mobile_phone');commitForm();" id="mobile_phone_order">手机号码</th>
									<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">转账金额</th>
									<th onclick="orderBy(this,'used');commitForm();" id="used_order">资金用途</th>
									<th onclick="orderBy(this,'cash_back');commitForm();" id="cash_back_order">返现金额</th>
									<th onclick="orderBy(this,'operator');commitForm();" id="operator_order">操作人</th>
									<th onclick="orderBy(this,'deal_date');commitForm();" id="deal_date_order"><i id="deal_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>交易时间</th>
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

									<td>${list.orderNumber}</td>
									<%--<td>${list.dealNumber}</td>--%>
									<%--<td>${list.orderType}</td>--%>
									<td><s:Code2Name mcode="${list.orderType}" gcode="ORDER_TYPE"></s:Code2Name></td>
									<td>${list.fullName}</td>
									<td>${list.mobilePhone}</td>
									<td>${list.cash}</td>
									<td>${list.used}</td>
									<td>${list.cashBack}</td>
									<td>${list.operator}</td>
									<td><fmt:formatDate value="${list.dealDate}" type="both"/></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>

					<%--分页start--%>
					<div class="row">
						<div class="col-sm-6">
							<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条|共 ${pageInfo.total} 条|共 ${pageInfo.pages} 页</div>
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
