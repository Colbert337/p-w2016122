	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	
	jQuery(function($) {
		var $overflow = '';
		var colorbox_params = {
			rel: 'colorbox',
			reposition:true,
			scalePhotos:true,
			scrolling:false,
			previous:'<i class="ace-icon fa fa-arrow-left"></i>',
			next:'<i class="ace-icon fa fa-arrow-right"></i>',
			close:'&times;',
			current:'{current} of {total}',
			maxWidth:'100%',
			maxHeight:'100%',
			onOpen:function(){
				$overflow = document.body.style.overflow;
				document.body.style.overflow = 'hidden';
			},
			onClosed:function(){
				document.body.style.overflow = $overflow;
				$('[data-rel="tooltip"]').tooltip('hide');
			},
			onComplete:function(){
				$.colorbox.resize();
			}
		};
	
		$('.gastation-log-colorbox').colorbox(colorbox_params);

		$("#cboxLoadingGraphic").html("<i class='ace-icon fa fa-spinner orange fa-spin'></i>");//let's add a custom loading icon
		
		
		$(document).one('ajaxloadstart.page', function(e) {
			$('#colorbox, #cboxOverlay').remove();
	   });
	})
	
	var listOptions ={   
		url:'../web/transportion/depositList',   
	    type:'post',                    
	    dataType:'html',
	    success:function(data){
	    	$("#main").html(data);
		    if($("#retCode").val() != "100"){
			   //$("#modal-table").modal("show");
			}
			$('[data-rel="tooltip"]').tooltip();
	    },error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
		}
	}
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formcard").ajaxSubmit(listOptions);
	}
	
	function showOrderLog(obj){
		var orderNumber = $(obj).parents('tr').find("td:first").find("input").val();
		commitForm1('', orderNumber);
	}
	
	function commitForm1(obj,orderNumber){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		var listOptions ={   
	            url:'../web/order/queryOrderDeal2?order_number='+orderNumber,
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
		$("#formcard").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/transportion/depositList');
	}

	//导出报表
	function importReport(){
		$("#formcard").submit();
	}
