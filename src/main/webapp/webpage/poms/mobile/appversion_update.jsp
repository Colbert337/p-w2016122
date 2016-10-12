<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	String imagePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>

<link href="<%=basePath%>/common/uploadify/uploadify.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>/common/uploadify/swfobject.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/uploadify/jquery.uploadify.v2.1.4.min.js"/>

<script type="text/javascript"
	src="<%=basePath%>/assets/js/date-time/moment.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/assets/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/common/js/fileinput.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/zh.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/json2.js"></script>

<link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome.css" />
<link rel="stylesheet"
	href="<%=basePath%>/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/chosen.css" />
<link rel="stylesheet"
	href="<%=basePath%>/assets/css/bootstrap-datepicker3.css" />
<link rel="stylesheet"
	href="<%=basePath%>/assets/css/bootstrap-timepicker.css" />
<link rel="stylesheet"
	href="<%=basePath%>/assets/css/daterangepicker.css" />
<link rel="stylesheet"
	href="<%=basePath%>/assets/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/colorpicker.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/ace-fonts.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/css/ace.css"
	class="ace-main-stylesheet" id="main-ace-style" />

<link rel="stylesheet" href="<%=basePath%>/common/css/fileinput.css" />




<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<!-- #section:basics/content.breadcrumbs -->
		<div class="breadcrumbs" id="breadcrumbs">
			<script type="text/javascript">
				try {
					ace.settings.check('breadcrumbs', 'fixed')
				} catch (e) {
				}
			</script>

			<ul class="breadcrumb">
				<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">主页</a>
				</li>

				<li><a href="#">APP版本管理</a></li>
				<li class="active">APP版本管理</li>
			</ul>
			<!-- /.breadcrumb -->

			<!-- #section:basics/content.searchbox -->
			<div class="nav-search" id="nav-search">
				<form class="form-search">

					<input id="retCode" type="hidden" value=" ${ret.retCode}" /> <input
						id="retMsg" type="hidden" value=" ${ret.retMsg}" /> <span
						class="input-icon"> <input type="text"
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
			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>APP版本管理</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-8">
					<!-- PAGE CONTENT BEGINS -->
					<form class="form-horizontal" id="editForm">

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="url">   下载地址：
							</label>
							<div class="col-sm-8">
								<input name="url" id="url" style="resize: none;"

									value="${mbAppVersion.url }"
									class="col-xs-10 col-sm-12 limited form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								   for="version">   版本信息：
							</label>
							<div class="col-sm-8">
								<input name="version" id="version" style="resize: none;"

									   value="${mbAppVersion.version }"
									   class="col-xs-10 col-sm-12 limited form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								   for="code">   版本号：
							</label>
							<div class="col-sm-8">
								<input name="code" id="code" style="resize: none;"

									   value="${mbAppVersion.code }"
									   class="col-xs-10 col-sm-12 limited form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								   for="isPublish">   是否发布：
							</label>
							<div class="col-sm-8">
								<%--<input name="isPublish" id="isPublish" style="resize: none;"

									   value="${mbAppVersion.isPublish }"
									   class="col-xs-10 col-sm-12 limited form-control" />--%>
								<select name="isPublish" title="状态">
									<option value="1" <c:if test="${mbAppVersion.isPublish == '1' }">selected</c:if> >是</option>
									<option value="0" <c:if test="${mbAppVersion.isPublish == '0' }">selected</c:if> >否</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								   for="remark"> 备注： </label>
							<div class="col-sm-8">
								<textarea name="remark" id="remark" style="resize: none;"
										  maxlength="100" placeholder="备注"
										  class="col-xs-10 col-sm-12 limited form-control">${mbAppVersion.remark}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="createdDateStr">添加时间： </label>
							<div class="col-sm-4 datepicker-noicon">
								<div class="input-group">
									<input class="form-control date-picker" name="createdDateStr" id="createdDateStr" type="text" readonly="readonly" data-date-format="yyyy-mm-dd"  value="${mbAppVersion.createdDateStr}"/>
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
								</div>
							</div>
						</div>

						<input type="hidden" id="appVersionId" name="appVersionId"
							value="${mbAppVersion.appVersionId}" />
					</form>

				<%--	<div class="showImg">
						<img class="showImgs" width="100" height="100" alt="" src="easy/js/uploadify/default_image.gif">
					</div>--%>
					<input type="file" name="uploadify" id="uploadify" style="width:200px;"/>
					<p><a href="javascript: jQuery('#uploadify').uploadifyUpload()">开始上传</a></p>



					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">

							<button class="btn btn-info" type="button"
								onclick="saveAppVersion();">
								<i class="ace-icon fa fa-check bigger-110"></i> 保存
							</button>
							&nbsp; &nbsp; &nbsp;

						<%--	<button class="btn" id="clear" type="button" onclick="clear1();">
								<i class="ace-icon fa fa-repeat bigger-110"></i> 重置
							</button>
							&nbsp; &nbsp; &nbsp;
