<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!-- page specific plugin styles -->
<link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap-duallistbox.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap-multiselect.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/select2.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/daterangepicker.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap-datepicker3.css" />

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>

	<ul class="breadcrumb">
		<li><i class="ace-icon fa fa-home home-icon"></i> <a href="javascript:void(0);">主页</a>
		</li>

		<li><a href="javascript:void(0);">资源管理</a></li>
		<li class="active">用户卡管理</li>
	</ul>
	<!-- /.breadcrumb -->

	<!-- #section:basics/content.searchbox -->
	<div class="nav-search" id="nav-search">
		<form class="form-search">
			<span class="input-icon"> <input type="text"
				placeholder="Search ..." class="nav-search-input"
				id="nav-search-input" autocomplete="off" /> <i
				class="ace-icon fa fa-search nav-search-icon"></i>
			</span>
		</form>
	</div>
	<!-- /.nav-search -->

	<!-- /section:basics/content.searchbox -->
</div>




<!-- /section:basics/content.breadcrumbs -->
<div class="page-content">
	<!-- #section:settings.box -->
	<!-- <div class="ace-settings-container" id="ace-settings-container">
		<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
			id="ace-settings-btn">
			<i class="ace-icon fa fa-cog bigger-130"></i>
		</div>

		<div class="ace-settings-box clearfix" id="ace-settings-box">
			<div class="pull-left width-50">
				#section:settings.skins
				<div class="ace-settings-item">
					<div class="pull-left">
						<select id="skin-colorpicker" class="hide">
							<option data-skin="no-skin" value="#438EB9">#438EB9</option>
							<option data-skin="skin-1" value="#222A2D">#222A2D</option>
							<option data-skin="skin-2" value="#C6487E">#C6487E</option>
							<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
						</select>
					</div>
					<span>&nbsp; Choose Skin</span>
				</div>

				/section:settings.skins

				#section:settings.navbar
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-navbar" /> <label class="lbl"
						for="ace-settings-navbar"> Fixed Navbar</label>
				</div>

				/section:settings.navbar

				#section:settings.sidebar
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-sidebar" /> <label class="lbl"
						for="ace-settings-sidebar"> Fixed Sidebar</label>
				</div>

				/section:settings.sidebar

				#section:settings.breadcrumbs
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-breadcrumbs" /> <label class="lbl"
						for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
				</div>

				/section:settings.breadcrumbs

				#section:settings.rtl
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-rtl" /> <label class="lbl"
						for="ace-settings-rtl"> Right To Left (rtl)</label>
				</div>

				/section:settings.rtl

				#section:settings.container
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-add-container" /> <label class="lbl"
						for="ace-settings-add-container"> Inside <b>.container</b>
					</label>
				</div>

				/section:settings.container
			</div>
			/.pull-left

			<div class="pull-left width-50">
				#section:basics/sidebar.options
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-hover" /> <label class="lbl"
						for="ace-settings-hover"> Submenu on Hover</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-compact" /> <label class="lbl"
						for="ace-settings-compact"> Compact Sidebar</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-highlight" /> <label class="lbl"
						for="ace-settings-highlight"> Alt. Active Item</label>
				</div>

				/section:basics/sidebar.options
			</div>
			/.pull-left
		</div>
		/.ace-settings-box
	</div> -->
	<!-- /.ace-settings-container -->


	<!-- /.page-header -->
	<form id="formcard">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->

