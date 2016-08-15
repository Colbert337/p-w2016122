var sjny = sjny || {};
sjny.admin = sjny.admin || {};

sjny.admin.login = {

	verticalMiddle: function(){
		//登陆框垂直居中
		$(window).resize(function() {
			$('.login').css({
				position: 'absolute',
				left: ($(window).width() - $('.login').outerWidth()) / 2,
				top: ($(window).height() - $('.login').outerHeight()) / 2 + $(document).scrollTop()
			});
		});
		$(window).resize();
	},

	/*loginValidate: function() {

		//客户端开始验证
		$('#login').validate({

			/!* 设置验证规则 *!/
			rules: {
				userName: {
					required: true,
					rangelength: [3, 20]
				},
	            password: {
	                required: true,
	                rangelength: [6, 16]
	            }
			},

			/!* 设置错误信息 *!/
			messages: {
				userName: {
					required: "请填写用户名",
					rangelength: "用户名需由3-20个字符（数字、字母、下划线）组成！"
				},
	            password: {
	                required: "请填写密码",
	                rangelength: "密码需由6-16个字符（数字、字母、下划线）组成！"
	            }
			},

			/!*错误提示位置*!/
	        errorPlacement: function (error, element) {
	            error.appendTo(element.siblings("div"));
	        }

		});
	},*/

	rememberPW: function(){
		//初始化页面时验证是否记住了密码
		var remember = $.cookie('remember');
		if (remember == 'true') {
			var user = $.cookie('user');
			var password = $.cookie('password');
			$('#userName').val(user);
			$('#password').val(password);
		}
	},

	saveUserInfo: function () {
		if ($('#remember').is(':checked')) {
			var user = $('#userName').val();
			var password = $('#password').val();

			// set cookies to expire in 7 days
			$.cookie('user', user, {
				expires: 7
			});
			$.cookie('password', password, {
				expires: 7
			});
			$.cookie('remember', true, {
				expires: 7
			});
		} else {
			// reset cookies
			$.cookie('user', "", { expires: -1 });
			$.cookie('password', "", { expires: -1 });
			$.cookie('remember',"false", { expire: -1 });
		}
	},

	clickSaveUserInfo: function(){
		//记住用户名密码
		$("#login").submit(function() {
			sjny.admin.login.saveUserInfo();
		})
	}
};

/*登录验证*/
function loginValidate(userName,password){
	if(userName == ""){
		$("#errorNotice").text("用户名不能为空！");
		return false;
	}else if(password == ""){
		$("#errorNotice").text("用户密码不能为空！");
		return false;
	}else{
		return true;
	}
}

/*用户登录*/
function submitForm(){
	console.log("登录验证");
	var userName = $("#userName").val();
	var password = $("#password").val();
	var resultVal = loginValidate(userName,password);
	if(resultVal){
		$.ajax({
			type: "POST",
			async:false,
			data:{userName:userName,password:password},
			url: "web/login/common",
			success: function(data){
				if(data != null && data.erroMsg != null && data.erroMsg =="suceess"){
					window.location.href="common/g_main.jsp";
				}else{
					$("#errorNotice").text(data.erroMsg);
				}

			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				$("#errorNotice").text("登录失败!");
			}
		});
	}

}

/*回车提交表单*/
$(document).keyup(function(event){
	if(event.keyCode ==13){
		$("#submitButton").trigger("click");
	}
});

$(document).ready(function() {
	sjny.admin.login.verticalMiddle();
	sjny.admin.login.loginValidate();
	sjny.admin.login.rememberPW();
	sjny.admin.login.clickSaveUserInfo();
});
