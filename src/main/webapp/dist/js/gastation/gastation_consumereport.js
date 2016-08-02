	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/gastation/queryConsumeReport',
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

	function commitForm(obj){
		
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formgastation").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/gastation/queryConsumeReport');
	}
	
	function showDetail(order_id,order_type,cash){
		var url = '../web/gastation/queryConsumeReportDetail?order_id='+order_id+'&order_type='+order_type+'&cash='+cash;
		ajaxQueryData('#main',url,'text');
	}


	//导出报表
	function importReport(){
		$("#formgastation").submit();
	}
