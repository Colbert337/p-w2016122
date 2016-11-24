var listOptions = {
	url: sjny.basePath + '/web/card/cardList',
	type: 'post',
	dataType: 'html',
	success: function(data) {
		//alert(data)
		$("#main").html(data);
		if ($("#retCode").val() != "100") {
			//$("#modal-table").modal("show");
		}
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		bootbox.alert("error");
	}
};

function commitForm(obj) {
	//设置当前页的值
	if (typeof obj == "undefined") {
		$("#pageNum").val("1");
	} else {
		$("#pageNum").val($(obj).text());
	}

	$("#formcard").ajaxSubmit(listOptions);
};

function del(obj) {
	var cardid = $(obj).parents('tr').find("td:first").find("input").val();
	bootbox.setLocale("zh_CN");
	bootbox.confirm("是否删除卡号为[" + cardid + "]的用户卡?", function(result) {
		if(result){

 	var deloptions = {
		url: sjny.basePath + '/web/card/deleteCard?cardid=' + cardid,
		type: 'post',
		dataType: 'text',
		success: function(data) {
			$("#main").html(data);
			if ($("#retCode").val() != "100") {
				$("#modal-table").modal("show");
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {

		}
	}

	$("#formcard").ajaxSubmit(deloptions);
		}
	}
	 );
};

function init() {

	$('#j-input-daterange-top').datepicker({
		autoclose: true,
		format: 'yyyy/mm/dd',
		language: 'cn'
	});

	window.onload = setCurrentPage();
};

function clickDelRow() {
	$('#logic-btn-card-reset').on('click', function() {
		loadPage('#main', sjny.basePath + '/web/card/cardList');
	});
	$('.logic-card-tbody-tr').each(function() {
		$(this).find('.logic-del').on('click', function(event) {
			event.preventDefault();
			del(this);
		})
	})
};

function clickCommitForm() {
	$('#logic-btn-card-search').on('click', function() {
		commitForm();
	});
	$('#dynamic-table thead th:not(:first,:last)').on('click', function() {
		commitForm();
	});
};

$(document).ready(function() {
	init();
	clickDelRow();
	clickCommitForm();
});