--%>
							<button class="btn btn-success" type="button" onclick="init();">
								<i class="ace-icon fa fa-undo bigger-110"></i> 返回
							</button>
						</div>
					</div>

				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
</div>
</div>
</div>
</div>
</div>
</div>

<script type="text/javascript">


	// bootstrap验证控件
	/*$('#editForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			/!*title : {
				validators : {
					notEmpty : {
						message : '标题不能为空'
					}
				}
			},
			content : {
				validators : {
					notEmpty : {
						message : '正文不能为空'
					}
				}
			},
			targetUrl : {
				validators : {
					notEmpty : {
						message : '链接地址不能为空'
					}
				}
			},
			sort : {
				validators : {
					notEmpty : {
						message : '顺序不能为空'
					}
				}
			}*!/
		}
	});
*/


	function saveAppVersion() {
		/*$('#editForm').data('bootstrapValidator').validate();
		if (!$('#editForm').data('bootstrapValidator').isValid()) {
			return;
		}*/




		var saveOptions = {
			url : '../web/mobile/appversion/save',
			type : 'post',
			dataType : 'html',
			success : function(data) {
				$("#main").html(data);
				$("#modal-table").modal("show");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				bootbox.alert("操作失败！");
			}
		}
		$("#editForm").ajaxSubmit(saveOptions);

		$("#editModel").modal('hide');
		$(".modal-backdrop").css("display", "none");

	}
	

	





	function init() {
		loadPage('#main', '../web/mobile/appversion/list/page');
	}


	//datepicker plugin
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		language: 'cn'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});




//官方网址：http://www.uploadify.com/
			$(document).ready(function(){
				$("#uploadify").uploadify({
					'uploader' : "<%=basePath%>/common/uploadify/uploadify.swf",
					'script'    : "<%=basePath%>/web/mobile/appversion/uploadFile",
					'cancelImg' : "<%=basePath%>/common/uploadify/cancel.png",
					'folder' : "<%=basePath%>/null/",//上传文件存放的路径,请保持与uploadFile.jsp中PATH的值相同
					'queueId' : "fileQueue",
					'queueSizeLimit' : 10,//限制上传文件的数量
					'fileExt' : "*.apk,*.apk",
					'fileDesc' : "APK *.apk",//限制文件类型
					'auto'  : false,
					'multi'  : true,//是否允许多文件上传
					'simUploadLimit': 2,//同时运行上传的进程数量
					'buttonText': " select files",
					'onComplete': function(event, ID, fileObj, response, data) {
						//response返回值：重命名后的文件名称,文件保存路径
						var resultArray = response.split(",");
						var realName = resultArray[0];//图片现在的名称，前面加上时间的图片名称，带扩展名
						realName = $.trim(realName);
						var p = "<%=basePath%>/null/"+realName;
						$(".showImgs").attr("src",p);
					}
				});
			});

</script>
