<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link href="<%=basePath %>/assets/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<script src="<%=basePath %>/assets/zTree/js/jquery.ztree.all-3.5.min.js"></script>
<script src="<%=basePath %>/assets/plupload/js/plupload.full.min.js"></script>
<script type="text/javascript">
	var setting = {  
	        isSimpleData : true,              //数据是否采用简单 Array 格式，默认false  
	        treeNodeKey : "id",               //在isSimpleData格式下，当前节点id属性  
	        treeNodeParentKey : "pId",        //在isSimpleData格式下，当前节点的父节点id属性  
	        showLine : true,                  //是否显示节点间的连线  
	        checkable : true,                  //每个节点上是否显示 CheckBox  
	        callback: {
	        	onClick: zTreeOnClick //设置节点点击前事件
	        }
	    };  
	    
	var zTreeObj;  
	var zTreeNodes;
	var parentId = '${menuId}';
	var rootId = '${rootId}';
	
	$(function(){  
		
	     $.ajax({  
	        async : false,  
	        type: 'POST',  
	        dataType : "json",  
	        url:"<%=basePath %>/web/bossDoc/queryBossDocTree",
	        error: function () {//请求失败处理函数  
	        	failDialog("请求失败！"); 
	        },  
	        success:function(data){ //请求成功后处理函数。  
	        	zTreeNodes = data.tree;   //把后台封装好的简单Json格式赋给treeNodes  
	        }  
	    });   
	    zTreeObj = $.fn.zTree.init($("#leftTree"), setting, zTreeNodes);
	    
	    var node = zTreeObj.getNodeByParam("pId",parentId);   //zTreeObj是tree对象   选中 
	    var node2 = zTreeObj.getNodeByParam("id",parentId);   //zTreeObj是tree对象   选中 
	    zTreeObj.selectNode(node);
	    zTreeObj.expandNode(node,true,false,false);//展开节点
	    zTreeObj.selectNode(node2);
	    zTreeObj.expandNode(node2,true,false,false);
	});
	
	//树节点的点击事件
	function zTreeOnClick(event, treeId, treeNode) {
		//点击树节点  加载右侧的表格
		var pid = treeNode.pId;//获取第一级id
		var rootId = treeNode.rootId;//获取根节点id
		var id = treeNode.id;
		//如果第一级不为空
		if(pid != null && pid != '' && pid != undefined){
			//查询下级
			  window.location = "<%=basePath %>/web/bossDoc/queryNextDoc?parentId="+pid;
		}
		if(rootId != null && rootId !='' && rootId !=undefined){
			//查询所有菜单
			window.location = "<%=basePath %>/web/bossDoc/queryAllBossDoc";
		}
		if(id != null && id !='' && id !=undefined){
			//查询所有菜单
			window.location = "<%=basePath %>/web/bossDoc/queryNextDoc?parentId="+id;
		}
		
	};
	
	
	var uploader;
	$(function(){
		
		//实例化一个plupload上传对象
	    uploader = new plupload.Uploader({
	        browse_button : 'browse', //触发文件选择对话框的按钮，为那个元素id
	        url : '<%=basePath %>/web/bossDoc/uploadBossDoc',    
	        flash_swf_url : 'js/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
	        silverlight_xap_url : 'js/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
	    	filters: {
	  		  prevent_duplicates : true //不允许队列中存在重复文件
	  		},
	  		multipart:true,
	  		multipart_params: {}
	  		
	    });    

	    //在实例对象上调用init()方法进行初始化
	    uploader.init();

	    //绑定各种事件，并在事件监听函数中做你想做的事
	    uploader.bind('FilesAdded',function(uploader,files){
	        //每个事件监听函数都会传入一些很有用的参数，
	        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
	    	for(var i = 0, len = files.length; i<len; i++){
				var file_name = files[i].name; //文件名
				//构造html来更新UI
				var html = '<li id="file-' + files[i].id +'"><p class="file-name">' + file_name + '</p> <b id="removeFile" data-val=""+files[i].id+"">删除</b> <p class="progress"></p></li>';
	   			 $(html).appendTo('#file-list');
			}
	    });
	    uploader.bind('UploadProgress',function(uploader,file){
	        //每个事件监听函数都会传入一些很有用的参数，
	        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作

	    });
	    //上传  传递参数
	    uploader.bind('BeforeUpload', function(uploader, filters) {
		    uploader.settings.multipart_params = {"parentId":""+parentId};
		  });
	    
	    //上传  完成
	    uploader.bind('FileUploaded', function(uploader, filters) {
	    	if(parentId != null ||  parentId !=''){		// 如果有上级文件夹
	    		window.location = "<%=basePath %>/web/bossDoc/queryNextDoc?parentId="+parentId;
	    	}else{		//如果没有上级文件夹
	    		window.location = '<%=basePath %>/web/bossDoc/queryAllBossDoc';
	    	}
	    	 
		  });
	    
	    
	  //删除已选择的文档  这个是删除一个已选择的文件
	    $(document).on("click","#removeFile",function(){
	        $(this).parent('li').remove();
	        var toremove = "";
	        var id=$(this).attr("data-val");
	        for(var i in uploader.files){
	            if(uploader.files[i].id === id){
	                toremove = i;
	            };
	        }
	        uploader.files.splice(toremove, 1);
	    });
	    
	    $('#upload-btn').click(function(){
	    	parentId = $("#bossDocId_documentId").val();
	    	if(parentId != null && parentId != ''){
	    		var fileName = $("#file-list").text();
	    		if(fileName != null && fileName !=''){
	    			uploader.start(); //开始上传
	    		}else{
	    			warnDialog("请选择文件!");
	    		}
	    	}else{
	    		var fileName = $("#file-list").text();
	    		if(fileName != null && fileName !=''){
	    			uploader.start(); //开始上传
	    		}else{
	    			warnDialog("请选择文件!");
	    		}
	    	}
	    });
	  
	})
	
	//上传文件
