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
		loadPage('#main', '../web/driver/queryRechargeDriverReport');
	}


	//导出报表
	function importReport(){
		$("#formgastation").submit();
	}

	function backQuery(){

		$.ajax({
			type: "POST",
			url: '../web/driver/queryRechargeDriverReport',
			dataType: 'html',
			data:{'sysDriver.userName':userName,
				'sysDriver.mobilePhone':mobilePhone,'orderNumber':orderNumber,'is_discharge':is_discharge,
				'startDate':startDate,'endDate':endDate,'pageNum':pageNumber,'pageSize':pageSize,'orderby':orderByss},
			success: function(msg){
				$("#main").html(msg);
				$('[data-rel="tooltip"]').tooltip();
			},error:function(XMLHttpRequest, textStatus, errorThrown) {
		}});
	}

