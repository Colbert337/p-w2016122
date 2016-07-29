<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/advance/fleets_log.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation" action="<%=basePath%>/web/tcms/fleetQuota/list/report/fleets/import">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							车队消费
						</h1>
					</div>
					
					<div class="search-types">
						<div class="item">
							<label>交易类型：</label>
							<select id="is_discharge" name="is_discharge"  maxlength="20">
								<option value="">全部</option>
								<option value="0">消费</option>
								<option value="1">冲红</option>
							</select>
						</div>
						<div class="item">
							<label>车队名称/车牌号：</label>
							<input type="text" name="orderNumber" placeholder="车队名称/车牌号"  maxlength="20" value="${order.orderNumber}"/>
						</div>
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>交易时间:</label>
								<input type="text" class="" name="startDate" value="${orderNumber.startDate}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="endDate" value="${orderNumber.endDate}" readonly="readonly"/>
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
							<span class="bigger-120">消费总金额：${totalCash}元</span>
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
									<th onclick="orderBy(this,'order_number');commitForm();" id="order_number_order">订单编号</th>
									<th onclick="orderBy(this,'order_type');commitForm();" id="order_type_order">订单类型</th>
									<th>交易类型</th>
									<%--<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">交易金额</th>--%>
									<th onclick="orderBy(this,'fleet_name');commitForm();" id="fleet_name_order">车队名称</th>
									<th onclick="orderBy(this,'plates_number');commitForm();" id="plates_number_order">车牌号</th>
									<th onclick="orderBy(this,'gas_station_name');commitForm();" id="gas_station_name_order">加注站名称</th>
									<th onclick="orderBy(this,'goods_type');commitForm();" id="goods_type_order">商品名称</th>
									<th onclick="orderBy(this,'price');commitForm();" id="price_order">结算单价</th>
									<th onclick="orderBy(this,'number');commitForm();" id="number_order">消费数量</th>
									<th onclick="orderBy(this,'sum_price');commitForm();" id="sum_price_order">消费金额</th>
									<th onclick="orderBy(this,'order_date');commitForm();" id="order_date_order"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>交易时间</th>
									<th>备注</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj" <c:if test="${list.is_discharge == 1}"> style="color: #A60000;" </c:if> >
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
									<td>
										<c:if test="${list.is_discharge == 0}">消费</c:if>
										<c:if test="${list.is_discharge == 1}">冲红</c:if>
									</td>
									<%--<td>${list.cash}</td>--%>
									<td>
										<c:if test="${list.fleet_name == '' || list.fleet_name == null}">其他</c:if>
										<c:if test="${list.fleet_name != ''}">${list.fleet_name}</c:if>
									</td>
									<td>${list.plates_number}</td>
									<td>${list.gas_station_name}</td>
									<td><s:Code2Name mcode="${list.goods_type}" gcode="CARDTYPE"></s:Code2Name></td>
									<td>${list.price}</td>
									<td>${list.number}</td>
									<td>${list.sum_price}</td>
									<td><fmt:formatDate value="${list.deal_date}" type="both"/></td>
									<td>${list.remark}</td>
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
	$(function(){
		/*消费类型*/
		var is_discharge = '${order.is_discharge}';
		/*alert("is_discharge:"+is_discharge);*/
		if(is_discharge == ""){
			$("#is_discharge").val("");
		}else if(is_discharge == "0"){
			$("#is_discharge").val("0");
		}else if(is_discharge == "1"){
			$("#is_discharge").val("1");
		}
	})
</script>