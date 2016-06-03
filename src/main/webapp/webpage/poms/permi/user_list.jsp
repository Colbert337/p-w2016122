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

<script type="text/javascript">
$(function() {
	/*表单验证*/
	jQuery('#userForm').validationEngine('attach', {
		promptPosition: 'topRight',		//提示信息的位置，可设置为：'topRight', 'topLeft', 'bottomRight', 'bottomLeft', 'centerRight', 'centerLeft', 'inline'
		validationEventTrigger:'blur',	//触发验证的事件
		binded:true,						//是否绑定即时验证
		scroll: true 					//屏幕自动滚动到第一个验证不通过的位置
	});
});

/*分页相关方法 start*/
window.onload = setCurrentPage();
function commitForm(obj){
	//设置当前页的值
	if(typeof obj == "undefined") {
		$("#pageNum").val("1");
	}else{
		$("#pageNum").val($(obj).text());
	}

	$("#listForm").ajaxSubmit(listOptions);
}
var listOptions ={
	url:'<%=basePath%>/web/permi/user/list/page',
	type:'post',
	dataType:'html',
	success:function(data){
		$("#main").html(data);
	}
}
/*分页相关方法 end*/
//显示添加用户弹出层
function addUser(){
	$("#userModel").modal('show');
	/*密码输入框改为可编辑*/
	$("#password").removeAttr("readonly");
	$("#re_password").removeAttr("readonly");
}
//显示编辑用户弹出层
function updateUser(){
	$("#userModel").modal('show');
}

/*取消弹层方法*/
function closeDialog(divId){
	jQuery('#userForm').validationEngine('hide');//隐藏验证弹窗
	$("#userForm :input").each(function () {
		$(this).val("");
	});
	$("#"+divId).modal('hide');
}
/**
 * 保存用户信息
 */
function saveUser(){
	if(jQuery('#userForm').validationEngine('validate')){
		var saveOptions ={
			url:'<%=basePath%>/web/permi/user/save',
			type:'post',
			dataType:'html',
			success:function(data){
				$("#main").html(data);
			}
		}
		$("#userForm").ajaxSubmit(saveOptions);

		$("#userModel").modal('hide');
		$(".modal-backdrop").css("display","none");
	}
}

/**
 * 回显用户信息
 */
function editUser(userId){
	$.ajax({
		url:"<%=basePath%>/web/permi/user/update",
		data:{sysUserId:userId},
		async:false,
		type: "POST",
		success: function(data){
			$("#sys_user_id").val(data.sysUserId);
			$("#user_name").val(data.userName);
			$("#remark").val(data.remark);
			$("#real_name").val(data.realName);
			$("#user_type").val(data.userType);
			$("#gender").val(data.gender);
			$("#email").val(data.email);
			$("#mobile_phone").val(data.mobilePhone);
			/*密码输入框改为只读*/
			$("#password").val(data.password);
			$("#re_password").val(data.password);
			/*密码输入框改为可编辑*/
			$("#password").attr("readonly","readonly");
			$("#re_password").attr("readonly","readonly");
			updateUser();
		}
	});

}

/**
 * 删除用户
 */
function deleteUser(userId){
	var deleteOptions ={
		url:'<%=basePath%>/web/permi/user/delete',
		data:{userId:userId},
		type:'post',
		dataType:'text',
		success:function(data){
			$("#main").html(data);
		}
	}
	$("#listForm").ajaxSubmit(deleteOptions);

}
/**
 * 修改用户状态 0 启用 1 禁用
 * @param userId
 */
function updateStatus(userId,status){
	var deleteOptions ={
		url:'<%=basePath%>/web/permi/user/save',
		data:{sysUserId:userId,status:status},
		type:'post',
		dataType:'text',
		success:function(data){
			$("#main").html(data);
		}
	}
	$("#listForm").ajaxSubmit(deleteOptions);

}
</script>
<div class="page-header">
	<h1>
		用户管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			用户列表
		</small>
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal"  id="listForm">
			<jsp:include page="/common/page_param.jsp"></jsp:include>
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<%--顶部按钮--%>
						<div class="pull-right btn-botton">
							<%--<a class="btn btn-primary" href="javascript:loadPage('#main', '<%=basePath%>/web/permi/user/add')">--%>
							<a class="btn btn-primary" href="javascript:addUser();">
								添加用户
							</a>
							<a class="btn btn-primary" href="javascript:addUser();">
								批量导入
							</a>
							<a href="javascript:addUser();">
								下载模板
							</a>
						</div>
					<%--</h4>--%>
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<%--<th class="center">
									<label class="pos-rel">
										<input type="checkbox" class="ace" />
										<span class="lbl"></span>
									</label>
								</th>--%>
								<th>账号</th>
								<th>姓名</th>
								<th class="hidden-480">性别</th>
								<th>邮箱</th>
								<th class="hidden-480">联系电话</th>
								<th>用户角色</th>
								<th>用户类型</th>
								<th>用户状态</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${userList}" var="user">
								<tr>
									<%--<td class="center">
										<label class="pos-rel">
											<input type="checkbox" class="ace" />
											<span class="lbl"></span>
										</label>
									</td>--%>
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
									<td class="hidden-480">${user.userName}</td>
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
									<td>
										<a class="btn btn-sm btn-white btn-primary" href="javascript:editUser('${user.sysUserId}');">修改</a>
										<c:if test="${user.status == 0}">
											<a class="btn btn-sm btn-white btn-inverse" href="javascript:updateStatus('${user.sysUserId}',1);">禁用</a>
										</c:if>
										<c:if test="${user.status == 1}">
											<a class="btn btn-sm btn-white btn-primary" href="javascript:updateStatus('${user.sysUserId}',0);">启用</a>
										</c:if>
										<a class="btn btn-sm btn-white btn-danger" href="javascript:deleteUser('${user.sysUserId}');">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div><!-- /.span -->
				<%--分页start--%>
				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info mar-left-15" id="dynamic-table_info" role="status" aria-live="polite">共 ${pageInfo.total} 条</div>
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
			</div><!-- /.row -->
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
										<select class="chosen-select col-xs-10 col-sm-12" id="user_type" name="userType">
											<s:option flag="true" gcode="PLF_TYPE" link="false" />
										</select>
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
									<label class="col-sm-2 control-label no-padding-right"> 备注： </label>
									<div class="col-sm-10">
										<textarea class="limited col-xs-10 col-sm-12"  id="remark" maxlength="50"></textarea>
									</div>
								</div>
								<h5 class="header smaller lighter blue">基本信息</h5>
								<div class="form-group">
									<label class="col-sm-2 control-label no-padding-right" for="real_name"> 姓名： </label>
									<div class="col-sm-4">
										<input type="text" name="realName" id="real_name" placeholder="姓名" class="col-xs-10 col-sm-12" />
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
										<input type="email" name="email" id="email" placeholder="邮箱" class="validate[custom[email]] col-xs-10 col-sm-12" />
									</div>
									<label class="col-sm-2 control-label no-padding-right" for="mobile_phone"> 手机： </label>
									<div class="col-sm-4">
										<input type="text" name="mobilePhone" id="mobile_phone" placeholder="手机" class="validate[custom[phone]] col-xs-10 col-sm-12" />
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
