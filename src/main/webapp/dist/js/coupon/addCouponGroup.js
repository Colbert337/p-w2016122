function Map() {
	var struct = function(key, value) {
		this.key = key;
		this.value = value;
	};
	var put = function(key, value) {
		for ( var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				this.arr[i].value = value;
				return;
			}
		}
		this.arr[this.arr.length] = new struct(key, value);
	};
	var remove = function (key) {
		var v;
		for (var i = 0; i < this.arr.length; i++) {
			v = this.arr.pop();
			if (v.key === key) {
				continue;
			}
			this.arr.unshift(v);
		}
	}
	var size = function () {
		return this.arr.length;
	}
	var isEmpty = function () {
		return this.arr.length <= 0;
	}
	var get = function(key) {
		for ( var i = 0; i < this.arr.length; i++) {
			if (this.arr[i].key === key) {
				return this.arr[i].value;
			}
		}
		return null;
	};
	this.arr = new Array();
	this.get = get;
	this.put = put;
	this.size = size;
	this.isEmpty = isEmpty;
	this.remove = remove;
}
//定义全局变量
var coupon_ids='';
var coupon_nums= new Map();

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
	var num=0;
	var checknum=0;
	$("input:checkbox[name='coupon_id']").each(function(){
		num++;
	});
	$("input:checkbox[name='coupon_id']:checked").each(function(){
		checknum++;
	});

	if(num==checknum){
		$("#checkboxAll").attr("checked");
		$("input[name='coupon_id']").each(function(){
			if(coupon_ids.indexOf($(this).val())==-1){
				coupon_ids += $(this).val()+",";
			}
		});
		$("input[name='coupon_ids']").val(coupon_ids);
	} else{
		$("#checkboxAll").removeAttr("checked");
		var couponids = $("input[name='coupon_ids']").val();
		$("input[name='coupon_id']").each(function(){
			couponids= couponids.replace($(this).val()+',','');
			coupon_nums.remove($(this).val());
		});
		$("input[name='coupon_ids']").val(couponids);
	}
	$("#checkboxAll").click(function(){
		var couponNum = $("input[name='couponNum']");
		var min =  $("input[name='min']");
		var add = $("input[name='add']");
		if(this.checked){
			$("input[name='coupon_id']").each(function(){
				this.checked=true;
				if(coupon_ids.indexOf($(this).val())==-1){
					coupon_ids += $(this).val()+",";
				}
			});
			$("input[name='coupon_ids']").val(coupon_ids);
			min.removeAttr("disabled");
			add.removeAttr("disabled");
			couponNum.removeAttr("disabled");
		}else{
			var couponids = $("input[name='coupon_ids']").val();
			$("input[name='coupon_id']").each(function(){
				this.checked=false;
				couponids= couponids.replace($(this).val()+',','');
				coupon_nums.remove($(this).val());
			});
			$("input[name='coupon_ids']").val(couponids);
			min.prop("disabled","disabled");
			add.prop("disabled","disabled");
			couponNum.prop("disabled","disabled");
		}
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
				var couponkind;
				if(conpon.coupon_kind=='1') {
					couponkind = '平台优惠卷';
				}else if(conpon.coupon_kind=='2'){
					couponkind='气站优惠卷，优惠气站：'+conpon.gas_station_name;
				}
				var couponkindstr = '';
				if(couponkind.length>10){
					couponkindstr = couponkind.substr(0,10)+'...';
				}else{
					couponkindstr = couponkind;
				}
				$("#coupon").append(
					"<tr class='success'>"
					+"<td style='text-align:center'><input type='checkbox' onclick='selectCoupon()' name='coupon_id'  value='"+conpon.coupon_id+"' /></td>"
					+"<td>"+conpon.coupon_no+"</td>"
					+"<td>"+conpon.coupon_title+"</td>"
					+"<td title='"+couponkind+"'>"+couponkindstr+"</td>"
					+"<td style='text-align:center'><input name='min'  type='button' onclick='minCouponNum(this)' class='btn btn-default "+conpon.coupon_id+"' value='-' disabled='disabled'/><input id='"+conpon.coupon_id+"' name='couponNum' class='"+conpon.coupon_id+"' style='text-align: right;width: 30px;' type='text' value='0' disabled='disabled' maxlength='2' 　readOnly='true' /><input name='add' onclick='addCouponNum(this)'   type='button' class='btn btn-default "+conpon.coupon_id+"' disabled='disabled' value='+'/></td>"
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




function minCouponNum(num){
	var couponNum=$(num).parent().find('input[name=couponNum]');
	couponNum.val(parseInt(couponNum.val())-1)
	if(parseInt(couponNum.val())<0){
		couponNum.val(0);
	}
	coupon_nums.put(couponNum.attr('id'),couponNum.val());
}
function addCouponNum(num){
	var couponNum=$(num).parent().find('input[name=couponNum]');
	couponNum.val(parseInt(couponNum.val())+1);
	if(parseInt(couponNum.val())=='100'){
		couponNum.val(99);
	}
	coupon_nums.put(couponNum.attr('id'),couponNum.val());
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
				coupongroup_title: {
					validators: {
						notEmpty: {
							message: '优惠卷组名称不能为空'
						}
					}
				}
			}
	    });


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
			"aoColumns": [{ "bSortable": false },null,null,null,{ "bSortable": false }],
			"aaSorting": [[1, "asc"]],
			"oLanguage" :lang, //提示信息
		} );
}
//选择优惠卷复选框
function selectCoupon(){
	var num=0;
	var checknum=0;
	$("input:checkbox[name='coupon_id']").each(function(){
		num++;
		var couponNum = $("input[name='couponNum'][class*='"+$(this).val()+"']");
		var min =  $("input[name='min'][class*='"+$(this).val()+"']");
		var add = $("input[name='add'][class*='"+$(this).val()+"']");
		if($(this).is(':checked')){
			checknum++;
			min.removeAttr("disabled");
			add.removeAttr("disabled");
			couponNum.removeAttr("disabled");
			//文本框输入事件,任何非正整数的输入都重置为1
			$(couponNum).bind("input propertychange", function () {
				if (isNaN(parseFloat($(this).val())) || parseFloat($(this).val()) <= 0){
					$(this).val(1);
				}
				coupon_nums.put($(this).attr('id'),$(this).val());
			});
			//只能输入数字
			$(couponNum).bind("keydown", function (e) {
				var code = parseInt(e.keyCode);
				if (code >= 96 && code <= 105 || code >= 48 && code <= 57 || code == 8) {
					return true;
				} else {
					return false;
				}
				coupon_nums.put($(this).attr('id'),$(this).val());
			});
			//选择优惠卷
			if(coupon_ids.indexOf($(this).val())==-1){
				coupon_ids += $(this).val()+",";
			}
			$("input[name='coupon_ids']").val(coupon_ids);
			coupon_nums.put($(couponNum).attr('id'),$(couponNum).val());
		}else{
			couponNum.val(0);
			min.prop("disabled","disabled");
			add.prop("disabled","disabled");
			couponNum.prop("disabled","disabled");
			//取消优惠卷
			coupon_ids= $("input[name='coupon_ids']").val().replace($(this).val()+',','');
			coupon_nums.remove($(this).val());
			$("input[name='coupon_ids']").val(coupon_ids);
		}
	});
	if(num==checknum){
		$("#checkboxAll").attr("checked");
		$("input[name='coupon_id']:checked").each(function(){
			if(coupon_ids.indexOf($(this).val())==-1){
				coupon_ids += $(this).val()+",";
			}
		});
		$("input[name='coupon_ids']").val(coupon_ids);
	} else{
		$("#checkboxAll").removeAttr("checked");
		var couponids = $("input[name='coupon_ids']").val();
		$("input[name='coupon_id']").not("input:checked").each(function(){
			couponids= couponids.replace($(this).val()+',','');
			coupon_nums.remove($(this).val());
		});
		$("input[name='coupon_ids']").val(couponids);
	}
}


