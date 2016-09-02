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
	            url:'../web/message/saveMessageNew',   
	            type:'post',
	            data:{
	            	device_token:device_token
	            },
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
		loadPage('#main', '../webpage/poms/message/messageNEW.jsp');
		
	}
	var device_token = '';
	function addUser(){
		
	
//		
//		 var listOptions ={
//		    		url:'../web/message/driverList',
//		    		type:'post',
//		    		dataType:'html',
//		    		success:function(data){
//		    			//console.log("这里分页之后");
//		    			$("#content").html(data);
//		    			
//		    			$("#editModel").modal('show');
//		    			if($("#retCode").val() != "100"){
//
//		    			}
//		    			$('[data-rel="tooltip"]').tooltip();
//		    		},error:function(XMLHttpRequest, textStatus, errorThrown) {
//
//		    		}
//		    	}
//			 $("#formgastation").ajaxSubmit(listOptions);
		 loadPage('#content', '../web/message/driverList');
			$("#editModel").modal('show');
	}
	function closeDialog(obj){
		$("#" + obj).modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
//		init();
		
	}
	function saveBanner(){
		//alert(device_token);
		$("#editModel").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
		
	}
	function returnpage(){
		loadPage('#main', '../web/message/messageList');
	}
		