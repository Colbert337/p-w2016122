			//bootstrap验证控件
		    
			
/*		    $('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true,
				language: 'cn'
			}).next().on(ace.click_event, function(){
				$(this).prev().focus();
			});*/
		    
		function save(){
			/*手动验证表单，当是普通按钮时。*/
			$('#cashbackform').data('bootstrapValidator').validate();
			if(!$('#cashbackform').data('bootstrapValidator').isValid()){
				return ;
			}
			  if ($("#sys_cash_back_no").val() != "203"
					&& $("#sys_cash_back_no").val() != "202"
					&& $("#sys_cash_back_no").val() != "201") {
					if($('#input3').val()*1>100){
						bootbox.alert('返现系数不能大于100');
						return ;
					}
			  }
			  
			
			var options ={   
		            url:'../web/sysparam/saveCashback',   
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
						$("#modal-table").modal("show");
						 if($("#retCode").val() != "100"){
			            	 //$("#modal-table").modal("show");
			            	 //init();	
			          }
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#cashbackform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '../web/sysparam/cashbackList?sys_cash_back_no='+$("[name=sys_cash_back_no]").val());
		}
		
			function init(){
				loadPage('#main', '../webpage/poms/system/system_cashback_update.jsp');
			}
