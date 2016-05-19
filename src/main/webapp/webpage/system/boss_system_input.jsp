<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
	//保存系统信息
	function saveBossSystem(){
		if($("#pems_name").val() == ""){
			warnDialog("请输入平台账号！");
			return false;
			$("#pems_name").focus();
		}else if($("#pems_pwd").val() == ""){
			warnDialog("请输入平台密码！");
			return false;
			$("#pems_pwd").focus();
		}else{
			$("#bossSystemSaveForm").submit();
		}
	}
	/* 取消 */
	function cancel(){
		window.location.href="<%=basePath%>/web/bossSystem/queryAllSystem";
	}
</script>
<div class="page-header">
	<h1>
		模块管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			系统菜单
		</small>
	</h1>
</div>
<div class="row">
 <form class="form-horizontal" id="bossSystemSaveForm" action="<%=basePath%>/web/bossSystem/saveSystem" method="post" enctype="multipart/form-data">
    <div class="col-xs-12">
	    	<div class="form-group">
				<label class="col-xs-1 control-label no-padding-right" > 集团名称： </label>
				<div class="col-xs-5">
					<input type="text" style="width: 300px" id="sys_systemName" value="${bossSystem.systemName}" name="systemName" onchange ="changeMenuName()" placeholder="集团名称" class="col-xs-10">
					<input id="sys_systemId" name="systemId" value="${bossSystem.systemId}" type="hidden"/>
				</div>
				<label class="col-xs-1 control-label no-padding-right" > 管理者： </label>
				<div class="col-xs-5">
					<input type="text" style="width: 300px" value="${bossSystem.manager}" id="sys_manager" name="manager" placeholder="管理者" class="col-xs-10">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-1 control-label no-padding-right" > 联系电话： </label>
				<div class="col-xs-5">
					<input type="text" style="width: 300px" id="sys_phoneNumber" value="${bossSystem.phoneNumber}" name="phoneNumber" onchange ="changeMenuName()" placeholder="联系电话" class="col-xs-10">
				</div>
				<label class="col-xs-1 control-label no-padding-right" > 状态： </label>
				<div class="col-xs-5">
					<label>
					启用
				       <input type="radio" name="systemStatus"<c:if test='${bossSystem.systemStatus == 1 || bossSystem.systemStatus == "" || bossSystem.systemStatus == null}'>checked="checked"</c:if> value="1">
				   	</label>
				   	<label>
				   	禁用
				       <input type="radio" name="systemStatus" <c:if test='${bossSystem.systemStatus == 0}'>checked="checked"</c:if>  value="0">
				   	</label>
				</div>
			</div>
	</div>
	
	<div class="col-xs-12">
		<label class="col-xs-1 control-label no-padding-right" > 平台账号： </label>
		<div class="col-xs-5">
			<input type="text" style="width: 300px" id="pems_name" onchange="checkSystemName()" value="${bossSystem.pemsName}" name="pemsName" placeholder="平台账号" class="col-xs-10 col-sm-5">
			<input type="hidden" value="${bossSystem.userId}" name="userId" id="user_userId">
		</div>
		<label class="col-xs-1 control-label no-padding-right" > 密    码： </label>
		<div class="col-xs-5">
			<input type="password" style="width: 300px" id="pems_pwd" name="pemsPwd" placeholder="密    码" value="${bossSystem.pemsPwd}" class="col-xs-10 col-sm-5">
		</div>
	</div>
	
	<div class="col-xs-12">
	系统菜单：
		<label>
			<c:forEach items="${bosslist}" var ="menu">
			<input type="checkbox" name="menuId" checked="checked" value="${menu.menuId}">
				${menu.menuName}
	        </c:forEach>
	   	</label>
	   
	</div>
	</form> 
	<div class="col-xs-12">  
		<button class="btn btn-primary" data-toggle="button" onclick="saveBossSystem();">
			保存
		</button>
		<button class="btn" data-toggle="button" onclick="cancel();">
			取消
		</button>
	</div>
</div>
