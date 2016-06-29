<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/advance/vehicle_list.js"/>
<div class="page-header">
	<h1>
		车辆管理
		<%--<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			用户列表
		</small>--%>
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal"  id="listForm">
			<jsp:include page="/common/page_param.jsp"></jsp:include>
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<%--顶部条件搜索及按钮--%>
					<div class="search-types">
						<div class="item">
							<input type="text" name="platesNumber" placeholder="车牌号"  maxlength="11" value="${vehicle.platesNumber}"/>
						</div>
						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								重置
							</button>
							<div class="item"></div>
							<button class="btn btn-sm btn-primary" type="button" onclick="addVehicle();">
								添加车辆
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="importVehicle();">
								批量导入
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="downLoadModel();">
								下载模板
							</button>
						</div>
					</div>
					<%--</h4>--%>
					<%--<table id="simple-table" class="table table-striped table-bordered table-hover">--%>
					<table id="dynamic-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th>车队名称</th>
							<th>车牌号</th>
							<th>实体卡号</th>
							<th>通知手机</th>
							<th>创建时间</th>
							<th>卡状态</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${vehicleList}" var="vehicle">
							<tr>
								<td>${vehicle.fleetName}</td>
								<td>${vehicle.platesNumber}</td>
								<td>${vehicle.cardNo}</td>
								<td>${vehicle.noticePhone}</td>
								<td><fmt:formatDate value="${vehicle.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>
									<c:if test="${vehicle.cardStatus == 0}">
										已冻结
									</c:if>
									<c:if test="${vehicle.cardStatus == 1}">
										未使用
									</c:if>
									<c:if test="${vehicle.cardStatus == 2}">
										使用中
									</c:if>
								</td>
								<td>
									<a class="" href="javascript:editVehicle('${vehicle.tcVehicleId}');" title="修改" data-rel="tooltip">
										<span class="ace-icon fa fa-pencil bigger-130"></span>
									</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div><!-- /.col-xs-12 -->
			</div><!-- /.row -->
			<%--分页start--%>
			<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">共 ${pageInfo.total} 条</div>
				</div>
				<div class="col-sm-6">
					<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
						<ul id="ulhandle" class="pagination">
							<li id="previous" class="paginate_button previous" aria-controls="dynamic-table" tabindex="0">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#listForm');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next" class="paginate_button next" aria-controls="dynamic-table" tabindex="0">
								<a id="nexthandle" href="javascript:nextpage('#listForm');" aria-label="Next" >
									<span aria-hidden="true">下一页</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<%--分页 end--%>
		</form>
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->
<!--添加车辆弹层-开始-->
<div id="editModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel">添加车辆</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="editForm">
								<!-- #section:elements.form -->
								<h5 class="header smaller lighter blue">基本信息</h5>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="plates_number"><span class="red_star">*</span> 车牌号： </label>
									<div class="col-sm-7">
										<input type="text" name="platesNumber" id="plates_number" placeholder="车牌号" class="col-xs-10 col-sm-12" />
										<input type="hidden" name="tcVehicleId" id="tc_vehicle_id" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="pay_code"><span class="red_star">*</span> 支付密码： </label>
									<div class="col-sm-7">
										<input type="password" name="payCode" id="pay_code" placeholder="支付密码" class="col-xs-10 col-sm-12" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="re_password"><span class="red_star">*</span> 确认密码： </label>
									<div class="col-sm-7">
										<input type="password" id="re_password" name="rePassword" placeholder="确认密码" class="col-xs-10 col-sm-12" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="notice_phone"><span class="red_star">*</span> 通知手机： </label>
									<div class="col-sm-7">
										<input type="text" id="notice_phone" placeholder="通知手机" name="noticePhone" class="col-xs-10 col-sm-12" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="copy_phone"> 抄送手机： </label>
									<div class="col-sm-7">
										<input type="text" name="copyPhone" id="copy_phone" placeholder="抄送手机" class="col-xs-10 col-sm-12" />
									</div>
								</div>
								<div id="cardInfoDiv">
									<h5 class="header smaller lighter blue">实体卡信息</h5>
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right"> 实体卡： </label>
										<div class="col-sm-6">
											<label class="pad-top-10" id="card_no"></label>
										</div>
										<div class="col-sm-2"><button onclick="freeze()">冻结</button></div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right"> 卡类型： </label>
										<div class="col-sm-8">
											<label class="pad-top-10" id="card_type"></label>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right"> 卡状态： </label>
										<div class="col-sm-8">
											<label class="pad-top-10" id="card_status"></label>
										</div>
									</div>
								</div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
				<!--底部按钮 -->
				<div class="row">
					<div class="space"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveVehicle()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('editModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加车辆弹层-结束-->

<!--提示弹层-开始-->
<div id="alertModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="alertModalLabel">警告提示</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							sadfasdfasdf
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
				<!--底部按钮 -->
				<div class="row">
					<div class="space"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveUser()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('alertModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--提示弹层-结束-->
