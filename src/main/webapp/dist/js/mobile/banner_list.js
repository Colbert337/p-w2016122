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
function addBanner(){
    clearDiv();
    $("#editBanner").text("添加图片");
    /*密码输入框改为可编辑*/
    $("#pay_code").removeAttr("readonly");
    $("#re_password").removeAttr("readonly");
    $("#plates_number").attr("data-onFlag","add");

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
            $("#pay_code").val(data.vehicle.payCode);
            $("#re_password").val(data.vehicle.payCode);
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
var projectfileoptions = {
    showUpload : false,
    showRemove : false,
    language : 'zh',
    allowedPreviewTypes : [ 'image' ],
    allowedFileExtensions : [ 'jpg', 'png', 'gif', 'jepg' ],
    maxFileSize : 1000,
}
// 文件上传框
$('input[class=projectfile]').each(function() {
    var imageurl = $(this).attr("value");

    if (imageurl) {
        var op = $.extend({
            initialPreview : [ // 预览图片的设置
                "<img src='" + imageurl + "' class='file-preview-image'>", ]
        }, projectfileoptions);

        $(this).fileinput(op);
    } else {
        $(this).fileinput(projectfileoptions);
    }
});

//bootstrap验证控件
$('#editForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        title: {
            validators: {
                notEmpty: {
                        message: '标题不能为空'
                }
            }
        },
        targetUrl: {
            validators: {
                notEmpty: {
                    message: '链接地址不能为空'
                }
            }
        }
    }
});

function save_photo(fileobj,obj,obj1){
    $(fileobj).parents("div").find("input[name=uploadfile]").each(function(){
        $(this).attr("name","");
    });
    $(fileobj).parent("div").find("input:first").attr("name","uploadfile");
    if($(obj).val()==null || $(obj).val()==""){
        bootbox.alert("请先上传文件");
        return;
    }

    var multipartOptions ={
        url:'../crmInterface/crmBaseService/web/upload?stationid='+$("#sys_gas_station_id").val(),
        type:'post',
        dataType:'text',
        enctype:"multipart/form-data",
        success:function(data){
            var s = JSON.parse(data);
            if(s.success == true){
                bootbox.alert("上传成功");
                $(obj1).val(s.obj);
            }

        },error:function(XMLHttpRequest, textStatus, errorThrown) {
            bootbox.alert("上传成功");
        }
    }
    $("#gastationform").ajaxSubmit(multipartOptions);
}
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
