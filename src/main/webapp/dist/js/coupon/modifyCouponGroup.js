$('#start_moneyrated_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	format: "yyyy/mm/dd",
	pickerPosition: "bottom-left"
}).on("click",function(e){
	$("#start_moneyrated_time").datepicker("setEndDate", $("#end_moneyrated_time").val());
}).on('hide',function(e) {
	$('#coupongroupform').data('bootstrapValidator')
		.updateStatus('start_moneyrated_time', 'NOT_VALIDATED',null)
		.validateField('start_moneyrated_time');
});

$('#end_moneyrated_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	format: "yyyy/mm/dd",
	pickerPosition: "bottom-left"
}).on("click", function (e) {
	$("#end_moneyrated_time").datepicker("setStartDate", $("#start_moneyrated_time").val());
}).on('hide',function(e) {
	$('#coupongroupform').data('bootstrapValidator')
		.updateStatus('end_moneyrated_time', 'NOT_VALIDATED',null)
		.validateField('end_moneyrated_time');
});
$('#start_timesrated_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	format: "yyyy/mm/dd",
	pickerPosition: "bottom-left"
}).on("click",function(e){
	$("#start_timesrated_time").datepicker("setEndDate", $("#end_timesrated_time").val());
}).on('hide',function(e) {
	$('#coupongroupform').data('bootstrapValidator')
		.updateStatus('start_timesrated_time', 'NOT_VALIDATED',null)
		.validateField('start_timesrated_time');
});

$('#end_timesrated_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	format: "yyyy/mm/dd",
	pickerPosition: "bottom-left"
}).on("click", function (e) {
	$("#end_timesrated_time").datepicker("setStartDate", $("#start_timesrated_time").val());
}).on('hide',function(e) {
	$('#coupongroupform').data('bootstrapValidator')
		.updateStatus('end_timesrated_time', 'NOT_VALIDATED',null)
		.validateField('end_timesrated_time');
});

//bootstrap验证控件
$('#coupongroupform').bootstrapValidator({
	message: 'This value is not valid',
	feedbackIcons: {
		valid: 'glyphicon glyphicon-ok',
		invalid: 'glyphicon glyphicon-remove',
		validating: 'glyphicon glyphicon-refresh'
	},
	fields: {
		couponGroup_title: {
			validators: {
				notEmpty: {
					message: '优惠卷组名称不能为空'
				}
			}
		},
		start_rated_time: {
			validators: {
				notEmpty: {
					message: '额定开始时间不能为空'
				}
			}
		},
		end_rated_time: {
			validators: {
				notEmpty: {
					message: '额定结束时间不能为空'
				}
			}
		},
	}
});

function save(){
	/*手动验证表单，当是普通按钮时。*/
	$('#coupongroupform').data('bootstrapValidator').validate();
	if(!$('#coupongroupform').data('bootstrapValidator').isValid()){
		return ;
	}

	var options ={
		url:'../web/couponGroup/saveCouponGroup',
		type:'post',
		dataType:'text',
		success:function(data){
			$("#main").html(data);
			$("#modal-table").modal("show");
		},error:function(XMLHttpRequest, textStatus, errorThrown) {

		}
	}

	$("#coupongroupform").ajaxSubmit(options);
}

function resetform(){
	loadPage('#main', '../webpage/poms/coupon/modifyCouponGroup.jsp');
}

function returnpage(){
	loadPage('#main', '../web/couponGroup/couponGroupList');
}
		