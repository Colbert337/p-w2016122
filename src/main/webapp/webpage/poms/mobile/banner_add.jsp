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
	
	<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap.css" />
	<link rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.css" />
	<link rel="stylesheet" href="<%=basePath %>/assets/css/jquery-ui.custom.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/chosen.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-datepicker3.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-timepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/daterangepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-datetimepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/colorpicker.css" />
	<link rel="stylesheet" href="<%=basePath %>/assets/css/ace-fonts.css" />
	<link rel="stylesheet" href="<%=basePath %>/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
	
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
					<div class="page-content">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								添加banner图
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="gastationform">
								<jsp:include page="/common/page_param.jsp"></jsp:include>
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 标题： </label>
										<div class="col-sm-4">
											<input type="text" id="" class="form-control"  name="sys_gas_station_id" value="${param.gastationid}" readonly="readonly"/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 图片： </label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h4 class="widget-title">图片上传</h4>
												</div>
												<div class="widget-body">
													<div class="widget-main">
														<!-- <input type="file" id="id-input-file-3"/>-->
														<input type="file" name="image" class="projectfile"  id="indu_com_certif_select" />
														<input type="hidden" id="indu_com_certif" name="indu_com_certif"/>
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#indu_com_certif_select','#indu_com_certif');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 链接地址： </label>
										<div class="col-sm-4">
											<input type="text" id="" class="form-control"  name="sys_gas_station_id" value="${param.gastationid}" readonly="readonly"/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 版本号： </label>
										<div class="col-sm-4">
											<input type="text" id="" class="form-control"  name="sys_gas_station_id" value="${param.gastationid}" readonly="readonly"/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 备注： </label>
										<div class="col-sm-4">
											<input type="text" id="" class="form-control"  name="sys_gas_station_id" value="${param.gastationid}" readonly="readonly"/>
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
	
			var projectfileoptions = {
		        showUpload : false,
		        showRemove : false,
		        language : 'zh',
		        allowedPreviewTypes : [ 'image' ],
		        allowedFileExtensions : [ 'jpg', 'png', 'gif', 'jepg' ],
		        maxFileSize : 1000,
		    }
		// 文件上传框
		$('input[class=projectfile]').each(function() {
		    var imageurl = $(this).attr("value");
		
		    if (imageurl) {
		        var op = $.extend({
		            initialPreview : [ // 预览图片的设置
		            "<img src='" + imageurl + "' class='file-preview-image'>", ]
		        }, projectfileoptions);
		
		        $(this).fileinput(op);
		    } else {
		        $(this).fileinput(projectfileoptions);
		    }
		});
	
		var contral = "0";
		
			//bootstrap验证控件		
		    $('#gastationform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		            indu_com_number: {
		                    stringLength: {
		                        max: 15,
		                        message: '工商注册号不能超过15位'
		                    }
		                },
		                tax_number: {
			                    stringLength: {
			                        max: 15,
			                        message: '税务注册号不能超过15位'
			                    }
			            }
		            }
		    });
			    
		function save(){
			$("#address").val($("#province").find("option:selected").text()+" "+$("#city").find("option:selected").text()+" "+$("#detail").val());
			
			/*手动验证表单，当是普通按钮时。*/
			$('#gastationform').data('bootstrapValidator').validate();
			if(!$('#gastationform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var options ={   
		            url:'<%=basePath%>/web/gastation/saveGastation',
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
		            	$("#modal-table").modal("show");
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
						
			$("#gastationform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '<%=basePath%>/web/gastation/gastationList');
		}
		
		function save_photo(fileobj,obj,obj1){
			
			$(fileobj).parents("div").find("input[name=uploadfile]").each(function(){
				$(this).attr("name","");
			});
			
			$(fileobj).parent("div").find("input:first").attr("name","uploadfile");
			
			if($(obj).val()==null || $(obj).val()==""){
				bootbox.alert("请先上传文件");
				return;
			}
			
			var multipartOptions ={   
		            url:'../crmInterface/crmBaseService/web/upload?stationid='+$("#sys_gas_station_id").val(),
		            type:'post',                    
		            dataType:'text',
		            enctype:"multipart/form-data",
		            success:function(data){
		            	var s = JSON.parse(data);
		            	if(s.success == true){
		            		bootbox.alert("上传成功");
		            		$(obj1).val(s.obj);
		            	}
		            	
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#gastationform").ajaxSubmit(multipartOptions);
		}
		</script>
