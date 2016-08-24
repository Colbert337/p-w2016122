/**
 * Created by Administrator on 2016/6/20. Author: wdq
 */
/* 分页相关方法 start */
window.onload = setCurrentPage();

var listOptions = {
	url : '../web/mobile/img/list/page',
	type : 'post',
	dataType : 'html',
	success : function(data) {
		$("#main").html(data);
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		bootbox.alert("操作失败!")// 保存成功弹窗
	}
}

function commitForm(obj) {
	// 设置当前页的值
	if (typeof obj == "undefined") {
		$("#pageNum").val("1");
	} else {
		$("#pageNum").val($(obj).text());
	}

	$("#listForm").ajaxSubmit(listOptions);
}

function choose(obj) {
	$("[name=imgType]").val($(obj).val());
	// loadPage('#main', '../web/sysparam/cashbackList');
	commitForm();
}

/* 分页相关方法 end */
// 显示添加用户弹出层add
function addBanner() {

	$('input:checkbox[name=form-field-checkbox]:checked').each(function(i) {
		spCodesTemp = $(this).attr("checked", true);
	});
	photoType=0;
	$("#show_img").hide();
	clearDiv();
	$("#editBanner").text("添加内容");
	/* 密码输入框改为可编辑 */
	$("#pay_code").removeAttr("readonly");
	$("#re_password").removeAttr("readonly");
	$("#plates_number").attr("data-onFlag", "add");

	$("#editModel").modal('show').on('hide.bs.modal', function() {
		$('#editForm').bootstrapValidator('resetForm', true);
		$('.user-name-valid').remove();
	});

	$("[name=imgType]:last").val($("[name=imgType]:first").val());
}

/**
 * 删除图片
 */
function deleteBanner(imgId) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("确认要删除图片吗？", function(result) {
		if (result) {
			var deleteOptions = {
				url : '../web/mobile/img/delete',
				data : {
					mbBannerId : imgId
				},
				type : 'post',
				dataType : 'text',
				success : function(data) {
					$("#main").html(data);
					$("#modal-table").modal("show");
					$('[data-rel="tooltip"]').tooltip();
				}
			}
			$("#listForm").ajaxSubmit(deleteOptions);
		}
	})

}
// 获取当前网址，如： http://localhost:8080/Tmall/index.jsp
var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录如：/Tmall/index.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8080
var localhostPaht = curWwwPath.substring(0, pos);
// 获取带"/"的项目名，如：/Tmall
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
// 显示编辑用户弹出层
var html;
function editBanner(imgId) {
	$("#show_img").show();
	$('input:checkbox[name=form-field-checkbox]:checked').each(function(i) {
		spCodesTemp = $(this).attr("checked", false);
	});

	$.ajax({
		url : "../web/mobile/img/info",
		data : {
			mbBannerId : imgId
		},
		async : false,
		type : "POST",
		success : function(data) {
			$("#mb_banner_id").val(data.mbBannerId);
			$("#title").val(data.title);
			$("#img_path").val(data.imgPath);
			$("#target_url").val(data.targetUrl);
			$("#sort").val(data.sort);
			$("#remark").text(data.remark);
			$("#show_img").attr("src", localhostPaht + data.imgPath);
			$("#operator").val(data.operator);
			$("#content").val(data.content);+
			$('#city').val(data.city_id);
			$("#editBanner").text("修改内容");
//			alert(data.version);
//			if(data.version!=""){
//			版本信息
//			var datas = data.version.split(',');
//			for (var i = 0; i < datas.length; i++) {
//				eval("$('#c" + datas[i] + "').attr('checked',true);");
//				// eval("alert($('#c"+datas[i]+"').attr('checked'));");
//				// alert("$('#c"+datas[i]+"').attr('checked',true);");
//			}
//			}
		}
	})
	$("#editModel").modal('show');
}

$(document).keyup(function(event){

	 switch(event.keyCode) {
	 case 27:
	 case 96:
		 closeDialog('editModel')
	 }
	});

function init(){
	loadPage('#main', '../web/mobile/img/list/page');
}
/* 取消弹层方法 */
function closeDialog(divId) {

	jQuery('#editForm').validationEngine('hide');// 隐藏验证弹窗
	$("#editForm :input").each(function() {
		$(this).val("");
	});
	$("#avatar_b").empty();
	
	 $("#"+divId).modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
	    
	    init();

}
function clearDiv() {
	$("#editForm :input").each(function() {
		$(this).val("");
	});
	$("#avatar_b").empty();
}

/**
 * 查看明细
 */
function showInnerModel(obj1, tr) {
	var show = $("label[name='show']");
	for (var i = 0; i < show.length; i++) {

		show[i].innerHTML = tr.children('td').eq(i).text().replace(/(.{28})/g,
				'$1\n');
	}
	$("#innerimg1").attr("src", obj1);
	$("#innerimg1").parent("a").attr("href", obj1);

	$("#innerModel").modal('show');
}
/**
 * 保存图片信息
 */

function saveBanner() {
	$('#editForm').data('bootstrapValidator').validate();
	if (!$('#editForm').data('bootstrapValidator').isValid()) {
		return;
	}

	// alert( $("#editForm").is(":visible")); //是否可见)
	var spCodesTemp = "";
	$('input:checkbox[name=form-field-checkbox]:checked').each(function(i) {
		if (0 == i) {
			spCodesTemp = $(this).attr("values");
		} else {
			spCodesTemp += ("," + $(this).attr("values"));
		}
	});
//	var file=$("#indu_com_certif_select").val();
	if (photoType!=1) {
		bootbox.alert("请上传图片");
		return;	
	}
	
	var saveOptions = {
		url : '../web/mobile/img/save',
		type : 'post',
		dataType : 'html',
		data : {
//			version : spCodesTemp,// 版本号  目前不需要
			city_id:$('#city').val()
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
/**
 * 判断文件格式
 */
function fileFormat() {
	var fileName = $("#file_import").val();
	var suffix = fileName.substr(fileName.indexOf("."));
	if (suffix != '.xls' && suffix != ".xlsx") {
		bootbox.alert("导入文件格式错误，必须是excle格式！");
		return false;
	}
}

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

var photoType=0;
function savePhoto(fileobj, obj, obj1) {
	
	$(fileobj).parents("div").find("input[name=uploadfile]").each(function() {
		$(this).attr("name", "");
	});
	$(fileobj).parent("div").find("input:first").attr("name", "uploadfile");
	if ($(obj).val() == null || $(obj).val() == "") {
		bootbox.alert("请先上传文件");
		return;
	}
	photoType=1;
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
				$("#show_img").hide();
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
/**
 * 文件上传验证
 */
 
$('#importForm').bootstrapValidator({
	message : 'This value is not valid',
	feedbackIcons : {
		valid : 'glyphicon glyphicon-ok',
		invalid : 'glyphicon glyphicon-remove',
		validating : 'glyphicon glyphicon-refresh'
	},
	fields : {
		fileImport : {
			validators : {
				notEmpty : {
					message : '请选择要导入的文件'
				}
			}
		},
		remote : {
			url : '../web/tcms/vehicle/info/fileFormat',
			type : "post",
			async : false,
			data : function(validator, $field, value) {
				return {
					fileImport : $("#file_import").val()
				};
			},
			message : '文件导入失败'
		}
	}
});

jQuery(function($) {
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
