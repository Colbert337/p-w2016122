/**
 * Created by Administrator on 2016/6/20.
 * Author: wdq
 */
var setlectItem;
jQuery(function($){
    var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox(
        {infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'}
    );
    setlectItem = demo1;
    var container1 = demo1.bootstrapDualListbox('getContainer');
    container1.find('.btn').addClass('btn-white btn-info btn-bold');
});

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
    url:'../web/tcms/fleet/list/page',
    type:'post',
    dataType:'html',
    success:function(data){
        $("#main").html(data);
    }
}
/*分页相关方法 end*/
//显示添加车队弹出层add
function addFleet(){
    clearDiv();
    $.ajax({
        url:"../web/permi/user/list/info",
        data:{},
        async:false,
        type: "POST",
        success: function(data){
            $("#sys_user_id").empty();
            $("#sys_user_id").append("<option value=''>--选择队长--</option>");
            $.each(data,function(i,val){
                $("#sys_user_id").append("<option value='"+val.sysUserId+"'>"+val.userName+"-"+val.realName+"</option>");
            })
        }
    })

    /*密码输入框改为可编辑*/
    $("#pay_code").removeAttr("readonly");
    $("#re_password").removeAttr("readonly");

    $("#cardInfoDiv").hide();
    $("#editModel").modal('show');
}

//显示编辑车队弹出层
function editFleet(fleetId){
    var userId;
    $.ajax({
        url:"../web/tcms/fleet/info",
        data:{tcFleetId:fleetId},
        async:false,
        type: "POST",
        success: function(data){
            $("#fleet_name").val(data.fleetName);
            $("#tc_fleet_id").val(data.tcFleetId);
            $("#sys_user_id").val(data.sysUserId);
            userId = data.sysUserId;
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

            $.ajax({
                url:"../web/permi/user/list/info",
                data:{},
                async:false,
                type: "POST",
                success: function(data){
                    $("#sys_user_id").empty();
                    $("#sys_user_id").append("<option value=''>--选择队长--</option>");
                    $.each(data,function(i,val){
                        if(val.sysUserId == userId){
                            $("#sys_user_id").append("<option value='"+val.sysUserId+"' selected='selected'>"+val.userName+"-"+val.realName+"</option>");
                        }else{
                            $("#sys_user_id").append("<option value='"+val.sysUserId+"'>"+val.userName+"-"+val.realName+"</option>");
                        }
                    })
                }
            })

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
 * 保存车队信息
 */
function saveFleet(){
        $('#editForm').data('bootstrapValidator').validate();
        if(!$('#editForm').data('bootstrapValidator').isValid()){
            return ;
        }

        var saveOptions ={
            url:'../web/tcms/fleet/save',
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
    loadPage('#main', '../web/tcms/fleet/list/page');
}
/**
 * 删除车队
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

/**
 * 管理车队车辆
 */
function mangFleet(fleetId){
    $("#fleet_id").val(fleetId);
    $.ajax({
        url:"../web/tcms/fleet/list/fv",
        data:{tcFleetId:fleetId},
        async:false,
        type: "POST",
        success: function(data){
            /*$("#duallist").empty();*/
            setlectItem.empty();
            $.each(data,function(i,val){
                if(val.selected == "true"){
                    setlectItem.append("<option value='"+val.tcVehicleId+"' selected='selected'>"+val.platesNumber+"</option>");
                }else{
                    setlectItem.append("<option value='"+val.tcVehicleId+"'>"+val.platesNumber+"</option>");
                }
            })
            setlectItem.bootstrapDualListbox('refresh');
        }
    })

    $("#manageModel").modal('show');
}

/**
 * 保存车队车辆分配
 */
function saveManage(){
    /*$('#manageForm').data('bootstrapValidator').validate();
    if(!$('#manageForm').data('bootstrapValidator').isValid()){
        return ;
    }*/
    var vehicleStr = $('[name="duallistbox_demo1[]"]').val();
    $("#sysUserId").val(vehicleStr);
    var saveOptions ={
        url:'../web/tcms/fleet/save/fv',
        type:'post',
        dataType:'html',
        success:function(data){
            $("#main").html(data);
        }
    }
    $("#manageForm").ajaxSubmit(saveOptions);

    $("#manageModel").modal('hide');
    $(".modal-backdrop").css("display","none");
    setlectItem.empty();

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
        fleetName: {
            validators: {
                notEmpty: {
                    message: '车队名称不能为空'
                }
            }
        },
        sysUserId: {
            validators: {
                notEmpty: {
                    message: '车队队长不能为空'
                }
            }
        }
    }
});


