	function save(){
		var options ={   
		        url:'../web/chargeCard/save',   
		        type:'post',                    
		        dataType:'text',
		        success:function(data){
		            $("#main").html(data);
		            //$("#modal-table").modal("show");
		            alert("保存成功");
		         },error:function(XMLHttpRequest, textStatus, errorThrown) {
		            	
		 	     }
			}	
		$("#formcharge").ajaxSubmit(options);
	}
		
	function returnpage(){
		loadPage('#main', '../web/chargeCard/chargeCardList');
	}			    
		