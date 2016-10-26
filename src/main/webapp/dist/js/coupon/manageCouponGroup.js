
	var listOptions ={   
            url:'../web/couponGroup/couponGroupList',
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
	
	function preUpdate(coupongroup_id){
		loadPage('#main', '../web/couponGroup/modifyCouponGroup?coupongroup_id='+coupongroup_id);
	}
	function deleteCoupon(coupongroup_id){
		bootbox.setLocale("zh_CN");
		bootbox.confirm("您确认要删除该优惠卷组吗？", function (result) {
			if (result) {
				loadPage('#main', '../web/couponGroup/deleteCouponGroup?coupongroup_id='+coupongroup_id);
			}
		})
	}
	function importUserCouponGroup(coupongroup_id){
		loadPage('#main', '../web/couponGroup/importUserCouponGroup?coupongroup_id='+coupongroup_id);
	}
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formCouponGroup").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/couponGroup/couponGroupList');
	}
	
	function closeDialog(obj){
		$("#" + obj).modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
	}

