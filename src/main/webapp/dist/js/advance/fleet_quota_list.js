/**
 * Created by Administrator on 2016/6/20.
 * Author: wdq
 */
function commitForm(obj){
    $("#listForm").ajaxSubmit(listOptions);
}
var listOptions ={
    url:'../web/tcms/fleetQuota/list/page',
    type:'post',
    dataType:'html',
    success:function(data){
        $("#main").html(data);
    }, error: function (XMLHttpRequest, textStatus, errorThrown) {
        bootbox.alert("操作失败!")//保存成功弹窗
    }
}

//显示充值弹出层add
function addChongzhi(){
    clearDiv();
    $("#chongzhiModel").modal('show');
}

//显示添加资金分配弹出层add
function addFenpei(){
    clearDiv();

    $("#fenpeiModel").modal('show').on('hide.bs.modal', function() {
        $('#fenpeiForm').bootstrapValidator('resetForm',true);
    });
}


//显示添加个人转账弹出层add
function addZhuan(){
    clearDiv();
    $("#zhuanModel").modal('show').on('hide.bs.modal', function() {
        $('#zhuanForm').bootstrapValidator('resetForm',true);
    });
}

//显示添加修改密码弹出层add
function addPassword(){
    clearDiv();
    $.ajax({
        type: "POST",
        async:false,
        url: "../web/transportion/info/tc",
        success: function(data){
            if(data != null && data.pay_code != null && data.pay_code !=""){
                $("#paswordDiv").show();
                $("#firstDiv").hide();
            }else{
                $("#firstDiv").show();
                $("#paswordDiv").hide();
            }

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert("操作失败!")//保存成功弹窗
        }
    });
    $("#passwordModel").modal('show').on('hide.bs.modal', function() {
        $('#passwordForm').bootstrapValidator('resetForm',true);
    });
}

/**
 * 切换支付密码弹层
 * @param objOpen 选中浮层
 * @param objClose 不选中浮层
 */
function changePassDiv(objOpen,objClose){
    $("#"+objOpen).show();
    $("#"+objClose).hide();
}

function savePassword(){
    var url = "";
    var fistDiv = $("#firstDiv").is(":visible");
    var updatePsDiv =  $("#updatePsDiv").is(":visible");
    var lossPsDiv =  $("#lossPsDiv").is(":visible");

    if(fistDiv){
        url = "../web/transportion/update/setPasswordMail"
    }else if(updatePsDiv){
        $('#passwordForm').data('bootstrapValidator').validate();
        if(!$('#passwordForm').data('bootstrapValidator').isValid()){
            return ;
        }

        url = "../web/transportion/update/password"
    }else if(lossPsDiv){
        url = "../web/transportion/update/setPasswordMail"
    }
    var saveOptions ={
        url:url,
        type:'post',
        dataType:'html',
        success:function(data){
            $("#main").html(data);
            bootbox.alert("支付密码修改成功");//保存成功弹窗
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert("操作失败!");
        }
    }
    $("#passwordForm").ajaxSubmit(saveOptions);

    hideModal("#passwordModel");
}
/**
 * 资金分配状态修改
 * @param obj
 * @param index
 */
function allocation(obj,index){
    if ($(obj).attr('checked')) {
        $(obj).attr("checked",false);
        $("#is_allot_"+index).val(0);
        var allotVal = $("#notIsAllot").text();
        $("#quota_"+index).val(allotVal);
        $("#quota_"+index).attr("readonly","readonly");
    }else{
        $(obj).attr("checked","checked");
        $("#is_allot_"+index).val(1);
        $("#quota_"+index).removeAttr("readonly");
        $("#quota_"+index).val(0.00);
    }

}

/**
 * 保存配置
 */
function saveFenpei(){
    $('#fenpeiForm').data('bootstrapValidator').validate();
    if(!$('#fenpeiForm').data('bootstrapValidator').isValid()){
        return ;
    }

    var dataForm = $("#fenpeiForm").serialize(); //序列化表单 获取到数据
    dataForm = decodeURIComponent(dataForm,true);
    var saveOptions ={
        url:"../web/tcms/fleetQuota/save/fenpei",
        type:'post',
        data:{data:dataForm},
        dataType:'html',
        success:function(data){
            $("#main").html(data);
            bootbox.alert("资金分配成功");//保存成功弹窗
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert("操作失败!");
        }
    }
    $("#fenpeiForm").ajaxSubmit(saveOptions);
    $("#fenpeiModel").modal('hide').removeClass('in');
    $("body").removeClass('modal-open').removeAttr('style');
    $(".modal-backdrop").remove();

}

