		//bootstrap验证控件
		    $('#cashbackform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
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
		                    }/*,
		                    callback: {
		                    	message: '生效日期必须大于等于当前日期',
		                    	callback: function (value, validator, $field) {
		                    		return compareDate(value ,new Date().toLocaleDateString());
	                            }
		                    }*/
		                },
		                trigger: 'change'
		            },
		            start_date_before: {
		                validators: { 
		                    notEmpty: {
		                        message: '失效日期不能为空'
		                    },
		                    callback: {
		                    	callback: function (value, validator, $field) {
		                    		if(!compareDate(value ,new Date().toLocaleDateString())){
		                    			var txt = $('.start-date-before small[data-bv-validator="callback"]').text('失效日期必须大于等于当前日期');

		                    			return false;
		                    		}     		
		                    		return !compareDate($('[name=start_date_after]').val(), value);
	                            },
	                            message: '失效日期必须大于等于生效日期'
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
			            	// init();	
			          }
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#cashbackform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '../web/sysparam/cashbackList?sys_cash_back_no='+$("[name=sys_cash_back_no]").val());
		}
		
		function gainProp(obj){
			$.ajax({
				   type: "POST",
				   url:'../web/sysparam/gainProp?sys_cash_back_no='+$("[name=sys_cash_back_no]").val()+'&level='+$(obj).val(),   
		           dataType:'text',
		           success:function(data){ 
		        	   var s = JSON.parse(data);
		        	   if(s.length == 1){
			        	   $("[name=threshold_min_value]").val(s[0].threshold_max_value);
			        	   $("[name=start_date_after]").val(s[0].start_date.substr(0,10));
			        	   $("[name=start_date_before]").val(s[0].end_date.substr(0,10));
			        	   $("[name=start_date_after]").removeClass("date-picker").off();
			        	   $("[name=start_date_before]").removeClass("date-picker").off();
		        	   }else{
		        		   $("[name=threshold_min_value]").val("0");
		        	   }
		            }
				});
		}
		