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
	if(driver_name.split(",")>20){		
		bootbox.alert("单次发送不能超过20个人");
		return;
	}
	if ($(obj).is(':checked')) {
		device_token += $(obj).val() + ",";
		driver_name+=$(obj).attr('value1')+',';
	} else {
		if (device_token.indexOf($(obj).val() + ',') != -1) {
			device_token = device_token.substring(0, device_token
					.indexOf($(obj).val()))
					+ device_token.substring(device_token.indexOf($(obj).val()
							+ ',')
							+ $(obj).val().length + 1, device_token.length);
		}
		if (driver_name.indexOf($(obj).val() + ',') != -1) {
			driver_name = driver_name.substring(0, driver_name
					.indexOf($(obj).val()))
					+ driver_name.substring(driver_name.indexOf($(obj).val()
							+ ',')
							+ $(obj).val().length + 1, driver_name.length);
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
