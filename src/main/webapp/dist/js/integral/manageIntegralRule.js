
	var listOptions ={   
            url:'../web/integralRule/integralRuleList',
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
	
	function preUpdate(integral_rule_id){
		loadPage('#main', '../web/integralRule/modifyIntegralRule?integral_rule_id='+integral_rule_id);
	}
	function deleteIntegralRule(integral_rule_id){
		bootbox.setLocale("zh_CN");
		bootbox.confirm("您确认要删除该积分规则吗？", function (result) {
			if (result) {
				loadPage('#main', '../web/integralRule/deleteIntegralRule?integral_rule_id='+integral_rule_id);
			}
		})
	}
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#integralRuleForm").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/integralRule/integralRuleList');
	}
