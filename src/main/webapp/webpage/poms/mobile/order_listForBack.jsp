<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	String imagePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>


<link rel="stylesheet" href="<%=basePath%>/dist/js/message/show.css">
<script src="<%=basePath%>/dist/js/message/show.js"></script>
<div class="" id="div">
	<!-- /.page-header -->
	<button class="btn" onclick="loadPage('#main', '../web/order/list/page');">返回</button>
	<form id="formRoad">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="page-header">
					<h1>订单退款</h1>
				</div>

				<div class="search-types">
					  </div>

				<div class="clearfix">
					<div class="pull-right tableTools-container"></div>
				</div>

				<div class="table-header">APP信息发送列表</div>

				<!-- div.table-responsive -->

				<!-- div.dataTables_borderWrap -->
				<div class="sjny-table-responsive">
					<table id="dynamic-table"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th onclick="orderBy(this,'order_number');commitForm();"
									id="order_number_order">订单编号</th>
								<th onclick="orderBy(this,'order_type');commitForm();"
									id="order_type_order">订单类型</th>
								<!-- 	<th onclick="orderBy(this,'img_path');commitForm();"
											id="threshold_max_value_order">缩略图</th> -->
								<th >金额</th>
								<th>用户电话号码</th>
								<th>消费者电话号码</th>
								<th onclick="orderBy(this,'order_date');commitForm();"
									id="order_date_order"><i
									class="ace-icon fa fa-clock-o bigger-110 hidden-480">
									</i>创建日期</th>
								<th onclick="orderBy(this,'charge_type');commitForm();"
									id="charge_type_order">充值方式</th>
								<th onclick="orderBy(this,'spend_type');commitForm();"
									id="spend_type_order">消费类型</th>
								<th onclick="orderBy(this,'is_discharge');commitForm();"
									id="is_discharge_order">是否冲红</th>
								<th onclick="orderBy(this,'channel');commitForm();"
									id="channel_order">充值渠道</th>
									
								<th onclick="orderBy(this,'spend_type');commitForm();"
									id="spend_tyep_order">消费方式</th>
								 
								<th onclick="orderBy(this,'trade_no');commitForm();"
									id="trade_no_order">交易号</th>
								<th>退款原因</th>
								<th>退款批号</th>
								<th>退款状态</th>
								 
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="${list.orderId }">
									<td>${list.orderNumber}</td>
									<td><s:Code2Name mcode="${list.orderType}"
											gcode="ORDER_TYPE" /></td>
									<%-- 		<td><img width="150" height="150" alt="150x150"
												src="<%=imagePath %>${list.imgPath}" /></td> --%>
									<td>${list.cash}</td>
									<td>${list.mobile_phone}</td>
									<td>${list.creditPhone}</td>
									<td><fmt:formatDate value="${list.orderDate}" type="both" /></td>
									<td> 
										<s:Code2Name mcode="${list.chargeType}" gcode="CHARGE_TYPE"></s:Code2Name>
									</td>
									<td> 
										<s:Code2Name mcode="${list.spend_type}" gcode="SPEND_TYPE"></s:Code2Name>
									</td>
									<td><c:if test="${list.is_discharge eq '0'}">否</c:if> <c:if
											test="${list.is_discharge eq '1'}">是</c:if></td>
									<td>${list.channel}</td>
									<td><s:Code2Name mcode="${list.spend_type}" gcode="CHARGE_TYPE"></s:Code2Name></td>
									<td>${list.trade_no}</td>
									<td>${list.orderRemark}</td>
									<td>${list.batch_no}</td>
									<td><c:if test="${list.orderType eq '230'}">
											<c:if test="${list.orderStatus==1}">成功</c:if>
											<c:if test="${list.orderStatus==2}">失败</c:if>
											<c:if test="${list.orderStatus==3}">待退款</c:if>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>


				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info sjny-page" id="dynamic-table_info"
							role="status" aria-live="polite">
							每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共
							${pageInfo.total} 条 <span class="line">|</span> 共
							${pageInfo.pages} 页
						</div>
					</div>
					<div class="col-sm-6">
						<nav>
							<ul id="ulhandle" class="pagination pull-right no-margin">
								<li id="previous"><a href="javascript:void(0);"
									aria-label="Previous" onclick="prepage('#formRoad');"> <span
										aria-hidden="true">上一页</span>
								</a></li>
								<li id="next"><a id="nexthandle" href="javascript:void(0);"
									aria-label="Next" onclick="nextpage('#formRoad');"> <span
										aria-hidden="true">下一页</span>
								</a></li>
							</ul>
						</nav>
					</div>
				</div>
 
				 
			
				<jsp:include page="/common/message.jsp"></jsp:include>

			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</form>
		<div class="modal-footer">
		
			 </div>
</div>
 
 


<script>
	//	$("#conditionStatus").val("${road.conditionStatus}");

	
</script>