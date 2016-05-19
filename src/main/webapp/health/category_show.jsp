<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String categoryId = (String)request.getAttribute("categoryId");
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/tree/dhtmlxtree.css">
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>/assets/tree/tree.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>/assets/tree/dhtmlxtree.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=basePath%>/assets/tree/dhtmlxcommon.js"></script>

<script type="text/javascript">
	var tempid = "" ;
	function add_category(){
		var pcategoryId = tempid ;
		$("#pcategoryId").val(pcategoryId);
		if(pcategoryId != ""){
			//dialog-addCategory
			var d = dialog({
				width:600,
				height:350,
				top:'2%',
				title: '添加分类',
				content: $("#dialog-addCategory"),
				fixed: false,
				drag: true,
					onclose: function () {
						clean_Category();
				    }
				});
				d.showModal();
		}else{
			warnDialog("请选择上级分类");
		}
	}
	
	function saveHealthCategory(){
		if(jQuery('#categoryform').validationEngine('validate')){
			var categoryName = $("#categoryName").val();
			var pcategoryId = $("#pcategoryId").val();
			var listorder = $("#listorder").val();
			var sm = $("#sm").val();
			var wh = $("#wh").val();
			
			//alert(categoryId + "--" + categoryName + "--" + pcategoryId + "===" + sm + "===" + wh);
			<%-- --%>
			$.ajax({
			   	type: "POST",
			   	data:{pcategoryId:pcategoryId,categoryName:categoryName,sm:sm,wh:wh,listorder:listorder},
			   	async:false,
			   	url: "<%=basePath%>/web/health/saveHealth",
			   	success: function(data){
			   		window.location.reload();
			   	}
			});
			
		}
	}
	
	function clean_Category(){
		$("#categoryId").val("");
		$("#categoryName").val("");
		$("#pcategoryId").val("");
		$("#sm").val("");
		$("#wh").val("");
	}
	
</script>
<div class="page-header">
	<h1>
		文档管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			保健管理
		</small>
	</h1>
</div>
<div class="row">
	<div class="col-xs-2">
 		<table width="100%" height="95%">
	      	<tr>
	        	<td align="left" style="display:" id="left"  valign="top" width="220px" height="100%" onmousemove="m_move(this, 0, 'hsezzssfl_tree')" onmousedown="setXY()">
	          		<div id="health_tree" 
	            		style="width: 220px;min-height: 530px; border: 1px solid Silver;; overflow: auto;background-color: #fffdf1" >
	          		</div>
	        	</td>
	      	</tr>
	    </table>
	    <script type="text/javascript">

		    var tree = new dhtmlXTreeObject("health_tree","100%","100%","-1");
			tree.setImagePath("<%=basePath%>/assets/images/tree/");
			tree.setXMLAutoLoading("<%=basePath %>/web/health/loadCategoryTree");
			tree.loadXML("<%=basePath %>/web/health/loadCategoryTree");
			
			//tree.enableDragAndDrop(1);
			tree.setOnClickHandler(onNodeClicked);
			
			function onNodeClicked(temp) {
				selectedId = temp;
				ids = temp.split(":");
				id = ids[1];
				tempid = id ;
				if(id == '11111111111111111111111111111111'){
			  		window.open("<%=basePath%>/web/health/openHealth?categoryId=","right");
			  	}else{
			  		window.open("<%=basePath%>/web/health/openHealth?categoryId="+id,"right");
			  	}
			}
	    </script>
    </div>
    <div class="col-xs-9">
		<table width="100%" height="95%">
	      	<tr>
	        	<td valign="top" align="left" height="100%">
	        		<div class="pull-right btn-bottom">
					    <button class="btn btn-xs btn-primary" onclick="add_category();">添加分类</button>
					</div>
	          		<iframe align='top' id='right' name='right' width='100%' style="margin-left: 10px;min-height:500px;"
			            src='<%=basePath%>/web/health/openHealth?categoryId=<%=categoryId%>'
			            scrolling="auto" frameborder='0'>
		            </iframe>
	        	</td>
	      	</tr>
	    </table>
    </div>
</div>
<!--复制弹层-开始 -->
<div id="dialog-addCategory" class="all-hidden" style="display: none;">
	<form action=""  id="categoryform" method="post" enctype="multipart/form-data">
			<table class="dialog-tbl" width="100%" cellpadding="1" cellspacing="1" border="0">
			<tr height="35px;">
				<td width="10%">分类名称</td>
				<td width="40%">
					<div align="left">
					<input type="hidden" name="categoryId" id="categoryId" value="">
					<input type="hidden" name="pcategoryId" id="pcategoryId" value="">
					<input type="text" class="validate[required] dialog-txt" style="width: 85%" name="categoryName" id="categoryName" value=""
						data-errormessage-value-missing="分类名称不能为空!">
					</div>
				</td>
				<td width="10%">序号</td>
				<td width="40%">
					<div align="left">
					<input type="text" class="dialog-txt" style="width: 50%" name="listorder" id="listorder" value="100">
					</div>
				</td>
			</tr>
			<tr height="70px;">
				<td width="10%">说明</td>
				<td width="40%" colspan="3">
					<div align="left">
					<textarea rows="3" class="dialog-txtar" cols="" name="sm" id="sm" style="width: 90%"></textarea>
					</div>
				</td>
			</tr>
			<tr height="70px;">
				<td width="10%">危害</td>
				<td width="40%" colspan="3">
					<div align="left">
					<textarea rows="3" class="dialog-txtar" cols="" name="wh" id="wh" style="width: 90%"></textarea>
					</div>
				</td>
			</tr>
		</table>
		<div class="fl-l dialog-ok" style="margin-left:140px;" onclick="saveHealthCategory();">确定</div>
		<div class="fl-l dialog-cancel" style="margin-right:0px;" i="close" onclick="clean_Category();">取消</div>
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
});
</script>

