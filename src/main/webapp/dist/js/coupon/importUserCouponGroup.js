/**
 * 判断文件格式
 */
function fileFormat(){
	var fileName= $("#file_import").val();
	var suffix = fileName.substr(fileName.indexOf("."));
	if(suffix != '.xls'){
		bootbox.alert("导入文件格式错误，必须是excle格式，后缀名是xls！");
		$("#file_import").val("");
		return false;
	}
}
/**
 * 文件上传验证
 */
$('#importForm').bootstrapValidator({
	message: 'This value is not valid',
	feedbackIcons: {
		valid: 'glyphicon glyphicon-ok',
		invalid: 'glyphicon glyphicon-remove',
		validating: 'glyphicon glyphicon-refresh'
	},
	fields: {
		fileImport: {
			validators: {
				notEmpty: {
					message: '请选择要导入的文件'
				}
			}
		}
	}
});

//根据电话号码导入享受批量优惠卷的名单
function importUserCouponGroup(){
	if ($("#file_import").val()=="") {
		bootbox.alert("请选择文件");
		return false;
	}
	$("#driver").empty();
	var multipartOptions ={
		url:'../web/couponGroup/file',
		type:'post',
		dataType:'json',
		enctype:"multipart/form-data",
		beforeSend: function () {
			$("#importAction").hide();
			$('body').addClass('modal-open').css('padding-right','17px')
			$('body').append('<div class="loading-warp"><div class="loading"><i class="ace-icon fa fa-spinner fa-spin"></i></div><div class="modal-backdrop fade in"></div></div>')
		},
		success:function(data){
				var num=0;
				$("#import").hide();
				$("#importAction").hide();
				if(data.message.length>0){
					bootbox.alert(data.message);
				}
				$("#info").text(data.info);
				var sysDriverIds='';
				$.each(data.driverList, function(i, driver){
					num++;
					var fuelType;
					if(driver.fuelType!=null){
						if(driver.fuelType=='1'){
							fuelType='LNG';
						}else if(driver.fuelType=='2'){
							fuelType='LNG';
						}else{
							fuelType='柴油';
						}
					}else{
						fuelType='';
					}
					sysDriverIds+= driver.sysDriverId+",";
					var userName=driver.userName;
					if(userName==null){
						userName='';
					}
					var fullName=driver.fullName;
					if(fullName==null){
						fullName='';
					}
					var plateNumber=driver.plateNumber;
					if(plateNumber==null){
						plateNumber='';
					}
					var identityCard=driver.identityCard;
					if(identityCard==null){
						identityCard='';
					}
					var stationId=driver.stationId;
					if(stationId==null){
						stationId='';
					}
					var regisSource=driver.regisSource;
					if(regisSource==null){
						regisSource='';
					}
					$("#driver").append(
					"<tr class='success'>"
					+"<td>"+userName+"</td>"
					+"<td>"+fullName+"</td>"
					+"<td>"+plateNumber+"</td>"
					+"<td>"+identityCard+"</td>"
					+"<td style='text-align:center'>"+fuelType+"</td>"
					+"<td>"+stationId+"</td>"
					+"<td>"+regisSource+"</td>"
					+"</tr>"
					);
				});
			$("#userCouponList").show();
			if(num==0){
				$("#importuserConpon").hide();
			}
			initTable();
			$("input[name='sysDriverIds']").val(sysDriverIds.substr(0,sysDriverIds.length-1));
		}, complete: function () {
			$("body").removeClass('modal-open').removeAttr('style');
			$(".loading-warp").remove();
		},error:function(XMLHttpRequest, textStatus, errorThrown) {
			bootbox.alert("操作失败！");
		}
	}
	$("#importForm").ajaxSubmit(multipartOptions);
	$("#innerModel").modal('hide').removeClass('in');
	$("body").removeClass('modal-open').removeAttr('style');
	$(".modal-backdrop").remove();
}

function saveUserCouponGroup(){
	var options ={
		url:'../web/couponGroup/importCouponGroup',
		type:'post',
		dataType:'text',
		beforeSend: function () {
			$('body').addClass('modal-open').css('padding-right','17px')
			$('body').append('<div class="loading-warp"><div class="loading"><i class="ace-icon fa fa-spinner fa-spin"></i></div><div class="modal-backdrop fade in"></div></div>')
		},
		success:function(data){
			$("#main").html(data);
			$("#modal-table").modal("show");
		},complete: function () {
			$("body").removeClass('modal-open').removeAttr('style');
			$(".loading-warp").remove();
		},error:function(XMLHttpRequest, textStatus, errorThrown) {
			bootbox.alert("操作失败！");
		}
	}
	$("#importForm").ajaxSubmit(options);
}
function cancel(){
	$("#import").show();
	$("#importAction").show();
	$("#info").val("");
	$("#userCouponList").hide();
	$("#driver").empty();
}
$(function(){
	//美化上传框
	$('#file_import').ace_file_input({
		no_file:'请导入符合模板格式的excel文件',
		btn_choose:'选择文件',
		btn_change:'重新选择',
		droppable:false,
		onchange:null,
		thumbnail:false,
		width:"500px",
		allowedFileExtensions:['xls'],//接收的文件后缀
	});
})

function returnpage(){
	loadPage('#main', '../web/couponGroup/couponGroupList');
}

//动态初始化详细列表
function initTable() {
	//提示信息
	var lang = {
		"sProcessing": "处理中...",
		"sLengthMenu": "显示 _MENU_ 项结果",
		"sZeroRecords": "没有匹配结果",
		"sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
		"sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
		"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
		"sInfoPostFix": "",
		"sSearch": "搜索:",
		"sUrl": "",
		"sEmptyTable": "表中数据为空",
		"sLoadingRecords": "载入中...",
		"sInfoThousands": ",",
		"oPaginate": {
			"sFirst": "首页",
			"sPrevious": "上页",
			"sNext": "下页",
			"sLast": "末页"
		},
		"oAria": {
			"sSortAscending": ": 以升序排列此列",
			"sSortDescending": ": 以降序排列此列"
		}
	};

	//initiate dataTables plugin
	$('#dynamic-table').DataTable(
		{
		bDestroy:true,
		bAutoWidth: false,
		"aoColumns": [null,null, null,null, null, null, null],
		"aaSorting": [],
		"oLanguage" :lang, //提示信息
		select: {
			style: 'multi'
		}
	} );
}