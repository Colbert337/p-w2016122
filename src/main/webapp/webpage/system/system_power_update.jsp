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
<script type="text/javascript">
	var setting = {  
	        isSimpleData : true,              //数据是否采用简单 Array 格式，默认false  
	        treeNodeKey : "id",               //在isSimpleData格式下，当前节点id属性  
	        treeNodeParentKey : "id",        //在isSimpleData格式下，当前节点的父节点id属性  
	        showLine : true,                  //是否显示节点间的连线  
	        checkable : true,                  //每个节点上是否显示 CheckBox  
	        check: {
	        	enable: true //表示是否显示节点前的checkbox选择框
	        	},
	        callback: {
	        	onClick: zTreeOnClick //设置节点点击前事件
	        }
	    };  
	    
	var zTreeObj;  
	var zTreeNodes;
	var menuId = '${menuId}';
	var rootId = '${rootId}';
	var systemId ='${systemId}';
	$(function(){  
	     $.ajax({  
	        async : false,  
	        type: 'POST',  
	        dataType : "json",  
	        url:"<%=basePath %>/web/bossSystem/querySystemTree?systemId="+systemId,
	        error: function () {//请求失败处理函数  
	        	failDialog("请求失败！"); 
	        },  
	        success:function(data){ //请求成功后处理函数。 
	        	zTreeNodes = data.tree;   //把后台封装好的简单Json格式赋给treeNodes  
	        }  
	    });   
	    zTreeObj = $.fn.zTree.init($("#leftTree"), setting, zTreeNodes);
	    
	    var node = zTreeObj.getNodeByParam("id",menuId);   //zTreeObj是tree对象   选中 gradeId 
	    zTreeObj.selectNode(node);
	    zTreeObj.expandAll(true);  //展开所有节点
	});
	
	//树节点的点击事件
	function zTreeOnClick(event, treeId, treeNode) {
		
	};
	
	
	//保存权限
	function saveBossSystemMenu(){
		//获取选中树节点
		var treeObj = $.fn.zTree.getZTreeObj("leftTree");
		var sNodes = treeObj.getCheckedNodes(true);
		
		if (sNodes.length > 0) {
			var menuIds = [];
			var id;
			for(var i=0;i<=sNodes.length-1;i++){
				console.log(sNodes[i]);
				//获取到子节点 id
				if(sNodes[i].id != undefined){
					id = sNodes[i].id;
					menuIds.push(id);
				}
				
			}
			window.location = "<%=basePath %>/web/bossSystem/addSystemMenu?systemId="+systemId+'&menuIds='+menuIds;
			
		}
		
		
	}
	//返回列表页
	function cancel(){
		window.location = "<%=basePath %>/web/bossSystem/querySystemPower";
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
<div class="col-xs-2">
		${bossSystem.systemName}  -->拥有的权限：
    </div>
    <div class="col-xs-2">
    	<button class="btn btn-primary" data-toggle="button" onclick="saveBossSystemMenu();">
			保存
		</button>
		<button class="btn" data-toggle="button" onclick="cancel();">
			取消
		</button>
	</div>
	<div class="col-xs-7">
		<!-- 菜单树 -->
         <div class="fl-l tree-left"><ul id="leftTree" class="ztree"></ul></div>
         <!-- 菜单树 -->
    </div>
</div>

