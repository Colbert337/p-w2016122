$('#start_rated_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	format: "yyyy/mm/dd",
	pickerPosition: "bottom-left"
}).on("click",function(e){
	$("#start_rated_time").datepicker("setEndDate", $("#end_rated_time").val());
}).on('hide',function(e) {
	$('#coupongroupform').data('bootstrapValidator')
		.updateStatus('start_rated_time', 'NOT_VALIDATED',null)
		.validateField('start_rated_time');
});

$('#end_rated_time').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	format: "yyyy/mm/dd",
	pickerPosition: "bottom-left"
}).on("click", function (e) {
	$("#end_rated_time").datepicker("setStartDate", $("#start_rated_time").val());
}).on('hide',function(e) {
	$('#coupongroupform').data('bootstrapValidator')
		.updateStatus('end_rated_time', 'NOT_VALIDATED',null)
		.validateField('end_rated_time');
});

$(function () {
	$.ajax({
		type: "GET",
		url: "../web/couponGroup/couponList",
		data: {},
		dataType: "text",
		async:false,
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data){
			var conponList = JSON.parse(data);
			$("select[name='coupon_ids']").empty();
			$.each(conponList, function(i, conpon){
				$("#coupon_ids").append("<option title='"+conpon.coupon_title+"' value='"+conpon.coupon_id+"'>"+conpon.coupon_no+"</option>");
			});
		}
	});
	$('#coupon_ids').multiselect({
		placeholder: "请选择优惠卷",
		filterPlaceholder:'搜索',
		selectAllText:'全选/取消全选',
		nonSelectedText:'请选择优惠卷',
		nSelectedText:'项被选中',
		maxHeight:300,
		numberDisplayed:4,
		includeSelectAllOption: true,
		enableFiltering: true,
		selectAllJustVisible: true,
		optionClass: function(element) {
			var value = $(element).parent().find($(element)).index();
			if(value>1){
				if (value%2 == 0) {
					return 'even';
				}
				else {
					return 'odd';
				}
			}
		}
	});
	//设置选中值后，需要刷新select控件
	$("#coupon_ids").multiselect('refresh');
});


//更改优惠卷发送类型
function changeissuedtype(){
	var checkedids;
	$("input[name='issued_type']:checked").each(function () {
		checkedids+=$(this).val()+",";
	});
	//选择非固定优惠卷
	if(checkedids.indexOf("5")>=0){
		$("#ratedtime").show();
		 $("#money").show();
	}else{
		if(checkedids.indexOf("6")<0){
			$("#ratedtime").hide();
		}
		$("#money").hide();
	}
	if(checkedids.indexOf("6")>=0){
		$("#ratedtime").show();
		$("#times").show();
	}else{
		if(checkedids.indexOf("5")<0){
			$("#ratedtime").hide();
		}
		$("#times").hide();
	}
}
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
		loadPage('#main', '../webpage/poms/coupon/addCouponGroup.jsp');
	}

	function returnpage(){
		loadPage('#main', '../web/couponGroup/couponGroupList');
	}
		