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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑器</title>
<!-- 配置文件 -->
<script type="text/javascript" src="<%=basePath %>/assets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/ueditor/ueditor.all.js"></script>
<!-- 中文包文件 -->
<script type="text/javascript" src="<%=basePath %>/assets/ueditor/lang/zh-cn/zh-cn.js"></script>

</head>
<body>
<div>
	<script type="text/plain" style="width:1024px;height:500px;" id="editor"></script>	
	<!-- <script type="text/plain" id="upload_ue"></script>	
	<input type="text" id="picture" name="cover" /><a href="javascript:void(0);" onclick="upImage();">上传图片</a>
	<input type="text" id="picture" name="cover" /><a href="javascript:void(0);" onclick="upFiles();">上传文件</a> -->
</div>
    <script type="text/javascript">   
      var ue = UE.getEditor("editor"); 
    </script>
 <!--    <script type="text/javascript">
  //上传独立使用
    var _editor = UE.getEditor('upload_ue');
        _editor.ready(function () {
        //_editor.setDisabled();
        _editor.hide();

        _editor.addListener('beforeInsertImage', function (t, arg) {     //侦听图片上传

          alert('asdf');
            $("#pic_list").attr("value", arg[0].src);       //将地址赋值给相应的input
            $("#preview").attr("src", arg[0].src);

        });         
    }); 
    </script> -->
</body>
</html>