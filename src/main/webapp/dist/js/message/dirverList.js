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
//	console.log('check');
	if (driver_name.split(",") > 20) {
		bootbox.alert("单次发送不能超过20个人");
		return;
	}

	while (device_token.indexOf("#"+$(obj).val() + ',') != -1) {
		if (device_token.indexOf("#"+$(obj).val() + ',') != -1) {
			device_token = device_token.substring(0, device_token
					.indexOf("#"+$(obj).val()))
					+ device_token.substring(device_token.indexOf("#"+$(obj).val()
							+ ',')
							+ $(obj).val().length + 2, device_token.length);
		}
	}
	while (driver_name.indexOf($(obj).attr('value1') + ',') != -1) {
		if (driver_name.indexOf($(obj).attr('value1') + ',') != -1) {
			driver_name = driver_name.substring(0, driver_name.indexOf($(obj)
					.attr('value1')))
					+ driver_name.substring(driver_name.indexOf($(obj).attr(
							'value1')
							+ ',')
							+ $(obj).attr('value1').length + 1,
							driver_name.length);
		}
	}
	if ($(obj).is(':checked')) {
		device_token +="#"+ $(obj).val() + ",";
		driver_name += $(obj).attr('value1') + ',';
	}
	$("#text").html(driver_name.replace(/(.{120})/g, '$1<br\>'));
	// alert(device_token);
	/*
	 * $('.checkbox').each(function(i,obj){ console.log('11')
	 * 
	 * if($(obj).is(':checked')){ device_token+=$(obj).val()+","; } });
	 * alert(device_token);
	 */
}
function checkedAllRows() {

	$(".checkbox").prop("checked", !$(".checkbox").prop("checked"));
	for (var i = 0; i < $(".checkbox").length; i++) {
		checkchange($(".checkbox")[i]);
	}
//	console.log($(".checkbox"));
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
		$("#allcheckbox").prop("checked", true);
		var token = device_token.split(',')
//		console.log(token);
		for (var i = 0; i < token.length; i++) {
			eval('$( token[i] ).prop("checked", true);')
			
		}

	}
}

window.onload = setCurrentPage();
