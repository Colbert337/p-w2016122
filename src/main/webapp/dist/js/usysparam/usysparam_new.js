	function save(){
		var options ={   
		        url:'../web/usysparam/saveUsysparam?operation=',   
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
		$("#formusysparam").ajaxSubmit(options);
	}
		
	function returnpage(){
		loadPage('#main', '../web/usysparam/queryAll');
	}			    
		