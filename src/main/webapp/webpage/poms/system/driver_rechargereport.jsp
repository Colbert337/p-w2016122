<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript"
		src="<%=basePath%>/assets/js/global/jedate.js"></script>

<script src="<%=basePath %>/dist/js/sysparam/driver_rechargereport.js"></script>
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
	<form id="formgastation" action="<%=basePath%>/web/driver/consumeReport">
	<input type="hidden" name="downloadreport" value="true"/>
	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							个人司机消费报表
						</h1>
					</div>
					
					<div class="search-types">
						<div class="item">
						    <label>会员账号：</label>
							<input type="text" name="sysDriver.userName" placeholder="请输入会员账号"  maxlength="20" value="${sysOrder.sysDriver.userName}"/>
						</div>
						<div class="item">
						    <label>手机号：</label>
							<input type="text" name="sysDriver.mobilePhone" placeholder="请输入手机号"  maxlength="20" value="${sysOrder.sysDriver.mobilePhone}"/>
						</div>
						<div class="item">
							<label>订单编号：</label>
							<input type="text" name="orderNumber" placeholder="请输入订单编号/订单流水号"  maxlength="20" value="${sysOrder.orderNumber}"/>
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
								<label   >交易时间:</label>
									<span>
								<input type="text" style="width:140px;"   name="startDate" id="startDate" value="${sysOrder.startDate}" readonly/>

									<i class="fa fa-exchange"></i>

								<input type="text" style="width:140px;"  name="endDate"  id="endDate" value="${sysOrder.endDate}" readonly/>
									</span>
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
									<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">实收金额</th>
									<th onclick="orderBy(this,'should_payment');commitForm();" id="should_payment_order">订单金额</th>
									<th onclick="orderBy(this,'spend_type');commitForm();" id="spend_type_order">支付方式</th>
									<th onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">会员账号</th>
									<th onclick="orderBy(this,'mobile_phone');commitForm();" id="mobile_phone_order">电话号码 </th>
									<th onclick="orderBy(this,'channel_number');commitForm();" id="channel_number_order">加注站编号</th>
									<th onclick="orderBy(this,'channel');commitForm();" id="channel_order">加注站名称</th>
									<th onclick="orderBy(this,'transportion_name');commitForm();" id="transportion_name_order">关联运输公司</th>
									<th onclick="orderBy(this,'remark');commitForm();" id="remark_order">备注</th>
									<th onclick="orderBy(this,'operator');commitForm();" id="operator_order">操作人</th>
									<th>商品详情</th>
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
										<c:if test="${list.is_discharge == 0}">消费</c:if>
										<c:if test="${list.is_discharge == 1}">冲红</c:if>
									</td>
									<td>${list.cash}</td>
									<td>${list.should_payment}</td>
									<td><s:Code2Name mcode="${list.spend_type}" gcode="SPEND_TYPE"></s:Code2Name></td>
									<td>${list.user_name}</td>
									<td>${list.mobile_phone}</td>
									<td>${list.channel_number}</td>
									<td>${list.channel}</td>
									<td>${list.transportion_name}</td>
									<td>${list.remark}</td>
									<td>${list.operator}</td>
									<td class="text-center">
										<a class="" href="javascript:void(0);" title="查看明细" data-rel="tooltip">
											<i class="ace-icon fa fa-search-plus bigger-130" onclick="showDetail('${list.order_id}','${list.order_type}'
													,'${list.cash}','${sysOrder.sysDriver.userName}'
													,'${sysOrder.sysDriver.mobilePhone}'
													,'${sysOrder.orderNumber}'
													,'${sysOrder.is_discharge}'
													,'${sysOrder.startDate}'
													,'${sysOrder.endDate}',
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
							<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">
								每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页
								&nbsp;&nbsp;转到第 <input type="text" name="convertPageNum" style="height:25px;width:45px" maxlength="4"/>  页
								<button type="button" class="btn btn-white btn-sm btn-primary" onclick="commitForm();">跳转</button>
							</div>
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