<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="index" lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>用户邀请-司集APP</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="share">
		<div class="share-hd">
			<div class="info">
				<p>您已获得¥10优惠券，快去领取吧！</p>
				<p>请输入您的电话号码并下载司集应用程序完成注册</p>
				<p class="code-style">邀请码：${param.invitationCode}</p>
			</div>
			<div class="subinfo">邀请码优惠，完成下载注册，20元平台代金券；心动不如行动！为了感谢您一路以来的支持，司集特别定制的红包大礼活动回馈广大用户。
			</div>
		</div>
		<form class="download-form" action="<%=basePath %>/portal/crm/help/user/register" method="post" id="shareInviteCode">
			<div class="df-item">
				<input id="phone" name="phone" tabIndex="1" class="txt required" type="text" placeholder="请输入手机号码">
			</div>
			<div class="df-item">
				<input id="vcode" name="vcode" tabIndex="2" class="txt code required" type="text" placeholder="请输入验证码">
				<button class="get-code-info">获取验证码</button>
				<input type="hidden" id="invitationCode" name="invitationCode" value="${param.invitationCode}">
			</div>
			<div class="df-item-btn">
				<button type="submit" class="btn-app-primary">立即使用</button>
			</div>
		</form>
	</div>
	
	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/assets/js/jquery.validate.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>
