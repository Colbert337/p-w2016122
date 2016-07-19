	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/tcms/fleetQuota/list/report/fleetMg',
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
		$("[name=startDate]").val("");
		$("[name=endDate]").val("");
		$("[name=orderNumber]").val("");
		$("[name=is_discharge]").val("");
		commitForm();
		//loadPage('#main', '../crmGasPriceService/queryProductPriceList');
	}

	/*初始化选择菜单*/
	$(function(){
		$.ajax({
			url:"../web/tcms/fleet/list",
			data:{},
			async:false,
			type: "POST",
			success: function(data){
				$("#channel").empty();
				$("#channel").append("<option value=''>全部车队</option>");
				$.each(data,function(i,val){
					$("#channel").append("<option value='"+val.tcFleetId+"'>"+val.fleetName+"</option>");
				})
			}
		})
	})