<!-- 			<h4 class="pink">
				<i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
				<a href="#modal-table" role="button" class="green" data-toggle="modal"> Table Inside a Modal Box </a>
			</h4> 

			<div class="hr hr-18 dotted hr-double"></div>-->
						
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter blue">用户卡轨迹信息</h3>

					<div class="search-types">
						<div class="item">
						    <label>用户卡号:</label>
							<input type="text" name="card_no" placeholder="输入用户卡号"  maxlength="9" value="${gascardlog.card_no}"/>
						</div>
						
						<div class="item">
						    <label>用户卡类型:</label>
							<select class="chosen-select " name="card_type" >
									<s:option flag="true" gcode="CARDTYPE" form="gascardlog" field="card_type" link="true" />
							</select>
						</div>

						<div class="item">
							<label>用户卡状态:</label>
							<select class="chosen-select " name="card_status" >
									 <s:option flag="true" gcode="CARDSTATUS" form="gascardlog" field="card_status" link="true" />
							</select>
						</div>
						
						<div class="item">
						    <label>操作员:</label>
							<input type="text" name="operator" placeholder="操作员工号"  maxlength="10" value="${gascardlog.operator}"/>
						</div>
						
						<div class="item">
						    <label>操作时间:</label>
							 <input type="text" name="optime_range" id="date-range-picker" size="22" value="${gascardlog.optime_range}"/>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								重置
							</button>
						</div>

					</div>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					<div class="table-header">用户卡详细信息列表</div>

					<!-- div.table-responsive -->

					<!-- div.dataTables_borderWrap -->
					<div>
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>
									<th onclick="orderBy(this,'card_no');commitForm();" id="card_no_order">用户卡号</th>
									<th onclick="orderBy(this,'card_type');commitForm();" id="card_type_order">用户卡类型</th>
									<th onclick="orderBy(this,'card_status');commitForm();" id="card_status_order">用户卡状态</th>
									<th onclick="orderBy(this,'card_property');commitForm();" id="card_property_order">用户卡属性</th>
									<th onclick="orderBy(this,'workstation');commitForm();" id="workstation_order">所属工作站</th>
									<th onclick="orderBy(this,'workstation_resp');commitForm();" id="workstation_resp_order">工作站领取人</th>
									<th onclick="orderBy(this,'operator');commitForm();" id="operator_order">操作员</th> 
									<th onclick="orderBy(this,'batch_no');commitForm();" id="batch_no_order">入库批次号</th> 
									<th onclick="orderBy(this,'storage_time');commitForm();" id="storage_time_order"><i id="storage_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>入库时间</th>
									<th onclick="orderBy(this,'release_time');commitForm();" id="release_time_order"><i id="release_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>出库时间</th>
									<th onclick="orderBy(this,'optime');commitForm();" id="optime_order"><i id="optime" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>操作时间</th>
									<th onclick="orderBy(this,'action');commitForm();" id="action_order">操作动作</th> 
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.card_no}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.card_no}</td>
								 	<td><s:Code2Name mcode="${list.card_type}" gcode="CARDTYPE"></s:Code2Name> </td> 
									<td><s:Code2Name mcode="${list.card_status}" gcode="CARDSTATUS"></s:Code2Name> </td>
									<td><s:Code2Name mcode="${list.card_property}" gcode="CARDPROPERTY"></s:Code2Name> </td>
									<td><s:Code2Name mcode="${list.workstation}" gcode="WORKSTATION"></s:Code2Name></td>
									<td><s:Code2Name mcode="${list.workstation_resp}" gcode="WORKSTATION_RESP"></s:Code2Name></td>
									<td>${list.operator}</td> 
									<td>${list.batch_no}</td> 
									<td><fmt:formatDate value="${list.storage_time}" type="both"/></td>
									<td><fmt:formatDate value="${list.release_time}" type="both"/></td>
					                <td><fmt:formatDate value="${list.optime}" type="both"/></td>
									<td><s:Code2Name mcode="${list.action}" gcode="ACTION"></s:Code2Name></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			

			<label>共 ${pageInfo.total} 条</label>
			
			<nav>
				  <ul id="ulhandle" class="pagination pull-right no-margin">
				  
				    <li id="previous">
					      <a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formcard');">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
				    </li>
				    
				    <li id="next">
					      <a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formcard');">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
				    </li>
				    
				  </ul>
			</nav>


			<jsp:include page="/common/message.jsp"></jsp:include>
			

			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>
<!-- /.page-content -->



<!-- page specific plugin scripts -->
<script src="<%=basePath%>/assets/js/dataTables/jquery.dataTables.js"></script>
<script src="<%=basePath%>/assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
<script src="<%=basePath%>/assets/js/dataTables/extensions/buttons/dataTables.buttons.js"></script>
<script src="<%=basePath%>/assets/js/dataTables/extensions/buttons/buttons.flash.js"></script>
<script src="<%=basePath%>/assets/js/dataTables/extensions/buttons/buttons.html5.js"></script>
<script src="<%=basePath%>/assets/js/dataTables/extensions/buttons/buttons.print.js"></script>
<script src="<%=basePath%>/assets/js/dataTables/extensions/buttons/buttons.colVis.js"></script>
<script src="<%=basePath%>/assets/js/dataTables/extensions/select/dataTables.select.js"></script>

<script src="<%=basePath%>/assets/js/date-time/moment.js"></script>
<script src="<%=basePath%>/assets/js/date-time/daterangepicker.js"></script>
<script src="<%=basePath%>/assets/js/date-time/bootstrap-datetimepicker.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
var mydate = new Date();
	$('#date-range-picker').daterangepicker({'applyClass' : 'btn-sm btn-success', 'cancelClass' : 'btn-sm btn-default',
					locale: {
						"format": 'YYYY.MM.DD',
						"applyLabel": "确定",
						"cancelLabel": "取消",
						"fromLabel": "起始时间",
						"toLabel": "结束时间'",
						"daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
						"monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
						"firstDay": 1
					}, 
					"startDate": "${gascardlog.optime_after}"==""?mydate.getFullYear()+"/1/1":"${gascardlog.optime_after}",
				    "endDate": "${gascardlog.optime_before}"==""?mydate.getFullYear()+"/"+(parseInt(mydate.getMonth())+1)+"/"+mydate.getDate():"${gascardlog.optime_before}"
				})
				.prev().on(ace.click_event, function(){
					$(this).next().focus();
				});
	
	var listOptions ={   
            url:'../web/card/cardLogList',   
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
		            	 //$("#modal-table").modal("show");
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
            	alert("error");
	       }
	}
	
	window.onload = setCurrentPage();
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formcard").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/card/cardLogList');
	}
	</script>
