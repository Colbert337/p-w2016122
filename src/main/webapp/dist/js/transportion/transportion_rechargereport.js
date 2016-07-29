	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/transportion/queryRechargeReport',
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
		/*$.ajax({
			type: "POST",
			url: '../web/transportion/queryRechargeReportDetail?order_id='+order_id+'&order_type='+order_type+'&cash='+cash,
			dataType: 'text',
			success: function(data){
				$("#main").html(data);
				$('[data-rel="tooltip"]').tooltip();
			},
			error:function(){
				bootbox.alert('加载页面时出错！');
			}
		});*/
		var url = '../web/transportion/queryRechargeReportDetail?order_id='+order_id+'&order_type='+order_type+'&cash='+cash;
		ajaxQueryData('#main',url,'text');
	}
	
	function init(){
		loadPage('#main', '../web/transportion/queryRechargeReport');
	}


	//导出报表
	function importReport(){
		var cur = "${sessionScope.currUser.userId}";
		//alert(cur);
		$("#formgastation").submit();
	}
