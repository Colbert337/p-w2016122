var rewardTypeNum=0;
$(function(){
	$(".number").bind("input propertychange", function () {
		if (isNaN(parseFloat($(this).val())) || parseFloat($(this).val()) <= 0){
			$(this).val("");
		}
	});
	//只能输入数字
	$(".number").bind("keydown", function (e) {
		var code = parseInt(e.keyCode);
		if (code >= 96 && code <= 105 || code >= 48 && code <= 57 || code == 8) {
			return true;
		} else {
			return false;
		}
	});
	changeintegralType();
	initIntegral();
});
function initIntegral(){
var integral_reward_type = $("input[name='integral_reward_type']").val();
	if(integral_reward_type=='2'){
		var ladder_befores = $("input[name='ladder_befores']").val().split(",");
		var ladder_afters = $("input[name='ladder_afters']").val().split(",");
		var reward_integrals = $("input[name='reward_integrals']").val().split(",");
		var reward_factors = $("input[name='reward_factors']").val().split(",");
		var reward_maxs = $("input[name='reward_maxs']").val().split(",");
		var reward_types = $("input[name='reward_type']").val().split(",");
		console.log("reward_types",reward_types);
		if(ladder_befores.length>0){
			for(var i=0;i<ladder_befores.length;i++){
				rewardTypeNum++;
				var reward_type='reward_type'+rewardTypeNum;
				var redioStr1 = "<input name="+reward_type+"  type='radio' class='ace' value='1' onclick='changeRewardType(this)'>";
				var integral1 = "<input type='text' class='number' style='width:60px'  size='5' name='rewardintegral' disabled='disabled' value='"+reward_integrals[i]+"' />";
				if(reward_types[i]=='1'){
					redioStr1 = "<input name="+reward_type+"  type='radio' class='ace' value='1'  checked='checked' onclick='changeRewardType(this)'>";
					integral1  = "<input type='text' class='number' style='width:60px'  size='5' name='rewardintegral' value='"+reward_integrals[i]+"' />";
				}
				var redioStr2 ="<input name="+reward_type+" type='radio' class='ace' value='2' onclick='changeRewardType(this)'>";
				var integral2 ="<span class='lbl'>金额 * <input type='text' class='number'  style='width:60px' size='5' name='rewardfactor' onchange='checkfactor(this)' value='"+reward_factors[i]+"' disabled='disabled' />%<=<input type='text'  style='width:60px'  class='number' size='5' value='"+reward_maxs[i]+"' name='rewardmax' disabled='disabled'/>分</span>";
				if(reward_types[i]=='2'){
					redioStr2 = "<input name="+reward_type+"  type='radio' class='ace' value='2'  checked='checked' onclick='changeRewardType(this)'>";
					integral2 ="<span class='lbl'>金额 * <input type='text' class='number'  style='width:60px' size='5' name='rewardfactor' onchange='checkfactor(this)' value='"+reward_factors[i]+"'  />%<=<input type='text'  style='width:60px'  class='number' size='5' value='"+reward_maxs[i]+"' name='rewardmax' />分</span>";
				}
				$("#integralRule").append(
					"<tr>"
					+"<td><input type='text' size='5' style='width:50px' onchange='checkladdermoney(this)' name='ladder_before' value='"+ladder_befores[i]+"'/>元~<input type='text'  size='5' style='width:50px'  onchange='checkladdermoney(this)' value='"+ladder_afters[i]+"' name='ladder_after'/>元</td>"
					+"<td>"
					+redioStr1
					+"<span class='lbl'>"
					+integral1
					+"</span>"
					+redioStr2
					+integral2
					+"</td>"
					+"</tr>");
			}
		}
	}
}
function changeintegralType(){
	var type = $('select[name="integral_type"] option:selected').val();
	if(type=='qd'||type=='smrz'||type=='szmbsj'||type=='yqcg'||type=='sblk'||type=='fx'){
		$("#integralReward").show();
		$("#integralLadder").hide();
	}else{
		$("#integralReward").hide();
		$("#integralLadder").show();
	}
	if(type=='qd'||type=='smrz'||type=='szmbsj'){
		if(type=='qd'){
			$("select[name='reward_cycle']").val("day");
		}else{
			$("select[name='reward_cycle']").val("one");
		}
		$("select[name='reward_cycle']").attr("disabled","disabled");
		$("input[name='limit_number']").val("1");
		$("input[name='limit_number']").attr("readonly","readonly");
		$("input:radio[name='limit']").attr("disabled","disabled");
	}else{
		$("select[name='reward_cycle']").removeAttr("disabled");
		$("input[name='limit_number']").removeAttr("readonly");
		$("input:radio[name='limit']").removeAttr("disabled");;
	}
}
function changeRewardType(rewardType){
	var reward_factor = $(rewardType).parent().find('input[name=rewardfactor]');
	var reward_max = $(rewardType).parent().find('input[name=rewardmax]');
	var reward_integral = $(rewardType).parent().find('input[name=rewardintegral]');
	if($(rewardType).val()=='2'){
		$(reward_factor).val("");
		$(reward_factor).removeAttr("disabled");
		$(reward_max).val("");
		$(reward_max).removeAttr("disabled");
		$(reward_integral).val("");
		$(reward_integral).attr("disabled","disabled");
	}else{
		$(reward_factor).val("");
		$(reward_factor).attr("disabled","disabled");
		$(reward_max).val("");
		$(reward_max).attr("disabled","disabled");
		$(reward_integral).val("");
		$(reward_integral).removeAttr("disabled");
	}
}
function changeLimitType(){
	var type = $('input:radio[name="limit"]:checked').val();
	if(type=='2'){
		$("input[name='limit_number']").val("");
		$("input[name='limit_number']").attr("disabled","disabled");
		$('#integralRuleform').data('bootstrapValidator')
			.updateStatus('limit_number', 'NOT_VALIDATED', null)
			.validateField('limit_number');
	}else{
		$("input[name='limit_number']").val("");
		$("input[name='limit_number']").removeAttr("disabled");
	}
}
function addLadder(){
	rewardTypeNum++;
	var reward_type='reward_type'+rewardTypeNum;
	$("#integralRule").append(
		"<tr>"
		+"<td><input type='text' size='5' style='width:50px' onchange='checkladdermoney(this)' name='ladder_before'/>元~<input type='text'  size='5' style='width:50px'  onchange='checkladdermoney(this)' name='ladder_after'/>元</td>"
		+"<td>"
		+"<input name="+reward_type+"  type='radio' class='ace' value='1' checked='checked' onclick='changeRewardType(this)'>"
		+"<span class='lbl'>"
		+"<input type='text' class='number' style='width:60px'  size='5' name='rewardintegral' />"
		+"</span>"
		+"<input name="+reward_type+" type='radio' class='ace' value='2' onclick='changeRewardType(this)'>"
		+"<span class='lbl'>金额 * <input type='text' class='number'  style='width:60px' size='5' name='rewardfactor' onchange='checkfactor(this)' disabled='disabled' />%<=<input type='text'  style='width:60px'  class='number' size='5' name='rewardmax' disabled='disabled'/>分</span>"
		+"</td>"
		+"</tr>");
}
function delLadder(){
	var flag = window.confirm("您确定要清空设置的积分阶梯和积分奖励吗？");
	if(!flag){
		return false;
	} else {
		$("#integralRule").empty();
		return false;
	}
	return false;
}
function checkladdermoney(laddermoney){
	$(laddermoney).css("background-color","#FFF");
	var ladder_falg=false;
	if($(laddermoney).attr("name")=="ladder_before"){
		var ladder_after =  $(laddermoney).parent().find('input[name=ladder_after]');
		if($(ladder_after).length>0&&$(ladder_after).val()!="undefined"&&$(ladder_after).val()!=""){
			if(parseFloat($(laddermoney).val())>= parseFloat($(ladder_after).val())){
				bootbox.alert("积分阶梯中开始钱数不能大于等于结束钱数！");
				ladder_falg = true;
				if(ladder_falg){
					$(laddermoney).css("background-color","red");
				}
				return false;
			}
		}
		var prev_ladder_after =  $(laddermoney).parent().parent().prev().children().eq(0).find('input[name=ladder_after]');
		if($(prev_ladder_after).length>0&&$(prev_ladder_after).val()!="undefined"&&$(prev_ladder_after).val()!=""){
			if(parseFloat($(laddermoney).val())!= parseFloat($(prev_ladder_after).val())){
				bootbox.alert("积分阶梯中开始钱数必须等于上个阶梯中结束钱数！");
				ladder_falg = true;
				if(ladder_falg){
					$(laddermoney).css("background-color","red");
				}
				return false;
			}
		}
	}else{
		var ladder_before =  $(laddermoney).parent().find('input[name=ladder_before]');
		if($(ladder_before).length>0&&$(ladder_before).val()!="undefined"&&$(ladder_before).val()!=""){
			if(parseFloat($(laddermoney).val())<= parseFloat($(ladder_before).val())){
				bootbox.alert("积分阶梯中结束钱数不能小于等于开始钱数！");
				ladder_falg = true;
				if(ladder_falg){
					$(laddermoney).css("background-color","red");
				}
				return false;
			}
		}
		var next_ladder_before =  $(laddermoney).parent().parent().next().children().eq(0).find('input[name=ladder_before]');
		if($(next_ladder_before).length>0&&$(next_ladder_before).val()!="undefined"&&$(next_ladder_before).val()!=""){
			if(parseFloat($(laddermoney).val())!= parseFloat($(next_ladder_before).val())){
				bootbox.alert("积分阶梯中结束钱数必须等于下个阶梯中结束钱数！");
				ladder_falg = true;
				if(ladder_falg){
					$(laddermoney).css("background-color","red");
				}
				return false;
			}
		}
	}
}
//检查金额百分比
function checkfactor(factor){
	$(factor).css("background-color","#FFF");
	var factorval = $(factor).val();
	if(parseFloat(factorval)>100){
		bootbox.alert("金额百分比不能大于100！");
		$(factor).val('');
		$(factor).css("background-color","red");
		return false;
	}
}
//bootstrap验证控件
$('#integralRuleform').bootstrapValidator({
	message: 'This value is not valid',
	feedbackIcons: {
		valid: 'glyphicon glyphicon-ok',
		invalid: 'glyphicon glyphicon-remove',
		validating: 'glyphicon glyphicon-refresh'
	},
	fields: {
		integral_type: {
			validators: {
				notEmpty: {
					message: '积分类型不能为空'
				}
			}
		},
		reward_cycle: {
			validators: {
				notEmpty: {
					message: '限制周期不能为空'
				}
			}
		},
		integral_reward: {
			validators: {
				notEmpty: {
					message: '积分奖励不能为空'
				}
			}
		},
		limit_number: {
			validators: {
				notEmpty: {
					message: '限制次数不能为空'
				}
			}
		},
	}
});

