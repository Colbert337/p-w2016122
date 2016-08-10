<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <link rel=stylesheet href="<%=basePath%>webapp/ueditor/themes/default/css/ueditor.css"> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑器</title>
<!-- 配置文件 -->
<script type="text/javascript" src="<%=basePath %>/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/ueditor/ueditor.all.js"></script>
<!-- 中文包文件 -->
<script type="text/javascript" src="<%=basePath %>/assets/ueditor/lang/zh-cn/zh-cn.js"></script>

</head>
<body>sdfgsdfg
<div>
	<script type="text/plain" style="width:1024px;height:500px;" id="editor"></script>
</div>

<script type="text/javascript">
  /* UEDITOR_CONFIG.UEDITOR_HOME_URL = './ueditor/';
  var editor = new UE.ui.Editor();
  UE.getEditor("container");  */    
  var ue = UE.getEditor('editor'); 
</script>
</body>
</html>