<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="index" lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>帮助热phoneType = ${phoneType}点问题</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet"
	href="<%=basePath %>/webpage/crm/css/fontello.css">
<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
</head>
<body>
	<div class="share">
		<div class="traffic-detail-hd">如何给司集应用进行权限设置</div>
		<div class="traffic-detail-bd">
			<c:choose>
				<c:when test="${phoneType==1}">
					<div class="info">
						1)	华为手机：<br/><br/>
						a)	首先，您需要进入手机主页进入“设置”，选择“全部设置”<br/><br/>
						b)	然后，再菜单中选择进入“权限管理”，<br/><br/>
						c)	在权限管理中心，选择“应用”，在所有应用中找到“司集”，打开后，选择“信任此应用”，就可以啦。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='2'}">
					<div class="info">
						2)	Vivo手机：<br/><br/>
						a)	首先，请您在手机主页中进入“i管家”，在界面然后选择“软件管理”<br/><br/>
						b)	进入“软件管理”页面，在展开的菜单中查找“软件权限管理”<br/><br/>
						c)	进入之后，选择“软件”，在下拉菜单中找“司集”，然后选择“信任该软件”就可以啦！<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='3'}">
					<div class="info">
						3)	三星手机：<br/><br/>
						a)	首先，请您在手机主页中进入“设置”，然后在下拉菜单中查找“隐私和安全”<br/><br/>
						b)	进入主页后，选择进入“应用程序许可”。<br/><br/>
						c)	如果司集APP不能定位，可选择“位置信息”，找到司集，开启位置信息权限即可。<br/><br/>
						d)	如果司集APP不能进行拍照，可选项“相机”，找到司集，开启相机权限即可。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='4'}">
					<div class="info">
						4)	Oppo R9手机：<br/><br/>
						a)	首先请您在手机主页中进入“手机管家”，然后在页面中进入“权限隐私”。<br/><br/>
						b)	进入“应用权限管理”页面后，在应用列表中找到“司集”，进入页面，开启“我信任该应用”；或者可以在列表中点击相关例如“数据网络开关”，选择“允许”，就可以正常使用网络、定位以及拍照等功能啦。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='5'}">
					<div class="info">
						5)	小米 5<br/><br/>
						a)	首先，请您在手机主页中进入“设置”，然后在下拉菜单中查找“授权管理”<br/><br/>
						b)	进入主页后，选择“应用权限管理”，然后再下拉菜单中找到“应用管理”中找到司集<br/><br/>
						c)	如果司集APP不能定位，点击“定位”，选择“允许”即可。<br/><br/>
						d)	如果司集APP不能进行拍照，点击“相机”，选择“允许”即可。<br/><br/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="info">
						1.	在使用APP的过程中提示网络异常，无法定位，收不到验证码怎么办？<br/><br/>
						1)	首先需要确认您的手机是否开启相关的网络或者位置的相关权限哦。<br/><br/>
						2)	如果还不能解决您的问题，可以致电司集客服为您解答哦：400-836-6622<br/><br/>
					</div>
					<form class="app-intro-form" action="<%=basePath %>/portal/crm/help/suggest@##￥￥" method="post" id="appIntroFrom">
					<div class="item">
						<input id="title" name="title" tabIndex="1" class="txt required" type="text" class="txt" placeholder="请在这里输入您的手机机型，以便我们更好的为您服务">
					</div>
					<button type="submit" class="btn-app-primary">提交</button>
				</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
	<script src="<%=basePath %>/assets/js/jquery.validate.js"></script>
	<script src="<%=basePath %>/webpage/crm/js/webapp.js"></script>
</body>
</html>
