/**
 * Created by Administrator on 2016/6/20.
 * Author: wdq
 */
/*分页相关方法 start*/
/**
 * 保存支付密码
 */
$(function(){
    //bootstrap验证控件
    $('#passwordForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },submitHandler: function(validator, form, submitButton) {
            bootbox.alert("支付密码修改成功！");
            var payCode = $("#pay_code").val();
            window.location=form.attr('action')+"?pay_code="+payCode;
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
})

