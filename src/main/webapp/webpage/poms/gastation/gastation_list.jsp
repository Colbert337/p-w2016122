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
<div class="">
	<!-- /.page-header -->
	<form id="formgastation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							加气站管理
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>加气站编号:</label>
							<input type="text" name="sys_gas_station_id" placeholder="输入加气站编号"  maxlength="9" value="${gastation.sys_gas_station_id}"/>
						</div>
						
						<div class="item">
						    <label>加气站名称:</label>
							<input type="text" name="gas_station_name" placeholder="输入加气站名称"  maxlength="9" value="${gastation.gas_station_name}"/>
						</div>
						
						<div class="item">
							<label>气站状态:</label>
							<select class="chosen-select " name="status" >
									<s:option flag="true" gcode="STATION_STATUS" form="gastation" field="status" />
							</select>
						</div>
						
						 <div class="item">
						    <label>平台有效期:</label>
							<%--<span class="input-group-addon">--%>
									<%--<i class="fa fa-calendar bigger-110"></i>--%>
							<%--</span>--%>
							<input type="text" name="expiry_date_frompage" id="date-range-picker" size="22" value="${gascard.expiry_date_frompage}"/>
						</div> 
						
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>平台有效期:</label>
								<input type="text" class="" name="expiry_date_after" value="${gastation.expiry_date_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="expiry_date_before" value="${gastation.expiry_date_before}" readonly="readonly"/>
							</div>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/gastation/gastation_new.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								新建加气站
							</button>
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
									<td>${list.salesmen_name}</td>
									<td>${list.operations_name}</td>
									<td>${list.indu_com_number}</td>
									<td><s:Code2Name mcode="${list.status}" gcode="STATION_STATUS"></s:Code2Name></td>
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
			

			<label>共 ${pageInfo.total} 条</label>
			
			<nav>
				  <ul id="ulhandle" class="pagination pull-right no-margin">
				  
				    <li id="previous">
					      <a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formgastation');">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
				    </li>
				    
				    <li id="next">
					      <a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formgastation');">
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
<script src="<%=basePath%>/assets/js/date-time/bootstrap-datepicker.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	var listOptions ={   
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
		
		$("#formgastation").ajaxSubmit(listOptions);
	}
	
	function del(obj){
		var cardid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/gastation/deleteGastation?gastationid='+cardid,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		             $("#main").html(data);
		             $("#modal-table").modal("show");
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	            }
		}
		
		$("#formgastation").ajaxSubmit(deloptions);
	}
	
	function init(){
		loadPage('#main', '../web/gastation/gastationList');
	}
	</script>
