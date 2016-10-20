$(function () {
	//多选下拉框
	// $.ajax({
	// 	type: "GET",
	// 	url: "../web/couponGroup/couponList",
	// 	data: {},
	// 	dataType: "text",
	// 	async:false,
	// 	contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	// 	success: function(data){
	// 		var conponList = JSON.parse(data);
	// 		$("select[name='coupon_ids']").empty();
	// 		$.each(conponList, function(i, conpon){
	// 			$("#coupon_ids").append("<option title='"+conpon.coupon_title+"' value='"+conpon.coupon_id+"'>"+conpon.coupon_no+"</option>");
	// 		});
	// 	}
	// });
	// $('#coupon_ids').multiselect({
	// 	placeholder: "请选择优惠卷",
	// 	filterPlaceholder:'搜索',
	// 	selectAllText:'全选/取消全选',
	// 	nonSelectedText:'请选择优惠卷',
	// 	nSelectedText:'项被选中',
	// 	maxHeight:300,
	// 	numberDisplayed:4,
	// 	includeSelectAllOption: true,
	// 	enableFiltering: true,
	// 	selectAllJustVisible: true,
	// 	optionClass: function(element) {
	// 		var value = $(element).parent().find($(element)).index();
	// 			if (value%2 == 0) {
	// 				return 'even';
	// 			}
	// 			else {
	// 				return 'odd';
	// 			}
	// 		}
	// });
	// //设置选中值后，需要刷新select控件
	// $("#coupon_ids").multiselect('refresh');

	$("#checkboxAll").click(function(){
		if(this.checked){
			$("input[name='coupon_id']").each(function(){this.checked=true;});
		}else{
			$("input[name='coupon_id']").each(function(){this.checked=false;});
		}
	});

		//限制键盘只能按数字键、小键盘数字键、退格键
		$("input[name='couponNum']").keydown(function (e) {
			var code = parseInt(e.keyCode);
			if (code >= 96 && code <= 105 || code >= 48 && code <= 57 || code == 8) {
				return true;
			} else {
				return false;
			}
		});
		//文本框输入事件,任何非正整数的输入都重置为1
		$("input[name='couponNum']").bind("input propertychange", function () {
			if (isNaN(parseFloat($(this).val())) || parseFloat($(this).val()) <= 0) $(this).val(1);
		});
	var multipartOptions ={
		type: "GET",
		url: "../web/couponGroup/couponList",
		data: {},
		dataType: "text",
		async:false,
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		beforeSend: function () {
			$('body').addClass('modal-open').css('padding-right','17px')
			$('body').append('<div class="loading-warp"><div class="loading"><i class="ace-icon fa fa-spinner fa-spin"></i></div><div class="modal-backdrop fade in"></div></div>')
		},
		success:function(data){
			var conponList = JSON.parse(data);
			$.each(conponList, function(i, conpon){
				$("#coupon").append(
					"<tr class='success'>"
					+"<td style='text-align:center'><input type='checkbox' onclick='selectCoupon()' name='coupon_id' value='"+conpon.coupon_id+"' /></td>"
					+"<td>"+conpon.coupon_no+"</td>"
					+"<td>"+conpon.coupon_title+"</td>"
					+"<td style='text-align:center'><input id='min'  type='button' onclick='minCouponNum(this)' class='btn btn-default' value='-'/><input id='couponNum' name='couponNum' style='text-align: right;width: 30px;' class='form-control' placeholder='2' type='text' value='0' 　readOnly='true' /><input id='add' onclick='addCouponNum(this)'   type='button' class='btn btn-default' value='+'/></td>"
					+"</tr>"
				);
			});
			initTable();
		}, complete: function () {
			$("body").removeClass('modal-open').removeAttr('style');
			$(".loading-warp").remove();
		},error:function(XMLHttpRequest, textStatus, errorThrown) {
			bootbox.alert("操作失败！");
		}
	}
	$("#coupongroupform").ajaxSubmit(multipartOptions);
});

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


function minCouponNum(num){
	var couponNum=$(num).parent().find('input[name=couponNum]');
	couponNum.val(parseInt(couponNum.val())-1)
	if(parseInt(couponNum.val())<0){
		couponNum.val(0);
	}
}
function addCouponNum(num){
	var couponNum=$(num).parent().find('input[name=couponNum]');
	couponNum.val(parseInt(couponNum.val())+1);
}

//更改优惠卷发送类型
function changeissuedtype(){
	var checkedids;
	$("input[name='issued_type']:checked").each(function () {
		checkedids+=$(this).val()+",";
	});
	if(typeof(checkedids) == "undefined"||!checkedids){
		$("#ratedtime").hide();
		$("#ratedmoney").hide();
		$("#money").hide();
		$("#times").hide();
	}else{
		//选择非固定优惠卷
		if(checkedids.indexOf("5")>=0&&checkedids.indexOf("6")<0){
			$("#ratedtime").show();
			$("#times").show();
			$("#ratedmoney").hide();
			$("#money").hide();
		}else if(checkedids.indexOf("6")>=0&&checkedids.indexOf("5")<0){
			$("#ratedmoney").show();
			$("#money").show();
			$("#ratedtime").hide();
			$("#times").hide();
		} else if(checkedids.indexOf("5")>=0&&checkedids.indexOf("6")>=0){
			$("#ratedtime").show();
			$("#ratedmoney").show();
			$("#money").show();
			$("#times").show();
		}else{
			$("#ratedtime").hide();
			$("#ratedmoney").hide();
			$("#money").hide();
			$("#times").hide();
		}
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
			"bProcessing" : true,
			"bLengthChange":true,
			"bFilter" : true,
			"bDeferRender": true,
			"bDestroy":true,
			"bAutoWidth": false,
			"aoColumns": [{ "bSortable": false },null,null,{ "bSortable": false }],
			"aaSorting": [[1, "asc"]],
			"oLanguage" :lang, //提示信息
		} );
}