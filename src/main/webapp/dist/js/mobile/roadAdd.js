$('.timebox').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

//bootstrap验证控件		
	    $('#roadform').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	conditionType: {
	                validators: {
	                    notEmpty: {
	                        message: '路况类型不能为空'
	                    }
	                }
	            },
	            captureTime: {
	                validators: {
	                    notEmpty: {
	                        message: '拍照时间不能为空'
	                    }
	                }
	            },
	            longitude: {
	                validators: {
	                    notEmpty: {
	                        message: '坐标不能为空'
	                    }
	                }
	            },
	            longitude: {
	                validators: {
	                    notEmpty: {
	                        message: '坐标不能为空'
	                    }
	                }
	            },
	            captureLongitude: {
	                validators: {
	                    notEmpty: {
	                        message: '拍照时坐标不能为空'
	                    }
	                }
	            },
	            captureTime: {
	                validators: {
	                    notEmpty: {
	                        message: '拍照时时间不能为空'
	                    }
	                }
	            },
	            conditionMsg: {
	                validators: {
	                    notEmpty: {
	                        message: '路况说明不能为空'
	                    }
	                }
	            },
	            startTime: {
	                validators: {
	                    notEmpty: {
	                        message: '开始时间不能为空'
	                    }
	                }
	            },
	            endTime: {
	                validators: {
	                    notEmpty: {
	                        message: '结束时间不能为空'
	                    }
	                }
	            },
	            address: {
	                validators: {
	                    notEmpty: {
	                        message: '详细地址不能为空'
	                    }
	                }
	            }
	         }
	    });

	    $('#date-timepicker1').datetimepicker({
			 icons: {
				time: 'fa fa-clock-o',
				date: 'fa fa-calendar',
				up: 'fa fa-chevron-up',
				down: 'fa fa-chevron-down',
				previous: 'fa fa-chevron-left',
				next: 'fa fa-chevron-right',
				today: 'fa fa-arrows ',
				clear: 'fa fa-trash',
				close: 'fa fa-times'
			 }
			}).next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
	function saveRoad(){
		/*手动验证表单，当是普通按钮时。*/
		$('#roadform').data('bootstrapValidator').validate();
		if(!$('#roadform').data('bootstrapValidator').isValid()){
			return ;
		}
		
		var options ={   
	            url:'..//web/mobile/road',   
	            type:'post',
	            data:{
	            	device_token:device_token,
	            	driver_name:driver_name
	            },
	            dataType:'text',
	            success:function(data){
	            	$("#main").html(data);
	            	$("#modal-table").modal("show");
	            },error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	 	       }
		}
					
		$("#roadform").ajaxSubmit(options);
	}
	
	function init(){
		loadPage('#main', '../webpage/poms/mobile/roadAdd.jsp');
		
	}
	var device_token = '';
	var driver_name = '';
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
		