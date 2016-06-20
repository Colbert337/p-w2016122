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
<script src="<%=basePath %>/dist/js/driver/dirver_list.js"/>
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
							<label>加注站名称:</label>
							<input type="text" name="gas_station_name" placeholder="输入加注站名称"  maxlength="20" value="${gastation.gas_station_name}"/>
						</div>
						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<div class="pull-right btn-botton">
								<a class="btn btn-sm btn-primary" href="javascript:addUser();">
									添加司机
								</a>
							</div>
							<div class="pull-right btn-botton">
								<a class="btn btn-sm btn-primary" href="javascript:addUser();">
									离职
								</a>
							</div>
						</div>
					</div>
					<%--</h4>--%>
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th class="center">
								<label class="pos-rel">
									<input type="checkbox" class="ace" onclick="checkedAllRows(this);" />
									<span class="lbl"></span>
								</label>
							</th>
							<th>姓名</th>
							<th>入职时间</th>
							<th>手机号码</th>
							<th>实体卡号</th>
							<th>实名认证</th>
							<th>状态</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${userList}" var="user">
							<tr>
								<td>${user.userName}</td>
								<td>${user.realName}</td>
								<td>
									<c:if test="${user.gender == 0}">
										男
									</c:if>
									<c:if test="${user.gender == 1}">
										女
									</c:if>
								</td>
								<td>${user.email}</td>
								<td>${user.mobilePhone}</td>
								<td class="hidden-480">${user.avatarB}</td>
								<td><s:Code2Name mcode="${user.userType}" gcode="PLF_TYPE"></s:Code2Name></td>
								<td>
									<c:if test="${user.status == 0}">
										启用
									</c:if>
									<c:if test="${user.status == 1}">
										禁用
									</c:if>
								</td>
								<td class="hidden-480"><fmt:formatDate value="${user.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
								<td class="text-center">
									<a class="" href="javascript:editUser('${user.sysUserId}');" title="修改">
										<span class="ace-icon fa fa-pencil bigger-130"></span>
									</a>
									<span class="span-state">
										<c:if test="${user.status == 0}">
											<a class="green" href="javascript:updateStatus('${user.sysUserId}',1);" title="禁用">
												<span class="ace-icon fa fa-unlock bigger-130"></span>
											</a>
										</c:if>
										<c:if test="${user.status == 1}">
											<a class="red" href="javascript:updateStatus('${user.sysUserId}',0);" title="启用">
												<span class="ace-icon fa fa-lock bigger-130"></span>
											</a>
										</c:if>
									</span>
									<a class="" href="javascript:deleteUser('${user.sysUserId}');">
										<span class="ace-icon fa fa-trash-o bigger-130" title="删除"></span>
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
<!--添加用户弹层-开始-->
<div id="userModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel">编辑用户</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="userForm">
								<!-- #section:elements.form -->
								<h5 class="header smaller lighter blue">账户信息</h5>
								<div class="form-group">
									<label class="col-sm-2 control-label no-padding-right" for="user_name"><span class="red_star">*</span> 用户名： </label>
									<div class="col-sm-4">
										<input type="text" name="userName" id="user_name" placeholder="用户名" class="validate[required,minSize[3],custom[onlyLetterNumber]] col-xs-10 col-sm-12" />
										<input type="hidden" name="sysUserId" id="sys_user_id" class="col-xs-10 col-sm-12" />
									</div>
									<label class="col-sm-2 control-label no-padding-right" for="user_type"><span class="red_star">*</span> 用户类型： </label>
									<div class="col-sm-4">
										<label class="col-xs-10 col-sm-12 pad-top-10" id="mname"></label>
										<input type="hidden" id="user_type" name="userType"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label no-padding-right" for="password"><span class="red_star">*</span> 用户密码： </label>
									<div class="col-sm-4">
										<input type="password" readonly="readonly" name="password" id="password" placeholder="用户密码" class="validate[required,minSize[6]] col-xs-10 col-sm-12" />
									</div>
									<label class="col-sm-2 control-label no-padding-right" for="re_password"><span class="red_star">*</span> 确认密码： </label>
									<div class="col-sm-4">
										<input type="password" id="re_password" placeholder="确认密码" class="validate[required,minSize[6],equals[password]] col-xs-10 col-sm-12" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label no-padding-right" for="user_type"><span class="red_star">*</span> 用户角色： </label>
									<div class="col-sm-4">
										<select class="chosen-select col-xs-10 col-sm-12" id="avatar_b" name="avatarB"></select>
									</div>
									<label class="col-sm-2 control-label no-padding-right"> 备注： </label>
									<div class="col-sm-4">
										<textarea class="limited col-xs-10 col-sm-12"  id="remark" name="remark" maxlength="50" style="resize: none;"></textarea>
									</div>
								</div>
								<h5 class="header smaller lighter blue">基本信息</h5>
								<div class="form-group">
									<label class="col-sm-2 control-label no-padding-right" for="real_name">  <span class="red_star">*</span>姓名： </label>
									<div class="col-sm-4">
										<input type="text" name="realName" id="real_name" placeholder="姓名" class="validate[required,maxSize[5]] col-xs-10 col-sm-12" />
									</div>
									<label class="col-sm-2 control-label no-padding-right"> 性别： </label>
									<div class="col-sm-4">
										<div class="radio">
											<label>
												<input name="gender" id="gender_b" type="radio" class="ace" checked="checked" value="0">
												<span class="lbl"> 男</span>
											</label>
											<label>
												<input name="gender" id="gender_g" type="radio" class="ace" value="1">
												<span class="lbl"> 女</span>
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label no-padding-right" for="email"> 邮箱： </label>
									<div class="col-sm-4">
										<input type="email" name="email" id="email" placeholder="邮箱" class="validate[maxSize[20],custom[email]] col-xs-10 col-sm-12" />
									</div>
									<label class="col-sm-2 control-label no-padding-right" for="mobile_phone"> 手机： </label>
									<div class="col-sm-4">
										<input type="text" name="mobilePhone" id="mobile_phone" placeholder="手机" class="validate[maxSize[11],custom[phone]] col-xs-10 col-sm-12" />
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
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveUser()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('userModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加用户弹层-结束-->

<!--提示弹层-开始-->
<div id="alertModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
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
