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
            url:'../web/sysparam/operation/operationLogList',
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

	function init(){
		loadPage('#main', '../web/sysparam/operation/operationLogList');
	}
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		$("#sysOperationLog").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/sysparam/operation/operationLogList');
	}
