<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path;
%>
<link rel="stylesheet" href="<%=basePath %>/comm/css/comm.css" />
<script src="<%=basePath %>/assets/js/jquery-1.9.1.min.js"></script>
<!-- 弹层样式及插件 -->
<link type="text/css" href="<%=basePath %>/assets/artDialog/css/ui-dialog.css" rel="stylesheet" />
<script type="text/javascript"  src="<%=basePath %>/assets/artDialog/dist/dialog-plus-min.js"></script>

<!-- JqueryValidationEngine表单验证  -->
<link rel="stylesheet" type="text/css" href="<%=basePath %>/assets/jQueryVE/css/validationEngine.jquery.css" />
<script type="text/javascript" src="<%=basePath %>/assets/jQueryVE/js/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/jQueryVE/js/jquery.validationEngine.min.js"></script>
<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap.css" />
<link rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.css" />
<link rel="stylesheet" href="<%=basePath %>/assets/css/ace-fonts.css" />
<link rel="stylesheet" href="<%=basePath %>/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

<script type="text/javascript">
	function save_category(){
		if(jQuery('#categoryform').validationEngine('validate')){
			var categoryId = $("#categoryId").val();
			var categoryName = $("#categoryName").val();
			var pcategoryId = $("#pcategoryId").val();
			var listorder = $("#listorder").val();
			var sm = $("#sm").val();
			var wh = $("#wh").val();
			//alert(categoryId + "--" + categoryName + "--" + pcategoryId + "===" + sm + "===" + wh);
			<%-- --%>
			$.ajax({
			   	type: "POST",
			   	data:{categoryId:categoryId,pcategoryId:pcategoryId,categoryName:categoryName,sm:sm,wh:wh,listorder:listorder},
			   	async:false,
			   	url: "<%=basePath%>/web/health/updateHealth",
			   	success: function(data){
			   		//window.location.reload();
			   		//sucDialog("保存成功");
			   		//window.location.href = "<%=basePath%>/web/health/openHealth?categoryId=" + data;
			   		parent.window.location.href = "<%=basePath%>/web/health/showcategory?categoryId=" + data;
			   		//parent.window.location.reload();
			   	}
			});
			
		}
	}
	
	function delete_category(categoryId){
		//先检查该类目下面是否还有分类 ，有的话提示不能删除 
		var pcategoryId = $("#pcategoryId").val();
		$.ajax({
		   	//type: "POST",
		   	data:{categoryId:categoryId},
		   	async:false,
		   	url: "<%=basePath%>/web/health/checkHasChild",
		   	success: function(data){
		   		if(data == "1"){
		   			warnDialog("包含下级分类");
		   		}else if(data == "2"){
		   			//sucDialog("删除成功");
		   			parent.window.location.href = "<%=basePath%>/web/health/showcategory?categoryId=" + pcategoryId;
		   			//parent.window.location.reload();
		   		}else{
		   		}
		   		
		   	}
		});
	}
	
	//添加建议项目
	var tempid = "" ;
	function add_healthsub(categoryId){
		tempid = categoryId ;
		var mdd = dialog({
			width:600,
			height:350,
			top:'2%',
			title: '添加建议',
			content: $("#dialog-addHealthsub"),
			fixed: false,
			drag: true,
				onclose: function () {
					clean_Healthsub();
			    }
			});
			mdd.showModal();
			
	}
	//保存建议内容 
	function saveHealthsub(){
		if(jQuery('#healthsub').validationEngine('validate')){
			var pid = $("#pid").val();
			var name = $("#name").val();
			var order = $("#order").val();
			var reasion = $("#reasion").val();
			var advise = $("#advise").val();
			var categoryId2 = $("#categoryId2").val();
			if(tempid == ""){
				tempid = categoryId2 ;
			}
			$.ajax({
			   	type: "POST",
			   	data:{pid:pid,categoryId:tempid,name:name,order:order,reasion:reasion,advise:advise},
			   	async:false,
			   	url: "<%=basePath%>/web/healthsub/addsubinfo",
			   	success: function(data){
			   		window.location.reload();
			   	}
			});
		}
	}
	function deleteHealthsub(id){
		$.ajax({
		   	//type: "POST",
		   	data:{id:id},
		   	async:false,
		   	url: "<%=basePath%>/web/healthsub/deleteHealthsub",
		   	success: function(data){
		   		window.location.reload();
		   	}
		});
	}
	function openHealthsub(id){
		var dd = dialog({
			width:600,
			height:350,
			top:'2%',
			title: '添加建议',
			content: $("#dialog-addHealthsub"),
			fixed: false,
			drag: true,
				onclose: function () {
					clean_Healthsub();
			    }
			});
			dd.showModal();
			
			$.ajax({
			   	//type: "POST",
			   	data:{id:id},
			   	async:false,
			   	url: "<%=basePath%>/web/healthsub/queryHealthsub",
			   	success: function(data){
			   		if(data != null){
			   			$("#pid").val(data.id);
			   			$("#categoryId2").val(data.categoryId);
			   			$("#name").val(data.name);
			   			$("#order").val(data.listorder);
			   			$("#reasion").val(data.reasion);
			   			$("#advise").val(data.advise);
			   		}
			   	}
			});
			
	}
	
	function clean_Healthsub(){
		$("#pid").val("");
		$("#categoryId2").val("");
		$("#name").val("");
		$("#order").val("100");
		$("#reasion").val("");
		$("#advise").val("");
	}
	