/**
 * 添加列
 */
var zhuanIndex = 2;
function addRow(){

    var objIndex = zhuanIndex++;
    var zhuan = "";
    zhuan += "<tr id='tr_"+objIndex+"'>";
    zhuan += "<td><input type='text'  id='mobile_phone_"+objIndex+"' name='mobilePhone' class='col-sm-12' data-sj-result='invalid' onblur='queryDriverInfo("+objIndex+");'/></td>";
    zhuan += "<td><input type='text'  id='full_name_"+objIndex+"' name='fullName' class='col-sm-12' readonly='readonly' data-sj-result='invalid' onclick='queryDriverInfo("+objIndex+");'>";
    zhuan += "<input type='hidden' id='sys_driver_id_"+objIndex+"' name='sysDriverId' class='col-sm-12'/></td>";
    zhuan += "<td><input type='text'  id='amount_"+objIndex+"' name='amount' class='col-sm-12' data-sj-result='invalid' onblur='isTransfer("+objIndex+");'/></td>";
    zhuan += "<td><input type='text'  id='remark_"+objIndex+"' name='remark' class='col-sm-12' /></td><td class='fleet-quota-option'>";
    zhuan += "<a href='javascript:deleteRow("+objIndex+");'>删除该行</a>";
    zhuan += "</td></tr>";

    $("#zhuanTable").append(zhuan);
}

//验证手机号
function vailPhone(index){
    var $mobilePhone = $("#mobile_phone_"+index);
    var mobilePhoneVal = $mobilePhone.val();
    var flag = false;
    var message = "";
    //手机真实性校验
    //var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
    $("#full_name_"+index).val("");
    if(mobilePhoneVal == ''){
        message = "手机号码不能为空！";
    }else if(mobilePhoneVal.length !=11){
        message = "请输入有效的手机号码！";
    }
    /*else if(!myreg.test(mobilePhoneVal)){
        message = "请输入有效的手机号码！";
    }*/
    else{
        flag = true;
    }
    if(!flag){
        $(".fleet-quota-error").text(message);
        $mobilePhone.attr("data-sj-result","invalid");
    }else{
        $(".fleet-quota-error").empty();
        $mobilePhone.attr("data-sj-result","valid");
        $(event.target).parent().next().find('input[name="fullName"]').trigger('click');
    }
    return flag;
}

/**
 * 根据下标查找司机信息
 * @param index
 */
function queryDriverInfo(index){
    var mobilePhone = $("#mobile_phone_"+index).val();
    $.ajax({
        type: "POST",
        data:{mobilePhone:mobilePhone},
        //async:false,
        url: "../web/tcms/fleetQuota/info/driver",
        success: function(data){
            if(data != null && (data.fullName == "" || data.fullName == undefined)){
                $("#full_name_"+index).val("").attr("data-sj-result","invalid");
                $(".fleet-quota-error").text("未认证司机无法接受转账!");
                return false;
            }else if(data != null){
                $("#full_name_"+index).val(data.fullName).attr("data-sj-result","valid");
                $("#sys_driver_id_"+index).val(data.sysDriverId);
                return false;
            }else{
                $(".fleet-quota-error").text("请求失败！");
                return false;
            }

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            $(".fleet-quota-error").text("操作失败!");
            return false;
        }
    });

}
/**
 * 判断是否可以转账
 */
function isTransfer(index){
    var $amount = $("#amount_"+index);
    var amount = $("#amount_"+index).val();
    var notIsAllot = $("#notIsAllot").text();
    var mobilePhone = $("#mobile_phone_"+index).val();
    var fullName = $("#full_name_"+index).val();
    var error = $(".fleet-quota-error");
    if(mobilePhone == ""){
        error.text("请先输入手机号！");
        $amount.attr("data-sj-result","invalid");
        return false;
    }else if(fullName == ""){
        error.text("未认证司机无法接受转账！");
        $amount.attr("data-sj-result","invalid");
        return false;
    }else if(amount > notIsAllot){
        error.text("转账金额不能大于未分配额度！");
        $amount.attr("data-sj-result","invalid");
        return false;
    } else {
        $amount.attr("data-sj-result","valid");
    }
}
/**
 * 删除列
 * @param obj
 */
function deleteRow(index){
    $("#tr_"+index).remove();
}
/**
 * 提交个人转账
 */
