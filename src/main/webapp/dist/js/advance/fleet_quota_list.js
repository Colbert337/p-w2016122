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

    $("#passwordModel").modal('show');
}


//显示编辑车队额度弹出层
function editFleetQuota(fleetQuotaId){
    $.ajax({
        url:"../web/tcms/fleetQuota/info",
        data:{tcFleetQuotaId:fleetQuotaId},
        async:false,
        type: "POST",
        success: function(data){
            $("#plates_number").val(data.fleetQuota.platesNumber);
            $("#tc_FleetQuota_id").val(data.fleetQuota.tcFleetQuotaId);
            $("#pay_code").val(data.fleetQuota.payCode);
            $("#re_password").val(data.fleetQuota.payCode);
            $("#notice_phone").val(data.fleetQuota.noticePhone);
            $("#copy_phone").val(data.fleetQuota.copyPhone);

            if(data.gasCard != null && data.gasCard.card_no != null){
                var cardType,cardStatus;
                //卡类型
                switch(data.gasCard.card_type)
                {
                    case '0':
                        cardType = "LNG"
                        break;
                    case '1':
                        cardType = "柴油"
                        break;
                    case '2':
                        cardType = "LNG"
                        break;
                    default:
                        cardType = "CNG"
                }
                //卡状态
                switch(data.gasCard.card_status)
                {
                    case '0':
                        cardStatus = "已冻结"
                        break;
                    case '1':
                        cardStatus = "未使用"
                        break;
                    case '2':
                        cardStatus = "使用中"
                        break;
                    default:
                        cardStatus = "未使用"
                }
                $("#card_no").text(data.gasCard.card_no);
                $("#card_type").text(cardType);
                $("#card_status").text(cardStatus);
            }

            /*密码输入框改为可编辑*/
            $("#pay_code").attr("readonly","readonly");
            $("#re_password").attr("readonly","readonly");
        }
    })
    $("#cardInfoDiv").show();
    $("#editModel").modal('show');
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
$('#editForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        platesNumber: {
            validators: {
                notEmpty: {
                    message: '手机号码不能为空'
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
        },
        noticePhone: {
            validators: {
                notEmpty: {
                    message: '手机号不能为空'
                },
                regexp: {
                    regexp: '^[0-9]+$',
                    message: '手机号只能包含数字'
                },
                stringLength: {
                    min: 11,
                    max: 11,
                    message: '手机号码为11位'
                }
            }
        },
        copyPhone: {
            validators: {
                notEmpty: {
                    message: '手机号不能为空'
                },
                regexp: {
                    regexp: '^[0-9]+$',
                    message: '手机号只能包含数字'
                },
                stringLength: {
                    min: 11,
                    max: 11,
                    message: '手机号码为11位'
                }
            }
        }
    }
});
