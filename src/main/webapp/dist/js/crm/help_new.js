//保存
function save(){
	$('#formnew').data('bootstrapValidator').validate();
	if(!$('#formnew').data('bootstrapValidator').isValid()){
		return ;
	}
	if($('.user-name-valid').is(':visible')){
		return ;
	}
        var options ={   
                url:'../web/crm/help/save',   
                type:'post',                    
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    alert("保存成功");
                 },error:function(XMLHttpRequest, textStatus, errorThrown) {                        
                  }
            }    
        $("#formnew").ajaxSubmit(options);
    }

//重置
function returnpage(){
    	loadPage('#main', '../web/crm/help/list');
    } 

//bootstrap验证控件		
$("#formnew").bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        title: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 10,
                    message: '标题不能超过10个汉字'
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
         crmHelpTypeId: {
        	    message: 'The cardno is not valid',
				validators: {
					notEmpty: {
						message: '类型不能为空'
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
       createdDate: {
            message: 'The cardno is not valid',
            validators: { 
                notEmpty: {
                    message: '日期不能为空'
                },
                callback: {
                	message: '日期必须大于等于当前日期',
                	callback: function (value, validator, $field) {
                         if(compareDate(new Date().toLocaleDateString(),value)){
                        	 return false;
                         }
                         return true;
                    }
                }
            },
            trigger: 'change'
        },
            }
        }
     }
});

