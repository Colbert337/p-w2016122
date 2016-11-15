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

				<li><a href="#">加注站管理</a></li>
				<li class="active">加注站信息管理</li>
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
				<h1>添加banner图</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-8">
					<!-- PAGE CONTENT BEGINS -->
					<form class="form-horizontal" id="editForm">
						<!-- #section:elements.form -->
						<%--<h5 class="header smaller lighter blue">基本信息</h5>--%>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="title"><span class="red_star">*</span> 标题： </label>
							<div class="col-sm-8">
								<input type="text" name="title" id="title" data-onFlag=""
									placeholder="标题(最大长度为12个汉字)" maxlength="12"
									class="col-xs-10 col-sm-12" value="${mbBanner.title}" /> <input
									type="hidden" id="mbBannerId" name="mbBannerId"
									value="${mbBanner.mbBannerId}" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="remark"> <span class="red_star">*</span> 正文：
							</label>
							<div class="col-sm-8">
								<input name="content" id="content" style="resize: none;"
									maxlength="32" placeholder="正文(最大长度为32个汉字)"
									value="${mbBanner.content }"
									class="col-xs-10 col-sm-12 limited form-control" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"><span
								class="red_star">*</span> 图片： </label>
							<div class="col-sm-8">
								<div class="widget-box">
									<div class="widget-header">
										<h4 class="widget-title">
											图片上传 <font size="2" color="red">图片比例：16:9，图片大小100k以内</font>
										</h4>
									</div>
									<div class="widget-body">
										<div class="widget-main">
											<img id="show_img" width="150" height="150" alt="150x150"
												src="" /> <input type="file" name="image"
												class="projectfile" id="indu_com_certif_select" /> <input
												type="hidden" id="img_path" value="${mbBanner.imgPath }"
												name="imgPath" />
											<button class="btn btn-sm btn-primary btn-file-space"
												type="button"
												onclick="savePhoto(this,'#indu_com_certif_select','#img_path','#show_img');">
												<i class="ace-icon fa fa-check bigger-110"></i> 图片上传
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"><span
								class="red_star">*</span>缩略图： </label>
							<div class="col-sm-8">
								<div class="widget-box">
									<div class="widget-header">
										<h4 class="widget-title">
											图片上传<font size="2" color="red">图片比例：1:1，图片大小50k以内</font>
										</h4>
									</div>
									<div class="widget-body">
										<div class="widget-main">
											<img id="show_sm_img" width="150" height="150" alt="150x150"
												src="" /> <input type="file" name="image2"
												class="projectfile" id="indu_com_sm_select" /> <input
												type="hidden" id="img_sm_path"
												value="${mbBanner.imgSmPath }" name="imgSmPath" />
											<button class="btn btn-sm btn-primary btn-file-space"
												type="button"
												onclick="savePhoto(this,'#indu_com_sm_select','#img_sm_path','#show_sm_img');">
												<i class="ace-icon fa fa-check bigger-110"></i> 图片上传
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="target_url"><span class="red_star">*</span> 链接地址： </label>
							<div class="col-sm-8">
								<input type="text" id="target_url"  maxlength="150"
									name="targetUrl" placeholder="链接地址" class="col-xs-10 col-sm-12" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-10 control-label no-padding-right"
								for="target_url">
								链接地址如：/portal/crm/help/showPage?pageid=<span class="red_star">66b24d910a0f4ce68689682f497c0349</span>
							</label>

						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="target_url"><span class="red_star">*</span>顺序 </label>
							<div class="col-sm-8">
								<input type="text" id="sort" onkeyup="clearNoNum2(this)" value="${mbBanner.sort }"
									placeholder="顺序" maxlength="11" name="sort"
									class="col-xs-10 col-sm-12" />
							</div>
						</div>


						<div class="form-group">
							<%-- <label class="col-sm-3 control-label no-padding-right"
										for="version">版本号： </label>
									<div class="col-sm-3">
										<div class="row">
											<div class="col-sm-3">
												<div class="btn-group j-android-versions">
													<span class="btn btn-primary btn-white dropdown-toggle" >选择安卓版本号<i class="ace-icon fa fa-angle-down icon-on-right"></i>
													</span>
													<ul class="dropdown-menu">
														<c:forEach items="${datas}" var="one" >
															<li>
																<div class="checkbox">
																	<label>
																		<input name="form-field-checkbox" type="checkbox" id="c${one }" values="${one }"  checked="checkbox" class="ace">
																		<span class="lbl"> ${one }</span>
																	</label>
																</div>
															</li>
														</c:forEach>
														 
													</ul>
												</div>
											</div>
										
										</div>
									</div> --%>

							<label class="col-sm-3 control-label no-padding-right"
								for="version">城市列表： </label>
							<div class="col-sm-8">
								<div class="row">
									<div class="col-sm-8">
										<div class="btn-group j-android-versions">
											<span class="btn btn-primary btn-white dropdown-toggle">城市列表<i
												class="ace-icon fa fa-angle-down icon-on-right"></i>
											</span>
											<ul class="dropdown-menu">
												<li>
													<div class="checkbox">
														<label> <input name="" type="checkbox" id="allche"
															onchange="checkedchange(this)" class="ace"> <span
															class="lbl"> 全选</span>
														</label>
													</div>
												</li>
												<c:forEach items="${city}" var="one">
													<li>
														<div class="checkbox">
															<label> <input name="form-field-checkbox"
																type="checkbox" value2="${one.cityId }"
																id="c${one.cityId }" values="${one.cityName }"
																onclick="checkedclick()" class="ace checked" /> <span
																class="lbl"> ${one.cityName }</span>
															</label>
														</div>
													</li>
												</c:forEach>

											</ul>

										</div>
										<div id="cityNameList"></div>
									</div>

								</div>
							</div>
							<%-- <label class="col-sm-3 control-label no-padding-right"
										for="version">城市： </label>
									<div class="col-sm-8">
										<select id="city" class="combobox col-sm-8">
											<option></option>
											<c:forEach items="${city}" var="one">
												<option value="${one.cityId }">${one.cityName }</option>
											</c:forEach>
										</select>
									</div> --%>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="remark"> 备注： </label>
							<div class="col-sm-8">
								<textarea name="remark" id="remark" style="resize: none;"
									maxlength="100" placeholder="备注"
									class="col-xs-10 col-sm-12 limited form-control">${mbBanner.remark}</textarea>
							</div>
						</div>
						<input type="hidden" id="imgType" name="imgType"
							value="${mbBanner.imgType}" />
					</form>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">

							<button class="btn btn-info" type="button"
								onclick="saveBanner();">
								<i class="ace-icon fa fa-check bigger-110"></i> 保存
							</button>
							&nbsp; &nbsp; &nbsp;

							<button class="btn" id="clear" type="button" onclick="clear1();">
								<i class="ace-icon fa fa-repeat bigger-110"></i> 重置
							</button>
							&nbsp; &nbsp; &nbsp;

							<button class="btn btn-success" type="button" onclick="init();">
								<i class="ace-icon fa fa-undo bigger-110"></i> 返回
							</button>
						</div>
					</div>
					<!-- <div class="modal-footer">
						<button class="btn btn-primary btn-sm" onclick="saveBanner()">确定</button>
						<button class="btn btn-sm" i="close" onclick="init()">取 消</button>
					</div>
					<div class="modal-footer"></div> -->
					<!-- /.col -->
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

