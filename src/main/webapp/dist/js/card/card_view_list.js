    //编辑
	function preUpdate(obj){		
		var ownid = $(obj).parents('tr').children("td").eq(1).text();		
		loadPage('#main', '../web/chargeCard/update?ownidvalue='+ownid);
	}
	
	//查询
	function commitForm(obj){
	    var listOptions ={
		    url:'../web/chargeCard/query',
		    type:'post',
		    dataType:'html',
		    success:function(data){
			   $("#main").html(data);
		  }
	   }
	$("#listForm").ajaxSubmit(listOptions);	
    }
	
	//重置
	function init(){
		loadPage('#main', '../web/chargeCard/chargeCardList');
	}	