

$('#roadform').bootstrapValidator({
	message: 'This value is not valid',
	feedbackIcons: {
		valid: 'glyphicon glyphicon-ok',
		invalid: 'glyphicon glyphicon-remove',
		validating: 'glyphicon glyphicon-refresh'
	},
	fields: {
		conditionType: {
			validators: {
				notEmpty: {
					message: '路况类型不能为空'
				}
			}
		},
		longitude: {
			validators: {
				notEmpty: {
					message: '坐标不能为空'
				}
			}
		},
		longitude: {
			validators: {
				notEmpty: {
					message: '坐标不能为空'
				}
			}
		},
		conditionMsg: {
			validators: {
				notEmpty: {
					message: '路况说明不能为空'
				}
			}
		},
		address: {
			validators: {
				notEmpty: {
					message: '详细地址不能为空'
				}
			}
		},
		direction: {
			validators: {
				notEmpty: {
					message: '方向不能为空'
				}
			}
		}
	}
});


$('.timebox').datetimepicker({
	icons: {
		time: 'fa fa-clock-o',
		date: 'fa fa-calendar',
		up: 'fa fa-chevron-up',
		down: 'fa fa-chevron-down',
		previous: 'fa fa-chevron-left',
		next: 'fa fa-chevron-right',
		today: 'fa fa-arrows ',
		clear: 'fa fa-trash',
		close: 'fa fa-times'
	}
}).next().on(ace.click_event, function(){
	$(this).prev().focus();
});

function saveRoad(){
	/*手动验证表单，当是普通按钮时。*/
	if($("#startTime_str").val()==''){
		bootbox.alert("开始日期不能为空！");
		return;
	}
	
	$('#roadform').data('bootstrapValidator').validate();
	if(!$('#roadform').data('bootstrapValidator').isValid()){
		return ;
	}

	var options ={
		url:'../web/mobile/road/saveRoad',
		type:'post',
		dataType:'text',
		success:function(data){
			$("#main").html(data);
			$("#modal-table").modal("show");
		},error:function(XMLHttpRequest, textStatus, errorThrown) {

		}
	}

	$("#roadform").ajaxSubmit(options);
}
$("#end").hide();
function changeType(obj){
	if($('#'+obj).val()=='01'){
		$("#end").hide();
	}else if($('#'+obj).val()=='02'){
		$("#end").hide();
	}else if($('#'+obj).val()=='05'){
		$("#end").hide();
	}else{
		$("#end").show();
	}

}
function init(){
	loadPage('#main', '../webpage/poms/mobile/roadAdd.jsp');

}
var device_token = '';
var driver_name = '';
var user_id = '';
function addUser(){


//
//		 var listOptions ={
//		    		url:'../web/message/driverList',
//		    		type:'post',
//		    		dataType:'html',
//		    		success:function(data){
//		    			//console.log("这里分页之后");
//		    			$("#content").html(data);
//
//		    			$("#editModel").modal('show');
//		    			if($("#retCode").val() != "100"){
//
//		    			}
//		    			$('[data-rel="tooltip"]').tooltip();
//		    		},error:function(XMLHttpRequest, textStatus, errorThrown) {
//
//		    		}
//		    	}
//			 $("#formgastation").ajaxSubmit(listOptions);
	loadPage('#content', '../web/message/driverList');
	$("#editModel").modal('show');
}
function closeDialog(obj){
	$("#" + obj).modal('hide').removeClass('in');
	$("body").removeClass('modal-open').removeAttr('style');
	$(".modal-backdrop").remove();
//		init();

}
var projectfileoptions = {
	showUpload : false,
	showRemove : false,
	language : 'zh',
	allowedPreviewTypes : [ 'image' ],
	allowedFileExtensions : [ 'jpg', 'png', 'gif', 'jepg' ],
	maxFileSize : 10000,
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
				var a=s.obj.substring(s.obj.indexOf('/')+1,s.obj.length);

				bootbox.alert("上传成功");
				$(obj1).val(s.obj);
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			bootbox.alert("上传成功");
		}
	}
	$("#roadform").ajaxSubmit(multipartOptions);

}



function returnpage(){
	loadPage('#main', '../web/mobile/road/roadList');
}
		