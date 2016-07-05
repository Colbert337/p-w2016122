	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	var listOptions ={   
            url:'../web/card/cardLogList',   
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
		
		$("#formcard").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/card/cardLogList');
	}