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
<script src="<%=basePath %>/dist/js/advance/fleet_list.js"></script>
<div class="page-header">
	<h1>
		车队管理
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
							<input type="text" name="fleetName" placeholder="车队名称/队长姓名"  value="${fleet.fleetName}"/>
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
							<button class="btn btn-sm btn-primary" type="button" onclick="addFleet();">
								添加车队
							</button>

						</div>
					</div>
					<%--</h4>--%>
					<%--<table id="simple-table" class="table table-striped table-bordered table-hover">--%>
					<table id="dynamic-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th>车队名称</th>
							<th>队长名称</th>
							<th>手机号</th>
							<th>车辆数</th>
							<th>创建时间</th>
							<th class="text-center">操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${fleetList}" var="fleet">
							<tr>
								<td>${fleet.fleetName}</td>
								<td>${fleet.real_name}</td>
								<td>${fleet.mobile_phone}</td>
								<td>${fleet.vCount}</td>
								<td><fmt:formatDate value="${fleet.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
								<td class="text-center">
									<a class="option-btn-m" href="javascript:editFleet('${fleet.tcFleetId}');" title="修改" data-rel="tooltip">
										<span class="ace-icon fa fa-pencil bigger-130"></span>
									</a>
									<a class="" href="javascript:mangFleet('${fleet.tcFleetId}');" title="管理车辆" data-rel="tooltip">
										<span class="ace-icon fa fa-car bigger-120"></span>
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
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条|共 ${pageInfo.total} 条|共 ${pageInfo.pages} 页</div>
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
<!--添加车队弹层-开始-->
<div id="editModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="editFleetDiv">添加车队</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="editForm">
								<!-- #section:elements.form -->
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="fleet_name"><span class="red_star">*</span> 车队名称： </label>
									<div class="col-sm-7">
										<input type="text" name="fleetName" id="fleet_name" placeholder="车队名称" class="col-xs-10 col-sm-12" />
										<input type="hidden" name="tcFleetId" id="tc_fleet_id" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="sys_user_id"><span class="red_star">*</span> 车队队长： </label>
									<div class="col-sm-7">
										<select class="form-control col-xs-10 col-sm-12" placeholder="车队队长" id="sys_user_id" name="sysUserId"></select>
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
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveFleet()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('editModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加车队弹层-结束-->

<!--添加车辆管理弹层-开始-->
<div id="manageModel" class="modal fade" role="dialog" aria-labelledby="manageModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="manageModalLabel">管理车辆</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="manageForm">
								<div class="form-group">
									<div class="col-sm-12">
										<!-- #section:plugins/input.duallist -->
										<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" style="display: none;height: 270px;"></select>
										<input type="hidden" id="fleet_id" name="tcFleetId" />
										<input type="hidden" id="sysUserId" name="sysUserId" />
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
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveManage()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('manageModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加车辆管理弹层-结束-->

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
