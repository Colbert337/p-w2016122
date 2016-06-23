<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

	<script type="text/javascript" src="<%=basePath %>/assets/js/date-time/moment.js"></script>
	<script type="text/javascript" src="<%=basePath %>/assets/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="<%=basePath %>/common/js/fileinput.js"></script>
	<script type="text/javascript" src="<%=basePath %>/common/js/zh.js"></script>
	<script type="text/javascript" src="<%=basePath %>/common/js/json2.js"></script>

	<link rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.css" />
	<link rel="stylesheet" href="<%=basePath %>/assets/css/jquery-ui.custom.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/chosen.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-datepicker3.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-timepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/daterangepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-datetimepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/colorpicker.css" />
	
	<link rel="stylesheet" href="<%=basePath %>/common/css/fileinput.css" />

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">主页</a>
							</li>

							<li>
								<a href="#">加注站管理</a>
							</li>
							<li class="active">加注站信息管理</li>
						</ul><!-- /.breadcrumb -->

						<!-- #section:basics/content.searchbox -->
						<div class="nav-search" id="nav-search">
							<form class="form-search" >
							
								<input id="retCode" type="hidden" value=" ${ret.retCode}" />
								<input id="retMsg" type="hidden" value=" ${ret.retMsg}" />
								
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->

						<!-- /section:basics/content.searchbox -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								加注站保证金设置
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="gastationform">
								
								<jsp:include page="/common/page_param.jsp"></jsp:include>

									<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right"> 加注站名称： </label>

										<div class="col-sm-4">
											<label class="control-label no-padding-right" >${param.gastationame}</label>
											<input type="text" name="account_id" value="${param.acconutid}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label for="email" class="col-sm-3 control-label no-padding-right" >加注站保证金： </label>

										<div class="col-sm-4">
											<input type="text" name="deposit" placeholder="输入保证金" class="form-control" value="${param.gastationdeposit}" maxlength="8"/>
										</div>
									</div>
									
									<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right">企业名称：</label>

										<div class="col-sm-4">
											<input type="text" name="company" placeholder="输入企业名称" class="form-control" maxlength="20"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="expiry_date">转账时间： </label>
										<div class="col-sm-4 datepicker-noicon">
											<div class="input-group">
												<input class="form-control date-picker" name="transfer_time_frompage" id="transfer_time_frompage" type="text" readonly="readonly" data-date-format="yyyy-mm-dd"/>
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
												</div>
										</div>
									</div>
									
									<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right">转账方式：</label>

										<div class="col-sm-4">
											<input type="text" name="transfer_type" placeholder="输入转账方式" class="form-control" maxlength="20"/>
										</div>
									</div>
									
									<div class="form-group">
										<label for="station_manager" class="col-sm-3 control-label no-padding-right">操作人员：</label>

										<div class="col-sm-4">
											<input type="text" id="operator" name="operator" class="form-control"  maxlength="10" value="${sessionScope.currUser.user.userName}" readonly="readonly"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">备注： </label>

										<div class="col-sm-4">
											<textarea class="form-control" name="remark" rows="5" maxlength="100"></textarea>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn" type="reset">
												<i class="ace-icon fa fa-repeat bigger-110"></i>
												重置
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn btn-success" type="button" onclick="returnpage();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>

									<jsp:include page="/common/message.jsp"></jsp:include>
										
								</form>						
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>

	<script type="text/javascript">
	
	//datepicker plugin
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		language: 'cn'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
		
			//bootstrap验证控件		
		    $('#gastationform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	deposit: {
		                validators: {
		                    notEmpty: {
		                        message: '加注站保证金不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]+([.]{1}[0-9]+){0,1}$',
		                        message: '加注站保证必须是数字'
		                    }
		                }
		            },
		            company: {
		                validators: {
		                    notEmpty: {
		                        message: '企业名称不能为空'
		                    }
		                }
		            }
		         }
		    });
			    
		function save(){			
			/*手动验证表单，当是普通按钮时。*/
			$('#gastationform').data('bootstrapValidator').validate();
			if(!$('#gastationform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var options ={   
		            url:'../web/gastation/depositGastation',   
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
		            	$("#modal-table").modal("show");
		            	if($("#retCode").val() != 100){
		            		
		            	}
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {
		            	
		 	       }
			}
		
			$("#gastationform").ajaxSubmit(options);
		}
		
		function init(){
			loadPage('#main', '../webpage/poms/gastation/gastation_deposit.jsp');
		}
		</script>
