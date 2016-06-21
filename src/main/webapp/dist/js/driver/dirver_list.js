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
function addDriver(){
    $("#driverModel").modal('show');
}

/**
 * 发送验证码
 */
function sendMessage(){

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
    $("#driverModel").modal('show');
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
    if(jQuery('#driverForm').validationEngine('validate')){
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
}

/**
 * 删除用户
 */
function leaveDriver(driverId){
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

