/**
 * Created by Administrator on 2016/6/20.
 * Author: wdq
 */
/*/!*分页相关方法 start*!/
window.onload = setCurrentPage();
function commitForm(obj){
    //设置当前页的值
    if(typeof obj == "undefined") {
        $("#pageNum").val("1");
    }else{
        $("#pageNum").val($(obj).text());
    }

    $("#listForm").ajaxSubmit(listOptions);
}
var listOptions ={
    url:'../web/tcms/FleetQuota/list/page',
    type:'post',
    dataType:'html',
    success:function(data){
        $("#main").html(data);
    }
}
/!*分页相关方法 end*!/*/

//显示充值弹出层add
function addChongzhi(){
    clearDiv();
    $("#chongzhiModel").modal('show');
}

//显示添加资金分配弹出层add
function addFenpei(){
    clearDiv();

    $("#fenpeiModel").modal('show');
}


//显示添加个人转账弹出层add
function addZhuan(){
    clearDiv();
    $("#zhuanModel").modal('show');
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

        }
    });
    $("#passwordModel").modal('show');
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
        url = "../web/transportion/update/setPasswordMail"
    }else if(lossPsDiv){
        url = "../web/transportion/update/password"
    }
    alert("url:"+url);
    var saveOptions ={
        url:url,
        type:'post',
        dataType:'html',
        success:function(data){

            $("#main").html(data);
        }
    }
    $("#passwordForm").ajaxSubmit(saveOptions);
    $("#passwordModel").modal('hide')
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
        $("#quota_"+index).attr("readonly","readonly");
    }else{
        $(obj).attr("checked","checked");
        $("#is_allot_"+index).val(1);
        $("#quota_"+index).removeAttr("readonly");
    }

}

/**
 * 保存配置
 */
function saveFenpei(){
    var data = $("#fenpeiForm").serialize(); //序列化表单 获取到数据
    data = decodeURIComponent(data,true);
    $.ajax({
        type: "POST",
        data:{data:data},
        async:false,
        url: "../web/tcms/fleetQuota/save/fenpei",
        success: function(data){
            sucDialog("批量保存成功!")//保存成功弹窗
            if(data.success()){
                //暂时不需要跳转
                window.location = '../web/tcms/fleetQuota/list/page' ;
            }else{
                alert("请求失败！");
            }

        }
    });

}

/**
 * 添加列
 */
var zhuanIndex = 2;
function addRow(){

    var objIndex = zhuanIndex++;
    var zhuan = [];
    zhuan.push("<tr id='tr_"+objIndex+"'>");
    zhuan.push("<td><input type='text'  id='mobile_phone_"+objIndex+"' name='mobilePhone' class='col-sm-12' onblur='queryDriverInfo("+objIndex+");'/></td>");
    zhuan.push("<td><input type='text'  id='full_name_"+objIndex+"' name='fullName' class='col-sm-12' readonly='readonly'>");
    zhuan.push("<input type='hidden' id='sys_driver_id_"+objIndex+"' name='sysDriverId' class='col-sm-12'/></td>");
    zhuan.push("<td><input type='text'  id='amount_"+objIndex+"' name='amount' class='col-sm-12' /></td>");
    zhuan.push("<td><input type='text'  id='remark_"+objIndex+"' name='remark' class='col-sm-12' /></td><td>");
    zhuan.push("<a href='javascript:deleteRow("+objIndex+");'>删除</a>");
    zhuan.push("</td></tr>");

    $("#zhuanTable").append(zhuan.join());
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
        async:false,
        url: "../web/tcms/fleetQuota/info/driver",
        success: function(data){
            sucDialog("批量保存成功!")//保存成功弹窗
            if(data != null){
                $("#full_name_"+index).val(data.fullName);
                $("#sys_driver_id_"+index).val(data.sysDriverId);

            }else{
                alert("请求失败！");
            }

        }
    });

}

/**
 * 删除列
 * @param obj
 */
function deleteRow(index){
    console.log( $("#tr_"+index));
    $("#tr_"+index).remove();
}
/**
 * 提交个人转账
 */
function saveZhuan(){
    var data = $("#zhuanForm").serialize(); //序列化表单 获取到数据
    data = decodeURIComponent(data,true);
    var saveOptions ={
        url: "../web/tcms/fleetQuota/save/zhuan",
        data:{data:data},
        type:'post',
        dataType:'html',
        success:function(data){
            $("#main").html(data);
        }
    }
    $("#zhuanForm").ajaxSubmit(saveOptions);
    $("#zhuanModel").modal('hide');
    $("#zhuanTable").empty();
}

/*取消弹层方法*/
function closeDialog(divId){
    jQuery('#editForm').validationEngine('hide');//隐藏验证弹窗
    $("#editForm :input").each(function () {
        $(this).val("");
    });
    $("#avatar_b").empty();
    $("#"+divId).modal('hide');
}
function clearDiv(){
    $("#editForm :input").each(function () {
        $(this).val("");
    });
    $("#avatar_b").empty();
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
            }
        }
        $("#editForm").ajaxSubmit(saveOptions);

        $("#editModel").modal('hide');
        $(".modal-backdrop").css("display","none");

}

//重置
function init(){
    loadPage('#main', '../web/tcms/FleetQuota/list/page');
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
                    message: '支付密码不能为空'
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
        payCode: {
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
                        if($("[name=payCode]").val() != value){
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
    }
});