function saveZhuan(){

    $('input[name="mobilePhone"],input[name="fullName"],input[name="amount"]').each(function(index,val){
        if($(this).attr("data-sj-result")=="invalid"){
            console.log('ssssssssssssssss')
            $(".fleet-quota-error").text('信息有误！')
            return;
        }
    });

    $('#zhuanForm').data('bootstrapValidator').validate();
    if(!$('#zhuanForm').data('bootstrapValidator').isValid()){
        return ;
    }

    var data = $("#zhuanForm").serialize(); //序列化表单 获取到数据
    data = decodeURIComponent(data,true);
    var saveOptions ={
        url: "../web/tcms/fleetQuota/save/zhuan",
        data:{data:data},
        type:'post',
        dataType:'html',
        success:function(data){
            $("#main").html(data);
            bootbox.alert("个人转账成功");//保存成功弹窗
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert("操作失败!");
        }
    }
    $("#zhuanForm").ajaxSubmit(saveOptions);

    hideModal("#zhuanModel");
    $("#zhuanTable").empty();
}

/*取消弹层方法*/
function closeDialog(divId){
    $("#"+divId+" :input").each(function () {
        $(this).val("").removeAttr("data-sj-result");
    });
    $(".fleet-quota-error").empty();
    $("#"+divId).modal('hide');
}
function clearDiv(){
    $("#editForm :input").each(function () {
        $(this).val("");
    });
    $("#avatar_b").empty();
}
function hideModal(obj){
    $(obj).modal('hide').removeClass('in');
    $("body").removeClass('modal-open').removeAttr('style');
    $(".modal-backdrop").remove();
}
/**
 * 保存车队额度信息
 */
function saveFleetQuota(){
        $('#editForm').data('bootstrapValidator').validate();
        if(!$('#editForm').data('bootstrapValidator').isValid()){
            return ;
        }

        var saveOptions ={
            url:'../web/tcms/FleetQuota/save',
            type:'post',
            dataType:'html',
            success:function(data){
                $("#main").html(data);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                bootbox.alert("操作失败!");
            }
        }
        $("#editForm").ajaxSubmit(saveOptions);

        hideModal("#editModel");

}

//重置
function init(){
    loadPage('#main', '../web/tcms/fleetQuota/list/page');
}
/**
 * 删除车队额度
 */
function leaveDriver(){
    if(confirm("确定要离职该司机吗？")){
        var deleteOptions ={
            url:'../web/driver/delete',
            data:{},
            type:'post',
            dataType:'text',
            success:function(data){
                $("#main").html(data);
            }
        }
        $("#listForm").ajaxSubmit(deleteOptions);
    }

}

//bootstrap验证控件
$('#passwordForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        oldPassword: {
            validators: {
                notEmpty: {
                    message: '原支付密码不能为空'
                },
                regexp: {
                    regexp: '^[0-9a-zA-Z]+$',
                    message: '密码只能包含数字和字母'
                },
                remote: {
                    url: '../web/transportion/info/isExist',
                    type: "post",
                    async: false,
                    data: function(validator, $field, value) {
                        return{
                            password:$("#old_password").val()
                        };
                    },
                    message: '支付密码错误'
                }
            }
        },
        pay_code: {
            validators: {
                notEmpty: {
                    message: '支付密码不能为空'
                },
                regexp: {
                    regexp: '^[0-9a-zA-Z]+$',
                    message: '密码只能包含数字和字母'
                }
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '确认密码不能为空'
                },
                regexp: {
                    regexp: '^[0-9a-zA-Z]+$',
                    message: '密码只能包含数字和字母'
                },
                callback: {
                    message: '支付密码不一致',
                    callback: function (value, validator, $field) {
                        if($("#new_password").val() != value){
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
    }
});

//个人转账bug
$('#zhuanForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        pay_code: {
            validators: {
                notEmpty: {
                    message: '请输入支付密码！'
                },
                regexp: {
                    regexp: '^[0-9]+$',
                    message: '支付密码只能包含数字'
                },
                remote: {
                    url: '../web/transportion/info/password',
                    type: "post",
                    async: false,
                    data: function(validator, $field, value) {
                        return{
                            pay_code:$("#pay_code").val()
                        };
                    },
                    message: '支付密码错误'
                }
            }
        }
    }
});

/*资金分配*/
//bootstrap验证控件
$('#fenpeiForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        pay_code: {
            validators: {
                notEmpty: {
                    message: '请输入支付密码!'
                },
                regexp: {
                    regexp: '^[0-9]+$',
                    message: '支付密码只能包含数字'
                },
                remote: {
                    url: '../web/transportion/info/password',
                    type: "post",
                    data: function(validator, $field, value) {
                        return{
                            pay_code:$("#pay_code").val()
                        };
                    },
                    message: '支付密码错误'
                }
            }
        }
    }
});


