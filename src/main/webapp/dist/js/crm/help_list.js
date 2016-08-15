//重置
function init(){
        loadPage('#main', '../web/crm/help/list');
    } 
    
//编辑
function preUpdate(crmHelpId){
        loadPage('#main', '../web/crm/help/edit?crmHelpIdvalue='+crmHelpId);
    }

window.onload = setCurrentPage();
//问题类型查询
function commitForm(obj){
	if(typeof obj == "undefined") {
		$("#pageNum").val("1");
	}else{
		$("#pageNum").val($(obj).text());
	}
	$("#listForm").ajaxSubmit(options);
}	
	var options ={ 
			url:'../web/crm/help/list', 
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);	             
            },error:function(XMLHttpRequest, textStatus, errorThrown) {}
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
}

function prepage(formid){
	//如果是第一页
	if(parseInt($("#pageNum").val()) <= 1){
		return ;
	}

	//设置当前页-1
	$("#pageNum").val(parseInt($("#pageNum").val())-1);

	$(formid).ajaxSubmit(options);
}

function nextpage(formid){
	//如果是最后一页
	if(parseInt($("#pageNum").val()) >= parseInt($("#pageNumMax").val())){
		return ;
	}
	//设置当前页+1
	$("#pageNum").val(parseInt($("#pageNum").val())+1);
	$(formid).ajaxSubmit(options);
}

 //时间插件   
$(function() {
   $("#datepicker").datepicker();
});