<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- page specific plugin styles -->
<link rel="stylesheet" href="../assets/css/bootstrap-duallistbox.css" />
<link rel="stylesheet" href="../assets/css/bootstrap-multiselect.css" />
<link rel="stylesheet" href="../assets/css/select2.css" />
<link rel="stylesheet" href="../assets/css/daterangepicker.css" />
<link rel="stylesheet" href="../assets/css/bootstrap-datepicker3.css" />

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
	<div class="ace-settings-container" id="ace-settings-container">
		<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
			id="ace-settings-btn">
			<i class="ace-icon fa fa-cog bigger-130"></i>
		</div>

		<div class="ace-settings-box clearfix" id="ace-settings-box">
			<div class="pull-left width-50">
				<!-- #section:settings.skins -->
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

				<!-- /section:settings.skins -->

				<!-- #section:settings.navbar -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-navbar" /> <label class="lbl"
						for="ace-settings-navbar"> Fixed Navbar</label>
				</div>

				<!-- /section:settings.navbar -->

				<!-- #section:settings.sidebar -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-sidebar" /> <label class="lbl"
						for="ace-settings-sidebar"> Fixed Sidebar</label>
				</div>

				<!-- /section:settings.sidebar -->

				<!-- #section:settings.breadcrumbs -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-breadcrumbs" /> <label class="lbl"
						for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
				</div>

				<!-- /section:settings.breadcrumbs -->

				<!-- #section:settings.rtl -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-rtl" /> <label class="lbl"
						for="ace-settings-rtl"> Right To Left (rtl)</label>
				</div>

				<!-- /section:settings.rtl -->

				<!-- #section:settings.container -->
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2"
						id="ace-settings-add-container" /> <label class="lbl"
						for="ace-settings-add-container"> Inside <b>.container</b>
					</label>
				</div>

				<!-- /section:settings.container -->
			</div>
			<!-- /.pull-left -->

			<div class="pull-left width-50">
				<!-- #section:basics/sidebar.options -->
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

				<!-- /section:basics/sidebar.options -->
			</div>
			<!-- /.pull-left -->
		</div>
		<!-- /.ace-settings-box -->
	</div>
	<!-- /.ace-settings-container -->


	<!-- /.page-header -->
	<form id="formcard">
	
		<input id="pageNum" type="text" name="pageNum"  value ="${pageinfo.pageNum}"/>
		<input id="pageSize" type="text" name="pageSize"  value ="10"/>
		<input id="pageNumMax" type="text" name="pageNumMax" value ="${pageinfo.pages}"/>
		<input id="total" type="text" name="total"  value =" ${pageinfo.total}"/>

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->

			<h4 class="pink">
				<i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
				<a href="#modal-table" role="button" class="green" data-toggle="modal"> Table Inside a Modal Box </a>
			</h4>

			<div class="hr hr-18 dotted hr-double"></div>
						
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter blue">用户卡管理</h3>

					<div class="form-group">
						<div class="col-md-2 control-label no-padding-right">
						    <label>用户卡号:</label>
							<input type="text" name="card_id" placeholder="States of USA"  maxlength="12" value="${gascard.card_id}"/>
						</div>
						<div class="col-md-2 control-label no-padding-right">
						    <label>用户卡类型:</label>
							<input type="text" name="card_type" placeholder="States of USA"  maxlength="12" value="${gascard.card_type}"/>
						</div>
						
						<div class="col-md-2 control-label  no-padding-right">
						    <label>操作员:</label>
							<input type="text" name="operator" placeholder="States of USA"  maxlength="12" value="${gascard.operator}"/>
						</div>
						
						<div class="col-md-4 input-group no-padding-right  control-label">
						    <label>操作时间:</label>
							<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
							</span>
							<input type="text" name="date-range-picker" id="id-date-range-picker-1" />
						</div>
						
						
					</div>
					
					<div class="form-group">
						<div class="col-md-12 pull-right">
								<button class="btn btn-primary" type="button" onclick="loadPage('#main','card/new_card.jsp');">
										<i class="ace-icon fa fa-flask align-top bigger-125"></i>
										新建
								</button>
								<button class="btn btn-primary" type="button" onclick="commitForm();">
										<i class="ace-icon fa fa-flask align-top bigger-125"></i>
										查询
								</button>
								<!-- <input type="submit" value="提交"/> -->
						</div>
					</div>
					
					<br/><br/>

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
									<th onclick="orderBy(this,'cardno');">用户卡号<i class="glyphicon glyphicon-chevron-up"></i></th>
									<th onclick="orderBy(this,'cardtype');">用户卡类型<i class="glyphicon glyphicon-chevron-up"></i></th>
									<th onclick="orderBy(this,'ownerid');">所属人身份证号<i class="glyphicon glyphicon-chevron-up"></i></th> 
									<th onclick="orderBy(this,'mobile');">所属人电话<i class="glyphicon glyphicon-chevron-up"></i></th>
									<th onclick="orderBy(this,'operater');">操作人工号<i class="glyphicon glyphicon-chevron-up"></i></th> 
									<th onclick="orderBy(this,'optime');"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>操作时间<i class="glyphicon glyphicon-chevron-up"></i></th>
									<th>更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageinfo.list}" var="list" varStatus="s"> 		
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.card_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.card_no}</td>
								 	<td>${list.card_type}</td> 
									<td>${list.card_name}</td>
									<td>${list.card_status}</td>
									<td>${list.operator}</td> 
									<td><fmt:formatDate value="${list.storage_time}" type="both"/></td>

									<td>
										<div class="hidden-sm hidden-xs action-buttons">
											<a class="blue" href="#"> 
												<i class="ace-icon fa fa-search-plus bigger-130"></i>
											</a> 
											<a class="green" href="#"> 
												<i class="ace-icon fa fa-pencil bigger-130"></i>
											</a> 
											<a class="red"  href="javascript:void(0);" onclick="del(this);"> 
												<i class="ace-icon fa fa-trash-o bigger-130"></i>
											</a>
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
			

			<label>共 ${pageinfo.total} 条</label>
			
			<nav>
				  <ul class="pagination pull-right no-margin">
				  
				    <li id="previous">
					      <a href="javascript:void(0);" aria-label="Previous" onclick="prepage();">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
				    </li>

				    <li id="navigator"><a href="javascript:void(0);" onclick="commitForm(this)">1</a></li>
				    <li id="navigator"><a href="javascript:void(0);" onclick="commitForm(this)">2</a></li>
				    <li id="navigator"><a href="javascript:void(0);" onclick="commitForm(this)">3</a></li>
				    <li id="navigator"><a href="javascript:void(0);" onclick="commitForm(this)">4</a></li>
				    <li id="navigator"><a href="javascript:void(0);" onclick="commitForm(this)">5</a></li>
				    
				    <li id="next">
					      <a href="javascript:void(0);" aria-label="Next" onclick="nextpage();">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
				    </li>
				    
				  </ul>
			</nav>

			

			<div id="modal-table" class="modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header no-padding">
							<div class="table-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">
									<span class="white">&times;</span>
								</button>
								Results for "Latest Registered Domains
							</div>
						</div>

						<div class="modal-body no-padding">
							<table
								class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
								<thead>
									<tr>
										<th>Domain</th>
										<th>Price</th>
										<th>Clicks</th>

										<th><i class="ace-icon fa fa-clock-o bigger-110"></i>
											Update</th>
									</tr>
								</thead>

								<tbody>
									<tr>
										<td><a href="#">ace.com</a></td>
										<td>$45</td>
										<td>3,330</td>
										<td>Feb 12</td>
									</tr>

									<tr>
										<td><a href="#">base.com</a></td>
										<td>$35</td>
										<td>2,595</td>
										<td>Feb 18</td>
									</tr>

									<tr>
										<td><a href="#">max.com</a></td>
										<td>$60</td>
										<td>4,400</td>
										<td>Mar 11</td>
									</tr>

									<tr>
										<td><a href="#">best.com</a></td>
										<td>$75</td>
										<td>6,500</td>
										<td>Apr 03</td>
									</tr>

									<tr>
										<td><a href="#">pro.com</a></td>
										<td>$55</td>
										<td>4,250</td>
										<td>Jan 21</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="modal-footer no-margin-top">
							
							<button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
								<i class="ace-icon fa fa-times"></i> Close
							</button>
							 
							<ul class="pagination pull-right no-margin">
								<li class="prev disabled"><a href="#"> <i
										class="ace-icon fa fa-angle-double-left"></i>
								</a></li>

								<li class="active"><a href="#">1</a></li>

								<li><a href="#">2</a></li>

								<li><a href="#">3</a></li>

								<li class="next">
									<a href="#"> 
										<i class="ace-icon fa fa-angle-double-right"></i>
									</a>
								</li>
							</ul>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>

			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>
