// 获取当前网址，如： http://localhost:8080/Tmall/index.jsp
var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录如：/Tmall/index.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8080
var localhostPaht = curWwwPath.substring(0, pos);
// 获取带"/"的项目名，如：/Tmall
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

var listOptions = {
	url : '../web/mobile/road/roadList',
	type : 'post',
	dataType : 'html',
	success : function(data) {
		$("#main").html(data);
		if ($("#retCode").val() != "100") {
			$("#modal-table").modal("show");
		}
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {

	}
}

function showContent(){
	closeDialog('innerModel');
	$("#content").modal('show');
	
}

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


window.onload = setCurrentPage();

	
function commitForm(obj) {
	// 设置当前页的值
	
	if(obj=='return'){
	
		$("#formRoad").ajaxSubmit( {
			url : '../web/mobile/road/roadList',
			type : 'post',
			dataType : 'html',
			data:{
				conditionMsg:roadmsg,       
				conditionStatus:roadStatus,
				conditionType:roadType,
				publisherTime_str:roadPT,
				auditorTime_str:roadAT
			},
			success : function(data) {
				$("#main").html(data);
				if ($("#retCode").val() != "100") {
					$("#modal-table").modal("show");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {

			}
		});
		return;
	}else{
		if (typeof obj == "undefined") {
			$("#pageNum").val("1");
		} else {
			$("#pageNum").val($(obj).text());
		}
	}
	$("#formRoad").ajaxSubmit(listOptions);
}
function closeDialog(obj) {
	$("#" + obj).modal('hide').removeClass('in');
	$("body").removeClass('modal-open').removeAttr('style');
	$(".modal-backdrop").remove();
	// init();

}
var roadmsg;
var roadStatus;
var roadType;
var roadPT;
var roadAT;
function showShixiao(url){
	roadmsg=$('#msg').val();
	roadStatus=$('#conditionStatus').val();
	roadType=$('#type').val();
	roadPT=$('#publisherTime_str').val();
	roadAT=$('#auditorTime_str').val();
	loadPage('#main', url);
}


function init() {
	loadPage('#main', '../web/mobile/road/roadList');
}

function updateCheck(obj1, tr, id) {
	
	if (id != undefined) {
		$('#buttonList')
				.html(
						'	<button class="btn btn-primary btn-sm" onclick="updateRoad(\'2\')">审核通过</button>					<button class="btn btn-primary btn-sm" onclick="showContent(\'3\')">审核不通过</button>					<button class="btn btn-primary btn-sm"  data-dismiss="modal">关闭</button>')

	} else {
		$('#buttonList')
				.html(
						'	<button class="btn btn-primary btn-sm"  data-dismiss="modal">关闭</button>');
	}
	var show = $("div[name='show']");
	for (var i = 0; i < show.length; i++) {
		show[i].innerHTML = tr.children('td').eq(i).text().replace(/(.{50})/g,
				'$1<br\>');
	}
	if(obj1==""){
		obj1="/common/images/default_productBig.jpg"
	}
	$("#innerimg1").attr("src", obj1);
	$("#innerimg1").parent("a").attr("href",  obj1);
	$("#innerModel").modal('show');
	$("#roadId").val(id)

}
function shixiao(id){
	$("#roadId").val(id);
	$("#conditionStatus").val('0');
	bootbox.setLocale("zh_CN");
	bootbox.confirm("确认要失效路况信息吗？", function(result) {
		if (result) {
		var options = {
				url : '../web/mobile/road/updateRoad',
				type : 'post',
				data : {
					id : $('#roadId').val(),
					status:$("#conditionStatus").val()
				},
				dataType : 'text',
				success : function(data) {
					
					$("body").removeClass('modal-open').removeAttr('style');
					$(".modal-backdrop").remove();
//					$("#main").html(data);
//					$("#modal-table").modal("show");
					bootbox.alert("失效成功");
					commitForm();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

				}
			}

			$("#formRoad").ajaxSubmit(options);}
	})
	
}
function updateRoad(type) {
	var oldtype=$("#conditionStatus").val();
	$("#conditionStatus").val(type);
	var options = {
		url : '../web/mobile/road/updateRoad',
		type : 'post',
		data : {
			id : $('#roadId').val(),
			content: $('#contentmes').val(),
			status:type
//			conditionStatus:type
		},
		
		dataType : 'text',
		success : function(data) {
			$("body").removeClass('modal-open').removeAttr('style');
			$(".modal-backdrop").remove();
//			$("#main").html(data);
//			$("#modal-table").modal("show");
			bootbox.alert("审核成功");
			$("#conditionStatus").val(oldtype);
			commitForm();
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	}

	$("#formRoad").ajaxSubmit(options);

}
function deleteRoad(id){

	bootbox.setLocale("zh_CN");
	bootbox.confirm("确认要删除路况信息吗？", function(result) {
		if (result) {
			var deleteOptions = {
				url : '../web/mobile/road/delete',
				data : {
					id : id
				},
				type : 'post',
				dataType : 'text',
				success : function(data) {
//					$("#main").html(data);
					$("body").removeClass('modal-open').removeAttr('style');
					$(".modal-backdrop").remove();
					bootbox.alert("删除成功");
					commitForm();
				}
			}
			$("#formRoad").ajaxSubmit(deleteOptions);
		}
	})


}
