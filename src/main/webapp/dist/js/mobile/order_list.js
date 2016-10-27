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
	url : '../web/order/list/page',
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
var type=null;
var tradeNo=null;
var cash=null
var orderId=null;
function showBreak(tradeNo1,type1,cash1,orderId1){
	
	closeDialog('content');
	type=type1;
	cash=cash1;
	tradeNo=tradeNo1;
	orderId=orderId1;
	$("#content").modal('show');
	$("#money").val("");
	$("#msgcontent").val();
	
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
			url : '../web/order/list/page',
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
function init() {
	loadPage('#main', '../web/order/list/page');
}

function clearNoNum(obj)
{
//先把非数字的都替换掉，除了数字和.
obj.value = obj.value.replace(/[^\d.]/g,"");
//必须保证第一个为数字而不是.
obj.value = obj.value.replace(/^\./g,"");
//保证只有出现一个.而没有多个.
obj.value = obj.value.replace(/\.{2,}/g,".");
//保证.只出现一次，而不能出现两次以上
obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

/*sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
sParaTemp.put("partner", AlipayConfig.partner);
sParaTemp.put("_input_charset", AlipayConfig.input_charset);
sParaTemp.put("notify_url", notify_url);
sParaTemp.put("batch_no", batch_no);
sParaTemp.put("refund_date", refund_date);
sParaTemp.put("batch_num", batch_num);
sParaTemp.put("detail_data", detail_data);*/
function subbreak() {
	
	if ($('#money').val()=="") {
		 bootbox.alert("退款金额不能为空");
		 return;
	}
		
	 if ($('#money').val()*1>cash*1) {
		 bootbox.alert("退款金额不能大于交易金额");
		 return;
	}
	var options = {
		url : '../web/order/saveBreak',
		type : 'post',
		data : {
			money : $('#money').val(),
			msg: $('#msgcontent').val(),
			type:type,
			cash:cash,
			orderId:orderId,
			tradeNo:tradeNo
//			conditionStatus:type
		},
		
		dataType : 'text',
		success : function(data) {
			$("body").removeClass('modal-open').removeAttr('style');
			$(".modal-backdrop").remove();
//			$("#main").html(data);
//			$("#modal-table").modal("show");
			bootbox.alert(data);
//			$("#conditionStatus").val(oldtype);
			commitForm();
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	}
	$("#formRoad").ajaxSubmit(options);
}