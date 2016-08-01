//修改  
function save(){
        var options ={   
                url:'../web/crm/help/update',   
                type:'post',                    
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    alert("修改成功");
                 },error:function(XMLHttpRequest, textStatus, errorThrown) {
                        
                  }
            }    
        $("#formedit").ajaxSubmit(options);
    }

//重置
function returnpage(){
        loadPage('#main', '../web/crm/help/list');
    }                
       
