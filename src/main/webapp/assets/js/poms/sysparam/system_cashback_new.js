			//bootstrap验证控件
		    $('#cashbackform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	threshold_min_value: {
		                validators: {
		                    notEmpty: {
		                        message: '阈最小值不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]*$',
		                        message: '阈最小值必须是整数'
		                    }
		                }
		            },
		            threshold_max_value: {
		                validators: { 
		                    notEmpty: {
		                        message: '阈最大值不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]*$',
		                        message: '阈最大值必须是整数'
		                    },
		                    callback: {
		                    	message: '阈最大值必须大于最小值',
		                    	callback: function (value, validator, $field) {
	                                 if(parseFloat($('[name=threshold_min_value]').val()) >= value){
	                                	 return false;
	                                 }
	                                 return true;
	                            }
		                    }
		                }
		            },
		            cash_per: {
		                validators: {
		                    notEmpty: {
		                        message: '返现系数不能为空'
		                    },
		                    regexp: {
		                        regexp: '^([0-9])|([1-9]\d+)\.\d?$',
		                        message: '返现系数必须是数字'
		                    }
		                }
		            },
		            status: {
		                validators: {
		                    notEmpty: {
		                        message: '状态不能为空'
		                    }
		                }
		            },
		            level: {
		                validators: {
		                    notEmpty: {
		                        message: '优先级不能为空'
		                    }
		                }
		            },
		            start_date_after: {
		                validators: {
		                    notEmpty: {
		                        message: '生效日期不能为空'
		                    }
		                },
		                trigger: 'change'
		            },
		            start_date_before: {
		                validators: {
		                    notEmpty: {
		                        message: '失效日期不能为空'
		                    },
		                    callback: {
		                    	message: '失效日期必须大于生效日期',
		                    	callback: function (value, validator, $field) {
		                    		return compareDate(value, $('[name=start_date_after]').val());
	                            }
		                    }
		                },
		                trigger: 'change'
		            }
		        }
		    });
			
		    $('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true,
				language: 'cn'
			}).next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
		    
		function save(){
			/*手动验证表单，当是普通按钮时。*/
			$('#cashbackform').data('bootstrapValidator').validate();
			if(!$('#cashbackform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var options ={   
		            url:'../web/sysparam/saveCashback',   
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
		            	$("#modal-table").modal("show");
						 if($("#retCode").val() != "100"){
			            	 //init();	
			          }
						
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#cashbackform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '../web/sysparam/cashbackList');
		}
		