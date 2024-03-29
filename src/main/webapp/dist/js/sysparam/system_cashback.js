
$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	$.ajax({
	    type: "POST",
	    url:'../web/usysparam/query?gcode=CASHBACK&scode=',   
	    contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	    dataType:'text',
	    async:false,
	    success:function(data){
	    		if(data != ""){
					var s = JSON.parse(data);
		        	   for(var i=0;i<s.length;i++){
		        		   $("#cashbackol").append("<li class='dd-item dd2-item' data-id='14' onclick='choose(this);' value='"+s[i].mcode+"'><div class='dd-handle dd2-handle'><i class='normal-icon ace-icon fa fa-clock-o pink bigger-130'></i><i class='drag-icon ace-icon fa fa-arrows bigger-125'></i></div><div class='dd2-content'>"+s[i].mname+"</div></li>");
						   $('.dd-item.dd2-item').each(function(){
							   if($("[name=sys_cash_back_no]").val()==$(this).val()){
								   $(this).find('>div').addClass('btn-info');
							   }
						   });
		        	    }
	    		}
	     }
	});

$('[data-rel="tooltip"]').tooltip();

function commitForm(){
	//设置当前页的值
	if(typeof obj == "undefined") {
		$("#pageNum").val("1");
	}else{
		$("#pageNum").val($(obj).text());
	}

	var listOptions ={
			url:'../web/sysparam/cashbackList',
			type:'post',
			dataType:'html',
			success:function(data){
				  $("#main").html(data);
			},error:function(XMLHttpRequest, textStatus, errorThrown) {

		   }
	}

	$("#formcashback").ajaxSubmit(listOptions);
}

function init(){
	loadPage('#main', '../web/sysparam/cashbackList?sys_cash_back_no='+$("[name=sys_cash_back_no]").val());
}

/**
 * 返现设置
 */
function choose(obj){
	$("[name=sys_cash_back_no]").val($(obj).val());
	//loadPage('#main', '../web/sysparam/cashbackList');
	commitForm();
}

function preUpdate(obj){
	var sys_cash_back_id = $(obj).parents("tr").find("td:first").find("input").val();
	loadPage('#main', '../web/sysparam/preUpdate?sysCashBackid='+sys_cash_back_id);
}

function del(obj){
	
	var sys_cash_back_no = $(obj).parents('tr').find("td:eq(1)").text().substr(0,3);
	var level = $(obj).parents('tr').find("td:eq(6)").find("input").val();
	
	bootbox.setLocale("zh_CN");
	bootbox.confirm("将删除该级别的所有配置项，是否删除?", function (result) {
		if (result) {
			var deloptions ={   
		            url:'../web/sysparam/deleteCashBack?sysCashBackNo='+sys_cash_back_no+'&leveltmp='+level+'',
		            type:'post',                    
		            success:function(data){
			             $("#main").html(data);
			             $("#modal-table").modal("show");
		            },
		            error:function(XMLHttpRequest, textStatus, errorThrown) {
						
		            }
			}
			
			$("#formcashback").ajaxSubmit(deloptions);
			
		}else{
			$('[data-rel=tooltip]').tooltip('hide');
			return;
		}
	})
	
	
}

$("#newbutton").on('click', function(){
	loadPage('#main','../webpage/poms/system/system_cashback_new.jsp?sys_cash_back_no='+$("[name=sys_cash_back_no]").val());
});



window.onload = setCurrentPage();