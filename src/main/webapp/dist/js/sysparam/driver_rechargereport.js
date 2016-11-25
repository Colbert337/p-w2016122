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


	var  userName;
	var mobilePhone;
	var orderNumber;
	var is_discharge;
	var startDate;
	var endDate;
	var  pageNumber;
	var  pageSize;
	var orderByss;
	function showDetail(order_id,order_type,cash,userNames,mobilePhones,
						orderNumbers,is_discharges,startDates,endDates,pageNumbers,pageSizes,orderBys){
		userName=userNames;
		mobilePhone=mobilePhones;
		orderNumber=orderNumbers;
		is_discharge=is_discharges;
		startDate=startDates;
		endDate=endDates;
		pageNumber=pageNumbers;
		pageSize=pageSizes;
		orderByss=orderBys;
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

