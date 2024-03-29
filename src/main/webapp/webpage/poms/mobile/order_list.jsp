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
<link rel="stylesheet" href="<%=basePath%>/dist/js/message/show.css">
<script src="<%=basePath%>/dist/js/message/show.js"></script>
<div class="" id="div">
	<!-- /.page-header -->
	<form id="formRoad">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="page-header">
					<h1>订单退款</h1>
				</div>
					
				<div class="search-types">
					 
					<%-- <div class="item">
						<label>充值类型</label> <select name="chargeType"  id='type1'  
							value='${order.chargeType}'>
							<s:option flag="true" gcode="CHARGE_TYPE_SEL" form="road"
								field="conditionType" />
						</select>
					</div>  --%>
					
						<div class="item">
						<label>消费类型</label> <select name="spend_type"  id='type2'  
							value="${order.spend_type}">
							<s:option flag="true" gcode="SPEND_TYPE_SEL" form="road"
								field="conditionType" />
						</select>
					</div>
					<div class="item">
						<label>手机号</label> <input type='text' name=creditPhone value="${order.creditPhone}" />
					</div>  
					<div class="item">
						<label>订单号</label> <input type='text' name=orderNumber value="${order.orderNumber}" />
					</div> 
					<div class="item">
						<div class="input-daterange top" id="j-input-daterange-top">
							<label>订单日期:</label> <input type="text" class="timebox"
								name="startDate" id="publisherTime_str"
								value="${order.startDate}" readonly="readonly" /> <span
								class=""> <i class="fa fa-exchange"></i>
							</span> <input type="text" class="timebox" name="endDate"
								id="auditorTime_str" value="${order.endDate}"
								readonly="readonly" />
						</div>
					</div>

					<div class="item">
						 
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
								<th >金额</th>
								 
								<th>用户电话号码</th>
								<th onclick="orderBy(this,'order_date');commitForm();"
									id="order_date_order"><i
									class="ace-icon fa fa-clock-o bigger-110 hidden-480">
									</i>创建日期</th>
								 
								 
								<th onclick="orderBy(this,'channel');commitForm();"
									id="channel_order">充值渠道</th>
									
								<th onclick="orderBy(this,'spend_type');commitForm();"
									id="spend_tyep_order">消费方式</th>
								 
								<th onclick="orderBy(this,'trade_no');commitForm();"
									id="trade_no_order">交易号</th>
									<th >气站编号</th>
								<th onclick="orderBy(this,'chk_user');commitForm();"
									id="chk_user_order">退款操作人</th>
								<th onclick="orderBy(this,'chk_time');commitForm();"
									id="chk_time_order">操作时间</th>
								<th >退款原因</th>
								 
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
									<td>${list.cash}</td>
									 
									<td>${list.creditPhone}</td>
									<td><fmt:formatDate value="${list.orderDate}" type="both" /></td>
									 
									 
									<td>${list.channel}</td>
									<td><s:Code2Name mcode="${list.spend_type}" gcode="SPEND_TYPE"></s:Code2Name></td>
									<td>${list.trade_no}</td>
									<td>${list.channelNumber}</td>
									<td>${list.chk_user}</td>
									<td><fmt:formatDate value="${list.chk_time}" type="both"/></td>
									<td>${list.chk_memo}</td>
									<td class="text-center">
									<c:if test="${list.orderStatus eq -1 }">
										<c:if test="${list.cash ne '0.00' }">
											<c:if test="${not empty list.trade_no}">
												<a class=""  style="color:#337ab7" 
													href="javascript:void(0);"
													onclick="showBreak('${list.trade_no}','${list.chargeType}','${list.cash}','${list.orderId }','${list.orderNumber}',1);" title="退款"
													data-rel="tooltip"> <i
														class="ace-icon glyphicon glyphicon-warning-sign bigger-130"></i>
												</a>
											</c:if>
										 
											<c:if test="${list.spend_type eq 'C01' }">
												<a class="option-btn-m"  style="color:#337ab7" 
													href="javascript:void(0);"
													onclick="showBreak('${list.trade_no}','${list.chargeType}','${list.cash}','${list.orderId }','${list.orderNumber}',2);" title="退款"
													data-rel="tooltip"> <i
														class="ace-icon glyphicon glyphicon-warning-sign bigger-130"></i>
												</a>
											</c:if>
										</c:if>
										</c:if>
										<a class="option-btn-m" style="color:#337ab7" 
											href="javascript:void(0);"
											onclick="showOrderForBack('${list.orderNumber}' );" title="查看退款记录"
											data-rel="tooltip"> <i
												class="ace-icon fa fa-search-plus bigger-130"></i>
										</a>
										
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>


				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">
							每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页
							&nbsp;&nbsp;转到第 <input type="text" name="convertPageNum" style="height:25px;width:45px" maxlength="4"/>  页
							<button type="button" class="btn btn-white btn-sm btn-primary" onclick="commitForm();">跳转</button>
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
					<h3 class="col-sm-11 control-label no-padding-right" id="titleNo" style="color: #428bca"></h3>
				</div>
				<br />  
				<div class="form-group">
					 
				</div>
				<br />  
					<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">气站编号：</label>
					<label class="col-sm-8 control-label no-padding-right" id="gas_id" name="show"></label>
				</div>
				<br />  
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">气站名称：</label>
					<label class="col-sm-8 control-label no-padding-right" id="gas_name" name="show"></label>
				</div>  <br /> 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">订单号：</label>
					<label class="col-sm-8 control-label no-padding-right" id="order_number" name="show"></label>
				</div>
				<br /> 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">会员账号：</label>
					<label class="col-sm-8 control-label no-padding-right" id="user_id" name="show"></label>
				</div>
				
				<br />  
					<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">车牌号：</label>
					<label class="col-sm-8 control-label no-padding-right" id="plate_number" name="show"></label>
				</div>
				
				<br />   
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">交易时间：</label>
					<label class="col-sm-8 control-label no-padding-right" id="order_time"></label>
				</div>
				<br />  
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">实付金额：</label>
					<label class="col-sm-8 control-label no-padding-right" id='order_c' name="show"></label>
				</div>
				 <br /> 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">验证手机号：</label>
					<div class="col-sm-5">
						<input type="text" id="phone"  onkeyup="clearNoNum2(this)"   placeholder="请输入验证手机号"
							class="form-control" maxlength="11" />
							</div>
					<div class="col-sm-3">
						<button class="btn btn-primary btn-sm" onclick="subCheck()">获取验证码</button>
					</div>
				</div>
				<br /> <br /> 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">验证码：</label>
					<div class="col-sm-8">
						<input type="text" id="code"  onkeyup="clearNoNum2(this)"   placeholder="请输入验证码"
							class="form-control" maxlength="6" />
					</div>
				</div>
				<br /><br /> 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">请输入金额：</label>
					<div class="col-sm-8">
						<input type="text" id="money"  onkeyup="clearNoNum(this)"   placeholder="请输入退款金额"
							class="form-control" maxlength="12" />
						<input type='hidden' id="retype" />
					</div>
				</div>
				<br /> <br />
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" style="text-align: right;">请输入退款原因：</label>

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
	$("#type1").val('${order.chargeType}');
	$("#type2").val('${order.spend_type}');
</script>