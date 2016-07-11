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
	
	function resetPassword(obj){
		var userName = $(obj).parents("tr").children("td").eq(5).text();
		var station = $(obj).parents("tr").children("td").eq(1).text();
		
		bootbox.setLocale("zh_CN");
		bootbox.confirm("将重置该运输公司的管理员密码，是否确认?", function (result) {
			if (!result) {
				$('[data-rel=tooltip]').tooltip('hide');
				return;
			}else{
				$.ajax({
					   type: "POST",
					   url:'../web/transportion/resetPassword?gastationid='+station+'&username='+userName,
			           dataType:'text',
			           async:false,
			           success:function(data){
			        	   $("#main").html(data);
			        	   $("#modal-table").modal("show");
			           }
				});
			}
		})
	}