function uploadDocument(docId,docName){
	var d = dialog({
		width:600,
		height:200,
		top:'20%',
		title: '上传文件',
		content: $("#dialog-bg-uploadDocument"),
		fixed: false,
		drag: true,
			onclose: function () {
				cleanUploadFile();
		    }
		});
		d.showModal();
		$("#bossDocId_documentId").val(docId);
		$("#upload_path").text(docName); //设置上传路经
}
	
	
	//删除菜单
	function deleteBossDoc(bossDocId){
		
		$("#deleteBossDocId").val(bossDocId);//设置到隐藏域中
		var d = dialog({
			width:480,
			height:150,
			title:'删除文档信息',
			content: $("#deleteBossDocDiv"),
		});
		d.showModal();
		
	}
	//点击删除的确定按钮
	function deleteByBossDocId(){
		var bossDocId = $("#deleteBossDocId").val();
		window.location = "<%=basePath %>/web/bossDoc/deleteBossDoc?bossDocId="+bossDocId;
	}
	
	
	//新建文件夹
	function addFolder(docId,docName){
		var d = dialog({
			width:500,
			height:350,
			top:'20%',
			title: '添加文件夹',
			content: $("#add_folder_div"),
			fixed: false,
			drag: true,
		    onclose: function () {
		    	
		    }
			});
			d.showModal();
			
			$("#suoshu_folder").text(docName);
			$("#folder_suoshu_folder").val(docId);
	}
	
	//点击取消按钮
	function exitFolder(){
		$("#add_folder_div :input").each(function () { 
			$(this).val(""); 
		}); 
	}
	
	//点击保存文件夹的方法
	function saveFolder(){
		var documentName = $("#doc_document_name").val();
		var bossDocId = $("#doc_boss_docId").val();
		
		$.ajax({
			url:"<%=basePath %>/web/bossDoc/check",
			data:{documentName:documentName,bossDocId:bossDocId},
			success: function(data){
				if(data.isHave ==  '1' && (bossDocId == null || bossDocId == '')){
					warnDialog("此文件夹已存在!");
				}else if(data.isHave ==  1 && (bossDocId != null || bossDocId != '') && (data.update = 'no')){
					warnDialog("此文件夹已存在!");
				}else{
					$("#folderSaveForm").submit();
				}
			}
		});
	}
	
	//改变文件夹名称调用的方法
	function changeDocumentName(){
		var documentName = $("#doc_document_name").val();
		var bossDocId = $("#doc_boss_docId").val();
		
		$.ajax({
			url:"<%=basePath %>/web/bossDoc/check",
			data:{documentName:documentName,bossDocId:bossDocId},
			success: function(data){
				if(data.isHave ==  '1' && (bossDocId == null || bossDocId == '')){
					warnDialog("此文件夹已存在!");
				}else if(data.isHave ==  1 && (bossDocId != null || bossDocId != '') && (data.update = 'no')){
					warnDialog("此文件夹已存在!");
				}
			}
		});
	}
	
	//文件夹的重命名
	function reNameBossDoc(bossDocId,index){
		
		$("#doc_docName"+index).hide();
		$("#rename_doc_docName"+index).show();
		$("#rename_doc_docName"+index).focus();		//获取焦点
		$("#rename_doc_docName"+index).select();	//选中文字
		
	}
	//焦点离开  保存文件重命名的名称
	function saveDocumentNewName(bossDocId,newDocumentName){
		
		$.ajax({
			url:"<%=basePath %>/web/bossDoc/reDocumentNameBossDoc",
			data:{bossDocId:bossDocId,newDocumentName:newDocumentName},
			success: function(data){
				if(data != '' && data != null){
					window.location = "<%=basePath %>/web/bossDoc/queryNextDoc?parentId="+parentId;
				}else{
					window.location = '<%=basePath %>/web/bossDoc/queryAllBossDoc';
				}
			}
		});
	}
	
	//下载系统文档
	function downLoadBossDoc(filePath){
		 self.location.href = "<%=basePath %>/"+filePath;
	}
