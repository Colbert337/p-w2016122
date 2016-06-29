	
	//datepicker plugin
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		language: 'cn'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	
			//bootstrap验证控件		
		    $('#transportionform').bootstrapValidator({
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
		                        message: '运输公司保证金不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[-+]?[0-9]+(\.[0-9]+)?$',
		                        message: '运输公司保证必须是数字'
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
			$('#transportionform').data('bootstrapValidator').validate();
			if(!$('#transportionform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			$("#sub").confirm({
				title: "运输公司充值确认",
				text: "是否确认将 ["+$("#stationame").text()+"] 账户余额更新"+$("[name=deposit]").val()+"元",
				confirm: function (button) {
					var options ={   
				            url:'../web/transportion/deposiTransportion',   
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

					$("#transportionform").ajaxSubmit(options);
				},
				cancel: function (button) {

				},
				confirmButton: "确认",
				cancelButton: "取消"
			});
			
		}
		
		function returnpage(){
			loadPage('#main', '../web/transportion/transportionList');
		}
		
		function returnpage2(){
			loadPage('#main', '../web/transportion/transportionList2');
		}