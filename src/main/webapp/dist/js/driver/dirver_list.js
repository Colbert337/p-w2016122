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
var countdown=60;
/*分页相关方法 end*/
//显示添加用户弹出层
function addDriver(){
    countdown = 0;
    var obj = $("#sendMsgA");
    obj.removeAttr("disabled");
    obj.attr("onclick","sendMessage()");
    obj.text("发送验证码");

    $("#driverModel").modal('show').on('hidden.bs.modal', function() {
        $('#driverForm').bootstrapValidator('resetForm',true);
    });
}

function settime() {
    var obj = $("#sendMsgA");
    if (countdown == 0) {
        obj.removeAttr("disabled");
        obj.attr("onclick","sendMessage()");
        obj.text("发送验证码");
        countdown = 60;
        return true
    } else {
        obj.removeAttr("onclick");
        obj.attr("disabled",true);
        obj.text("重新发送(" + countdown + ")");
        countdown--;
        setTimeout(function() {
            settime()
        },1000)
    }
}

/**
 * 发送验证码
 */
function sendMessage(){
    var hasError = $("#mobile_phone").parents(".form-group").hasClass("has-error");
    console.log("hasError:"+hasError);
    if(hasError){
        $("#sendMsgA").off("click");
        return false;
    }else{
        $("#sendMsgA").on("click",function(){sendMessage()});
    }

    var mobilePhone = $("#mobile_phone").val();
    if(mobilePhone == ""){
        return false;
    }else{
        countdown=60;
        settime();
        $.ajax({
            url:"../crmInterface/crmCustomerService/web/sendMsg/api",
            data:{mobilePhone:mobilePhone,msgType:'register'},
            async:false,
            type: "POST",
            success: function(data){
                bootbox.alert(data.msg);
            }
        })
    }

}
//显示编辑用户弹出层
function queryRoleList(roleId){
    $.ajax({
        url:"../web/permi/user/list/role",
        data:{},
        async:false,
        type: "POST",
        success: function(data){

            $("#avatar_b").append("<option value=''>--选择角色--</option>");
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

//重置
function init(){
    loadPage('#main', '../web/driver/list/page');
}
/**
 * 删除用户
 */
function leaveDriver(){
    var chLength = $("input[type='checkbox']:checked").length;
    if(chLength <= 0){
        bootbox.alert("请勾选要离职的员工！");
        return false;
    }else{
        bootbox.setLocale("zh_CN");
        bootbox.confirm("确定要离职当前勾选员工？", function (result) {
            if (result) {
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
        })
    }

}
/**
 * 显示导入
 */
function openImportDiv(){
    $("#importDivModel").modal("show");
}
/**
 *导入文件
 */
function saveTemplate(){
	if ($("#file_import").val()=="") {
		bootbox.alert("请选择文件");
		return false;
	}
    var multipartOptions ={
        url:'../portal/crm/help/info/file',
        type:'post',
        dataType:'json',
        enctype:"multipart/form-data",
        success:function(data){
            if (data.SUCCESS == true) {
            	bootbox.alert(data.datas);
            	init();
    		}
        },error:function(XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert("操作失败！");
        }
    }
    $("#importForm").ajaxSubmit(multipartOptions);
    $("#importDivModel").modal('hide').removeClass('in');
    $("body").removeClass('modal-open').removeAttr('style');
    $(".modal-backdrop").remove();
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
                    url: '../crmInterface/crmCustomerService/web/isMsg',
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
                    max: 20,
                    message: '姓名不能小于2个字，不能大于20个字'
                }
            }
        },
        payCode: {
            validators: {
                notEmpty: {
                    message: '支付密码不能为空'
                },
                regexp: {
                    regexp: '^[0-9]+$',
                    message: '密码只能包含数字'
                },
                stringLength: {
                    min: 6,
                    max: 6,
                    message: '支付密码不能小于6个数字'
                },
                callback: {
                    message: '支付密码不一致',
                    callback: function (value, validator, $field) {
                        if($("[name=rePassword]").val()!=""){
                        	if($("[name=rePassword]").val() != value){
                                return false;
                            }
                            return true;
                        }
                        return true;
                    }
                }
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '确认密码不能为空'
                },
                regexp: {
                    regexp: '^[0-9]+$',
                    message: '密码只能包含数字'
                },
                stringLength: {
                    min: 6,
                    max: 6,
                    message: '支付密码不能小于6个数字'
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
