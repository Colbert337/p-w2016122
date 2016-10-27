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

<script src="<%=basePath%>/dist/js/mobile/order_list.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formRoad">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="page-header">
					<h1>订单退款</h1>
				</div>

				<div class="search-types">
					<div class="item">
						<label>路况说明:</label> <input type="text" name="conditionMsg"
							id="msg" placeholder="输入路况说明" maxlength="200"
							value="${road.conditionMsg}" />
					</div>
					<div class="item">
						<label>路况类型</label> <select name="conditionType" id="type"
							value="${road.conditionType}">
							<s:option flag="true" gcode="CONDITION_TYPE" form="road"
								field="conditionType" />
						</select>
					</div>
					<div class="item">
						<label>路况状态</label> <select name="conditionStatus"
							id="conditionStatus">
							<option value="">--请选择--</option>
							<option value="0">已失效</option>
							<option value="1">待审核</option>
							<option value="2">审核通过</option>
							<option value="3">未通过</option>
						</select>
					</div>
					<div class="item">
						<div class="input-daterange top" id="j-input-daterange-top">
							<label>发布日期:</label> <input type="text" class="timebox"
								name="publisherTime_str" id="publisherTime_str"
								value="${road.publisherTime_str}" readonly="readonly" /> <span
								class=""> <i class="fa fa-exchange"></i>
							</span> <input type="text" class="timebox" name="auditorTime_str"
								id="auditorTime_str" value="${road.auditorTime_str}"
								readonly="readonly" />
						</div>
					</div>

					<div class="item">
						<button class="btn btn-sm btn-primary" type="button"
							onclick="loadPage('#main','<%=basePath%>/webpage/poms/mobile/roadAdd.jsp');">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i> 添加
						</button>
						<button class="btn btn-sm btn-primary" type="button"
							onclick="commitForm();">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i> 查询
						</button>
						<button class="btn btn-sm" type="button" onclick="init();">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i> 重置
						</button>
					</div>
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
								<th>金额</th>
								<th>用户电话号码</th>
								<th onclick="orderBy(this,'order_date');commitForm();"
									id="order_date_order"><i
									class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建日期</th>
								<th onclick="orderBy(this,'charge_type');commitForm();"
									id="charge_type_order">充值方式</th>
								<th onclick="orderBy(this,'is_discharge');commitForm();"
									id="is_discharge_order">是否充红</th>
								<th onclick="orderBy(this,'channel');commitForm();"
									id="channel_order">充值渠道</th>
								<th onclick="orderBy(this,'trade_no');commitForm();"
									id="trade_no_order">交易号</th>
								<th>退款原因</th>
								<th class="text-center td-w3">操作</th>
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
									<td><div class="td-inner-warp">${list.cash}</div></td>
									<td>${list.mobile_phone}</td>
									<td><fmt:formatDate value="${list.orderDate}" type="both" /></td>
									<td><div class="td-inner-warp">
											<s:Code2Name mcode="${list.chargeType}" gcode="CHARGE_TYPE"></s:Code2Name>
										</div></td>
									<td><c:if test="${list.is_discharge eq '0'}">否</c:if> <c:if
											test="${list.is_discharge eq '1'}">是</c:if></td>
									<td>${list.channel}</td>
									<td>${list.trade_no}</td>
									<td>${list.orderRemark}</td>
									<td class="text-center">
									<c:if test="${not empty list.trade_no }">
									<a class=""
										href="javascript:void(0);"
										onclick="showBreak('${list.trade_no}','${list.chargeType}','${list.cash}','${list.orderId }');" title="退款"
										data-rel="tooltip"> <i
											class="ace-icon glyphicon glyphicon-warning-sign bigger-130"></i>
									</a>
									</c:if></td>
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
</div>

<div id="content" class="modal fade" role="dialog"
	aria-labelledby="gridSystemModalLabel" data-backdrop="static"
	tabindex="-1">
	<div class="modal-dialog modal-lg" style='width: 552px;' role="document">
		<div class="modal-content">
			<%--<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>--%>
			<div class="modal-body">
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">请输入金额：</label>
					<div class="col-sm-8">
						<input type="text" id="money"  onkeyup="clearNoNum(this)"   placeholder="请输入退款金额"
							class="form-control" maxlength="12" />
						
					</div>
				</div>
				<br /> <br />
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">请输入退款原因：</label>

					<div class="col-sm-8">
						<textarea class="form-control" id="msgcontent" placeholder="请输入退款原因" rows="3"></textarea>
					</div>
				</div>
			</div>
			<br /> <br /> <br />	
			<div class="modal-footer" id="buttonList">
				<button class="btn btn-primary btn-sm" onclick="subbreak()">确定</button>
				<button class="btn btn-primary btn-sm" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>


<script>
	//	$("#conditionStatus").val("${road.conditionStatus}");

	
</script>