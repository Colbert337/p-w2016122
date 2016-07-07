	var listOptions ={   
            url:'../web/usysparam/queryAll',
            type:'post',                    
            dataType:'text',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
		            	 //$("#modal-table").modal("show");
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
           
	       }
	}
	
	window.onload = setCurrentPage();
	
	function preUpdate(obj){
		var gcode = $(obj).parents('tr').children("td").eq(1).text();
		var mcode = $(obj).parents('tr').children("td").eq(2).text();
		
		loadPage('#main', '../web/usysparam/preUpdate?gcodevalue='+gcode+'&mcodevalue='+mcode);
	}
	
	function commitForm(obj){		
		$("#formusysparam").ajaxSubmit(listOptions);
	}
	
	function delUsysparam(obj){
		var gcode = $(obj).parents('tr').children("td").eq(1).text();
		var mcode = $(obj).parents('tr').children("td").eq(2).text();
		
		var deloptions ={   
	            url:'../web/usysparam/deleteUsysparam?gcodevalue='+gcode+'&mcodevalue='+mcode,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		             $("#main").html(data);
		             $("#modal-table").modal("show");
		             if($("#retCode").val() != "100"){
		            	 
		             }
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
					
	            }
		}
		
		bootbox.setLocale("zh_CN");
		bootbox.confirm("是否确认删除", function (result) {
			if(result){
				$("#formusysparam").ajaxSubmit(deloptions);
			}
		});
	}
	
	function init(){
		loadPage('#main', '../web/usysparam/queryAll');
	}