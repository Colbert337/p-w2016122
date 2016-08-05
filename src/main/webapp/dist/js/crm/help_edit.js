//修改  
function save(){
        var options ={   
                url:'../web/crm/help/update',   
                type:'post',                    
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    alert("修改成功");
                 },error:function(XMLHttpRequest, textStatus, errorThrown) {
                        
                  }
            }    
        $("#formedit").ajaxSubmit(options);
    }

//重置
function returnpage(){
        loadPage('#main', '../web/crm/help/list');
}

//时间插件
$(function() {
    $("#datepicker").datepicker();
});

//bootstrap验证控件		
$("#formedit").bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
    	crmHelpId: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '题号不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 5,
                    message: '题号不能超过5个数字'
                }
            }
        },
        title: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 20,
                    message: '标题不能超过20个汉字'
                }
            }
        },
        question: {
            validators: {
                notEmpty: {
                    message: '问题不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 50,
                    message: '问题不能超过20个字符'
                }
            }
        },
        answer: {
        	 validators: {
                 notEmpty: {
                     message: '内容不能为空'
                 },
                 stringLength: {
                     min: 1,
                     max: 50,
                     message: '内容不能超过50个字符'
                 }
             }
         },
         issuer: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '发布人不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 5,
                    message: '发布人不能超过5个字符'
                },
            }
        }
     }
});
       
