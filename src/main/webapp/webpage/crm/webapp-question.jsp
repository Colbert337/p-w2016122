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
<title>帮助热点问题</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet"
	href="<%=basePath %>/webpage/crm/css/fontello.css">
<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/webapp.css">
<c:if test="${result=='ok'}">
	<script type="text/javascript">
		alert("提交成功...");
	</script>
</c:if>
<c:if test="${result=='error'}">
	<script type="text/javascript">
		alert("提交失败...");
	</script>
</c:if>
</head>
<body>
	<div class="share">
		<div class="traffic-detail-hd">如何给司集应用进行权限设置</div>
		<div class="traffic-detail-bd">
			<c:choose>
				<c:when test="${phoneType=='HUAWEI'}">
					<div class="info">
							华为系列：<br/><br/>
						1)	首先，您需要进入手机主页进入“设置”，选择“全部设置”<br/><br/>
						2)	然后，再菜单中选择进入“权限管理”，<br/><br/>
						3)	在权限管理中心，选择“应用”，在所有应用中找到“司集”，打开后，选择“信任此应用”，就可以啦。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='vivo'}">
					<div class="info">
							Vivo系列：<br/><br/>
						1)	首先，请您在手机主页中进入“i管家”，在界面然后选择“软件管理”<br/><br/>
						2)	进入“软件管理”页面，在展开的菜单中查找“软件权限管理”<br/><br/>
						3)	进入之后，选择“软件”，在下拉菜单中找“司集”，然后选择“信任该软件”就可以啦！<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='samsung'}">
					<div class="info">
							三星系列：<br/><br/>
						1)	首先，请您在手机主页中进入“设置”，然后在下拉菜单中查找“隐私和安全”<br/><br/>
						2)	进入主页后，选择进入“应用程序许可”。<br/><br/>
						3)	如果司集APP不能定位，可选择“位置信息”，找到司集，开启位置信息权限即可。<br/><br/>
						4)	如果司集APP不能进行拍照，可选项“相机”，找到司集，开启相机权限即可。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='OPPO'}">
					<div class="info">
							OPPO系列：<br/><br/>
						1)	首先请您在手机主页中进入“手机管家”，然后在页面中进入“权限隐私”。<br/><br/>
						2)	进入“应用权限管理”页面后，在应用列表中找到“司集”，进入页面，开启“我信任该应用”；或者可以在列表中点击相关例如“数据网络开关”，选择“允许”，就可以正常使用网络、定位以及拍照等功能啦。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='Xiaomi'}">
					<div class="info">
							小米系列<br/><br/>
						1)	首先，请您在手机主页中进入“设置”，然后在下拉菜单中查找“授权管理”<br/><br/>
						2)	进入主页后，选择“应用权限管理”，然后再下拉菜单中找到“应用管理”中找到司集<br/><br/>
						3)	如果司集APP不能定位，点击“定位”，选择“允许”即可。<br/><br/>
						4)	如果司集APP不能进行拍照，点击“相机”，选择“允许”即可。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='GiONEE'}">
					<div class="info">
							金立系列<br/><br/>
						1)	首先，请您在手机主页中进入“设置”，然后在下拉菜单选择“高级设置”<br/><br/>
						2)	进入主页面后在下拉列表中选择“应用权限”，然后选择“按应用分类”<br/><br/>
						3)	在下拉列表中查找“司集”，点击进入，并选择开启“我信任该应用”就可以啦。<br/><br/>
					</div>
				</c:when>
				<c:when test="${phoneType=='Coolpad'}">
					<div class="info">
							酷派系列<br/><br/>
						1)	首先，请您在手机主页中进入“权限配置”，然后在菜单选择“程序”<br/><br/>
						2)	进入页面后在下拉列表中查找“司集”，点击进入 ，然后选择开启“我信任该应用”就可以啦！<br/><br/>
						3)	如果司集APP没有网络，点击“开启数据连接”，选择“允许”即可。<br/><br/>
						4)	如果司集APP不能定位，点击“调用定位功能”，选择“允许”即可。<br/><br/>
						5)	如果司集APP不能进行拍照，点击“开启摄像头”，选择“允许”即可。<br/><br/>
					</div>
				</c:when>
				<c:otherwise>
					<div class="info">
						1.	在使用APP的过程中提示网络异常，无法定位，收不到验证码怎么办？<br/><br/>
						1)	首先需要确认您的手机是否开启相关的网络或者位置的相关权限哦。<br/><br/>
						2)	如果还不能解决您的问题，可以致电司集客服为您解答哦：400-836-6622<br/><br/>
					</div>
				<form class="app-intro-form" action="<%=basePath %>/portal/crm/help/suggest" method="post" id="phoneTypeFrom">
					<div class="item">
						<input id="info" name="info" tabIndex="1" type="text" class="txt required" placeholder="请在这里输入您的手机机型，以便我们更好的为您服务">
					</div>
					<textarea id="title" name="title" tabIndex="1" class="txt" style="display: none;" >帮助热点问题-手机型号提交</textarea>
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
