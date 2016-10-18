
	var listOptions ={   
            url:'../web/coupon/couponList',
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
	
	function preUpdate(coupon_id){
		loadPage('#main', '../web/coupon/modifyCoupon?coupon_id='+coupon_id);
	}
	function deleteCoupon(coupon_id){
		bootbox.setLocale("zh_CN");
		bootbox.confirm("您确认要删除该优惠卷吗？", function (result) {
			if (result) {
				loadPage('#main', '../web/coupon/deleteCoupon?coupon_id='+coupon_id);
			}
		})
	}
	function importuserCoupon(coupon_id){
		loadPage('#main', '../web/coupon/importUserCoupon?coupon_id='+coupon_id);
	}
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formCoupon").ajaxSubmit(listOptions);
	}
	
	function init(){
		loadPage('#main', '../web/coupon/couponList');
	}

	function showUserCoupon(coupon_id){
		loadPage('#content', '../web/coupon/showUserCoupon?coupon_id='+coupon_id);
		$("#userCouponModel").modal('show');
	}
	
	function closeDialog(obj){
		$("#" + obj).modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
	}

