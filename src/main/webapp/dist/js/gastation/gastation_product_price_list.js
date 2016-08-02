	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	
	function commitForm(obj){
		
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		var listOptions ={   
	            url:'../crmInterface/crmGasPriceService/queryProductPriceList',
	            type:'post',                    
	            dataType:'html',
	            success:function(data){
		              $("#main").html(data);
		              if($("#retCode").val() != "100"){
		            	  
			          }
		              $('[data-rel="tooltip"]').tooltip();
	            },error:function(XMLHttpRequest, textStatus, errorThrown) {

	            }
		}
		
		$("#formgastation").ajaxSubmit(listOptions);
	}
	
	function init(){
		$("[name=created_time_after]").val("");
		$("[name=created_time_before]").val("");
		commitForm();
		//loadPage('#main', '../crmGasPriceService/queryProductPriceList');
	}
	
	function returnpage(){
		loadPage('#main', '../crmInterface/crmGasPriceService/queryAllGasPriceList');
	}
	
	function hideInnerModel(){
		$("#innerModel").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
	}