	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();

	var listOptions ={
		url:'../web/tcms/fleetQuota/list/quota',
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

		$("#formtransportion").ajaxSubmit(listOptions);
	}
	
	function init(){
		$("[name=startDate]").val("");
		$("[name=endDate]").val("");
		$("[name=fleetName]").val("");
		commitForm();
	}

	//导出报表
	function importReport(){
		$("#formtransportion").submit();
	}