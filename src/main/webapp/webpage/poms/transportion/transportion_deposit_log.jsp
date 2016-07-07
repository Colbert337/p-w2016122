<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String imagePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>

<script src="<%=basePath %>/dist/js/transportion/transportion_deposit_log.js"></script>

<div class="">
	<form id="formcard">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h1>
							充值记录管理
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
						    <label>工作站编号:</label>
							<input type="text" name="stationId" placeholder="输入工作站编号"  maxlength="10" value="${deposit.stationId}"/>
						</div>
						
						<div class="item">
						    <label>操作员:</label>
							<input type="text" name="operator" placeholder="输入操作员"  maxlength="10" value="${deposit.operator}"/>
						</div>
						
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>操作时间:</label>
								<input type="text" class="" name="optime_after"  value="${deposit.optime_after}"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="optime_before" value="${deposit.optime_before}"/>
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
						</div>

					</div>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					<div class="table-header">充值金额轨迹信息</div>

					<!-- div.table-responsive -->

					<!-- div.dataTables_borderWrap -->
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center td-w1">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);"/> 
											<span class="lbl"></span>
										</label>
									</th>
									<th onclick="orderBy(this,'order_number');commitForm();" id="order_number_order">订单号</th>
									<th onclick="orderBy(this,'station_id');commitForm();" id="station_id_order">工作站编号</th>
									<th onclick="orderBy(this,'station_name');commitForm();" id="station_name_order">工作站名称</th>
									<th onclick="orderBy(this,'company');commitForm();" id="company_order">所属公司</th>
									<th onclick="orderBy(this,'deposit_time');commitForm();" id="deposit_time_order" class="td-w2"><i id="storage_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>转账时间</th>
									<th onclick="orderBy(this,'deposit_type');commitForm();" id="deposit_type_order">转账方式</th>
									<th onclick="orderBy(this,'operator');commitForm();" id="operator_order">操作员</th> 
									<th onclick="orderBy(this,'optime');commitForm();" id="optime_order" class="td-w2"><i id="storage_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>操作时间</th>
									<th onclick="orderBy(this,'deposit');commitForm();" id="deposit_order">充值金额(元)</th>
									<th>更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.order_number}"/> 
											<span class="lbl"></span>
										</label>
									</td>
									
									<td>${list.order_number}</td>
									<td>${list.stationId}</td>
								 	<td>${list.stationName}</td> 
									<td>${list.company}</td>
									<td><fmt:formatDate value="${list.depositTime}" type="both"/></td>
									<td><s:Code2Name mcode="${list.depositType}" gcode="RECHARGE_TYPE"></s:Code2Name></td>
									<td>${list.operator}</td>
					                <td><fmt:formatDate value="${list.optime}" type="both"/></td>
									<td>${list.deposit}</td>
									<td class="text-center">
										<a class="gastation-log-colorbox" href="<%=imagePath %>${list.transfer_photo}" title="查看图片" data-rel="colorbox">
											<i class="ace-icon fa fa-camera bigger-130"></i>
										</a>
										<c:if test="${currUser.userType == 5}">
											<a class="" href="javascript:void(0);" title="查看订单明细">
												<i class="ace-icon fa fa-search-plus bigger-130" onclick="showOrderLog(this);"></i>
											</a>
										</c:if>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条|共 ${pageInfo.total} 条|共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formcard');">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formcard');">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>  
						</ul>
					</nav>
				</div>
			</div>

			<jsp:include page="/common/message.jsp"></jsp:include>
			

			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>
