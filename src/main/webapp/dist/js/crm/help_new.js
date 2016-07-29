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
        $("#formnew").ajaxSubmit(options);
    }

//重置
function returnpage(){
    	loadPage('#main', '../web/crm/help/list');
    }                

