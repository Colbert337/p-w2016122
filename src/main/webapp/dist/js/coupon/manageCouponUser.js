
	var listOptions ={   
            url:'../web/coupon/couponUserList',
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
		loadPage('#main', '../web/coupon/couponUserList');
	}
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		$("#userCoupon").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/coupon/couponUserList');
	}
