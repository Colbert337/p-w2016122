oncheck();
var listOptions = {
	url : '../web/message/driverList',
	type : 'post',
	dataType : 'html',
	success : function(data) {
		// console.log("这里分页之后");
		$("#content").html(data);
		oncheck();
		$("#editModel").modal('show');
		if ($("#retCode").val() != "100") {

		}
		$('[data-rel="tooltip"]').tooltip();
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {

	}
}
function scher() {
	$("#formgastation").ajaxSubmit(listOptions);
}

function checkchange(obj) {
	console.log('check');
	if ($(obj).is(':checked')) {
		device_token += $(obj).val() + ","
	} else {
		if (device_token.indexOf($(obj).val() + ',') != -1) {
			device_token = device_token.substring(0, device_token
					.indexOf($(obj).val()))
					+ device_token.substring(device_token.indexOf($(obj).val()
							+ ',')
							+ $(obj).val().length + 1, device_token.length);
		}

	}
	// alert(device_token);
	/*
	 * $('.checkbox').each(function(i,obj){ console.log('11')
	 * 
	 * if($(obj).is(':checked')){ device_token+=$(obj).val()+","; } });
	 * alert(device_token);
	 */
}
function commitForm(obj) {
	// 设置当前页的值
	if (typeof obj == "undefined") {
		$("#pageNum").val("1");
	} else {
		$("#pageNum").val($(obj).text());
	}

	$("#formgastation").ajaxSubmit(listOptions);
}
function oncheck() {
	if (device_token != '') {
		var token = device_token.split(',')
		for (var i = 0; i < token.length; i++) {
			eval('$("#' + token[i] + '").prop("checked", true);')
		}
	}
}

window.onload = setCurrentPage();
