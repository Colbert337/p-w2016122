<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
//显示弹出层
function test(){
	var d = dialog({
		width:500,
		height:350,
		top:'20%',
		title: '测试弹层',
		content: $("#info-bg"),
		fixed: false,
		drag: true,
	    onclose: function () {
// 	        alert('对话框已经关闭!!!!');
	    }
		});
		d.showModal();
}
function test2(){
	sucDialog();
}
function test3(){
	failDialog();
}
function test4(){
	warnDialog();
}
</script>
<div class="page-header">
	<h1>
		总览
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			总览 &amp; 页面
		</small>
	</h1>
</div><!-- /.page-header -->
<div class="row">
	<div class="col-xs-12 panel-title">
		 <h1>欢迎登陆海贝运维支撑系统！</h1>
		 <input type="button" name="test" onclick="test()" value="弹层"/>
		 <input type="button" name="test" onclick="test2()" value="成功"/>
		 <input type="button" name="test" onclick="test3()" value="失败"/>
		 <input type="button" name="test" onclick="test4()" value="警告"/>
	</div>
</div>


<div class="row">
	<form class="form-horizontal">
		<div class="col-xs-5">
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
		</div>	
		<div class="col-xs-5">
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
			<div class="form-group">
				<label class="col-xs-2"><span class="red">*</span>办证人：</label>
				<input class="col-xs-10" type="text" name="transactor" placeholder="">
			</div>
		</div>	
	</form>
</div>


<!--弹层-开始-->
<div id="info-bg" class="all-hidden container col-xs-12">
	<!--弹层内容 -->
	<div class="b-dialog-content">
		
	</div>
	<!--底部按钮 -->
	<div class="row">
		<div class="col-xs-3"></div>
		<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="saveSchool()">确   定</button></div>
		<div class="col-xs-6"><button class="btn" i="close" onclick="close_mslog()">取   消 </button></div>
	</div>
</div>
<!--弹层-结束-->