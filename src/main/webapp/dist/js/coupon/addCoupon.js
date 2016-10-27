function changecouponkind(){
	var coupon_kind = $('input:radio[name="coupon_kind"]:checked').val();
	if(coupon_kind=='1'){
		$("select[name='sys_gas_station_id']").val("");
		$("#station_name").css("display","none");
	}else{
		$("#station_name").css("display","");
		$.ajax({
			type: "GET",
			url: "../web/coupon/gastationList",
			data: {},
			dataType: "text",
			async:false,
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data){
				var gas_stationInfo = JSON.parse(data);
				$("select[name='sys_gas_station_id']").val("");
				 $("#gas_station").append("<option value=''>--请选择--</option>");
				$.each(gas_stationInfo, function(i, grstaion){
					$("#gas_station").append("<option value='"+grstaion.sys_gas_station_id+"'>"+grstaion.gas_station_name+"</option>");
				});
			}
		});
		$("select[name='sys_gas_station_id']").trigger("change");
	}
}

$('#start_coupon_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	startDate:new Date(),
	endDate:"2037-12-31",
	format: "yyyy-mm-dd",
	dateFormat:"yyyy-mm-dd",
	pickerPosition: "bottom-left"
}).on("click",function(e){
	$("#start_coupon_time").datepicker("setEndDate", $("#end_coupon_time").val());
}).on('hide',function(e) {
	$('#couponform').data('bootstrapValidator')
		.updateStatus('start_coupon_time', 'NOT_VALIDATED',null)
		.validateField('start_coupon_time');
});

$('#end_coupon_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	startDate:new Date(),
	endDate:"2037-12-31",
	format: "yyyy-mm-dd",
	dateFormat:"yyyy-mm-dd",
	pickerPosition: "bottom-left"
}).on("click", function (e) {
	$("#end_coupon_time").datepicker("setStartDate", $("#start_coupon_time").val());
}).on('hide',function(e) {
	$('#couponform').data('bootstrapValidator')
		.updateStatus('end_coupon_time', 'NOT_VALIDATED',null)
		.validateField('end_coupon_time');
});
function changeUseCondition(){
	var type = $('input:radio[name="use_condition"]:checked').val();
	if(type=='2'){
		$("input[name='limit_money']").val("");
		$("input[name='limit_money']").attr("disabled","disabled");
		$('#couponform').data('bootstrapValidator')
			.updateStatus('limit_money', 'NOT_VALIDATED',null)
			.validateField('limit_money');
	}else{
		$("input[name='limit_money']").removeAttr("disabled");
	}
}
function changeCouponType(){
	var type = $('input:radio[name="coupon_type"]:checked').val();
	if(type=='2'){
		$("#discount").css("display","");
		$("#money").css("display","none");
	}else{
		$("#money").css("display","");
		$("#discount").css("display","none");
	}
}
//bootstrap验证控件
	    $('#couponform').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
			},
	        fields: {
				preferential_money: {
					validators: {
						notEmpty: {
							message: '优惠金额不能为空'
						}
					}
				},
				preferential_discount: {
					validators: {
						notEmpty: {
							message: '优惠折扣不能为空'
						},
						callback: {
							message: '优惠折扣必须是小于10的正数',
							callback: function (value, validator) {
								var coupon_type = $('input:radio[name="coupon_type"]:checked').val();
								if (( parseInt(value) <= 0 ||  parseInt(value)>=10)&& coupon_type == '2') {
									return false;
								}
								return true;
							}
						}
					}
				},
				limit_money: {
					validators: {
						notEmpty: {
							message: '限制金额不能为空'
						},
						callback: {
							message: '限制金额必须是正数',
							callback: function (value, validator) {
								if (parseInt(value) <= 0) {
									return false;
								}
								return true;
							}
						}
					}
				},
				sys_gas_station_id:{
					validators: {
						callback: {
							message: '优惠气站不能为空',
							callback: function (value, validator) {
								var coupon_kind = $('input:radio[name="coupon_kind"]:checked').val();
								if (value.length == 0 && coupon_kind == '2') {
									return false;
								}
								return true;
							}
						}
					}
				},
				start_coupon_time: {
					validators: {
						notEmpty: {
							message: '优惠开始时间不能为空'
						}
					}
				},
				end_coupon_time: {
					validators: {
						notEmpty: {
							message: '优惠结束时间不能为空'
						}
					}
				},
			}
	    });

	function save(){
		/*手动验证表单，当是普通按钮时。*/
		$('#couponform').data('bootstrapValidator').validate();
		if(!$('#couponform').data('bootstrapValidator').isValid()){
			return ;
		}
		var options ={   
	            url:'../web/coupon/saveCoupon',
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
	            	$("#main").html(data);
	            	$("#modal-table").modal("show");
	            },error:function(XMLHttpRequest, textStatus, errorThrown) {

	 	       }
		}
					
		$("#couponform").ajaxSubmit(options);
	}
	
	function resetform(){
		loadPage('#main', '../webpage/poms/coupon/addCoupon.jsp');
	}

	function returnpage(){
		loadPage('#main', '../web/coupon/couponList');
	}
		