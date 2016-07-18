$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
var listOptions ={   
            url:'../web/driver/driverInfoList',
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
	
	function review(obj,type){
		var driverid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/driver/review?driverid='+driverid+'&type='+type,   
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
		loadPage('#main', '../web/driver/driverInfoList');
	}
	
	function change(obj,status,cardno){
		var accountid = $(obj).parents("tr").find("td[id=sysUserAccountId]").text();
		$.ajax({
			   type: "POST",
			   url:'../web/driver/changeDriverStatus?accountid='+accountid+'&status='+status+'&cardno='+cardno,   
	           contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	           dataType:'text',
	           async:false,
	           success:function(data){
	        	   $("#main").html(data);
		           $("#modal-table").modal("show");
	           }
			});
	}