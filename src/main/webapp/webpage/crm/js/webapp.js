$(function(){

	$('.station-special-more').on('click', function(){
		if($('.station-special').hasClass('more')) {
			return true;
		} else {
			$('.station-special').addClass('more');
		}
	});

	/*if($(".form-validator").length){*/
		// 手机号码验证    
		jQuery.validator.addMethod("isMobile", function(value, element) {    
		  var length = value.length;
		  return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
		}, "请正确填写您的手机号码。");

		// 只能输入[0-9]数字
		jQuery.validator.addMethod("isDigits", function(value, element) {
		     return this.optional(element) || /^\d+$/.test(value);
		}, "只能输入0-9数字"); 
	//}
		// 验证手机号码是否已经存在
		jQuery.validator.addMethod("checkPhone", function(value, element){
			var result = false;
			// 设置同步
			$.ajaxSetup({
				async: false
			});
			var param = {
				phone: value
			};
			$.post("../../help/user/phone", param, function(data){
				result = ("true" == data);
				if(data == "false"){
					$("#isPhone").val("0");//标记手机号码无效
				}else{
					$("#isPhone").val("1");//标记手机号码有效
				}
			});
			// 恢复异步
			$.ajaxSetup({
				async: true
			});
			return result;
		}, "手机号码已存在");

		// 验证码是否有有效
		jQuery.validator.addMethod("checkCode", function(value, element){
			var result = false;
			var phone = $("#phone").val();
			// 设置同步
			$.ajaxSetup({
				async: false
			});
			var param = {
				vcode: value,phone:phone
			};
			$.post("../../help/user/checkCode", param, function(data){
				result = ("true" == data);
			});
			// 恢复异步
			$.ajaxSetup({
				async: true
			});
			return result;
		}, "验证码有误");

	//提交表单验证
    if($("#shareInviteCode").length){
		$("#shareInviteCode").validate({
			//debug: true, //调试模式取消submit的默认提交功能
			submitHandler: function(form) { //表单提交句柄,为一回调函数，带一个参数：form   
				/*alert("提交表单");*/
				form.submit(); //提交表单
			},
			rules: {
				phone: {
					isMobile:true,
					checkPhone:true
				},
				vcode: {
					isDigits:true,
					rangelength: [6, 6],
					checkCode:true
				}
			},
			//如果验证控件没有message，将调用默认的信息
			messages: {
				phone: {
					required: "请填写您的手机号码。",
					isMobile:"请正确填写您的手机号码。",
					checkPhone:"手机号码已存在。"
				},
				vcode: {
					required: "请填写验证码。",
					rangelength: "请填写6位数字验证码。",
					checkCode:"验证码有误。"
				}
			}
		});
	}

    if($("#appIntroFrom").length){
		$("#appIntroFrom").validate({
			//debug: true, //调试模式取消submit的默认提交功能    
			submitHandler: function(form) { //表单提交句柄,为一回调函数，带一个参数：form   
				//alert("提交表单");
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
    if($("#phoneTypeFrom").length){
    	$("#phoneTypeFrom").validate({
    		debug: true, //调试模式取消submit的默认提交功能    
    		submitHandler: function(form) { //表单提交句柄,为一回调函数，带一个参数：form   
    			form.submit(); //提交表单   
    		},
    		rules: {
                info: {
    				required: true
    			}
    		},
    		messages: {
                info: {
    				required: "请填写手机型号..."
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

	//是否在微信里面打开H5页面
	var $windowHeight = $(document).height();
	if(window.navigator.userAgent.match(/MicroMessenger/i) == "MicroMessenger") {
		$('.logic-download-app').on('click',function(event){
			event.preventDefault();

			var html = '';
			html += '<div class="download-app-help">'
			html += '<img class="img" src="images/download-app-help.png" alt="">'
			html += '<div class="transparent"></div>'
			html += '</div>'
			$('body').append(html);

			$('.transparent').height($(document).height());

			if($(window).scrollTop()>0) {
				$('body,html').animate({scrollTop:0},500);
			}
			
			setTimeout(function(){
				$('.download-app-help').remove();
			},3000);
		});
	}
});

//邀请用户发送验证码
var countdown=60;
//显示添加用户弹出层
function addDriver(){
	countdown = 0;
	var obj = $("#sendMsgA");
	obj.removeAttr("disabled");
	obj.attr("onclick","sendMessage()");
	obj.text("发送验证码");

	$("#driverModel").modal('show').on('hidden.bs.modal', function() {
		$('#driverForm').bootstrapValidator('resetForm',true);
	});
}

function settime() {
	var obj = $("#sendMsgA");
	if (countdown == 0) {
		obj.removeAttr("disabled");
		obj.attr("onclick","sendMessage()");
		obj.text("发送验证码");
		countdown = 60;
		return true
	} else {
		obj.removeAttr("onclick");
		obj.attr("disabled",true);
		obj.text("重新发送(" + countdown + ")");
		countdown--;
		setTimeout(function() {
			settime()
		},1000)
	}
}

/**
 * 发送验证码
 */
function sendMessage(){
	var isPhone = $("#isPhone").val();
	if(isPhone == "1"){
		var hasError = $("#mobile_phone").parents(".form-group").hasClass("has-error");
		console.log("hasError:"+hasError);
		if(hasError){
			$("#sendMsgA").off("click");
			return false;
		}else{
			$("#sendMsgA").on("click",function(){sendMessage()});
		}

		var mobilePhone = $("#phone").val();
		/*alert("123");*/
		if(mobilePhone == ""){
			return false;
		}else{
			countdown=60;
			settime();
			$.ajax({
				url:"../../help/user/getCode",
				data:{mobilePhone:mobilePhone,msgType:'register'},
				async:false,
				type: "POST",
				success: function(data){
					bootbox.alert(data.msg);
				}
			})
		}
	}


}

