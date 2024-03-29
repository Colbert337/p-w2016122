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
    url:'../web/tcms/vehicle/list/page',
    type:'post',
    dataType:'html',
    success:function(data){
        $("#main").html(data);
    }, error: function (XMLHttpRequest, textStatus, errorThrown) {
        bootbox.alert("操作失败!")//保存成功弹窗
    }
}
/*分页相关方法 end*/
//显示添加用户弹出层add
function addVehicle(){
    clearDiv();
    $("#editVehicle").text("添加车辆");
    /*密码输入框改为可编辑*/
    $("#pay_code").removeAttr("readonly");
    $("#re_password").removeAttr("readonly");
    $("#plates_number").attr("data-onFlag","add");

    $("#cardInfoDiv").hide();
    $("#editModel").modal('show').on('hide.bs.modal', function() {
        $('#editForm').bootstrapValidator('resetForm',true);
        $('.user-name-valid').remove();
    });
}

//显示编辑用户弹出层
function editVehicle(vehicleId){
    $.ajax({
        url:"../web/tcms/vehicle/info",
        data:{tcVehicleId:vehicleId},
        async:false,
        type: "POST",
        success: function(data){
            $("#plates_number").val(data.vehicle.platesNumber);
            $("#tc_vehicle_id").val(data.vehicle.tcVehicleId);
            document.getElementById("pay1").style.display="none";
            document.getElementById("pay2").style.display="none";
            $("#notice_phone").val(data.vehicle.noticePhone);
            $("#copy_phone").val(data.vehicle.copyPhone);
            $("#editVehicle").text("修改车辆");
            $("#plates_number").attr("data-onFlag","edit");
            $("#dongjie").empty();
            if(data.gasCard != null && data.gasCard.card_no != null){
                if(data.gasCard.card_no != "" && data.gasCard.card_status == 4){
                    var str = "<button onclick='freeze("+data.gasCard.card_no+",0)'>冻结</button>";
                    $("#dongjie").append(str);
                }else if(data.gasCard.card_no != "" && data.gasCard.card_status == 0){
                    var str = "<button onclick='freeze("+data.gasCard.card_no+",4)'>解冻</button>";
                    $("#dongjie").append(str);
                }
                var cardType,cardStatus;
                //卡类型
                switch(data.gasCard.card_type)
                {
                    case '10':
                        cardType = "LNG"
                        break;
                    case '1001':
                        cardType = "柴油"
                        break;
                    case '1002':
                        cardType = "LNG"
                        break;
                    case '2001':
                        cardType = "LNG"
                        break;
                    case '2002':
                        cardType = "LNG"
                        break;
                    case '20':
                        cardType = "LNG"
                        break;
                    default:
                        cardType = "LNG"
                }
                //卡状态
                switch(data.gasCard.card_status)
                {
                    case '0':
                        cardStatus = "已冻结"
                        break;
                    case '1':
                        cardStatus = "已入库"
                        break;
                    case '2':
                        cardStatus = "已出库"
                        break;
                    case '3':
                        cardStatus = "未发放"
                        break;
                    case '4':
                        cardStatus = "使用中"
                        break;
                    case '5':
                        cardStatus = "已失效"
                        break;
                    default:
                        cardStatus = "未使用"
                }
                $("#card_no").text(data.gasCard.card_no);
                $("#card_type").text(cardType);
                $("#card_status").text(cardStatus);
            }

            /*密码输入框改为可编辑*/
            /*$("#pay_code").attr("readonly","readonly");
            $("#re_password").attr("readonly","readonly");*/
            $("#pay_code").removeAttr("maxlength");
            $("#re_password").removeAttr("maxlength");
        }
    })
    $("#cardInfoDiv").show();
    $("#editModel").modal('show');
}
//显示编辑用户弹出层
function editPayCode(vehicleId){
	$("#tcVehicleId").val(vehicleId);
    $("#editPayCodeDiv").modal('show');
}

/**
 * 冻结卡
 */
