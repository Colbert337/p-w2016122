	
	//datepicker plugin
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		language: 'cn'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
		
			//bootstrap验证控件		
		    $('#gastationform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	deposit: {
		                validators: {
		                    notEmpty: {
		                        message: '加注站保证金不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[-+]?[0-9]+(\.[0-9]+)?$',
		                        message: '加注站保证必须是数字'
		                    }
		                }
		            },
		            company: {
		                validators: {
		                    notEmpty: {
		                        message: '企业名称不能为空'
		                    }
		                }
		            },
		            depositType: {
		                validators: {
		                    notEmpty: {
		                        message: '转账方式不能为空'
		                    }
		                }
		            }
		         }
		    });
			    
		    
		    
		    
		function save(){			
			/*手动验证表单，当是普通按钮时。*/
			$('#gastationform').data('bootstrapValidator').validate();
			if(!$('#gastationform').data('bootstrapValidator').isValid()){
				return ;
			} else {
				$("#sub").confirm({
					title: "运输公司充值确认",
					text: "是否确认将 ["+$("#gastationame").text()+"] 预付款额度调整"+$("[name=deposit]").val()+"元",
					confirm: function (button) {
						var options ={   
					            url:'../web/gastation/depositGastation',   
					            type:'post',                    
					            dataType:'text',
					            success:function(data){
					            	$("#main").html(data);
					            	$("#modal-table").modal("show");
					            	if($("#retCode").val() != 100){
					            		
					            	}
					            },error:function(XMLHttpRequest, textStatus, errorThrown) {
					            	
					 	       }
						}
						
						$("#gastationform").ajaxSubmit(options);
					},
					cancel: function (button) {

					},
					confirmButton: "确认",
					cancelButton: "取消"
				});
			}
			
			
			
		}
		
		function returnpage(){
			loadPage('#main', '../web/gastation/gastationList');
		}
		
		function returnpage2(){
			loadPage('#main', '../web/gastation/gastationList2');
		}