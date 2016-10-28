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
<script src="<%=basePath %>/dist/js/driver/dirver_list.js"></script>
<div class="page-header">
	<h1>
		司机管理
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
							<label>司机姓名/手机号码：</label>
							<input type="text" name="fullName" placeholder="司机姓名/手机号码"  maxlength="11" value="${driver.fullName}"/>
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
							<button class="btn btn-sm btn-primary" type="button" onclick="addDriver();">
								添加司机
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="leaveDriver();">
								离职
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="openImportDiv();">
								导入Excel
							</button>
						</div>
					</div>
					<%--</h4>--%>
					<%--<table id="simple-table" class="table table-striped table-bordered table-hover">--%>
					<table id="dynamic-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th class="center td-w1">
								<label class="pos-rel">
									<input type="checkbox" class="ace" onclick="checkedAllRows(this);" <c:if test='${driverList.size()==0}'>disabled='disabled'</c:if> />
									<span class="lbl"></span>
								</label>
							</th>
							<th>姓名</th>
							<th>入职时间</th>
							<th>手机号码</th>
							<th>实体卡号</th>
							<th>实名认证状态</th>
							<%--<th>操作</th>--%>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${driverList}" var="driver">
							<tr>
								<td class="center">
									<label class="pos-rel">
										<input type="checkbox" class="ace" id="pks" name="pks" value="${driver.sysDriverId}"/>
										<span class="lbl"></span>
									</label>
								</td>
								<td>${driver.fullName}</td>
								<td><fmt:formatDate value="${driver.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>${driver.mobilePhone}</td>
								<td>${driver.cardId}</td>
								<td>
									<c:if test="${driver.checkedStatus == 0}">
										新注册
									</c:if>
									<c:if test="${driver.checkedStatus == 1}">
										待审核
									</c:if>
									<c:if test="${driver.checkedStatus == 2}">
										已通过
									</c:if>
									<c:if test="${driver.checkedStatus == 3}">
										未通过
									</c:if>
								</td>
								<%--<td>--%>
									<%--<a class="green" href="javascript:updateStatus('${user.sysUserId}',1);" title="离职">--%>
										<%--<span class="ace-icon fa fa-unlock bigger-130"></span>--%>
									<%--</a>--%>
								<%--</td>--%>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div><!-- /.col-xs-12 -->
			</div><!-- /.row -->
			<%--分页start--%>
			<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
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
<%--提示弹层--%>
<jsp:include page="/common/message.jsp"></jsp:include>

<!--添加司机弹层-开始-->
<div id="driverModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel">添加司机</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="driverForm">
								<!-- #section:elements.form -->
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="user_name"><span class="red_star">*</span> 手机号码： </label>
									<div class="col-sm-4">
										<input type="text" name="mobilePhone" id="mobile_phone" maxlength="11" placeholder="手机号码" class="col-xs-10 col-sm-12" />
									</div>
									<div class="col-sm-2">
										<a id="sendMsgA" class="btn btn-sm btn-primary" >
											发送验证码
										</a>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="user_name"><span class="red_star">*</span> 验证码： </label>
									<div class="col-sm-7">
										<input type="text" id="user_name" placeholder="验证码" name="userName" class="col-xs-10 col-sm-12" /><%--临时存储验证码--%>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="full_name"><span class="red_star">*</span> 姓名： </label>
									<div class="col-sm-7">
										<input type="text" name="fullName" id="full_name" placeholder="姓名" class="col-xs-10 col-sm-12" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="pay_code"><span class="red_star">*</span> 支付密码： </label>
									<div class="col-sm-7">
										<input type="password" name="payCode" id="pay_code" maxlength="6" placeholder="支付密码" class="col-xs-10 col-sm-12" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="re_password"><span class="red_star">*</span> 确认密码： </label>
									<div class="col-sm-7">
										<input type="password" id="re_password" name="rePassword" maxlength="6" placeholder="确认密码" class="col-xs-10 col-sm-12" />
									</div>
								</div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="saveDriver()">确   定</button>
				<button class="btn btn-sm" i="close" onclick="closeDialog('driverModel')">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加用户弹层-结束-->

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
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="saveUser()">确   定</button>
				<button class="btn btn-sm" i="close" onclick="closeDialog('alertModel')">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--提示弹层-结束-->
<!--提示弹层-开始-->
<div id="importDivModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="alertModalLabel">批量导入</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<form class="form-horizontal" id="importForm" enctype="multipart/form-data">
								<div class="form-group">
									<div class="col-xs-12">
										<input type="file" id="file_import" name="fileImport" onchange="fileFormat()"/>
									</div>
								</div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="saveTemplate()">确   定</button>
				<button class="btn btn-sm" i="close" onclick="closeDialog('importDivModel')">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
