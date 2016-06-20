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
    url:'<%=basePath%>/web/permi/user/list/page',
    type:'post',
    dataType:'html',
    success:function(data){
        $("#main").html(data);
    }
}
/*分页相关方法 end*/
//显示添加用户弹出层
function addUser(){
    /*$("#userModel").modal('show');*/
    queryRoleList();
    queryUserTypeList("");
    /*密码输入框改为可编辑*/
    $("#password").removeAttr("readonly");
    $("#re_password").removeAttr("readonly");
}
//显示编辑用户弹出层
function queryRoleList(roleId){
    $.ajax({
        url:"<%=basePath%>/web/permi/user/list/role",
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
    $("#userModel").modal('show');
}

//显示编辑用户弹出层
function queryUserTypeList(userType){
    $.ajax({
        url:"<%=basePath%>/web/usysparam/info",
        data:{mcode:userType},
        async:false,
        type: "POST",
        success: function(data){
            $("#mname").text(data.mname);
            $("#user_type").val(data.mcode);
        }
    })
    $("#userModel").modal('show');
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
function saveUser(){
    if(jQuery('#userForm').validationEngine('validate')){
        var saveOptions ={
            url:'<%=basePath%>/web/permi/user/save',
            type:'post',
            dataType:'html',
            success:function(data){
                $("#main").html(data);
            }
        }
        $("#userForm").ajaxSubmit(saveOptions);

        $("#userModel").modal('hide');
        $(".modal-backdrop").css("display","none");
    }
}

/**
 * 回显用户信息
 */
function editUser(userId){
    $.ajax({
        url:"<%=basePath%>/web/permi/user/update",
        data:{sysUserId:userId},
        async:false,
        type: "POST",
        success: function(data){
            $("#sys_user_id").val(data.sysUserId);
            $("#user_name").val(data.userName);
            $("#remark").val(data.remark);
            $("#real_name").val(data.realName);
            $("#user_type").val(data.userType);

            if(data.gender == 0){
                $("#gender_b").attr("checked","checked");
                $("#gender_g").removeAttr("checked");
            }else if(data.gender == 1){
                $("#gender_g").attr("checked","checked");
                $("#gender_b").removeAttr("checked");
            }

            $("#email").val(data.email);
            $("#mobile_phone").val(data.mobilePhone);
            /*密码输入框改为只读*/
            $("#password").val(data.password);
            $("#re_password").val(data.password);
            /*密码输入框改为可编辑*/
            $("#password").attr("readonly","readonly");
            $("#re_password").attr("readonly","readonly");

            queryRoleList(data.sysRoleId);
            queryUserTypeList(data.userType);
        }
    });

}

/**
 * 删除用户
 */
function deleteUser(userId){
    if(confirm("确定要删除该用户吗？")){
        var deleteOptions ={
            url:'<%=basePath%>/web/permi/user/delete',
            data:{userId:userId},
            type:'post',
            dataType:'text',
            success:function(data){
                $("#main").html(data);
            }
        }
        $("#listForm").ajaxSubmit(deleteOptions);
    }

}
/**
 * 修改用户状态 0 启用 1 禁用
 * @param userId
 */
function updateStatus(userId,status){
    var alertStr = "确定要禁用该用户吗？";
    if(status == 0){
        alertStr = "确定要启用该用户吗？";
    }
    if(confirm(alertStr)){
        var deleteOptions ={
            url:'<%=basePath%>/web/permi/user/update/staruts',
            data:{sysUserId:userId,status:status},
            type:'post',
            dataType:'text',
            success:function(data){
                $("#main").html(data);
            }
        }
        $("#listForm").ajaxSubmit(deleteOptions);
    }

}
