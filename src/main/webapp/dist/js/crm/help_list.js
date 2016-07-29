//重置
function init(){
        loadPage('#main', '../web/crm/help/list');
    } 
    
//编辑
function preUpdate(obj){        
        var crmHelpId = $(obj).parents('tr').children("td").eq(0).text();        
        loadPage('#main', '../web/crm/help/edit?crmHelpIdvalue='+crmHelpId);
    }
    
////查询
//function commitForm(obj){
//	   var value = $('#select option:selected').val(); 
//	   loadPage('#main', '../web/crm/help/list?selectval='+value);
//}
//查询
function commitForm(obj){	
	var options ={   
            url:'../web/crm/help/list',
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);	             
            },error:function(XMLHttpRequest, textStatus, errorThrown) {}
	}
	
	$("#listForm").ajaxSubmit(options);
}
//function commitForm(){
//	var strvalue=$("#select").val();
//	$.ajax({
//	url: "../web/crm/help/list?crmHelp="+strvalue,
//	type: "POST",
//	dataType: "html",
//	success: function (data) {
//		 $("#main").html(data);
//	}
//	});
//	}
//弹框
function addQuestion(){
	$("#helpModel").modal('show');		
}
//取消弹框
function closeDialog(divId){
	$("#helpModel").modal('hide');	
}

//保存
function save(){
        var options ={   
                url:'../web/crm/help/save',   
                type:'post',                    
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    alert("保存成功");
                 },error:function(XMLHttpRequest, textStatus, errorThrown) {
                        
                  }
            }    
        $("#formsave").ajaxSubmit(options);
        $("#helpModel").modal('hide');	
    }
