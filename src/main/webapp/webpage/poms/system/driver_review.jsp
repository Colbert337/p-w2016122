<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/sysparam/driver_review.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formdriver">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							实名审核
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>会员账号:</label>
							<input type="text" name="userName" placeholder="输入会员账号"  maxlength="32" value="${driver.userName}"/>
						</div>
						
						<div class="item">
						    <label>车牌号:</label>
							<input type="text" name="plateNumber" placeholder="输入车牌号码"  maxlength="20" value="${driver.plateNumber}"/>
						</div>
						
						<div class="item">
						    <label>认证姓名:</label>
							<input type="text" name="fullName" placeholder="输入认证姓名"  maxlength="20" value="${driver.fullName}"/>
						</div>

						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>申请时间:</label>
								<input type="text" name="createdDate_after" value="${driver.createdDate_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" name="createdDate_before" value="${driver.createdDate_before}" readonly="readonly"/>
							</div>
						</div>

						<div class="item">
							<div class="input-daterange top" >
								<label>审核状态:</label>
								<select id="is_discharge" name="checkedStatus" maxlength="20">
									<option value="">全部</option>
									<option value="0">未认证</option>
									<option value="1">审核中</option>
									<option value="2">已认证</option>
									<option value="3">认证未通过</option>
								</select>
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
					
					<div class="table-header">实名审核信息列表</div>

					<!-- div.table-responsive -->

					<!-- div.dataTables_borderWrap -->
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
									<th onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">会员账号</th>
									<th onclick="orderBy(this,'full_name');commitForm();" id="full_name_order">认证姓名</th>
									<th onclick="orderBy(this,'plate_number');commitForm();" id="plate_number_order">车牌号</th>
									<th onclick="orderBy(this,'identity_card');commitForm();" id="identity_card_order">身份证号</th>
									<th onclick="orderBy(this,'fuel_type');commitForm();" id="fuel_type_order">燃料类型</th>
									<th onclick="orderBy(this,'station_id');commitForm();" id="station_id_order">注册工作站编号</th>
									<th onclick="orderBy(this,'regis_source');commitForm();" id="regis_source_order">注册工作站名称</th>
									<!--  <th onclick="orderBy(this,'is_ident');commitForm();" id="address_order">是否实名认证</th> -->
									<th onclick="orderBy(this,'updated_date');commitForm();" id="updated_date_order" class="td-w2"><i id="updated_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>申请时间</th>
									<th onclick="orderBy(this,'checked_status');commitForm();" id="checked_status_order">审核状态</th> 
									<th onclick="orderBy(this,'checked_date');commitForm();" id="checked_date_order" class="td-w2"><i id="checked_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>审核时间</th>
									<th id="memoth" style="display:none">备注</th>
									<th id="memoth" style="display:none">驾驶证</th>
									<th id="memoth" style="display:none">行驶证</th>
									<th id="memoth" style="display:none">认证来源</th>
									<th class="text-center">更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.sysDriverId}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.userName}</td>
								 	<td>${list.fullName}</td> 
									<td>${list.plateNumber}</td>
									<td>${list.identityCard}</td>
									<td><s:Code2Name mcode="${list.fuelType}" gcode="FUEL_TYPE"></s:Code2Name></td>
									<td>${list.stationId}</td>
									<td>${list.regisSource}</td>
									<%-- <td>${list.isIdent == '0'?'否':'是'}</td>  --%>
									<td><fmt:formatDate value="${list.updatedDate}" type="both"/></td>
									<td><s:Code2Name mcode="${list.checkedStatus}" gcode="CHECKED_STATUS"></s:Code2Name></td>
									<td><fmt:formatDate value="${list.checkedDate}" type="both"/></td>
									<td style="display:none">${list.memo}</td>
									<td style="display:none"><c:if test="${list.drivingLice != '' and list.drivingLice != null}"><%=basePath%></c:if>${list.drivingLice}</td>
									<td style="display:none"><c:if test="${list.vehicleLice != '' and list.vehicleLice != null}"><%=basePath%></c:if>${list.vehicleLice}</td>
									<td style="display:none">${list.verifySource=='0'?'CRM':'APP'}</td>
									<td class="text-center">
										<a class="option-btn-m" href="javascript:void(0);" title="更多信息" data-rel="tooltip">
											<i class="ace-icon fa fa-search-plus bigger-130" onclick="showInnerModel(this);"></i>
										</a>
										<c:if test="${list.checkedStatus == 1}">

											<a class="option-btn-m green" href="javascript:void(0);" title="审核通过" data-rel="tooltip">
												<i class="ace-icon fa fa-pencil-square-o bigger-130" onclick="showInnerModel(this,'2');"></i>
											</a>
											<a class="option-btn-m red" href="javascript:void(0);" title="审核拒绝" data-rel="tooltip">
												<i class="ace-icon fa fa-ban bigger-130" onclick="showInnerModel(this,'3');"></i>
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
					<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">
						每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页
						&nbsp;&nbsp;转到第 <input type="text" name="convertPageNum" style="height:25px;width:45px" maxlength="4"/>  页
						<button type="button" class="btn btn-white btn-sm btn-primary" onclick="commitForm();">跳转</button>
					</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formdriver');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formdriver');">
									<span aria-hidden="true">下一页</span>
								</a>
							</li>  
						</ul>
					</nav>
				</div>
			</div>

			<jsp:include page="/common/message.jsp"></jsp:include>
			<%-- <jsp:include page="inner_model.jsp"></jsp:include> --%>
		</div>
	</div>
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
					<%--两行表单 开始--%>
					<div class="shenhe-items">
						<div class="shenhe-items-hd">
							实名认证
						</div>
						
						<div class="shenhe-items-bd">
						<div class="row">
						<div class="col-xs-6">
							<label class="control-label no-padding-right">注册日期:</label>
							<label class="control-label no-padding-right" id="createdate"></label>
						</div>
						
						<div class="col-xs-6">
							<label class="control-label no-padding-right">审核日期:</label>
							<label class="control-label no-padding-right" id="checkedate"></label>
						</div>
						
						<div class="col-xs-6">
							<label class="control-label no-padding-right">认证来源:</label>
							<label class="control-label no-padding-right" id="regisource"></label>
						</div>
						
						<div class="col-xs-6">
							<label class="control-label no-padding-right">审核状态:</label>
							<label class="control-label no-padding-right" id="checkstatus"></label>
						</div>
						</div>
						</div>
						<div class="shenhe-items-hd">
							个人信息
						</div>
						<div class="shenhe-items-bd">
						<div class="row">
						<div class="col-xs-6">
							<label class="control-label no-padding-right">账号:</label>
							<label class="control-label no-padding-right" id="userName"></label>
						</div>
						
						<div class="col-xs-6">
							<label class="control-label no-padding-right">姓名:</label>
							<label class="control-label no-padding-right" id="fullName"></label>
						</div>
						
						<div class="col-xs-6">
							<label class="control-label no-padding-right">身份证号:</label>
							<label class="control-label no-padding-right" id="identityCard"></label>
						</div>
						
						<div class="col-xs-6">
							<label class="control-label no-padding-right">车牌号:</label>
							<label class="control-label no-padding-right" id="plateNumber"></label>
						</div>
						</div>
						</div>
						
						<div class="shenhe-items-hd">证件照片</div>
						<div class="shenhe-items-bd">
						<div class="row">
						<div class="col-xs-6">
							<label class="control-label no-padding-right">驾驶证：</label>
							<ul class="ace-thumbnails clearfix">
								<li>
									<a href="" data-rel="colorbox">
										<img width="150" height="150" alt="150x150" src="" id="vehicle_lice"/>
										<div class="text">
											<div class="inner">点击放大</div>
										</div>
									</a>
								</li>
							</ul>
						</div>
						<div class="col-xs-6">
							<label class="control-label no-padding-right">行驶证：</label>
							<ul class="ace-thumbnails clearfix">
								<li>
									<a href="" data-rel="colorbox">
										<img width="150" height="150" alt="150x150" src="" id="driving_lice"/>
										<div class="text">
											<div class="inner">点击放大</div>
										</div>
									</a>
								</li>
							</ul>
						</div>
					</div>
					</div>
					</div>
					
					<div class="form-group">
						<label class="shenhe-items-hd">审核备注：</label>
						<input type="hidden" id="objid"/>
						<input type="hidden" id="objval"/>
						<div>
							<textarea class="form-control" name="remark" rows="5" maxlength="100"></textarea>
						</div>
					</div>
					<%--两行表单 结束--%>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="addMemo()" id="optionbutton">确   定</button>
				<button class="btn btn-sm" i="close" onclick="hideInnerModel();">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>

<script type="text/javascript">
	var is_discharge = '${driver.checkedStatus}';
	$("#is_discharge").find("option[value='"+is_discharge+"']").attr("selected",true);
</script>