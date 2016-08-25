<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/transportion/vehicle_list.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formtransportion">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">
					<div class="page-header">
						<h1>
							车辆管理
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
						    <label>车辆编号:</label>
							<input type="text" name="tcVehicleId" placeholder="输入车辆编号" maxlength="32" value="${vehicle.tcVehicleId}"/>
						</div>
						
						<div class="item">
						    <label>车牌号:</label>
							<input type="text" name="platesNumber" placeholder="输入车牌号" maxlength="8" value="${vehicle.platesNumber}"/>
						</div>
						
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>创建时间:</label>
								<input type="text" class="" name="created_date_after" value="${vehicle.created_date_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="created_date_before" value="${vehicle.created_date_before}" readonly="readonly"/>
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
			
					<div class="table-header">车辆详细信息列表</div>

	
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center td-w1">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>
									<th onclick="orderBy(this,'tc_vehicle_id');commitForm();" id="tc_vehicle_id_order">车辆编号</th>
									<th onclick="orderBy(this,'plates_number');commitForm();" id="plates_number_order">车牌号</th>
									<th>车队名称</th>
									<th>所属公司</th>
									<th onclick="orderBy(this,'station_id');commitForm();" id="station_id_order">公司编号</th>
									<th onclick="orderBy(this,'notice_phone');commitForm();" id="notice_phone_order">通知手机</th>
									<th onclick="orderBy(this,'copy_phone');commitForm();" id="copy_phone_order">抄送手机</th>
									<th onclick="orderBy(this,'card_no');commitForm();" id="card_no_order">实体卡号</th>
									<th>实体卡状态</th> 
									<th onclick="orderBy(this,'created_date');commitForm();" id="created_date_order" class="td-w2"><i id="created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>									
									<th class="text-center td-w3">更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.tcVehicleId}"/> 
											<span class="lbl"></span>
										</label>
									</td>
									<td id="tcVehicleIdInfo" >${list.tcVehicleId}</td>
								 	<td>${list.platesNumber}</td> 
									<td>${list.fv.tf.fleetName}</td>
									<td><s:Code2Name mcode="${list.stationId}" gcode="TRANSTION"></s:Code2Name></td>
									<td>${list.stationId}</td>
									<td>${list.noticePhone}</td>
									<td>${list.copyPhone}</td> 
								    <td>${list.cardNo}</td>
								    <td><s:Code2Name mcode="${list.gas_card.card_status}" gcode="CARDSTATUS"></s:Code2Name></td>
									<td><fmt:formatDate value="${list.createdDate}" type="both"/></td>
									<td class="text-center">
										<c:if test="${list.gas_card.card_status == 4}">
											<a class="option-btn-m" href="javascript:void(0);" title="冻结卡" data-rel="tooltip">
												<i class="ace-icon fa fa-archive bigger-130" onclick="showInnerModel(this,'0');"></i>
											</a>
										</c:if>
										<c:if test="${list.gas_card.card_status == 0}">
											<a class="option-btn-m" href="javascript:void(0);" title="解冻卡" data-rel="tooltip">
												<i class="ace-icon fa fa-key bigger-130" onclick="showInnerModel(this,'4');"></i>
											</a>
											<a class="option-btn-m" href="javascript:void(0);" title="补发卡" data-rel="tooltip">
												<i class="ace-icon fa fa-credit-card bigger-130" onclick="showInnerModel2(this,'${list.stationId}');"></i>
											</a>
										</c:if>
										<c:if test="${list.gas_card.card_status == null}">
											<a class="option-btn-m" href="javascript:void(0);" title="补发卡" data-rel="tooltip">
												<i class="ace-icon fa fa-credit-card bigger-130" onclick="showInnerModel2(this,'${list.stationId}');"></i>
											</a>
										</c:if>
										<c:if test="${list.isLocked == 1}">
											<a class="" href="javascript:void(0);" onclick="unLockUser(this,2,'${list.tcFleetId}');" title="解锁" data-rel="tooltip">
												<i class="ace-icon fa fa-lock bigger-130"></i>
											</a>
										</c:if>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

			<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formtransportion');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formtransportion');">
									<span aria-hidden="true">下一页</span>
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


<div id="innerModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<div class="form-group">
						<label class="control-label">备注：</label>
						<div>
							<textarea class="form-control" name="remark" rows="5" maxlength="100"></textarea>
						</div>
					</div>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="addMemo()">确   定</button>
				<button class="btn btn-sm" i="close" onclick="hideInnerModel();">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>

<div id="innerModel2" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<div class="form-group">
						<label class="control-label">新卡号：</label>
						<input type="text" name="newcardno" maxlength="9" onblur="checkCard(this.value);"/>
						<label class="control-label">&nbsp;&nbsp;&nbsp;归属工作站：</label>
						<label id="station"></label>
					</div>
					<div class="form-group">
						<label class="control-label">备注：</label>
						<div>
							<textarea class="form-control" name="remark2" rows="5" maxlength="100"></textarea>
						</div>
					</div>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="changeCard();">确   定</button>
				<button class="btn btn-sm" i="close" onclick="hideInnerModel();">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>