</script>
<div class="page-header">
	<h1>
		文档管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			系统文档
		</small>
	</h1>
</div>
<div class="row">
	<div class="col-xs-2">
		<!-- 文档树 -->
         <div class="fl-l tree-left"><ul id="leftTree" class="ztree"></ul></div>
         <!-- 文档树 -->
    </div>
    <div class="col-xs-9">
    	<div class="pull-right btn-bottom">
		    <button class="btn btn-xs btn-primary" onclick="addFolder('','所有文档')">添加文件夹</button>
			<button class="btn btn-xs btn-primary" onclick="uploadDocument('','所有文档')">上传文档</button>
		</div>
         <table id="simple-table" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>文件名称</th>
					<th>文件描述</th>
					<th>类型</th>
					<th>时间</th>
					<th>操作</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${bossList}" var ="doc" varStatus="varStatus">
					<tr>
						<td>
							<span id="doc_docName${varStatus.index}">${doc.documentName}</span>
							<input onblur="saveDocumentNewName('${doc.bossDocId}',this.value)" style="display: none" id="rename_doc_docName${varStatus.index}" value="${doc.documentName}"/>
						</td>
						<td>${doc.remarks}</td>
						<td><c:if test="${doc.documentType == 1}"><img src="<%=basePath %>/comm/images/doc/folder.png" /></c:if>
							<c:if test="${doc.documentType == 2}"><img src="<%=basePath %>/comm/images/doc/doc.png" /></c:if>
							<c:if test="${doc.documentType == 3}"><img src="<%=basePath %>/comm/images/doc/excel.png" /></c:if>
							<c:if test="${doc.documentType == 4}"><img style="width: 70px;height: 70px" src="<%=basePath %>/${doc.filePath}" /></c:if>
							<c:if test="${doc.documentType == 5}"><img src="<%=basePath %>/comm/images/doc/pdf.png" /></c:if>
							<c:if test="${doc.documentType == 6}"><img src="<%=basePath %>/comm/images/doc/txt.png" /></c:if>
							<c:if test="${doc.documentType == 7}"><img src="<%=basePath %>/comm/images/doc/ppt.png" /></c:if>
						</td>
						<td><fmt:formatDate value="${doc.createdDate}" type="both" pattern="yyyy-MM-dd"/></td>
						<td>
							<div class="hidden-sm hidden-xs btn-group">
								<c:if test="${doc.documentType == 1}"> 
									<button class="btn btn-xs btn-primary" onclick="addFolder('${doc.bossDocId}','${doc.documentName}')">添加文件夹</button>&nbsp;&nbsp;
									<button class="btn btn-xs btn-primary" onclick="uploadDocument('${doc.bossDocId}','${doc.documentName}')">上传文档</button>&nbsp;&nbsp;
								</c:if>