function freeze(cardNo,status){
    var saveOptions ={
        url:"../web/tcms/vehicle/update/freeze",
        data:{card_no:cardNo,card_status:status},
        type: "POST",
        dataType:'html',
        success: function(data){
            bootbox.alert("操作成功！");
            $("#main").html(data);
        },error: function(XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert("操作失败！");
        }
    }
    $("#editForm").ajaxSubmit(saveOptions);

    $("#editModel").modal('hide');
    $(".modal-backdrop").css("display","none");
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
 * 保存用户信息
 */
function saveVehicle(){
        $('#editForm').data('bootstrapValidator').validate();
        if(!$('#editForm').data('bootstrapValidator').isValid()){
            return ;
        }
        if($('.user-name-valid').is(':visible')){
            return ;
        }

        var saveOptions ={
            url:'../web/tcms/vehicle/save',
            type:'post',
            dataType:'html',
            success:function(data){
                $("#main").html(data);
                $("#modal-table").modal("show");
            },error: function(XMLHttpRequest, textStatus, errorThrown) {
                bootbox.alert("操作失败！");
            }
        }
        $("#editForm").ajaxSubmit(saveOptions);
        $("#editModel").modal('hide');
        $(".modal-backdrop").css("display","none");

}

//重置
function init(){
    loadPage('#main', '../web/tcms/vehicle/list/page');
}
/**
 * 删除用户
 */
function leaveDriver(){
    if(confirm("确定要离职该司机吗？")){
        var deleteOptions ={
            url:'../web/driver/delete',
            data:{},
            type:'post',
            dataType:'text',
            success:function(data){
                bootbox.alert("操作成功!");//保存成功弹窗
                $("#main").html(data);
            },error: function(XMLHttpRequest, textStatus, errorThrown) {
                bootbox.alert("操作失败！");
            }
        }
        $("#listForm").ajaxSubmit(deleteOptions);
    }

}

//弹出导入模板弹层
function openImportDiv(){
    $("#importDivModel").modal("show");
}

/**
 * 判断文件格式
 */
function fileFormat(){
    var fileName= $("#file_import").val();
    var suffix = fileName.substr(fileName.indexOf("."));
    if(suffix != '.xls' && suffix !=".xlsx"){
        bootbox.alert("导入文件格式错误，必须是excle格式！");
        return false;
    }
}
/**
 * 判断车牌号是否存在
 */
function isVehicleExit(){
    var numberType = {
        platesNumber: $("#plates_number").val(),
        /*onFlag: $("#plates_number").attr("data-onFlag")*/
        tcVehicleId: $("#tc_vehicle_id").val()
    };
    $.ajax({
        url: '../web/tcms/vehicle/info/name',
        data: numberType,
        type: "POST",
        success: function(data){
            console.log(data);
            console.log(data.valid);

            if(!data.valid){
                if($('.user-name-valid').is(':visible')){
                    return false;
                }
                $('#plates_number').after('<div class="tooltip fade top in user-name-valid"><div class="tooltip-arrow"></div><div class="tooltip-inner">车牌号已存在!</div></div>');
            } else {
                $('.user-name-valid').remove();
            }

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {

        }
    })
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
                    message: '车牌号不能为空'
                },
                stringLength: {
					min: 7,
					max: 7,
					message: '请输入正确车牌号，长度为7的数字字母及汉字组合'
				},
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
                },
                stringLength: {
					min: 6,
					max:20,
					message: '密码长度必须在6~20位之间'
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
                    regexp: '^[0-9a-zA-Z]+$',
                    message: '密码只能包含数字和字母'
                },
                stringLength: {
					min: 6,
					max:20,
					message: '密码长度必须在6~20位之间'
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
/**
 * 修改密码密码校验
 */
$('#editPayCodeDiv').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        payCode1: {
            validators: {
                notEmpty: {
                    message: '支付密码不能为空'
                },
                regexp: {
                    regexp: '^[0-9a-zA-Z]+$',
                    message: '密码只能包含数字和字母'
                },
                stringLength: {
					min: 6,
					max:20,
					message: '密码长度必须在6~20位之间'
				},
				callback: {
                    message: '支付密码不一致',
                    callback: function (value, validator, $field) {
                        if($("[name=rePassword1]").val()!=""){
                        	if($("[name=rePassword1]").val() != value){
                                return false;
                            }
                            return true;
                        }
                        return true;
                    }
                }
            }
        },
        rePassword1: {
            validators: {
                notEmpty: {
                    message: '确认密码不能为空'
                },
                regexp: {
                    regexp: '^[0-9a-zA-Z]+$',
                    message: '密码只能包含数字和字母'
                },
                stringLength: {
					min: 6,
					max:20,
					message: '密码长度必须在6~20位之间'
				},
                callback: {
                    message: '支付密码不一致',
                    callback: function (value, validator, $field) {
                        if($("[name=payCode1]").val() != value){
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
    }
});

/**
 * 文件上传验证
 */
$('#importForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        fileImport: {
            validators: {
                notEmpty: {
                    message: '请选择要导入的文件'
                }
            }
        },
        remote: {
            url: '../web/tcms/vehicle/info/fileFormat',
            type: "post",
            async: false,
            data: function(validator, $field, value) {
                return{
                    fileImport:$("#file_import").val()
                };
            },
            message: '文件导入失败'
        }
    }
});

/**
 *导入文件
 */
function saveTemplate(){
    var multipartOptions ={
        url:'../web/tcms/vehicle/info/file',
        type:'post',
        dataType:'json',
        enctype:"multipart/form-data",
        success:function(data){
            bootbox.alert(data.msg+",导入"+data.sum+"条数据！");
            init();
            /*$("#main").html(data);
            $("#modal-table").modal("show");*/
        },error:function(XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert("操作失败！");
        }
    }
    $("#importForm").ajaxSubmit(multipartOptions);
    $("#importDivModel").modal('hide').removeClass('in');
    $("body").removeClass('modal-open').removeAttr('style');
    $(".modal-backdrop").remove();
}


$(function(){
    //美化上传框
    $('#file_import').ace_file_input({
        no_file:'请选择需要导入的文件',
        btn_choose:'选择文件',
        btn_change:'重新选择',
        droppable:false,
        onchange:null,
        thumbnail:false //| true | large
        //whitelist:'gif|png|jpg|jpeg'
        //blacklist:'exe|php'
        //onchange:''
        //
    });
})
/**
 * 重置支付密码
 */
function changePayCode(){
	 $('#editPayCodeDiv').data('bootstrapValidator').validate();
     if(!$('#editPayCodeDiv').data('bootstrapValidator').isValid()){
         return ;
     }
    $.ajax({
        url: '../web/tcms/vehicle/changePayCode',
        data: {tcVehicleId:$("#tcVehicleId").val(), payCode:$("#pay_code1").val()},
        type: "POST",
        success: function(data){
        	bootbox.alert("修改成功！");
        	$("#editPayCodeDiv").modal('hide');
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert("修改失败！");
        }
    })
}

