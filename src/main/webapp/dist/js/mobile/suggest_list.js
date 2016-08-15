
var listOptions = {
	url: sjny.basePath + '/web/mobile/suggest/suggestList',
	type: 'post',
	data:{"text":"'"+$("#text").val()+"'"},
	dataType: 'html',
	success: function(data) {
		//alert(data);
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

function clickCommitForm() {
	$('#logic-btn-card-search').on('click', function() {
		commitForm();
	});
	$('#dynamic-table thead th:not(:first,:last)').on('click', function() {
		commitForm();
	});
};


$(document).ready(function() {
	clickCommitForm();
	
})