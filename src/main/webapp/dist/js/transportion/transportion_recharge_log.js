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
	            url:'../web/transportion/list/recharge',
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
		$("[name=operatorSourceType]").val("");
		$("[name=operatorTargetType]").val("");
		$("[name=deal_number]").val("");
		commitForm();
		//loadPage('#main', '../crmGasPriceService/queryProductPriceList');
	}
	
