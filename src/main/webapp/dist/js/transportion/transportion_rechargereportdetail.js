	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/transportion/queryRechargeReportDetail',
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
		loadPage('#main', '../web/transportion/queryRechargeReport');
	}


	//导出报表
	function importReport(){
		$("#formgastation").submit();
	}


	function backQueryTransportionRecharge(){

		$.ajax({
			type: "POST",
			url: '../web/transportion/queryRechargeReport',
			dataType: 'html',
			data:{'creditAccount':creditAccount,
				'orderNumber':orderNumber,'sysDriver.plateNumber':plateNumber,
				'is_discharge':is_discharge,'orderNumber':orderNumber,'startDate':startDate,'endDate':endDate,'pageNum':pageNumber,'pageSize':pageSize,'transOrderbys':transOrderbys},
			success: function(msg){
				$("#main").html(msg);
				$('[data-rel="tooltip"]').tooltip();
			},error:function(XMLHttpRequest, textStatus, errorThrown) {
			}});
	}