function save(){
	/*手动验证表单，当是普通按钮时。*/
	$('#coupongroupform').data('bootstrapValidator').validate();
	if(!$('#coupongroupform').data('bootstrapValidator').isValid()){
		return ;
	}
	var couponid = $("input[name='coupon_ids']").val().substr(0,$("input[name='coupon_ids']").val().length-1);
	if(couponid==null||couponid=='undefined'||couponid==''){
		bootbox.alert("请选择优惠卷！");
		return false;
	}
	if(coupon_nums.size==0) {
		bootbox.alert("请填写优惠卷数量！");
		return false;
	}
	var couponnums = new Array();
	var couponids=couponid.split(',');
	for(var i=0;i<couponids.length;i++){
		couponnums.push(coupon_nums.get(couponids[i]));
	}
	var numStatus="false";
	for(var j=0;j<couponnums.length;j++){
		if(null==couponnums[j] ||couponnums[j]=='0'){
			numStatus = "true";
			break;
		}
	}
	if(numStatus=="true") {
		bootbox.alert("存在未填写数量的优惠卷！");
		return false;
	}
		var options ={
			url:'../web/couponGroup/saveCouponGroup',
			type:'post',
			data:{
				couponNums:couponnums.join(",")
			},
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
		$("#coupongroupform").ajaxSubmit(options);
}

function resetform(){
	loadPage('#main', '../webpage/poms/coupon/addCouponGroup.jsp');
}

function returnpage(){
	loadPage('#main', '../web/couponGroup/couponGroupList');
}