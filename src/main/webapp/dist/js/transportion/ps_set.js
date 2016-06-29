/**
 * Created by Administrator on 2016/6/20.
 * Author: wdq
 */
/*分页相关方法 start*/
/**
 * 保存支付密码
 */
function savePsSet(){
        $('#passwordForm').data('bootstrapValidator').validate();
        if(!$('#passwordForm').data('bootstrapValidator').isValid()){
            return ;
        }

        var saveOptions ={
            url:'../web/transportion/update/password',
            type:'post',
            dataType:'html',
            success:function(data){
                $("#main").html(data);
            }
        }
        $("#passwordForm").ajaxSubmit(saveOptions);

        $("#passwordForm").modal('hide');
        $(".modal-backdrop").css("display","none");

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
        pay_code: {
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
                        if($("[name=pay_code]").val() != value){
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
    }
});
