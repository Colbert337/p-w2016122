		//bootstrap验证控件
		    
			
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
			if (tyep==1) {
				bootbox.alert("该优先级已有数据 请重新选择优先级");
   			   
   			 	return;
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
		var tyep=0;
		function gainProp(obj){
			$.ajax({
				   type: "POST",
				   url:'../web/sysparam/gainProp?sys_cash_back_no='+$("[name=sys_cash_back_no]").val()+'&level='+$(obj).val(),   
		           dataType:'text',
		           success:function(data){ 
		        	   var s = JSON.parse(data);
		        	 //  alert($("#sys_cash_back_no").val());
		        	   if(s.length == 1){
		        		   if ($("#sys_cash_back_no").val()=="203"||$("#sys_cash_back_no").val()=="202"||$("#sys_cash_back_no").val()=="201") {
		        			   bootbox.alert("该优先级已有数据 请重新选择优先级");
		        			   tyep=0;
		        			   return;
							}else{
				        	   $("[name=threshold_min_value]").val(s[0].threshold_max_value);
				        	   $("[name=start_date_after]").val(s[0].start_date.substr(0,10));
				        	   $("[name=start_date_before]").val(s[0].end_date.substr(0,10));
				        	   $("[name=start_date_after]").removeClass("date-picker").off();
				        	   $("[name=start_date_before]").removeClass("date-picker").off();
				        	   tyep=1;
							}
		        	   }else{
		        		   if ($("#sys_cash_back_no").val()=="203"||$("#sys_cash_back_no").val()=="202"||$("#sys_cash_back_no").val()=="201") {
							
						}else{
		        		   $("[name=threshold_min_value]").val("0");
						}
		        	   }
		            }
				});
		}
		