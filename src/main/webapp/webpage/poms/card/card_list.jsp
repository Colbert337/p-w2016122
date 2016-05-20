<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">

//修改权限
function updateSystem(systemId){
	window.location = "<%=basePath %>/web/bossSystem/updateMenuRole?systemId="+systemId;
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
    <div class="col-xs-12">
<!--     <button class="btn btn-xs btn-success" onclick="addSystem()">
		新增
	</button> -->
		<h4 class="lighter text-muted">
             <i class="icon icon-list blue"></i>
             	园所权限管理列表
         </h4>
         <table id="simple-table" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>集团名称</th>
					<th>管理者</th>
					<th>状态</th>
					<th>联系电话</th>
					<th>时间</th>
					<th>操作</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${sysList}" var ="sys">
					<tr>
						<td>
							${sys.systemName}
						</td>
						<td>${sys.manager}</td>
						<td><c:if test="${sys.systemStatus == 1}">启用</c:if><c:if test="${sys.systemStatus == 0}">禁用</c:if></td>
						<td>${sys.phoneNumber}</td>
						<td><fmt:formatDate value="${sys.createdDate}" type="both" pattern="yyyy-MM-dd"/></td>
						<td>
							<div class="hidden-sm hidden-xs btn-group">
	
								<button class="btn btn-xs btn-info" onclick="updateSystem('${sys.systemId}')">
									修改权限
								</button>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!--删除菜单信息的弹层-开始-->
		<div id="deletesystemDiv" class="all-hidden">
			<input id="deleteSystemId" type="hidden"/>
			<div style="margin-top: 50px;margin-bottom:10px;text-align: center;font-size: 25px;color: red">您确定要禁用此系统信息？ </div>
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="deleteBySystemId()">禁   用</button></div>
				<div class="col-xs-6"><button class="btn" i="close">取   消 </button></div>
			</div>
			<div class="clear"></div>
		</div>
	<!--弹层-结束-->
	
	<!--启用信息的弹层-开始-->
		<div id="qiyongsystemDiv" class="all-hidden">
			<input id="qiyongSystemId" type="hidden"/>
			<div style="margin-top: 50px;margin-bottom:10px;text-align: center;font-size: 25px;color: red">您确定要启用此系统信息？ </div>
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="qiyongBySystemId()">启   用</button></div>
				<div class="col-xs-6"><button class="btn" i="close">取   消 </button></div>
			</div>
			<div class="clear"></div>
		</div>
	<!--弹层-结束-->