<!-- /.page-content -->



<!-- page specific plugin scripts -->
<script src="../assets/js/dataTables/jquery.dataTables.js"></script>
<script src="../assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
<script src="../assets/js/dataTables/extensions/buttons/dataTables.buttons.js"></script>
<script src="../assets/js/dataTables/extensions/buttons/buttons.flash.js"></script>
<script src="../assets/js/dataTables/extensions/buttons/buttons.html5.js"></script>
<script src="../assets/js/dataTables/extensions/buttons/buttons.print.js"></script>
<script src="../assets/js/dataTables/extensions/buttons/buttons.colVis.js"></script>
<script src="../assets/js/dataTables/extensions/select/dataTables.select.js"></script>

<script src="../assets/js/date-time/moment.js"></script>
<script src="../assets/js/date-time/daterangepicker.js"></script>
<script src="../assets/js/date-time/bootstrap-datetimepicker.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
	$('input[name=date-range-picker]').daterangepicker({'applyClass' : 'btn-sm btn-success', 'cancelClass' : 'btn-sm btn-default',
					locale: {
						applyLabel: 'Apply',
						cancelLabel: 'Cancel',
					}
				})
				.prev().on(ace.click_event, function(){
					$(this).next().focus();
				});
	
	var options ={   
            url:'../web/card/cardList',   
            type:'post',                    
            dataType:'html',
            success:function(data){
	              if(data.statusCode=="OK") {  
	                	
	              }
	              $("#main").html(data);
	              
	              setCurrentPage();
            }
	}
	
	window.onload = setCurrentPage();

	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		
			//设置当前选中的页数样式
			$("li[id=navigator]").removeClass("active");
			$(obj).parent("li").attr("class","active");
			//设置上一页按钮样式
			if(parseInt($("#pageNum").val())>1){
				$("#previous").removeAttr("class");
			}else if(parseInt($("#pageNum").val())==1){
				$("#previous").attr("class","disabled");
			}
			//设置下一页按钮样式
			if(parseInt($("#pageNum").val())>= parseInt($("#pageNumMax").val())){
				$("#next").attr("class","disabled");
			}else{
				$("#next").removeAttr("class");
			}
		}
		
		$("#formcard").ajaxSubmit(options);
	}
	
	function changePagenum(activiti){
		var pagenum = parseInt($("#pageNum").val());
		var pageNumMax = parseInt($("#pageNumMax").val());
		
		$("li[id=navigator]").each(function(index){
			if(activiti == "next"){
				$(this).find("a").text(pagenum+index);
				if(parseInt($(this).find("a").text())>pageNumMax){
					$(this).find("a").attr("style","display:none");
				}
			}else if(activiti == "pre"){
				$(this).find("a").removeAttr("style");
				$(this).find("a").text(pagenum-4+index);
			}
		});
	}
	
	function prepage(){
		if(parseInt($("#pageNum").val()) <= 1){
			return ;	
		}
		//设置下一页按钮enable
		$("li[id=next]").removeClass("disabled");
		//设置当前页-1
		$("#pageNum").val(parseInt($("#pageNum").val())-1);
		//如果是第一页
		if(parseInt($("#pageNum").val()) == 1){
			$("li[id=previous]").attr("class","disabled");
		}	
		//动态刷新页面编号
		if($("li[id=navigator]:first").attr("class") == "active"){
			changePagenum("pre");
		}
		//设置当前页按钮选中样式
		$("li[id=navigator]").removeClass("active");
		$("li[id=navigator]").each(function(){
			if($("#pageNum").val() == $(this).find("a").html()){
				$(this).attr("class","active");
			}
		});
		
		$("#formcard").ajaxSubmit(options);
	}
	
	function nextpage(){	

		//设置上一页按钮enable
		$("#previous").removeClass("disabled");
		//如果是最后一页
		if(parseInt($("#pageNum").val()) >= parseInt($("#pageNumMax").val())){
			return ;	
		}
		
		$("#pageNum").val(parseInt($("#pageNum").val())+1);
		
		//动态刷新页面编号
		if($("li[id=navigator]:last").attr("class") == "active"){
			changePagenum("next");
		}
		
		var total = parseInt($("#total").val());
		var pagenum = parseInt($("#pageNum").val());
		var pageSize = parseInt($("#pageSize").val());
		var pageNumMax = parseInt($("#pageNumMax").val());
		
		//设置下一页按钮disabled
		if(parseInt($("#pageNum").val()) >= parseInt($("#pageNumMax").val())){
			$("#next").attr("class","disabled");
		}
		
		setCurrentPage();
		
		$("#formcard").ajaxSubmit(options);
	}
	
	//设置当前页按钮选中样式
	function setCurrentPage(){
		var pagenum = parseInt($("#pageNum").val());
		var pageNumMax = parseInt($("#pageNumMax").val());
		
		if(pagenum == 1){
			$("#previous").attr("class","disabled");
		}
		if(pagenum == pageNumMax){
			$("#next").attr("class","disabled");
		}
		
		$("li[id=navigator]").removeClass("active");
		$("li[id=navigator]").each(function(){
			if($("#pageNum").val() == $(this).find("a").html()){
				$(this).attr("class","active");
			}
			
			if(parseInt($(this).find("a").text())>pageNumMax){
				$(this).find("a").attr("style","display:none");
			}
		});
	}
	
	function del(obj){
		var cardid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/card/deleteCard?cardid='+cardid,   
	            type:'post',                    
	            dataType:'html',
	            async:false,
	            success:function(data){
	            	var ret = "${ret}";
	            	console.debug("ret"+ret);
					console.debug("sucess"+data);
					alert("sucess"+data);
		              $("#main").html(data);
		              
		              setCurrentPage();
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
	            		console.debug(XMLHttpRequest);
	            		 alert(XMLHttpRequest.status);
	            		 alert(XMLHttpRequest.readyState);
	            		 alert(XMLHttpRequest.responseText);
	            		 alert(textStatus);
	            		 alert(errorThrown);
	            	var ret = "${ret}";
	            	console.debug("ret"+ret.retCode);
	            	//console.debug("error   "+data.retCode);
	            	//alert("error   "+data.retCode);
	            }
		}
		
		$("#formcard").ajaxSubmit(deloptions);
	}
	</script>
