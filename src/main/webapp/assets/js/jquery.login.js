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

	loginValidate: function() {

		//客户端开始验证
		$('#login').validate({

			/* 设置验证规则 */
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

			/* 设置错误信息 */
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

			/*错误提示位置*/
	        errorPlacement: function (error, element) {
	            error.appendTo(element.siblings("div"));
	        }

		});
	},

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

$(document).ready(function() {
	sjny.admin.login.verticalMiddle();
	sjny.admin.login.loginValidate();
	sjny.admin.login.rememberPW();
	sjny.admin.login.clickSaveUserInfo();
});