	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/gastation/queryRechargeReport',
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
		var page = $("[name=page]").val();
		loadPage('#main', '../web/gastation/queryRechargeReport?page='+page);
	}


	//导出报表
	function importReport(){
		var cur = "${sessionScope.currUser.userId}";
		//alert(cur);
		$("#formgastation").submit();
	}
