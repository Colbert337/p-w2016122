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
		jQuery('#roleForm').validationEngine('attach', {
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
		url:'<%=basePath%>/web/permi/role/list/page',
		type:'post',
		dataType:'html',
		success:function(data){
			$("#main").html(data);
		}
	}
	/*分页相关方法 end*/
	//显示添加用户弹出层
	function addRole(){
		$("#roleModel").modal('show');
	}

	/*取消弹层方法*/
	function closeDialog(divId){
		jQuery('#roleForm').validationEngine('hide');//隐藏验证弹窗
		$("#roleForm :input").each(function () {
			$(this).val("");
		});
		$("#"+divId).modal('hide');
	}
	/**
	 * 保存用户信息
	 */
	function saveRole(){
		if(jQuery('#roleForm').validationEngine('validate')){
			var saveOptions ={
				url:'<%=basePath%>/web/permi/role/save',
				type:'post',
				dataType:'html',
				success:function(data){
					$("#main").html(data);
				}
			}
			$("#roleForm").ajaxSubmit(saveOptions);

			$("#roleModel").modal('hide');
			$(".modal-backdrop").css("display","none");
		}
	}

	/**
	 * 回显用户信息
	 */
	function editRole(roleId){
		$.ajax({
			url:"<%=basePath%>/web/permi/role/update",
			data:{sysRoleId:roleId},
			async:false,
			type: "POST",
			success: function(data){
				$("#sys_role_id").val(data.sysRoleId);
				$("#role_name").val(data.roleName);
				$("#role_type").val(data.roleType);
				$("#role_desc").val(data.roleDesc);

				$("#roleModel").modal('show');
			}
		});

	}

	/**
	 * 删除用户
	 */
	function deleteRole(roleId){
		var deleteOptions ={
			url:'<%=basePath%>/web/permi/role/delete',
			data:{roleId:roleId},
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
	function updateStatus(roleId,status){
		var deleteOptions ={
			url:'<%=basePath%>/web/permi/role/save',
			data:{sysRoleId:roleId,roleStatus:status},
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
		角色管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			角色列表
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
						<a class="btn btn-primary" href="javascript:addRole();">
							添加角色
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
							<th>角色名称</th>
							<th>角色类型</th>
							<th>角色状态</th>
							<th class="hidden-480">角色描述</th>
							<th>添加时间</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${roleList}" var="role">
							<tr>
								<%--<td class="center">
									<label class="pos-rel">
										<input type="checkbox" class="ace" />
										<span class="lbl"></span>
									</label>
								</td>--%>
								<td>${role.roleName}</td>
								<td><s:Code2Name mcode="${role.roleType}" gcode="PLF_TYPE"></s:Code2Name></td>
								<td>
									<c:if test="${role.roleStatus == 0}">
										启用
									</c:if>
									<c:if test="${role.roleStatus == 1}">
										禁用
									</c:if>
								</td>
								<td>${role.roleDesc}</td>
								<td><fmt:formatDate value="${role.createdDate}" type="both" pattern="yyyy-MM-dd"/></td>
								<td>
									<a class="btn btn-sm btn-white btn-primary" href="javascript:editRole('${role.sysRoleId}');">修改</a>
									<c:if test="${role.roleStatus == 0}">
										<a class="btn btn-sm btn-white btn-inverse" href="javascript:updateStatus('${role.sysRoleId}',1);">禁用</a>
									</c:if>
									<c:if test="${role.roleStatus == 1}">
										<a class="btn btn-sm btn-white btn-primary" href="javascript:updateStatus('${role.sysRoleId}',0);">启用</a>
									</c:if>
									<a class="btn btn-sm btn-white btn-danger" href="javascript:deleteRole('${role.sysRoleId}');">删除</a>
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
<div id="roleModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel">编辑角色</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="roleForm">
								<!-- #section:elements.form -->
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="role_name"> <span class="red_star">*</span>角色名称： </label>
									<div class="col-sm-8">
										<input type="text" id="role_name" name="roleName" placeholder="角色名称" class="validate[required,minSize[3],custom[onlyLetterNumber]] col-xs-10 col-sm-10" />
										<input type="hidden" id="sys_role_id" name="sysRoleId" class="col-xs-10 col-sm-10" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="role_type"> <span class="red_star">*</span>角色类型： </label>
									<div class="col-sm-8">
										<select class="chosen-select col-xs-10 col-sm-10" id="role_type" name="roleType" data-placeholder="角色类型">
											<s:option flag="true" gcode="PLF_TYPE" link="false" />
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" for="role_desc"> 描述： </label>
									<div class="col-sm-8">
										<textarea class="limited col-xs-10 col-sm-10" id="role_desc" name="roleDesc" maxlength="50"></textarea>
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
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveRole()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('roleModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加用户弹层-结束-->