<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

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
								<a href="#">运输公司管理</a>
							</li>
							<li class="active">运输公司信息管理</li>
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
								上传许可证
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="gastationform">
								<jsp:include page="/common/page_param.jsp"></jsp:include>
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="sys_transportion_id"> 运输公司编号： </label>

										<div class="col-sm-4">
											<input type="text" id="sys_transportion_id"  name="sys_transportion_id" value="${param.transportionid}" class="form-control" readonly="readonly"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 工商注册号： </label>

										<div class="col-sm-4">
											<input type="text"  id="indu_com_number" name="indu_com_number" class="form-control"   placeholder="输入工商注册号"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 工商注册证书： </label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">工商注册证书照片上传</h5>
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
										<label class="col-sm-3 control-label no-padding-right"> 税务注册号： </label>

										<div class="col-sm-4">
											<input type="text"  id="tax_number" name="tax_number" class="form-control"  placeholder="输入税务注册号"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 税务注册证书： </label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">税务注册证书照片上传</h5>
												</div>

												<div class="widget-body">
													<div class="widget-main">
														<!-- <input type="file" id="id-input-file-31"/> -->
														<input type="file" name="image" class="projectfile"  id="tax_certif_select" />
														<input type="hidden" id="tax_certif" name="tax_certif"/>
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#tax_certif_select','#tax_certif');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> LNG储装证书： </label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">LNG储装证书照片上传</h5>
												</div>

												<div class="widget-body">
													<div class="widget-main">
														<!-- <input type="file" id="id-input-file-32" /> -->
														<input type="file" name="image" class="projectfile"  id="lng_certif_select" />
														<input type="hidden" id="lng_certif" name="lng_certif"/>
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#lng_certif_select','#lng_certif');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 危化品证书： </label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">危化品证书照片上传</h5>
												</div>

												<div class="widget-body">
													<div class="widget-main">
														<!-- <input type="file" id="id-input-file-33" /> -->
														
														<input type="file" name="image" class="projectfile"  id="dcp_certif_select" />
														
														<input type="hidden" name="dcp_certif" id="dcp_certif"/>
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#dcp_certif_select','#dcp_certif');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
														
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="submit" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="ace-icon fa fa-repeat bigger-110"></i>
												重置
											</button>
											
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-success" type="buttom" onclick="returnpage();">
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
		                message: 'The cardno is not valid',
						validators: {
							notEmpty: {
								message: '工商注册号不能为空'
							},
		                    stringLength: {
		                        max: 15,
		                        message: '工商注册号不能超过15位'
							}
		                    }
		                }
		            },
		            tax_number: {
		                message: 'The cardno is not valid',
					validators: {
						notEmpty: {
							message: '税务注册号不能为空'
						},
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
		            url:'<%=basePath%>/web/transportion/saveTransportion',
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
						$("#modal-table").modal("show");
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {
		            	$("#modal-table").modal("show");
		 	       }
			}
						
			$("#gastationform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '<%=basePath%>/web/transportion/transportionList');
		}
		
		function save_photo(fileobj,obj,obj1){
			
			$(fileobj).parents("div").find("input[name=uploadfile]").each(function(){
				$(this).attr("name","");
			});
			
			$(fileobj).parent("div").find("input:first").attr("name","uploadfile");
			
			if($(obj).val()==null || $(obj).val()==""){
				alert("请先上传文件");	
				return;
			}
			
			var multipartOptions ={   
		            url:'../crmBaseService/web/upload?stationid='+$("#sys_transportion_id").val(),   
		            type:'post',                    
		            dataType:'text',
		            enctype:"multipart/form-data",
		            success:function(data){
		            	var s = JSON.parse(data);
		            	if(s.success == true){
		            		alert("上传成功");
		            		$(obj1).val(s.obj);
		            	}
		            	
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#gastationform").ajaxSubmit(multipartOptions);
		}
		</script>
