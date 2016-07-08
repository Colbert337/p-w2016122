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
	$('#userForm').bootstrapValidator({
		message: 'This value is not valid',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			userName: {
				validators: {
					notEmpty: {
						message: '用户名不能为空'
					}
				}
			},
			password: {
				validators: {
					notEmpty: {
						message: '支付密码不能为空'
					},
					stringLength: {
						min: 6,
						message: '密码不能小于6个字符'
					}
				}
			},
			rePassword: {
				validators: {
					notEmpty: {
						message: '确认密码不能为空'
					},
					stringLength: {
						min: 6,
						message: '密码不能小于6个字符'
					},
					callback: {
						message: '密码不一致',
						callback: function (value, validator, $field) {
							if($("[name=password]").val() != value){
								return false;
							}
							return true;
						}
					}
				}
			},
			avatarB: {
				validators: {
					notEmpty: {
						message: '用户角色不能为空'
					}
				}
			},
			realName: {
				validators: {
					notEmpty: {
						message: '姓名不能为空'
					}
				}
			}
		}
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
		/*$("#userModel").modal('show');*/
		$("#editUserDiv").text("添加用户");
		queryRoleList();
		queryUserTypeList("");
		$("#user_name").removeAttr("readonly");
		$("#user_name").on("blur",function(){
			isUserExit();
		});
		$("#userModel").on('hidden.bs.modal', function() {
			$('#userForm').bootstrapValidator('resetForm',true);
			$('.user-name-valid').remove();
		});
	}
	//显示编辑用户弹出层
	function queryRoleList(roleId){
		$.ajax({
			url:"<%=basePath%>/web/permi/user/list/role",
			data:{},
			async:false,
			type: "POST",
			success: function(data){
				$("#avatar_b").append("<option value=''>--选择角色--</option>");
				$.each(data,function(i,val){
					if(val.sysRoleId == roleId){
						$("#avatar_b").append("<option value='"+val.sysRoleId+"' selected='selected'>"+val.roleName+"</option>");
					}else{
						$("#avatar_b").append("<option value='"+val.sysRoleId+"'>"+val.roleName+"</option>");
					}
				})
			}
		})
		$("#userModel").modal('show');
	}

	//显示编辑用户弹出层
	function queryUserTypeList(userType){
		$.ajax({
			url:"<%=basePath%>/web/usysparam/info",
			data:{mcode:userType},
			async:false,
			type: "POST",
			success: function(data){
				$("#mname").text(data.mname);
				$("#user_type").val(data.mcode);
			}
		})
		$("#userModel").modal('show');
	}

	/*取消弹层方法*/
	function closeDialog(divId){
		jQuery('#userForm').validationEngine('hide');//隐藏验证弹窗
		$("#userForm :input").each(function () {
			$(this).val("");
		});
		$("#avatar_b").empty();
		$("#"+divId).modal('hide');
	}
	function clearDiv(){
		$("#roleForm :input").each(function () {
			$(this).val("");
		});
		$("#avatar_b").empty();
	}
	/**
	 * 保存用户信息
	 */
	function saveUser(){
		$('#userForm').data('bootstrapValidator').validate();
		if(!$('#userForm').data('bootstrapValidator').isValid()){
			return ;
		}
		if($('.user-name-valid').is(':visible')){
			return ;
		}

		var saveOptions = {
			url: '<%=basePath%>/web/permi/user/save',
			type: 'post',
			dataType: 'html',
			success: function (data) {
				$("#main").html(data);
				$("#modal-table").modal("show");
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {

			}
		}
		$("#userForm").ajaxSubmit(saveOptions);

		$("#userModel").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
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
				$("#editUserDiv").text("修改用户");
				$("#user_name").off("blur");
				$('.user-name-valid').remove();

				if(data.gender == 0){
					$("#gender_b").attr("checked","checked");
					$("#gender_g").removeAttr("checked");
				}else if(data.gender == 1){
					$("#gender_g").attr("checked","checked");
					$("#gender_b").removeAttr("checked");
				}

				$("#email").val(data.email);
				$("#mobile_phone").val(data.mobilePhone);
				/*密码输入框改为只读*/
				$("#password").val(data.password);
				$("#re_password").val(data.password);
				/*密码输入框改为可编辑*/
				$("#user_name").attr("readonly","readonly");
				/*$("#password").attr("readonly","readonly");
				$("#re_password").attr("readonly","readonly");*/
				queryRoleList(data.sys_role_id);
				queryUserTypeList(data.userType);
			}
		});

	}

	/**
	 * 删除用户
	 */
	function deleteUser(userId){
		bootbox.setLocale("zh_CN");
		bootbox.confirm("确认要删除用户吗？", function (result) {
			if (result) {
				var deleteOptions ={
					url:'<%=basePath%>/web/permi/user/delete',
					data:{userId:userId},
					type:'post',
					dataType:'text',
					success:function(data){
						$("#main").html(data);
						$('[data-rel="tooltip"]').tooltip();
					}
				}
				$("#listForm").ajaxSubmit(deleteOptions);
		}
	})

	}
	/**
	 * 修改用户状态 0 启用 1 禁用
	 * @param userId
	 */
	function updateStatus(userId,status){
		var alertStr = "确定要禁用该用户吗？";
		if(status == 0){
			alertStr = "确定要启用该用户吗？";
		}
		bootbox.setLocale("zh_CN");
		bootbox.confirm(alertStr, function (result) {
			if (result) {
			 var deleteOptions ={
			 url:'<%=basePath%>/web/permi/user/update/staruts',
			 data:{sysUserId:userId,status:status},
			 type:'post',
			 dataType:'text',
				 success:function(data){
					 $("#main").html(data);
					 $('[data-rel="tooltip"]').tooltip();
				 }
			 }
			 $("#listForm").ajaxSubmit(deleteOptions);
			}
		})
	}

	/**
	 * 判断用户名是否存在
	 */
	function isUserExit(){
		var userName = $("#user_name").val().replace(/\s/g,'');
		$.ajax({
			url:"<%=basePath%>/web/permi/user/info/isUserName",
			data:{userName:userName},
			//async:false,
			type: "POST",
			success: function(data){
				if(data.valid){
					if($('.user-name-valid').is(':visible')){
						return false;
					}
					$('#user_name').after('<div class="tooltip fade top in user-name-valid"><div class="tooltip-arrow"></div><div class="tooltip-inner">用户名已存在!</div></div>');
				} else {
					$('.user-name-valid').remove();
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {

			}
		});
	}
	//重置
	function init(){
		loadPage('#main', '../web/permi/user/list/page');
	}
</script>
<div class="page-header">
	<h1>
		用户管理
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
							<input type="text" name="userName" placeholder="账号/姓名/联系电话"  maxlength="15" value="${sysUser.userName}"/>
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
							<button class="btn btn-sm btn-primary" type="button" onclick="addUser();">
								新建
							</button>
						</div>
					</div>
					<div class="sjny-table-responsive">
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th>账号</th>
							<th>姓名</th>
							<th class="hidden-480">性别</th>
							<th>邮箱</th>
							<th class="hidden-480">联系电话</th>
							<th>用户角色</th>
							<th>用户类型</th>
							<th>用户状态</th>
							<th class="td-w2">创建时间</th>
							<th class="text-center td-w2">操作</th>
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
									<a class="" href="javascript:editUser('${user.sysUserId}');" title="修改" data-rel="tooltip">
										<span class="ace-icon fa fa-pencil bigger-130"></span>
									</a>
									<span class="span-state">
										<c:if test="${user.status == 0}">
											<a class="green" href="javascript:updateStatus('${user.sysUserId}',1);" title="禁用" data-rel="tooltip">
												<span class="ace-icon fa fa-unlock bigger-130"></span>
											</a>
										</c:if>
										<c:if test="${user.status == 1}">
											<a class="red" href="javascript:updateStatus('${user.sysUserId}',0);" title="启用" data-rel="tooltip">
												<span class="ace-icon fa fa-lock bigger-130"></span>
											</a>
										</c:if>
									</span>
									<a class="" href="javascript:deleteUser('${user.sysUserId}');" title="删除" data-rel="tooltip">
										<span class="ace-icon fa fa-trash-o bigger-130"></span>
									</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
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
<%--提示弹层--%>
<jsp:include page="/common/message.jsp"></jsp:include>

<!--添加用户弹层-开始-->
<div id="userModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="editUserDiv">编辑用户</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="userForm">
								<!-- #section:elements.form -->
								<h5 class="header smaller lighter blue no-margin-top">账户信息</h5>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right" for="user_name"><span class="red_star">*</span> 用户名:</label>
											<div class="col-sm-8">
												<input type="text" name="userName" id="user_name" placeholder="用户名" class="form-control" />
												<input type="hidden" name="sysUserId" id="sys_user_id" />
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<label class="col-sm-4 control-label no-padding-right" for="user_type">用户类型:</label>
										<div class="col-sm-8">
											<span id="mname" class="form-control"></span>
											<input type="hidden" id="user_type" name="userType"/>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right" for="password"><span class="red_star">*</span> 用户密码:</label>
											<div class="col-sm-8">
												<input type="password" name="password" id="password" placeholder="用户密码" class="form-control" />
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right" for="re_password"><span class="red_star">*</span> 确认密码:</label>
											<div class="col-sm-8">
												<input type="password" name="rePassword" id="re_password" placeholder="确认密码" class="form-control" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right" for="user_type"><span class="red_star">*</span> 用户角色:</label>
											<div class="col-sm-8">
												<select class="chosen-select form-control" id="avatar_b" name="avatarB"></select>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> 备注:</label>
											<div class="col-sm-8">
												<textarea class="limited form-control"  id="remark" name="remark" maxlength="50" style="resize: none;"></textarea>
											</div>
										</div>
									</div>
								</div>
								<h5 class="header smaller lighter blue">基本信息</h5>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right" for="real_name">  <span class="red_star">*</span>姓名:</label>
											<div class="col-sm-8">
												<input type="text" name="realName" id="real_name" placeholder="姓名" class="form-control" />
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> 性别:</label>
											<div class="col-sm-8">
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
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right" for="email"> 邮箱:</label>
											<div class="col-sm-8">
												<input type="email" name="email" id="email" placeholder="邮箱" class="form-control" />
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right" for="mobile_phone"> 手机:</label>
											<div class="col-sm-8">
												<input type="text" name="mobilePhone" id="mobile_phone" placeholder="手机" class="form-control" />
											</div>
										</div>
									</div>
								</div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" type="submit" onclick="saveUser()">确   定</button>
				<button class="btn btn-sm" onclick="closeDialog('userModel')">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加用户弹层-结束-->

