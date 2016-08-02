	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	window.onload = setCurrentPage();
	
	function commitForm(obj,product_id){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		var listOptions ={   
	            url:'../crmGasPriceService/queryAllGasPriceList',
	            type:'post',                    
	            dataType:'html',
	            success:function(data){
		              	$("#main").html(data);
	            		//$("#innerModel").html(data);
		              if(typeof product_id != "undefined") {
		            	  $("#innerModel").modal("show");
		              }
		              
		              if($("#retCode").val() != "100"){
		            	  
			          }
					$('[data-rel="tooltip"]').tooltip();
	            },error:function(XMLHttpRequest, textStatus, errorThrown) {

	            }
		}
		
		$("#formgastation").ajaxSubmit(listOptions);
	}
	
	function commitForm1(obj,product_id){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		var listOptions ={   
	            url:'../crmGasPriceService/queryProductPriceList?product_id='+product_id,
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
		loadPage('#main', '../crmInterface/crmGasPriceService/queryAllGasPriceList?product_id=');
	}
	
	function showPriceLog(obj){
		var priceid = $(obj).parents('tr').find("td:first").find("input").val();
		
		/*$.ajax({
			   type: "POST",
			   url:'../crmGasPriceService/queryAllProductPriceList',
	           dataType:'text',
	           async:false,
	           success:function(data){
	        	   $("#innerModel").html(data);
	           }
		});*/
		commitForm1('', priceid);
		
	}
	
	function hideInnerModel(){
		$("#innerModel").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
	}