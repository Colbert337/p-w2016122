oncheck();
var listOptions = {
	url : '../web/message/driverList',
	type : 'post',
	dataType : 'html',
	success : function(data) {
		
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
//	alert(driver_name.split(",").length);
	if ($(obj).prop("checked")) {
		if (driver_name.split(",").length == 21) {
			bootbox.alert("单次发送不能超过20个人");
			$(obj).prop("checked", false);
			
			return;
		} 
	}

	while (device_token.indexOf("#" + $(obj).val() + ',') != -1) {
		if (device_token.indexOf("#" + $(obj).val() + ',') != -1) {
			device_token = device_token.substring(0, device_token.indexOf("#"
					+ $(obj).val()))
					+ device_token.substring(device_token.indexOf("#"
							+ $(obj).val() + ',')
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
	while (user_id.indexOf($(obj).attr('value2') + ',') != -1) {
		if (user_id.indexOf($(obj).attr('value2') + ',') != -1) {
			user_id = user_id.substring(0, user_id.indexOf( $(obj).attr('value2')))
					+ user_id.substring(user_id.indexOf( $(obj).attr('value2') + ',')
							+ $(obj).attr('value2').length + 2, user_id.length);
		}
	}
	if ($(obj).is(':checked')) {
		device_token += "#" + $(obj).val() + ",";
		driver_name += $(obj).attr('value1') + ',';
		user_id+=$(obj).attr('value2') + ',';
	}
	$("#text").html(driver_name.replace(/(.{120})/g, '$1<br\>'));
	return true;
	// alert(device_token);
	/*
	 * $('.checkbox').each(function(i,obj){ console.log('11')
	 * 
	 * if($(obj).is(':checked')){ device_token+=$(obj).val()+","; } });
	 * alert(device_token);
	 */
}
function checkedAllRows() {

	if (!$(".checkbox").prop("checked")) {
		if (driver_name.split(",").length > 20) {
			bootbox.alert("单次发送不能超过20个人");
			return;
		} else {
//			$(".checkbox").prop("checked", !$(".checkbox").prop("checked"));
			for (var i = 0; i < $(".checkbox").length  ; i++) {
				$($(".checkbox")[i]).prop('checked',true);
 
				if (driver_name.split(",").length > 20) {
					bootbox.alert("单次发送不能超过20个人");
					return;
				} else{
 
				 checkchange($(".checkbox")[i]);
				}
			}
		} 
	}else{
		
		for (var i = 0; i < $(".checkbox").length; i++) {
			checkchange($(".checkbox")[i]);
		}
	}
 
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
		 
		for (var i = 0; i < token.length; i++) {
			eval('$( token[i] ).prop("checked", true);')

		}

	}
}

window.onload = setCurrentPage();