function clearNoNum2(obj)
{
//先把非数字的都替换掉，除了数字和.
obj.value = obj.value.replace(/[^\d.]/g,"");
//必须保证第一个为数字而不是.
//obj.value = obj.value.replace(/^\./g,"");
//保证只有出现一个.而没有多个.
//obj.value = obj.value.replace(/\.{2,}/g,".");
//保证.只出现一次，而不能出现两次以上
//obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
 
	//获取主机地址，如： http://localhost:8080
	var localhostPaht = curWwwPath.substring(0, pos);
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

	// bootstrap验证控件
	$('#editForm').bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			title : {
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
			}
		}
	});

	var photoType = 0;
	var phototypeSm = 0;
	function savePhoto(fileobj, obj, obj1, obj2) {

		$(fileobj).parents("div").find("input[name=uploadfile]").each(
				function() {
					$(this).attr("name", "");
				});
		$(fileobj).parent("div").find("input:first").attr("name", "uploadfile");
		if ($(obj).val() == null || $(obj).val() == "") {
			bootbox.alert("请先上传文件");
			return;
		}
		if ("#img_path" == obj1) {
			if($(obj)[0].files[0].size/1024>100){
				bootbox.alert("图片大小过大，请重新上传");
				return;
			}
			photoType = 1;
		}
		if ("#img_sm_path" == obj1) {
			if($(obj)[0].files[0].size/1024>50){
				bootbox.alert("图片大小过大，请重新上传");
				return;
			}
			phototypeSm = 1;
		}
		

		var stationId = "mobile";
		var multipartOptions = {
			url : '../crmInterface/crmBaseService/web/upload?stationid='
					+ stationId,
			type : 'post',
			dataType : 'text',
			enctype : "multipart/form-data",
			success : function(data) {
				var s = JSON.parse(data);
				if (s.success == true) {
					
					$(obj2).hide();
					bootbox.alert("上传成功");
					
					$(obj1).val(s.obj);
				}

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				bootbox.alert("上传成功");
			}
		}
		$("#editForm").ajaxSubmit(multipartOptions);

	}

	function saveBanner() {
		$('#editForm').data('bootstrapValidator').validate();
		if (!$('#editForm').data('bootstrapValidator').isValid()) {
			return;
		}

		// alert( $("#editForm").is(":visible")); //是否可见)
		var cityName = "";
		var cityId = "";
		$('input:checkbox[name=form-field-checkbox]:checked').each(function(i) {
			if (0 == i) {
				cityName = $(this).attr("values");
				cityId = $(this).attr("value2");
			} else {
				cityName += ("," + $(this).attr("values"));
				cityId += ("," + $(this).attr("value2"));
			}
		});

		// var file=$("#indu_com_certif_select").val();
		if (photoType != 1) {
			bootbox.alert("请上传图片");
			return;
		}
		if (phototypeSm != 1) {
			bootbox.alert("请上传缩略图图片");
			return;
		}

		var saveOptions = {
			url : '../web/mobile/img/save',
			type : 'post',
			dataType : 'html',
			data : {
				// version : spCodesTemp,// 版本号 目前不需要
				city_id : cityId,
				city_name : cityName

			},
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
	
	function clear1(){
	//	console.log('1111');
	//	location.href=location.href;
		$.ajax({
			url : "../web/mobile/img/fondone",
			data : {
				imgType:$("[name=imgType]").val()
			},
			async : false,
			type : "POST",
			success : function(data) {
				$("#main").html(data);
			}
		})
	}
	
	jQuery(function($) {
		if ("${mbBanner.mbBannerId}" == "") {
			$("#show_img").hide();
			$("#show_sm_img").hide();
			$("#imgType").val("${imgType }");
			$("#clear").show();
			
		} else {
			$("#show_img").show();
			$("#show_sm_img").show();
			$("#clear").hide();
			var datas = '${mbBanner.city_id}'.split(',');
			for (var i = 0; i < datas.length; i++) {
				eval("$('#c" + datas[i] + "').attr('checked',true);");
			}
			checkedclick();
			$("#show_img").attr("src", '${mbBanner.imgPath}');
			$("#show_sm_img").attr("src", '${mbBanner.imgSmPath}');
			photoType = 1;
			phototypeSm = 1;
		}
		if ('${mbBanner.targetUrl }' != '') {
			$('#target_url').val(
					'${mbBanner.targetUrl }'.substr('${mbBanner.targetUrl }'
							.indexOf('/portal/crm/help/showPage?pageid='),
							'${mbBanner.targetUrl }'.length));
			
		}
		var $overflow = '';
		var colorbox_params = {
			rel : 'colorbox',
			reposition : true,
			scalePhotos : true,
			scrolling : false,
			previous : '<i class="ace-icon fa fa-arrow-left"></i>',
			next : '<i class="ace-icon fa fa-arrow-right"></i>',
			close : '&times;',
			current : '{current} of {total}',
			maxWidth : '100%',
			maxHeight : '100%',
			onOpen : function() {
				"'--"
				$overflow = document.body.style.overflow;
				document.body.style.overflow = 'hidden';
			},
			onClosed : function() {
				document.body.style.overflow = $overflow;
			},
			onComplete : function() {
				$.colorbox.resize();
			}
		};

		$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
		$("#cboxLoadingGraphic").html(
				"<i class='ace-icon fa fa-spinner orange fa-spin'></i>");// let's
		// add a
		// custom
		// loading
		// icon

		$(document).one('ajaxloadstart.page', function(e) {
			$('#colorbox, #cboxOverlay').remove();
		});

		$('.j-android-versions .btn').on('click', function() {
			var $parent = $(this).parent();
			if ($parent.hasClass('open')) {
				$parent.removeClass('open');
			} else {
				$parent.addClass('open');
			}
		});
	})

	function saveBanner() {
		$('#editForm').data('bootstrapValidator').validate();
		if (!$('#editForm').data('bootstrapValidator').isValid()) {
			return;
		}

		// alert( $("#editForm").is(":visible")); //是否可见)
		var cityName = "";
		var cityId = "";
		$('input:checkbox[name=form-field-checkbox]:checked').each(function(i) {
			if (0 == i) {
				cityName = $(this).attr("values");
				cityId = $(this).attr("value2");
			} else {
				cityName += ("," + $(this).attr("values"));
				cityId += ("," + $(this).attr("value2"));
			}
		});

		// var file=$("#indu_com_certif_select").val();
		if (photoType != 1) {
			bootbox.alert("请上传图片");
			return;
		}
		if (phototypeSm != 1) {
			bootbox.alert("请上传缩略图图片");
			return;
		}

		var saveOptions = {
			url : '../web/mobile/img/save',
			type : 'post',
			dataType : 'html',
			data : {
				// version : spCodesTemp,// 版本号 目前不需要
				city_id : cityId,
				city_name : cityName

			},
			success : function(data) {
				$("#main").html(data);
				$("#modal-table").modal("show");
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				bootbox.alert("操作失败！");
			}
		}
		$("#editForm").ajaxSubmit(saveOptions);
		init();
	}

	function checkedclick() {
		console.log('checked')
		var isok = true;
		var cityNameList = "";
		$('.checked').each(function(index, obj) {
			if (!$(obj).is(':checked')) {
				isok = false;
			} else {
				cityNameList += $(obj).attr('values') + ",";
			}
		})
		if (cityNameList.length != 0) {
			cityNameList = cityNameList.substring(0, cityNameList.length - 1)
		}
		$('#cityNameList').text(cityNameList);
		if (isok) {
			$("#allche").prop("checked", true);
		} else {
			$("#allche").prop("checked", false);
		}
	}

	function init() {
		loadPage('#main', '../web/mobile/img/list/page?imgType='
				+ $("[name=imgType]").val());
	}
</script>
