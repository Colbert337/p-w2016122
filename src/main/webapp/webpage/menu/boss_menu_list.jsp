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
	        url:"<%=basePath %>/web/bossMenu/queryMenuTree",
	        error: function () {//请求失败处理函数  
	        	failDialog("请求失败！"); 
	        },  
	        success:function(data){ //请求成功后处理函数。  
	        	zTreeNodes = data.tree;   //把后台封装好的简单Json格式赋给treeNodes  
	        }  
	    });   
	    zTreeObj = $.fn.zTree.init($("#leftTree"), setting, zTreeNodes);
	    
	    var node = zTreeObj.getNodeByParam("pId",parentId);   //zTreeObj是tree对象  
	    zTreeObj.selectNode(node);
	    var node2 = zTreeObj.getNodeByParam("id",parentId);   //zTreeObj是tree对象   选中 
	    zTreeObj.selectNode(node2);
	    
	    zTreeObj.expandNode(node,true,false,false);//展开节点
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
			  window.location = "<%=basePath %>/web/bossMenu/queryNextMenu?parentId="+pid;
		}
		if(rootId != null && rootId !='' && rootId !=undefined){
			//查询所有菜单
			window.location = "<%=basePath %>/web/bossMenu/goBossMenuList";
		}
		if(id != null && id !='' && id !=undefined){
			//查询所有菜单
			window.location = "<%=basePath %>/web/bossMenu/queryNextMenu?parentId="+id;
		}
	};
	
	
	//添加子集菜单  弹出div
	function addMenu(menuId,menuName){
		cleanBossMenu();
		var d = dialog({
			width:500,
			height:370,
			top:'20%',
			title: '添加菜单',
			content: $("#add_menu_div"),
			fixed: false,
			drag: true,
		    onclose: function () {
		    }
			});
			d.showModal();
			
			$("#menu_suoshu_menu").val(menuId);
			$("#suoshu_menu").text(menuName);
	}
	
	//清空菜单中的input
	function cleanBossMenu(){
		$("#menuSaveForm :input").each(function () { 
			$(this).val(""); 
		}); 
	}
	
	//添加确认
	function saveMenu(){
		//先判断是否已经有了此菜单
		var menuName = $("#menu_menuName").val(); // 获取菜单名称
		var menuId = $("#menu_menuId").val();
		var menuCode = $("#menu_menuCode").val();
		
		var isHas;//用来存是否已经有了 相同的menuCode
		//保存时 去判断  菜单简称是否 已经存在  如果存在 则让其修改简称
		$.ajax({
			url:"<%=basePath %>/web/bossMenu/checkMenuCode",
			data:{menuCode:menuCode,menuId:menuId},
			async: false,
			success: function(data){
				if(data.isHave ==  '1' && (menuId == null || menuId == '')){
					warnDialog("此简称已存在!");
					isHas = 'yes';
					return false;
				}else if(data.isHave ==  1 && (menuId != null || menuId != '') && (data.update == 'no')){
					warnDialog("此简称已存在!");
					isHas = 'yes';
					return false;
				}else{
					$.ajax({
						url:"<%=basePath %>/web/bossMenu/check",
						data:{menuName:menuName,menuId:menuId},
						async: false,
						success: function(data){
							if(data.isHave ==  '1' && (menuId == null || menuId == '')){
								warnDialog("此菜单已存在!");
							}else if(data.isHave ==  1 && (menuId != null || menuId != '') && (data.update == 'no')){
								warnDialog("此菜单已存在!");
							}else{
								$("#menuSaveForm").submit();
								
							}
						}
					});
				}
			}
		});
		
		
		
		
	}
	
	//修改菜单名称调用的方法
	function changeMenuName(){
		var menuName = $("#menu_menuName").val(); // 获取菜单名称
		var menuId = $("#menu_menuId").val();
		$.ajax({
			url:"<%=basePath %>/web/bossMenu/check",
			data:{menuName:menuName,menuId:menuId},
			success: function(data){
				if(data.isHave ==  '1' && (menuId == null || menuId == '')){
					warnDialog("此菜单已存在!");
				}else if(data.isHave ==  1 && (menuId != null || menuId != '') && (data.update = 'no')){
					warnDialog("此菜单已存在!");
				}
			}
		});
	}
	
	
	//修改菜单
	function updateMenu(menuId){
		cleanBossMenu();
		//打开弹窗
		var d = dialog({
			width:500,
			height:370,
			top:'20%',
			title: '修改菜单',
			content: $("#add_menu_div"),
			fixed: false,
			drag: true,
		    onclose: function () {
		    	
		    }
			});
			d.showModal();
		
		$.ajax({
			url:"<%=basePath %>/web/bossMenu/update",
			data:{menuId:menuId},
			success: function(data){
				if(data != null){
					$("#menu_menuId").val(data.menuId);
					$("#menu_menuName").val(data.menuName);
					$("#menu_menu_desc").val(data.menuDesc);
					$("#menu_menu_path").val(data.menuPath);
					$("#menu_menu_sort").val(data.menuSort);
					$("#menu_suoshu_menu").val(data.parentId)
					$("#menu_menuCode").val(data.menuCode);
					if(data.parentId == '0'){
						$("#suoshu_menu").text('所有菜单');
					}else{
						$("#suoshu_menu").text(data.parentName);
					}
					
				}
			}
		});
	}
	
	//删除菜单
	function deleteMenu(menuId){
		$("#deleteBossMenuId").val(menuId);//设置到隐藏域中
		var d = dialog({
			width:480,
			height:150,
			title:'删除菜单信息',
			content: $("#deleteBossMenuDiv"),
		});
		d.showModal();
	}
	//点击确定按钮
	function deleteByMenuId(){
		var menuId = $("#deleteBossMenuId").val();
		window.location = "<%=basePath %>/web/bossMenu/deleteMenu?menuId="+menuId;
	}

	
	//	菜单名称 失去焦点   （让menuCode 显示出来）
	function getMenuCode(){
		var menuName = $("#menu_menuName").val();
		menuName = encodeURI(menuName);
		$.ajax({
			url:"<%=basePath %>/web/bossMenu/getMenuCode",
			data:{menuName:menuName},
			success: function(data){
				$("#menu_menuCode").val(data);
			}
		});
		
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
		<!-- 菜单树 -->
         <div class="fl-l tree-left"><ul id="leftTree" class="ztree"></ul></div>
         <!-- 菜单树 -->
    </div>
    <div class="col-xs-9">
    	 <div class="pull-right btn-bottom">
    	 	<button class="btn btn-xs btn-primary" onclick="addMenu('','所有菜单')">新增菜单</button>
    	 </div>
         <table id="simple-table" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>菜单名称</th>
					<th>菜单简称</th>
					<th>菜单描述</th>
					<th>路径</th>
					<th>顺序</th>
					<th>图片</th>
					<th>时间</th>
					<th>操作</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${bosslist}" var ="menu">
					<tr>
						<td>
							${menu.menuName}
						</td>
						<td>${menu.menuCode}</td>
						<td>${menu.menuDesc}</td>
						<td>${menu.menuPath}</td>
						<td>${menu.menuSort}</td>
						<td><img style="width: 80px;height: 30px" src="http://localhost:8080/boss-web/${menu.menuIcon}"></td>
						<td><fmt:formatDate value="${menu.createdDate}" type="both" pattern="yyyy-MM-dd"/></td>
						<td>
							<div class="hidden-sm hidden-xs btn-group">
								<button class="btn btn-xs btn-success" onclick="addMenu('${menu.menuId}','${menu.menuName}')">
									新增
								</button>
	
								<button class="btn btn-xs btn-info" onclick="updateMenu('${menu.menuId}')">
									修改
								</button>
	
								<button class="btn btn-xs btn-danger" onclick="deleteMenu('${menu.menuId}')">
									删除
								</button>
	
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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