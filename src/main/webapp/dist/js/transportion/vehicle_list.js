$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
var obj,status;

	var listOptions ={   
            url:'../web/transportion/Vehiclelist',
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
	
	function updateStatus(){
		var cardno = $(obj).parents('tr').children("td").eq(8).text();
		
		var deloptions ={   
	            url:'../web/card/updateCardStatus?cardno='+cardno+'&cardstatus='+status+'&memo='+$("[name=remark]").val(),   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
	            	if(data >0){
	            		if(status == "0"){
	            			msg = "冻结";
	            		}else{
	            			msg = "解冻"
	            		}
	            		bootbox.alert("["+cardno+"]已"+msg);
	            	}
	            	$('[data-rel="tooltip"]').tooltip();
	            	commitForm();
		             /*$("#main").html(data);
		             if($("#retCode").val() != "100"){
		            	 $("#modal-table").modal("show");
		             }*/
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
					
	            }
		}
		
		$("#formtransportion").ajaxSubmit(deloptions);
	}
	
	function changeCard(){
		var tcVehicleId = $(obj).parents("tr").find("td:first").find("input").val();
		var newcardno = $("[name=newcardno]").val();
		
		if(newcardno == ""){
			if($("#tmpclass").length == '0'){
				$("[name=newcardno]").after("<div id='tmpclass' class='user-name-valid red'>卡号不能为空</div>");
			}
			
			return false;
		}
		
		var deloptions ={   
	            url:'../web/tcms/vehicle/changeCard?tc_vehicle_id='+tcVehicleId+'&newcardno='+newcardno+'&memo='+$("[name=remark2]").val(),   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
	            	if(data == "1"){
	            		bootbox.alert("["+tcVehicleId+"]已更改实体卡号为"+newcardno);
	            	}else{
	            		bootbox.alert(data);
	            	}
	            	$('[data-rel="tooltip"]').tooltip();
	            	commitForm();
		             /*$("#main").html(data);
		             if($("#retCode").val() != "100"){
		            	 $("#modal-table").modal("show");
		             }*/
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
					
	            }
		}
		
		$("#formtransportion").ajaxSubmit(deloptions);
		hideInnerModel();
	}
	
	function showInnerModel2(obj){
		this.obj = obj;
		
		$("#innerModel2").modal('show');
	}
	
	function showInnerModel(obj,status){
		this.obj = obj;
		this.status = status;
		
		$("#innerModel").modal('show');
	}
	
	function hideInnerModel(){
		$("#innerModel").modal('hide').removeClass('in');
		$("#innerModel2").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
		$("#tmpclass").remove();
	}
	
	function addMemo(){
		updateStatus(this.obj, this.status);
		hideInnerModel();
	}
	
	function init(){
		loadPage('#main', '../web/transportion/Vehiclelist');
	}
