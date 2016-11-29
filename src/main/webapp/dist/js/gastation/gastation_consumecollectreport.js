	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	window.onload = setCurrentPage();
	var listOptions ={
		url:'../web/gastation/gastationConsumeReport',
		type:'post',
		dataType:'html',
		beforeSend: function () {
			$("#importAction").hide();
			$('body').addClass('modal-open').css('padding-right','17px')
			$('body').append('<div class="loading-warp"><div class="loading"><i class="ace-icon fa fa-spinner fa-spin"></i></div><div class="modal-backdrop fade in"></div></div>')
		},
		success:function(data){
			$("#main").html(data);
			if($("#retCode").val() != "100"){

			}
			$('[data-rel="tooltip"]').tooltip();
		},complete: function () {
			$("body").removeClass('modal-open').removeAttr('style');
			$(".loading-warp").remove();
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
		loadPage('#main', '../web/gastation/gastationConsumeReport');
	}


	//导出报表
	function importReport(){
		$("#formgastation").submit();
	}

	//首次载入页面只查询当前月数据
	$(document).ready(function() {
		if($('#startDate').val()==''||$('#endDate').val()==''){
			var arry=new Array();
			arry =getMonthFirstDayToEndDate().split('||');
			$('#startDate').val(arry[0]);
			$('#endDate').val(arry[1]);
			commitForm();
		}
	});