</script>
<div class="col-xs-9">
	<form action=""  id="categoryform" method="post" enctype="multipart/form-data">
	<table style="min-width: 800px;margin-top: 0px;" class="sys-setup-tbl" cellpadding="0" cellspacing="0">
		<c:choose>
			<c:when test="${categoryId eq '11111111111111111111111111111111' }">
				<tr height="35px;" class="toptd"> 
					<td colspan="4">
						
					</td>
				</tr>
				<tr height="30px;">
					<td width="100%">
						<div>
						生长发育是儿童重要的特征之一。<br/>
						儿童生长发育状况 受家庭条件、 营养供给、卫生保健服务等多方面的影响。<br/>
						因此，儿童生长发育状况，作为儿童保健状况评价的生物学指标之一，是反映社会发展、经济文化状况、<br/>
						营养和卫生保健水平的一项重要综合指标。用不同年龄段、不同性别的健康儿童的身高、 体重测量值建立标准，<br/>
						与所检查儿童的身高、体重数值进行对照，衡量被检儿童生长发育的水平，这些用健康儿童的身高、<br/>
						体重测量值建立的标准值就是参考值。
						</div>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr height="35px;" class=""> 
					<td colspan="4">
						<div class="pull-right btn-bottom">
						    <button class="btn btn-xs btn-primary" onclick="save_category();">保存</button>
						    <button class="btn btn-xs btn-primary" onclick="delete_category('${health.categoryId}');">删除</button>
						    <button class="btn btn-xs btn-primary" onclick="add_healthsub('${health.categoryId}');">添加建议</button>
						</div>
					</td>
				</tr>
				<tr height="30px;">
					<td width="15%">分类名称</td>
					<td width="35%">
						<div align="left">
						<input type="hidden" name="categoryId" id="categoryId" value="${categoryId}">
						<input type="hidden" name="pcategoryId" id="pcategoryId" value="${health.pcategoryId}">
						<input type="text" class="validate[required] dialog-txt" style="width: 85%" name="categoryName" id="categoryName" 
							value="${health.categoryName}" data-errormessage-value-missing="分类名称不能为空!">
						</div>
					</td>
					<td width="15%">序号</td>
					<td width="35%">
						<div align="left">
						<input type="text" class="dialog-txt" style="width: 50%" name="listorder" id="listorder" value="${health.listorder}">
						</div>
					</td>
				</tr>
				<tr height="30px;">
					<td width="15%">说明</td>
					<td width="35%" colspan="3">
						<div align="left">
						<textarea rows="3" class="dialog-txtar" cols="" name="sm" id="sm" style="width: 90%">${health.sm}</textarea>
						</div>
					</td>
				</tr>
				<tr height="30px;">
					<td width="15%">危害</td>
					<td width="35%" colspan="3">
						<div align="left">
						<textarea rows="3" class="dialog-txtar" cols="" name="wh" id="wh" style="width: 90%">${health.wh}</textarea>
						</div>
					</td>
				</tr>
				<tr> 
					<td colspan="4">
						<table width="100%" class="std-table" cellpadding="0" cellspacing="0" style="margin: 0px;">
							<tr class="toptd" style="height: 25px;" > 
								<td>序号</td>
								<td>名称</td>
								<td>成因</td>
								<td>建议</td>
								<td>操作</td>
							</tr>
							<c:forEach items="${hslist}" var="healthsub">
								<tr>
									<td width="5%"><font size="2px;">${healthsub.listorder}</font></td>
									<td class="span-btn" width="25%">
										<div align="left" style="margin-left: 3px;">
											<font size="2px;">
											<a class="span-btn" onclick="openHealthsub('${healthsub.id}')" href="javascript:void(0)">${healthsub.name}</a>
											</font>
										</div>
									</td>
									<td width="30%">
										<div align="left" style="margin-left: 3px;">
											<font size="2px;">
											${healthsub.reasion}
											</font>
										</div>
									</td>
									<td width="30%">
										<div align="left" style="margin-left: 3px;">
											<font size="2px;">
											${healthsub.advise}
											</font>
										</div>
									</td>
									<td class="span-btn" width="10%">
										<font size="3px;">
											<a class="span-btn" onclick="deleteHealthsub('${healthsub.id}')" href="javascript:void(0)">删除</a>
										</font>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	</form>
