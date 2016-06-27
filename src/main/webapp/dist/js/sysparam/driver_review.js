$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
var listOptions ={   
            url:'../web/driver/driverList',
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
	
	function review(driverid,type,memo){
				
		var deloptions ={   
	            url:'../web/driver/review?driverid='+driverid+'&type='+type+'&memo='+memo,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		            $("#main").html(data);
		            $("#modal-table").modal("show");
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	            }
		}
		$("#gridSystemModalLabel").text("输入审核备注");
		$("#formdriver").ajaxSubmit(deloptions);
	}
	
	function init(){
		loadPage('#main', '../web/driver/driverList');
	}
	
	function showInnerModel(obj,type){
		
		if(type==null ||type==''){
			$("#optionbutton").css('display','none');
		}else{
			$("#optionbutton").css('display','block');
		}
		
		var driverid = $(obj).parents('tr').find("td:first").find("input").val();
		$("#objid").val(driverid);
		$("#objval").val(type);
		$("[name=remark]").val($(obj).parents('tr').children("td").eq(10).text());
		$("#innerModel").modal('show');
	}
	
	function hideInnerModel(){
		$("#innerModel").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
	}
	
	function addMemo(){
		review($("#objid").val(),$("#objval").val(),$("[name=remark]").val());
		hideInnerModel();
	}