	var listOptions ={   
            url:'<%=basePath%>/web/driver/driverList',
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
		            	 //$("#modal-table").modal("show");
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
            
	       }
	}
	
	window.onload = setCurrentPage();
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formdriver").ajaxSubmit(listOptions);
	}
	
	function del(obj){
		var cardid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/driver/deleteGastation?gastationid='+cardid,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		             $("#main").html(data);
		             $("#modal-table").modal("show");
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	            }
		}
		
		$("#formdriver").ajaxSubmit(deloptions);
	}
	
	function init(){
		loadPage('#main', '../web/gastation/gastationList');
	}