	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
 
var listOptions = {
	url: sjny.basePath + '/web/mobile/suggest/suggestList',
	type: 'post',
	dataType: 'html',
	success: function(data) {
		//alert(data);
		$("#main").html(data);
		//$("#modal-table").modal("show");
		if ($("#retCode").val() != "100") {
			//$("#modal-table").modal("show");
		}
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		bootbox.alert("error");
	}
};

function clickDelRow() {
	$('#logic-btn-card-reset').on('click', function() {
		loadPage('#main', sjny.basePath + '/web/mobile/suggest/suggestList');
	});
	$('.logic-card-tbody-tr').each(function() {
		$(this).find('.logic-del').on('click', function(event) {
			event.preventDefault();
			del(this);
		})
	})
};

function init() {

	$('#j-input-daterange-top').datepicker({
		autoclose: true,
		format: 'yyyy/mm/dd',
		language: 'cn'
	});

	window.onload = setCurrentPage();
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
	clickCommitForm();
	clickDelRow();
})