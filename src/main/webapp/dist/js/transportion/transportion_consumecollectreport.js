	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/transportion/transportionConsumeReport',
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
		if(compareDateMonth($('#startDate').val(),$('#endDate').val())==0){
			alert('请输入查询时间，且查询时间范围不能超过31天');
			return false;
		}
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
		loadPage('#main', '../web/transportion/transportionConsumeReport');
	}


	//导出报表
	function importReport(){
		$("#formgastation").submit();
	}
