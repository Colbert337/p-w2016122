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
<link href="<%=basePath %>/assets/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<script src="<%=basePath %>/assets/zTree/js/jquery.ztree.all-3.5.min.js"/>
<script type="text/javascript">

	$(function() {
		/*左侧树初始化 开始*/
		var zTreeObj;
		// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
		var setting = {
			view: {
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: queryMenuClick
			}
		};
		var zNodes = [];
		//初始化左侧树
		$.ajax({
			url:"<%=basePath%>/web/permi/function/list",
			data:{},
			async:false,
			type: "POST",
			success: function(data){
				zNodes = data;
			}
		});
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		zTreeObj.expandAll(true);//展开所有节点

		function zHeight() {
			var ztreeHeight = $(window).height()-160;
			var tableHeight = $("#simple-table").height();
			if(tableHeight>ztreeHeight) {
				var h = tableHeight;
			} else {
				var h = ztreeHeight;
			}
			$("#treeDemo").height(h);
		}
		zHeight();
		$(window).resize(function(){
			zHeight();
		});

		/**
		 * 获取当前节点下菜单
		 */
		function queryMenuClick(event, treeId, treeNode){
			/*bootbox.alert(treeNode.id + ", " + treeNode.name);*/
			var queryMenuOptions ={
				url:'<%=basePath%>/web/permi/function/list/page',
				data:{parentId:treeNode.id},
				type:'post',
				dataType:'text',
				success:function(data){
					$("#main").html(data);
				}
			}
			$("#listForm").ajaxSubmit(queryMenuOptions);
		}
		/*左侧树初始化 结束*/
		/*表单验证*/
		jQuery('#functionForm').validationEngine('attach', {
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
		url:'<%=basePath%>/web/permi/function/list/page',
		type:'post',
		dataType:'html',
		success:function(data){
			$("#main").html(data);
		}
	}
	/*分页相关方法 end*/
	//显示添加用户弹出层
	function addFunction(functionId){
		if(functionId != null && functionId != ""){
			$("#parent_id").val(functionId);
		}
		//获取当前排序
		$.ajax({
			url:"<%=basePath%>/web/permi/function/info/sort",
			data:{},
			async:false,
			type: "POST",
			success: function(data){
				$("#function_sort").val(data);
			}
		});
		<%--bootbox.alert("parentId:${parentId} +parentName:${parentName}");--%>
		$("#parent_id").val('${parentId}');
		$("#parent_name").val('${parentName}');
		$("#functionModel").modal('show');
	}

	/*取消弹层方法*/
	function closeDialog(divId){
		jQuery('#functionForm').validationEngine('hide');//隐藏验证弹窗
		$("#functionForm :input").each(function () {
			$(this).val("");
		});
		$("#"+divId).modal('hide');
	}
	/**
	 * 保存用户信息
	 */
	function saveFunction(){
		if(jQuery('#functionForm').validationEngine('validate')){
			var saveOptions ={
				url:'<%=basePath%>/web/permi/function/save',
				type:'post',
				dataType:'html',
				success:function(data){
					$("#main").html(data);
					$("#modal-table").modal("show");
				}, error: function (XMLHttpRequest, textStatus, errorThrown) {

				}
			}
			$("#functionForm").ajaxSubmit(saveOptions);

			$("#userModel").modal('hide').removeClass('in');
			$("body").removeClass('modal-open').removeAttr('style');
			$(".modal-backdrop").remove();
		}
	}

	/**
	 * 回显用户信息
	 */
	function editFunction(functionId){
		$.ajax({
			url:"<%=basePath%>/web/permi/function/update",
			data:{sysFunctionId:functionId},
			async:false,
			type: "POST",
			success: function(data){
				$("#sys_function_id").val(data.sysFunctionId);
				$("#function_name").val(data.functionName);
				$("#function_path").val(data.functionPath);
				$("#parent_id").val(data.parentId);
				$("#parent_name").val('${parentName}');
				$("#function_type").val(data.functionType);
				$("#function_icon").val(data.functionIcon);
				$("#function_sort").val(data.functionSort);

				if(data.isMenu == 0){
					$("#is_menu_yes").attr("checked","checked");
					$("#is_menu_no").removeAttr("checked");
				}else if(data.isMenu == 1){
					$("#is_menu_no").attr("checked","checked");
					$("#is_menu_yes").removeAttr("checked");
				}

				$("#functionModel").modal('show');
			}
		});

	}

	/**
	 * 删除功能
	 */
	function deleteFunction(functionId,parentId){
		bootbox.setLocale("zh_CN");
		bootbox.confirm("确认要删除功能吗？", function (result) {
			if (result) {
				var deleteOptions ={
					url:'<%=basePath%>/web/permi/function/delete',
					data:{functionId:functionId,parentId:parentId},
					type:'post',
					dataType:'text',
					success:function(data){
						$("#main").html(data);
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
	function updateStatus(functionId,status){
		var alertStr = "确定禁用该功能吗？";
		if(status == 0){
			alertStr = "确定启用该功能吗？";
		}
		if(confirm(alertStr)){
			var deleteOptions ={
				url:'<%=basePath%>/web/permi/function/save',
				data:{sysFunctionId:functionId,functionStatus:status},
				type:'post',
				dataType:'text',
				success:function(data){
					$("#main").html(data);
				}
			}
			$("#listForm").ajaxSubmit(deleteOptions);
		}

	}

</script>

<div class="page-header">
	<h1>
		功能管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			功能列表
		</small>
	</h1>
</div><!-- /.page-header -->
<div class="row">
	<div class="col-xs-2">
		<div class="space"></div>
		<div class="space"></div>
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="col-xs-10">
		<form class="form-horizontal"  id="listForm">
			<jsp:include page="/common/page_param.jsp"></jsp:include>
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<div class="clearfix">
						<div class="pull-left">当前选择菜单：<span id="currMenu" style="color: #2679b5;font-size: 18px">${parentName}</span></div>
						<%--顶部按钮--%>
						<div class="pull-right btn-botton">
							<a class="btn btn-sm btn-primary" href="javascript:addFunction();">
								新建
							</a>
						</div>
					</div>
					<div class="sjny-table-responsive">
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th>菜单名称</th>
							<th>路径</th>
							<th class="hidden-480">图标</th>
							<th>类型</th>
							<th class="hidden-480">排序</th>
							<th class="td-w2">创建时间</th>
							<th class="text-center td-w3">操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${functionList}" var="function">
							<tr>
								<td>${function.functionName}</td>
								<td>${function.functionPath}</td>
								<td>${function.functionIcon}</td>
								<td><s:Code2Name mcode="${function.functionType}" gcode="PLF_TYPE"></s:Code2Name></td>
								<td class="hidden-480">${function.functionSort}</td>
								<td class="hidden-480"><fmt:formatDate value="${function.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
								<td class="text-center">
									<a class="option-btn-m" href="javascript:editFunction('${function.sysFunctionId}');" title="修改" data-rel="tooltip">
										<span class="ace-icon fa fa-pencil bigger-130"></span>
									</a>
									<a href="javascript:deleteFunction('${function.sysFunctionId}','${function.parentId}');" title="删除" data-rel="tooltip">
										<span class="ace-icon fa fa-trash-o bigger-130"></span>
									</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
				</div><!-- /.span -->
			</div><!-- /.row -->
		</form>
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->
<%--提示弹层--%>
<jsp:include page="/common/message.jsp"></jsp:include>

<!--添加用户弹层-开始-->
<div id="functionModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel">编辑功能</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="functionForm">
								<!-- #section:elements.form -->
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="function_name"> <span class="red_star">*</span>功能名称： </label>
									<div class="col-sm-8">
										<input type="text" id="function_name" name="functionName" placeholder="功能名称" class="validate[required,maxSize[20]] col-xs-10 col-sm-10" />
										<input type="hidden" id="sys_function_id" name="sysFunctionId" class="col-xs-10 col-sm-10" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="function_path"> <span class="red_star">*</span>功能路径： </label>
									<div class="col-sm-8">
										<input type="text" id="function_path" name="functionPath" placeholder="功能路径" class="validate[required,maxSize[50]] col-xs-10 col-sm-10" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="parent_id">父级菜单： </label>
									<div class="col-sm-8">
										<input type="text" id="parent_name" placeholder="父级菜单" class="col-xs-10 col-sm-10"  readonly="readonly"/>
										<input type="hidden" id="parent_id" name="parentId" placeholder="父级菜单"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="function_type"> <span class="red_star">*</span>功能类型： </label>
									<div class="col-sm-8">
										<select class="chosen-select col-xs-10 col-sm-10" id="function_type" name="functionType" data-placeholder="功能类型">
											<s:option flag="true" gcode="PLF_TYPE" link="false" />
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="function_icon">功能图标： </label>
									<div class="col-sm-8">
										<input type="text" id="function_icon" name="functionIcon" placeholder="功能图标" class="validate[required,maxSize[10]] col-xs-10 col-sm-10" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="function_sort">功能排序： </label>
									<div class="col-sm-8">
										<input type="text" id="function_sort" name="functionSort" placeholder="功能排序" class="validate[required,maxSize[3]] col-xs-10 col-sm-10" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="is_menu_yes">是否菜单： </label>
									<div class="col-sm-8">
										<div class="radio">
										<label>
											<input name="isMenu" id="is_menu_yes" type="radio" class="ace" checked="checked" value="0">
											<span class="lbl"> 菜单</span>
										</label>
										<label>
											<input name="isMenu" id="is_menu_no" type="radio" class="ace" value="1">
											<span class="lbl"> 按钮</span>
										</label>
									</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="function_desc"> 描述： </label>
									<div class="col-sm-8">
										<textarea class="limited col-xs-10 col-sm-10" id="function_desc" name="roleDesc" maxlength="50" style="resize: none;"></textarea>
									</div>
								</div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="saveFunction()">确   定</button>
				<button class="btn btn-sm" i="close" onclick="closeDialog('functionModel')">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加用户弹层-结束-->
