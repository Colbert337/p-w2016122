/**
 * Created by Administrator on 2016/6/20.
 * Author: wdq
 */
/*分页相关方法 start*/
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
    url:'../web/driver/list/page',
    type:'post',
    dataType:'html',
    success:function(data){
        $("#main").html(data);
    }
}
/*分页相关方法 end*/
//显示添加用户弹出层
function addDriver(){
    $("#driverModel").modal('show');
}

/**
 * 发送验证码
 */
function sendMessage(){
    var mobilePhone = $("#mobile_phone").val();

    $.ajax({
        url:"../crmCustomerService/web/sendMsg/api",
        data:{mobilePhone:mobilePhone,msgType:'register'},
        async:false,
        type: "POST",
        success: function(data){
            alert(data.msg);
        }
    })
}
//显示编辑用户弹出层
function queryRoleList(roleId){
    $.ajax({
        url:"../web/permi/user/list/role",
        data:{},
        async:false,
        type: "POST",
        success: function(data){

            $("#avatar_b").append("<option value='0'>--选择角色--</option>");
            $.each(data,function(i,val){
                if(val.sysRoleId == roleId){
                    $("#avatar_b").append("<option value='"+val.sysRoleId+"' selected='selected'>"+val.roleName+"</option>");
                }else{
                    $("#avatar_b").append("<option value='"+val.sysRoleId+"'>"+val.roleName+"</option>");
                }
            })
        }
    })
    $("#driverModel").modal('show');
}

//显示编辑用户弹出层
function queryUserTypeList(userType){
    $.ajax({
        url:"../web/usysparam/info",
        data:{mcode:userType},
        async:false,
        type: "POST",
        success: function(data){
            $("#mname").text(data.mname);
            $("#user_type").val(data.mcode);
        }
    })
    $("#driverModel").modal('show');
}

/*取消弹层方法*/
function closeDialog(divId){
    jQuery('#userForm').validationEngine('hide');//隐藏验证弹窗
    $("#userForm :input").each(function () {
        $(this).val("");
    });
    $("#avatar_b").empty();
    $("#"+divId).modal('hide');
}
function clearDiv(){
    $("#roleForm :input").each(function () {
        $(this).val("");
    });
    $("#avatar_b").empty();
}
/**
 * 保存用户信息
 */
function saveDriver(){
        $('#driverForm').data('bootstrapValidator').validate();
        if(!$('#driverForm').data('bootstrapValidator').isValid()){
            return ;
        }

        var saveOptions ={
            url:'../web/driver/save',
            type:'post',
            dataType:'html',
            success:function(data){
                $("#main").html(data);
            }
        }
        $("#driverForm").ajaxSubmit(saveOptions);

        $("#driverModel").modal('hide');
        $(".modal-backdrop").css("display","none");

}

/**
 * 删除用户
 */
function leaveDriver(){
    if(confirm("确定要删除该用户吗？")){
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
$('#driverForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        mobilePhone: {
            validators: {
                notEmpty: {
                    message: '手机号码不能为空'
                },
                stringLength: {
                    min: 11,
                    max: 11,
                    message: '手机号码为11位'
                },
                remote: {
                    url: '../web/driver/info/isExist',
                    type: "post",
                    async: false,
                    data: function(validator, $field, value) {
                        return{
                            mobilePhone:$("#mobile_phone").val()
                        };
                    },
                    message: '手机号已存在'
                }
            }
        },
        userName: {
            validators: {
                notEmpty: {
                    message: '验证码不能为空'
                },
                stringLength: {
                    min: 6,
                    max: 6,
                    message: '验证码必须为6位'
                },
                remote: {
                    url: '../crmCustomerService/web/isMsg',
                    type: "post",
                    async: false,
                    data: function(validator, $field, value) {
                        return{
                            mobilePhone:$("#mobile_phone").val(),
                            msgCode:$("#user_name").val()
                        };
                    },
                    message: '验证码错误，请稍后重新发送'
                }
            }
        },
        fullName: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                },
                stringLength: {
                    min: 2,
                    max: 5,
                    message: '姓名不得小于两个字'
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