function save(){
	/*手动验证表单，当是普通按钮时。*/
	$('#integralRuleform').data('bootstrapValidator').validate();
	if(!$('#integralRuleform').data('bootstrapValidator').isValid()){
		return ;
	}

	if($("select[name='reward_cycle']").attr("disabled")=="disabled"){
		$("select[name='reward_cycle']").removeAttr("disabled");
	}
	//校验积分阶梯和积分奖励
	if($("#integralLadder").is(':visible')){
		var str="";
		var rewardtypes = "";
		var rewardintegrals = "";
		var rewardfactors = "";
		var rewardmaxs = "";
		$("#integralRule").find("tr").each(function(i){
			var tdArr = $(this).children();
			var ladder_before = tdArr.eq(0).find("input[name='ladder_before']").val();//前积分阶梯
			var ladder_after = tdArr.eq(0).find("input[name='ladder_after']").val();//后积分阶梯
			var reward_type = tdArr.eq(1).find("input[type='radio'][class='ace']:checked").val();//积分奖励类型
			var rewardfactor = tdArr.eq(1).find("input[name='rewardfactor']").val();//积分奖励百分比
			var rewardmax = tdArr.eq(1).find("input[name='rewardmax']").val();//积分奖励最大值
			var rewardintegral = tdArr.eq(1).find("input[name='rewardintegral']").val();//积分奖励值
			if(typeof(ladder_before) == "undefined"|| ladder_before.length==0||typeof(ladder_after) == "undefined"|| ladder_after.length==0){
				i=i+1;
				str = "积分阶梯不能为空！第"+i+"行积分阶梯为空！";
				return false;
			}
			if(reward_type=='1'){
				if(typeof(rewardintegral) == "undefined"|| rewardintegral.length==0){
					i=i+1;
					str = "积分奖励不能为空！第"+i+"行积分奖励为空！";
					return false;
				}
				rewardfactor = '';
				rewardmax ='';
			}else{
				if(typeof(rewardfactor) == "undefined"|| rewardfactor.length==0||typeof(rewardmax) == "undefined"|| rewardmax.length==0){
					i=i+1;
					str = "积分奖励不能为空！第"+i+"行积分奖励为空！";
					return false;
				}
				rewardintegral='';
			}
			console.log("reward_type",reward_type);
			console.log("reward_factor",rewardfactor);
			console.log("reward_max",rewardmax);
			console.log("rewardintegral",rewardintegral);
			rewardtypes += reward_type+",";
			rewardfactors+=rewardfactor+",";
			rewardmaxs +=rewardmax+",";
			rewardintegrals +=rewardintegral+",";
		});
		if(str.length>0){
			bootbox.alert(str);
			return false;
		}else{
			rewardtypes = rewardtypes.substr(0,rewardtypes.length-1);
			$("input[name='reward_type']").val(rewardtypes);
			rewardfactors = rewardfactors.substr(0,rewardfactors.length-1);
			$("input[name='reward_factor']").val(rewardfactors);
			rewardmaxs = rewardmaxs.substr(0,rewardmaxs.length-1);
			$("input[name='reward_max']").val(rewardmaxs);
			rewardintegrals = rewardintegrals.substr(0,rewardintegrals.length-1);
			$("input[name='reward_integral']").val(rewardintegrals);
			$("input[name='integral_reward_type']").val("2");
		}
	}else{
		$("input[name='integral_reward_type']").val("1");
	}
	var integral_type =  $('select[name="integral_type"] option:selected').val();
	var integral_label =  $('select[name="integral_type"] option:selected').text();
	var options ={
		url:'../web/integralRule/saveIntegralRule',
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
	$("#integralRuleform").ajaxSubmit(options);

}

function resetform(){
	loadPage('#main', '../webpage/poms/integral/modifyIntegralRule.jsp');
}

function returnpage(){
	loadPage('#main', '../web/integralRule/integralRuleList');
}