<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link href="<%=basePath %>/assets/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<script src="<%=basePath %>/assets/zTree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
	function addEvaluate(){
		$("#evaluateForm").submit();
	}
</script>
<div class="page-header">
	<h1>
		评价管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			评价设置
		</small>
	</h1>
</div>
<div class="row">
    <div class="col-xs-12">
    	 <div class="pull-right btn-bottom">
    	 	<button class="btn btn-xs btn-primary" onclick="addEvaluate()">保存评价</button>
    	 </div>
    	 <form id="evaluateForm" name="evaluateForm" action="<%=basePath %>/web/evaluate/item/update" method="post">
	         <table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr style="text-align: center;">
						<th>测量范围</th>
						<th>
							<c:if test="${itemType == 1}">身高别年龄评价</c:if>
							<c:if test="${itemType == 2}">体重别年龄评价</c:if>
							<c:if test="${itemType == 3}">BMI评价</c:if>
						</th>
						<th>等级<input id="itemType" name="itemType" type="hidden" value="${itemType }"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${itemEvaluateList}" var ="itemEvaluate" varStatus="itemEvaluateSta">
						<tr>
							<td>${itemEvaluate.standard }
								<input type="hidden" id="itemEvaluationId" name="itemEvaluationId" 
								value="${itemEvaluate.bossItemEvaluationId }"/>
							</td>
							<td><textarea id="evaluate" name="evaluate" 
							rows="" cols="100">${itemEvaluate.evaluate }</textarea> </td>
							<td>
								<c:choose>
									<c:when test="${itemEvaluate.level == 1}">
										高
									</c:when>
									<c:when test="${itemEvaluate.level == 2}">
										中
									</c:when>
									<c:when test="${itemEvaluate.level == 3}">
										低
									</c:when>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</div>

<!--弹层-开始-->
<div id="add_menu_div" class="all-hidden container col-xs-12">
	<!--弹层内容 -->
	<div class="b-dialog-content">
		<form id="menuSaveForm" class="form-horizontal" action="<%=basePath%>/web/bossMenu/saveMenu" method="post" enctype="multipart/form-data">
			
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 所属菜单： </label>
				<div class="col-sm-9">
					<span id="suoshu_menu"></span>
					<input id="menu_suoshu_menu" name="parentId" type="hidden"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 菜单名称： </label>
				<div class="col-sm-9">
					<input type="text" style="width: 300px" onblur="getMenuCode()" id="menu_menuName" name="menuName" onchange ="changeMenuName()" placeholder="菜单名称" class="col-xs-10 col-sm-5">
					<input id="menu_menuId" name="menuId" type="hidden"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 菜单简写： </label>
				<div class="col-sm-9">
					<input type="text" style="width: 300px" id="menu_menuCode" name="menuCode" placeholder="菜单简写" class="col-xs-10 col-sm-5">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 菜单描述： </label>
				<div class="col-sm-9">
					<input type="text" style="width: 300px" id="menu_menu_desc" name="menuDesc" placeholder="菜单描述" class="col-xs-10 col-sm-5">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 路径 ：</label>
				<div class="col-sm-9">
					<input type="text" style="width: 300px" id="menu_menu_path" name="menuPath" placeholder="路径" class="col-xs-10 col-sm-5">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 顺序 ：</label>
				<div class="col-sm-9">
					<input type="text" style="width: 300px" id="menu_menu_sort" name="menuSort" placeholder="顺序" class="col-xs-10 col-sm-5">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 图片 ：</label>
				<div class="col-sm-9">
					<input type="file" style="width: 300px" id="menu_menu_icon" name="file" placeholder="图片" class="col-xs-10 col-sm-5">
				</div>
			</div>
		</form>
	</div>
	<!--底部按钮 -->
	<div class="row">
		<div class="col-xs-3"></div>
		<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="saveMenu()">确   定</button></div>
		<div class="col-xs-6"><button class="btn" i="close" onclick="cleanBossMenu()">取   消 </button></div>
	</div>
</div>
<!--弹层-结束-->
<!--删除菜单信息的弹层-开始-->
		<div id="deleteBossMenuDiv" class="all-hidden">
			<input id="deleteBossMenuId" type="hidden"/>
			<div style="margin-top: 50px;margin-bottom:10px;text-align: center;font-size: 25px;color: red">您确定要删除此菜单信息？ </div>
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="deleteByMenuId()">确   定</button></div>
				<div class="col-xs-6"><button class="btn" i="close" >取   消 </button></div>
			</div>
			<div class="clear"></div>
		</div>
	<!--弹层-结束-->