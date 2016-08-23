	//bootstrap验证控件		
	    $('#messageform').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	messageTitle: {
	                validators: {
	                    notEmpty: {
	                        message: '消息标题不能为空'
	                    }
	                }
	            },
	            messageBody: {
	                validators: {
	                    notEmpty: {
	                        message: '消息内容不能为空'
	                    }
	                }
	            },
	            messageTicker: {
	                validators: {
	                    notEmpty: {
	                        message: '消息缩略不能为空'
	                    }
	                }
	            }
	         }
	    });
		    
	function save(){
		/*手动验证表单，当是普通按钮时。*/
		$('#messageform').data('bootstrapValidator').validate();
		if(!$('#messageform').data('bootstrapValidator').isValid()){
			return ;
		}
		
		var options ={   
	            url:'../web/message/saveMessage',   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
	            	$("#main").html(data);
	            	$("#modal-table").modal("show");
	            },error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	 	       }
		}
					
		$("#messageform").ajaxSubmit(options);
	}
	
	function init(){
		loadPage('#main', '../webpage/poms/message/message_new.jsp');
	}

	function returnpage(){
		loadPage('#main', '../web/message/messageList');
	}
		