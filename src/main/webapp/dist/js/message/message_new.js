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
	            },
				province_id: {
					validators: {
						callback: {
							message: '发送范围不能为空',
							callback: function(value, validator) {
								var messageType = $('input:radio[name="messageType"]:checked').val();
								if (value.length==0 && messageType =='2') {
									return false;
								}
									return true;
							}
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

	function changeMessageType(messageType){
		var type = messageType.value;
		if(type=='2' && $("#province").css("display")=='none'){
			$("#province").css("display","");
			$("select[name='province_id']").trigger("change");
		}else{
			$("#province").css("display","none");
			$("select[name='province_id']").val("");
			$("input[name='province_name']").val("");
		}
	}

	function changeProvince(province){
		var provincename = $(province).find("option:selected").text();
		$("input[name='province_name']").val(provincename);
	}