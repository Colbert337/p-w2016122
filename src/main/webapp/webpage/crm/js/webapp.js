$(function(){

	$('.station-special-more').on('click', function(){
		if($('.station-special').hasClass('more')) {
			return true;
		} else {
			$('.station-special').addClass('more');
		}
	});

	// 手机号码验证    
	jQuery.validator.addMethod("isMobile", function(value, element) {    
	  var length = value.length;    
	  return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
	}, "请正确填写您的手机号码。");

	// 只能输入[0-9]数字
	jQuery.validator.addMethod("isDigits", function(value, element) {       
	     return this.optional(element) || /^\d+$/.test(value);       
	}, "只能输入0-9数字"); 

    //提交表单验证
    if($("#shareInviteCode").length){
		$("#shareInviteCode").validate({
			//debug: true, //调试模式取消submit的默认提交功能    
			submitHandler: function(form) { //表单提交句柄,为一回调函数，带一个参数：form   
				alert("提交表单");
				form.submit(); //提交表单   
			},
			rules: {
				phone: {
					isMobile:true
				},
				vcode: {
					isDigits:true,
					rangelength: [6, 6]
				}
			},
			//如果验证控件没有message，将调用默认的信息
			messages: {
				phone: {
					required: "请填写您的手机号码。",
					isMobile:"请正确填写您的手机号码。"
				},
				vcode: {
					required: "请填写验证码。",
					rangelength: "请填写6位数字验证码。",
					isMobile:"请正确填写验证码。"
				}
			}
		});
	}

    if($("#appIntroFrom").length){
		$("#appIntroFrom").validate({
			//debug: true, //调试模式取消submit的默认提交功能    
			submitHandler: function(form) { //表单提交句柄,为一回调函数，带一个参数：form   
				alert("提交表单");
				form.submit(); //提交表单   
			},
			rules: {
				title: {
					required: true
				},
	            info: {
	                required: true
	            }
			},
			messages: {
				title: {
					required: "请填写Email/QQ/微信/电话..."
				},
	            info: {
	                required: "请填写反馈内容..."
	            }
			}
		});
	}

	//单页切换
	if($('#download-fullpage').length){
		$('#download-fullpage').fullpage({
			sectionsColor: ['#fff', '#d2d2d2'],
			anchors: ['download', 'intro'],
			menu: '#menu'
	    });
	}

	$('.logic-download-app').on('click',function(){
		var $help = $('.download-app-help');
		$help.show();
		setTimeout(function(){
			$help.hide();
		},3000)
	});
});