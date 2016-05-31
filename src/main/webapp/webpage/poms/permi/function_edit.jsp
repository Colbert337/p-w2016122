<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
</script>
<div class="page-header">
	<h1>
		功能管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			编辑功能 &amp;
		</small>
	</h1>
</div><!-- /.page-header -->
<%--单行表单 开始--%>
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form">
			<!-- #section:elements.form -->
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 用户名称： </label>
				<div class="col-sm-8">
					<input type="text" id="" placeholder="用户名称" class="col-xs-10 col-sm-5" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 用户密码： </label>
				<div class="col-sm-8">
					<input type="password" id="" placeholder="用户密码" class="col-xs-10 col-sm-5" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 选择角色： </label>
				<div class="col-sm-8">
					<select class="chosen-select col-xs-10 col-sm-5" id="form-field-select-3" data-placeholder="选择角色...">
						<option value="">  </option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 备注： </label>
				<div class="col-sm-8">
					<textarea class="limited col-xs-10 col-sm-5" id="" maxlength="50"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-offset-5 col-md-8">
					<button class="btn btn-info" type="button">
						<i class="ace-icon fa fa-check bigger-110"></i>
						保存
					</button>
					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						取消
					</button>
				</div>
			</div>
		</form>
	</div><!-- /.col -->
</div><!-- /.row -->
<%--单行表单结束--%>

<%--两行表单 开始--%>
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<form class="form-horizontal" role="form">
			<!-- #section:elements.form -->
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 用户名称： </label>
				<div class="col-sm-2">
					<input type="text" id="" placeholder="用户名称" class="col-xs-10 col-sm-12" />
				</div>
				<label class="col-sm-1 control-label no-padding-right" for=""> 用户名称： </label>
				<div class="col-sm-5">
					<input type="text" id="" placeholder="用户名称" class="col-xs-10 col-sm-5" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 用户密码： </label>
				<div class="col-sm-2">
					<input type="password" id="" placeholder="用户密码" class="col-xs-10 col-sm-12" />
				</div>
				<label class="col-sm-1 control-label no-padding-right" for=""> 用户密码： </label>
				<div class="col-sm-5">
					<input type="password" id="" placeholder="用户密码" class="col-xs-10 col-sm-5" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 选择角色： </label>
				<div class="col-sm-2">
					<select class="chosen-select col-xs-10 col-sm-12" id="">
						<option value="">选择角色</option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
					</select>
				</div>
				<label class="col-sm-1 control-label no-padding-right" for=""> 选择角色： </label>
				<div class="col-sm-5">
					<select class="chosen-select col-xs-10 col-sm-5" id="">
						<option value="">选择角色</option>
						<option value="AL">Alabama</option>
						<option value="AK">Alaska</option>
						<option value="AZ">Arizona</option>
						<option value="AR">Arkansas</option>
						<option value="CA">California</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label no-padding-right" for=""> 备注： </label>
				<div class="col-sm-2">
					<textarea class="limited col-xs-10 col-sm-12" id="" maxlength="50"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-offset-5 col-md-8">
					<button class="btn btn-info" type="button">
						<i class="ace-icon fa fa-check bigger-110"></i>
						保存
					</button>
					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						取消
					</button>
				</div>
			</div>
		</form>
	</div><!-- /.col -->
</div><!-- /.row -->
<%--两行表单 结束--%>
