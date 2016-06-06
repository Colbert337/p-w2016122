<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">

<head>
<title>${pd.SYSNAME}</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=basePath%>/assets/camera/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/camera/matrix-login.css" />
<link href="<%=basePath%>/assets/camera/font-awesome.css" rel="stylesheet" />

<link rel="stylesheet" href="<%=basePath%>/assets/camera/css/camera.css" />
<link rel="stylesheet" href="<%=basePath%>/assets/camera/css/camera-additional.css" />
<script type='text/javascript' src='<%=basePath%>/assets/camera/js/jquery.min.js'></script>
<script type='text/javascript' src='<%=basePath%>/assets/camera/js/jquery.mobile.customized.min.js'></script>
<script type='text/javascript' src='<%=basePath%>/assets/camera/js/jquery.easing.1.3.js'></script> 
<script type='text/javascript' src='<%=basePath%>/assets/camera/js/camera.min.js'></script> 
<script>
	jQuery(function(){
		jQuery('#camera_wrap_4').camera({
			height: 'auto',
			loader: 'bar',
			playPause: false,
			pagination: false,
			thumbnails: false,
			hover: false,
			opacityOnGrid: false,
			imagePath: '<%=basePath%>/assets/camera/images/'
		});
	});
</script>
</head>
<body>

	<div style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<div id="loginbox">
			<form action="<%=basePath%>/web/login" method="post" name="loginForm"
				id="loginForm">
				<div class="control-group normal_text">
					<h3>
						<img src="<%=basePath%>/assets/camera/logo.png" alt="Logo" />
					</h3>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i><img height="37" src="<%=basePath%>/assets/camera/user.png" /></i>
							</span><input type="text" name="userName" id="loginname" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i><img height="37" src="<%=basePath%>/assets/camera/suo.png" /></i>
							</span><input type="password" name="password" id="password" placeholder="请输入密码" value="" />
						</div>
					</div>
				</div>
				<div style="float:right;padding-right:10%;">
					<div style="float: left;margin-top:3px;margin-right:2px;">
						<font color="white">记住密码</font>
					</div>
					<div style="float: left;">
						<input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="savePaw();" style="padding-top:0px;" />
					</div>
				</div>
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">

						<div style="float: left;">
							<i><img src="<%=basePath%>/assets/camera/yan.png" /></i>
						</div>
						<div style="float: left;" class="codediv">
							<input type="text" name="code" id="code" class="login_code"
								style="height:16px; padding-top:0px;" />
						</div>
						<!-- <div style="float: left;">
							<i><img style="height:22px;" id="codeImg" alt="点击更换"
								title="点击更换" src="" /></i>
						</div> -->

						<span class="pull-right" style="padding-right:3%;">
							<a href="javascript:quxiao();" class="btn btn-success">取消</a>
						</span>

						<span class="pull-right">
							<a onclick="severCheck();" class="flip-link btn btn-info" id="to-recover">登录</a>
						</span>

					</div>
				</div>

			</form>

			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">Copyright © HBKIS
							2015</span></font>
				</div>
			</div>
		</div>
	</div>
	<div class="fluid_container">
        <div class="camera_wrap camera_emboss pattern_1" id="camera_wrap_4">
            <div data-src="<%=basePath%>/assets/camera/images/banner_slide_01.jpg"></div>
			<div data-src="<%=basePath%>/assets/camera/images/banner_slide_02.jpg"></div>
			<div data-src="<%=basePath%>/assets/camera/images/banner_slide_03.jpg"></div>
			<div data-src="<%=basePath%>/assets/camera/images/tree.jpg"></div>
			<div data-src="<%=basePath%>/assets/camera/images/shelter.jpg"></div>
			<div data-src="<%=basePath%>/assets/camera/images/sea.jpg"></div>
			<div data-src="<%=basePath%>/assets/camera/images/road.jpg"></div>
			<div data-src="<%=basePath%>/assets/camera/images/bridge.jpg"></div>
        </div>
    </div><!-- .fluid_container -->
	<script type="text/javascript">
	
		//服务器校验
		function severCheck(){
			/* if(check()){ */
				if(1==1){
				$("#loginForm").submit();
				/* var loginname = $("#loginname").val();
				var password = $("#password").val(); */
				var code = "qq313596790fh"+loginname+",fh,"+password+"QQ978336446fh"+",fh,"+$("#code").val();
				<%-- window.location.href="<%=basePath%>/web/login"; --%>
				/* $.ajax({
					type: "POST",
					url: 'login_login',
			    	data: {KEYDATA:code,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" == data.result){
							saveCookie();
							window.location.href="main/index";
						}else if("usererror" == data.result){
							$("#loginname").tips({
								side : 1,
								msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 15
							});
							$("#loginname").focus();
						}else if("codeerror" == data.result){
							$("#code").tips({
								side : 1,
								msg : "验证码输入有误",
								bg : '#FF5080',
								time : 15
							});
							$("#code").focus();
						}else{
							$("#loginname").tips({
								side : 1,
								msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
							$("#loginname").focus();
						}
					}
				}); */
			}
		}
	
		/* $(document).ready(function() {
			changeCode();
			$("#codeImg").bind("click", changeCode);
		}); */

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {

				$("#loginname").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}

			if ($("#password").val() == "") {

				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {

				$("#code").tips({
					side : 1,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#code").focus();
				return false;
			}

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 10
			});

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('loginname', '', {
					expires : -1
				});
				$.cookie('password', '', {
					expires : -1
				});
				$("#loginname").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('loginname', $("#loginname").val(), {
					expires : 7
				});
				$.cookie('password', $("#password").val(), {
					expires : 7
				});
			}
		}
		function quxiao() {
			$("#loginname").val('');
			$("#password").val('');
		}
		
		jQuery(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof(loginname) != "undefined"
					&& typeof(password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				$("#code").focus();
			}
		});
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
	<script type="text/javascript" src="<%=basePath%>/assets/js/jquery.cookie.js"></script> 
</body>

</html>