</div>
<!--复制弹层-开始 -->
<div id="dialog-addHealthsub" class="all-hidden">
	<form action=""  id="healthsub" method="post" enctype="multipart/form-data">
			<table class="dialog-tbl" width="100%" cellpadding="1" cellspacing="1" border="0">
			<tr height="35px;">
				<td width="10%">名称</td>
				<td width="40%">
					<div align="left">
					<input type="hidden" name="pid" id="pid" value="">
					<input type="hidden" name="categoryId2" id="categoryId2" value="">
					<input type="text" class="validate[required] dialog-txt" style="width: 85%" name="name" id="name" value=""
						data-errormessage-value-missing="名称不能为空!">
					</div>
				</td>
				<td width="10%">序号</td>
				<td width="40%">
					<div align="left">
					<input type="text" class="dialog-txt" style="width: 50%" id="order" value="100">
					</div>
				</td>
			</tr>
			<tr height="70px;">
				<td width="10%">原因</td>
				<td width="40%" colspan="3">
					<div align="left">
					<textarea rows="3" class="dialog-txtar" cols="" name="reasion" id="reasion" style="width: 90%"></textarea>
					</div>
				</td>
			</tr>
			<tr height="70px;">
				<td width="10%">建议</td>
				<td width="40%" colspan="3">
					<div align="left">
					<textarea rows="3" class="dialog-txtar" cols="" name="advise" id="advise" style="width: 90%"></textarea>
					</div>
				</td>
			</tr>
		</table>
		<div class="fl-l dialog-ok" style="margin-left:140px;" onclick="saveHealthsub();">确定</div>
		<div class="fl-l dialog-cancel" style="margin-right:0px;" i="close" onclick="clean_Healthsub();">取消</div>
		<div class="clear"></div>
		<div style="height:30px;font-size:20px;"></div>
	</form>
</div>
<!--弹层-结束-->
<script type="text/javascript">
jQuery(document).ready(function(){
	$('#categoryform').validationEngine('attach', { 
		  promptPosition: 'topRight',		//提示信息的位置，可设置为：'topRight', 'topLeft', 'bottomRight', 'bottomLeft', 'centerRight', 'centerLeft', 'inline'
		  validationEventTrigger:'blur',	//触发验证的事件  	blur  onblur
		  binded:true						//是否绑定即时验证
		  //scroll: false 					//屏幕自动滚动到第一个验证不通过的位置
	}); 
	
	$('#healthsub').validationEngine('attach', { 
		  promptPosition: 'topRight',		//提示信息的位置，可设置为：'topRight', 'topLeft', 'bottomRight', 'bottomLeft', 'centerRight', 'centerLeft', 'inline'
		  validationEventTrigger:'blur',	//触发验证的事件  	blur  onblur
		  binded:true						//是否绑定即时验证
		  //scroll: false 					//屏幕自动滚动到第一个验证不通过的位置
	}); 
});

</script>
