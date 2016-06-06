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

		<li><a href="javascript:void(0);">加气站管理</a></li>
		<li class="active">加气站信息管理</li>
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
	<form id="formgastation">

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
					<h3 class="header smaller lighter blue">加气站管理</h3>

					<div class="form-group">
						<div class="col-md-2 control-label no-padding-right">
						    <label>加气站编号:</label>
							<input type="text" name="sys_gas_station_id" placeholder="输入加气站编号"  maxlength="9" value="${gastation.sys_gas_station_id}"/>
						</div>
						
						<div class="col-md-2 control-label no-padding-right">
						    <label>加气站名称:</label>
							<input type="text" name="gas_station_name" placeholder="输入加气站名称"  maxlength="9" value="${gastation.gas_station_name}"/>
						</div>
						
						<div class="col-md-2 control-label no-padding-right">
							<label>气站状态:</label>
							<select class="chosen-select " name="status" >
									<s:option flag="true" gcode="GASTATION_STATUS" form="gastation" field="status" link="true" />
							</select>
						</div>
						
						<div class="col-md-4 input-group no-padding-right  control-label">
						    <label>平台有效期:</label>
							<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
							</span>
							<input type="text" name="expiry_date_frompage" id="date-range-picker" value="${gascard.expiry_date_frompage}"/>
						</div>
						<%-- <div class="col-md-3 control-label no-padding-right">
						    
							<label>用户卡状态:</label>
							<select class="chosen-select " name="card_status" >
									 <s:option flag="true" gcode="CARDSTATUS" form="gascard" field="card_status" link="true" />
							</select>
						</div>
						
						<div class="col-md-2 control-label  no-padding-right">
						    <label>操作员:</label>
							<input type="text" name="operator" placeholder="操作员工号"  maxlength="10" value="${gascard.operator}"/>
						</div>
						
						<div class="col-md-4 input-group no-padding-right  control-label">
						    <label>操作时间:</label>
							<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
							</span>
							<input type="text" name="storage_time_range" id="date-range-picker" value="value="${gascard.storage_time_range}"/>
						</div> --%>
					</div>
					<br/>
					<div class="form-group">
						<div class="col-md-12 pull-right">
								<button class="btn btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/gastation/gastation_new.jsp');">
										<i class="ace-icon fa fa-flask align-top bigger-125"></i>
										新建加气站
								</button>
								<button class="btn btn-primary" type="button" onclick="commitForm();">
										<i class="ace-icon fa fa-flask align-top bigger-125"></i>
										查询
								</button>
						</div>
					</div>
					
					<br/><br/>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					
					
					<div class="table-header">加气站详细信息列表</div>

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
									<th onclick="orderBy(this,'sys_gas_station_id');commitForm();" id="sys_gas_station_id_order">加气站编号</th>
									<th onclick="orderBy(this,'gas_station_name');commitForm();" id="gas_station_name_order">加气站名称</th>
									<th onclick="orderBy(this,'salesmen_name');commitForm();" id="salesmen_name_order">销售人员</th>
									<th onclick="orderBy(this,'operations_name');commitForm();" id="operations_name_order">运营人员</th>
									<th onclick="orderBy(this,'indu_com_number');commitForm();" id="indu_com_number_order">工商注册号</th>
									<th onclick="orderBy(this,'status');commitForm();" id="status_order">平台状态</th>
									<th onclick="orderBy(this,'address');commitForm();" id="address_order">注册地址</th> 
									<th onclick="orderBy(this,'created_time');commitForm();" id="created_time_order"><i id="created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>注册日期</th>
									<th onclick="orderBy(this,'expiry_date');commitForm();" id="expiry_date_order"><i id="expiry_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>平台有效期</th>
									<th>更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.sys_gas_station_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.sys_gas_station_id}</td>
								 	<td>${list.gas_station_name}</td> 
									<td><s:Code2Name mcode="${list.salesmen_name}" gcode="CARDSTATUS"></s:Code2Name> </td>
									<td><s:Code2Name mcode="${list.operations_name}" gcode="WORKSTATION"></s:Code2Name></td>
									<td>${list.indu_com_number}</td>
									<td><s:Code2Name mcode="${list.status}" gcode="GASTATION_STATUS"></s:Code2Name></td>
									<td>${list.address}</td> 
									<%-- <td>${list.batch_no}</td>  --%>
									<td><fmt:formatDate value="${list.created_time}" type="both"/></td>
									<td><fmt:formatDate value="${list.expiry_date}" type="both"/></td>

									<td>
										<div class="hidden-sm hidden-xs action-buttons">
											<a class="blue" href="javascript:void(0);"> 
												<i class="ace-icon fa fa-search-plus bigger-130"></i>
											</a> 
											<a class="green" href="javascript:void(0);"> 
												<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
											</a> 
											<!-- <a class="red"  href="javascript:void(0);" onclick="del(this);"> 
												<i class="ace-icon fa fa-trash-o bigger-130"></i>
											</a> -->
										</div>

										<div class="hidden-md hidden-lg">
											<div class="inline pos-rel">
												<button class="btn btn-minier btn-yellow dropdown-toggle"
													data-toggle="dropdown" data-position="auto">
													<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
												</button>

												<ul
													class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
													<li>
														<a href="#" class="tooltip-info" data-rel="tooltip" title="View"> 
															<span class="blue">
																	<i class="ace-icon fa fa-search-plus bigger-120"></i>
															</span>
														</a>
													</li>

													<li><a href="#" class="tooltip-success" data-rel="tooltip" title="Edit"> <span class="green">
																<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
														</span>
													</a></li>

													<li><a href="#" class="tooltip-error" data-rel="tooltip" title="Delete"> <span class="red">
																<i class="ace-icon fa fa-trash-o bigger-120"></i>
														</span>
													</a></li>
												</ul>
											</div>
										</div>
									</td>
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
						applyLabel: 'Apply',
						cancelLabel: 'Cancel',
						format: "YYYY/MM/DD",
					}, 
					"startDate": "${gastation.expiry_date_after}"==""?mydate.getFullYear()+"/1/1":"${gastation.expiry_date_after}",
				    "endDate": "${gastation.expiry_date_before}"==""?mydate.getFullYear()+"/12/31":"${gastation.expiry_date_before}"
				})
				.prev().on(ace.click_event, function(){
					$(this).next().focus();
				});
	
	var options ={   
            url:'<%=basePath%>/web/gastation/gastationList',
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
	
	function preUpdate(obj){
		var stationid = $(obj).parents("tr").find("td:first").find("input").val();
		loadPage('#main', '../web/gastation/preUpdate?gastationid='+stationid);
	}
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formgastation").ajaxSubmit(options);
	}
	
	function del(obj){
		var cardid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/gastation/deleteGastation?gastationid='+cardid,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		             $("#main").html(data);
		             if($("#retCode").val() != "100"){
		            	 $("#modal-table").modal("show");
		             }
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
					
	            }
		}
		
		$("#formgastation").ajaxSubmit(deloptions);
	}
	</script>
