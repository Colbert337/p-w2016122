	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/driver/queryRechargeDriverReport?page=个人司机',
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
	
	function showDetail(order_id,order_type,cash){
		$.ajax({
			type: "POST",
			url: '../web/driver/queryRechargeReportDetail?order_id='+order_id+'&order_type='+order_type+'&cash='+cash,
			dataType: 'text',
			success: function(data){
				$("#main").html(data);
				$('[data-rel="tooltip"]').tooltip();
			},
			error:function(){
				bootbox.alert('加载页面时出错！');
			}
		});
	}
	
	function init(){
		loadPage('#main', '../web/driver/queryRechargeDriverReport?page=个人司机');
	}


	//导出报表
	function importReport(){
		$("#formgastation").submit();
	}
