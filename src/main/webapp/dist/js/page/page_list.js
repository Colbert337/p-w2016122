	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	var listOptions ={   
            url:'../web/page/pageList',
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
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formpage").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/page/pageList');
	}
	
	function preview(obj){
		if(obj != null && obj != ''){
			
			$("#pageHtml").val($(obj).parents("tr").find("td").eq("6").text());
			$("#pageTitle").val($(obj).parents("tr").find("td").eq("2").text());
			$("#pageBody").val($(obj).parents("tr").find("td").eq("3").text());
			$("#pageTicker").val($(obj).parents("tr").find("td").eq("4").text());
			$("#page_created_time").val($(obj).parents("tr").find("td").eq("5").text());

			$("#previewForm").submit();
		}
	}
	
	function openSpecfiyWindown(windowName){
	    window.open('about:blank',windowName,'width=700,height=400,menubar=no,scrollbars=no');   
	} 