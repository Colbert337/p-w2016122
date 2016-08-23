	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	var listOptions ={   
            url:'../web/message/messageList',
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
	            	  $("#modal-table").modal("show");
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
            
	       }
	}
	
	window.onload = setCurrentPage();
	
	function del(obj){
		
		var tmp = confirm("是否删除该条数据?");
		
		if (!tmp) {
			$('[data-rel=tooltip]').tooltip('hide');
			return;
		}
		
		var messageid = $(obj).parents("tr").find("td:first").find("input").val();
		loadPage('#main', '../web/message/deleteMessage?messageid='+messageid);
	}
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formessage").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/message/messageList');
	}