<%-- 								<c:if test="${doc.documentType == 2 || doc.documentType == 3 || doc.documentType == 5 || doc.documentType == 7}">	 --%>
									<button class="btn btn-xs btn-primary" onclick="downLoadBossDoc('${doc.filePath}')">下载文档</button>&nbsp;&nbsp;
<%-- 								</c:if> --%>
								<button class="btn btn-xs btn-primary" onclick="reNameBossDoc('${doc.bossDocId}','${varStatus.index}')">重命名</button>&nbsp;&nbsp;
								<button class="btn btn-xs btn-danger" onclick="deleteBossDoc('${doc.bossDocId}')">删除</button>&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!--弹层-开始-->
<div id="add_folder_div" class="all-hidden container col-xs-12">
	<!--弹层内容 -->
	<div class="b-dialog-content">
		<form id="folderSaveForm" class="form-horizontal" action="<%=basePath%>/web/bossDoc/saveFolder" method="post" enctype="multipart/form-data">
			
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 所属文件夹： </label>
				<div class="col-sm-9">
					<span id="suoshu_folder"></span>
					<input id="folder_suoshu_folder" name="parentId" type="hidden"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 文件夹名称： </label>
				<div class="col-sm-9">
					<input type="text" style="width: 300px" id="doc_document_name" name="documentName" onchange ="changeDocumentName()" placeholder="文件夹名称" class="col-xs-10 col-sm-5">
					<input id="doc_boss_docId" name="bossDocId" type="hidden"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label no-padding-right" > 文件夹描述： </label>
				<div class="col-sm-9">
					<input type="text" style="width: 300px" id="doc_remarks" name="remarks" placeholder="文件夹描述" class="col-xs-10 col-sm-5">
				</div>
			</div>
		</form>
	</div>
	<!--底部按钮 -->
	<div class="row">
		<div class="col-xs-3"></div>
		<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="saveFolder()">确   定</button></div>
		<div class="col-xs-6"><button class="btn" i="close" onclick="exitFolder()">取   消 </button></div>
	</div>
</div>
<!--弹层-结束-->

	<!--上传文件 的  弹层-开始-->
	<div id="dialog-bg-uploadDocument" class="all-hidden" style="min-height:200px">
		<div>
		<form id="uploadDocumentForm" action="" method="post" enctype="multipart/form-data">
			<table class="dialog-tbl" cellpadding="0" cellspacing="0">
				<tr>
					<td>所属上级：<input type="hidden" id="bossDocId_documentId" name="parentId"/></td> 
					<td colspan="5"><span id="upload_path"></span></td>
				</tr>
				<tr>
					<td></td> 
					<td colspan="5"> <p>
        				<button id="browse">选择需要上传的文件</button>
        				<span id="file-list"></span>
    				</td>
				</tr>
			</table>
		<!--底部按钮 -->
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-3"><button class="btn btn-primary" i="close" id="upload-btn">确   定</button></div>
				<div class="col-xs-6"><button class="btn" i="close" onclick="exitFolder()">取   消 </button></div>
			</div>
		<div style="height:30px;font-size:20px;"></div>
		</form>
		</div>
	</div> 
	<!--删除文档或者文件夹信息的弹层-开始-->
		<div id="deleteBossDocDiv" class="all-hidden">
			<input id="deleteBossDocId" type="hidden"/>
			<div style="margin-top: 50px;margin-bottom:10px;text-align: center;font-size: 25px;color: red">您确定要删除此文档信息？ </div>
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="deleteByBossDocId()">确   定</button></div>
				<div class="col-xs-6"><button class="btn" i="close">取   消 </button></div>
			</div>
			<div class="clear"></div>
		</div>
	<!--弹层-结束-->