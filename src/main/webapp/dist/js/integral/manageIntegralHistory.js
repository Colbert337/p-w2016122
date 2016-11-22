$(function(){
	$(".number").bind("input propertychange", function () {
		if (isNaN(parseFloat($(this).val())) || parseFloat($(this).val()) <= 0){
			$(this).val("");
		}
	});
	//只能输入数字
	$(".number").bind("keydown", function (e) {
		var code = parseInt(e.keyCode);
		if (code >= 96 && code <= 105 || code >= 48 && code <= 57 || code == 8) {
			return true;
		} else {
			return false;
		}
	});
});
$('#created_date_after').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	startDate:"1970-01-01",
	endDate:"2037-12-31",
	format: "yyyy-mm-dd",
	dateFormat:"yyyy-mm-dd",
	pickerPosition: "bottom-left"
}).on("click",function(e){
	$("#created_date_after").datepicker("setEndDate", $("#created_date_before").val());
});

$('#created_date_before').datepicker({
	autoclose: true,
	todayHighlight: true,
	language: "cn",
	weekStart: 1,
	startDate:"1970-01-01",
	endDate:"2037-12-31",
	format: "yyyy-mm-dd",
	dateFormat:"yyyy-mm-dd",
	pickerPosition: "bottom-left"
}).on("click", function (e) {
	$("#created_date_before").datepicker("setStartDate", $("#created_date_after").val());
});
	var listOptions ={   
            url:'../web/integralHistory/integralHistoryList',
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
            
	       }
	}
	
	window.onload = setCurrentPage();


	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#integralHistoryform").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/integralHistory/integralHistoryList');
	}
