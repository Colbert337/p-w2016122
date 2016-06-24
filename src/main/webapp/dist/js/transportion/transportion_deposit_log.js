	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	
	function commitForm(obj){
		
		var listOptions ={   
	            url:'../web/transportion/depositList',   
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
		
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formcard").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/transportion/depositList');
	}