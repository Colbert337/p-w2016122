$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	var listOptions ={   
            url:'../web/transportion/transportionList',
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
	
	function preUpdate(obj){
		var stationid = $(obj).parents("tr").find("td:first").find("input").val();
		loadPage('#main', '../web/transportion/preUpdate?transportionid='+stationid);
	}
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formtransportion").ajaxSubmit(listOptions);
	}
	
	function commitForm2(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		var options ={   
	            url:'../web/transportion/transportionList2',
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
		
		$("#formtransportion").ajaxSubmit(options);
	}
	
	function del(obj){
		var cardid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/transportion/deletetransportion?transportionid='+cardid,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		             $("#main").html(data);
		             if($("#retCode").val() != "100"){
		            	 $("#modal-table").modal("show");
		             }
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
					
	            }
		}
		
		$("#formtransportion").ajaxSubmit(deloptions);
	}
	
	function preDeposit(obj){
		var stationid = $(obj).parents("tr").find("td:first").find("input").val();
		var stationame = $(obj).parents("tr").children("td").eq(2).text();
		var stationdeposit = $(obj).parents("tr").children("td").eq(10).text();
		var acconutid = $(obj).parents("tr").children("td").eq(11).text();
		
		loadPage('#main', '../webpage/poms/transportion/transportion_deposit.jsp?acconutid='+acconutid+'&stationame='+stationame+'&stationdeposit='+stationdeposit+'&stationid='+stationid);
	}
	
	function init(){
		loadPage('#main', '../web/transportion/transportionList');
	}
	
	function init2(){
		loadPage('#main', '../web/transportion/transportionList2');
	}