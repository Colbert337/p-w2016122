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

/**
 * 删除图片
 */
function deleteBanner(imgId){
    bootbox.setLocale("zh_CN");
    bootbox.confirm("确认要删除图片吗？", function (result) {
        if (result) {
            var deleteOptions ={
                url:'../web/mobile/img/delete',
                data:{mbBannerId:imgId},
                type:'post',
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    $("#modal-table").modal("show");
                    $('[data-rel="tooltip"]').tooltip();
                }
            }
            $("#formcashback").ajaxSubmit(deleteOptions);
        }
    })

}

//显示编辑用户弹出层
function editBanner(imgId){
    $.ajax({
        url:"../web/mobile/img/info",
        data:{mbBannerId:imgId},
        async:false,
        type: "POST",
        success: function(data){
            $("#mb_banner_id").val(data.mbBannerId);
            $("#title").val(data.title);
            $("#img_path").val(data.imgPath);
            $("#target_url").val(data.targetUrl);
            $("#version").val(data.version);
            $("#remark").text(data.remark);

            $("#editBanner").text("修改图片");
        }
    })
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
 * 保存图片信息
 */
function saveBanner(){
    $('#editForm').data('bootstrapValidator').validate();
    if(!$('#editForm').data('bootstrapValidator').isValid()){
        return ;
    }

    var saveOptions ={
        url:'../web/mobile/img/save',
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
        url:'../crmInterface/crmBaseService/web/upload?stationid='+$("#stationId").val(),
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
