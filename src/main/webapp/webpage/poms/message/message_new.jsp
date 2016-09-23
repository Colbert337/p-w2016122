<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<script src="<%=basePath%>/dist/js/message/message_new.js"></script>

<div class="">
	<!-- /section:settings.box -->
	<div class="page-header">
		<h1>新建信息</h1>
	</div>
	<!-- /.page-header -->

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal" id="messageform">

				<jsp:include page="/common/page_param.jsp"></jsp:include>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">信息标题：
					</label>

					<div class="col-sm-4">
						<input type="text" name="messageTitle" placeholder="输入信息标题"
							class="form-control" maxlength="30" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">信息内容：</label>

					<div class="col-sm-4">
						<textarea type="text" name="messageBody" placeholder="输入信息内容"
							class="form-control" maxlength="500"></textarea>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">详情（content）：</label>

					<div class="col-sm-4">
						<textarea type="text" name="content" placeholder="输入信息内容"
							class="form-control" maxlength="500"></textarea>
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-3 control-label no-padding-right">信息缩略：
					</label>

					<div class="col-sm-4">
						<input type="text" name="messageTicker" placeholder="输入信息缩略"
							class="form-control" maxlength="30" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">消息类型：</label>
					<div class="col-sm-4">
						<div class="radio">
							<label> <input name="messageType" type="radio"
								class="ace" value="1" checked="checked"
								onclick="changeMessageType(this)"> <span class="lbl">系统消息</span>
							</label> <label> <input name="messageType" type="radio"
								class="ace" value="2" onclick="changeMessageType(this)">
								<span class="lbl">路况消息</span>
							</label>
						</div>
					</div>
				</div>
				<div class="form-group" id="province" style="display: none">
					<label for="province_id"
						class="col-sm-3 control-label no-padding-right"> 发送范围： </label>
					<div class="col-sm-4">
						<select class="form-control" name="province_id"
							onchange="changeProvince(this);">
							<s:option flag="true" gcode="PROVINCE_CODE" form="messageform"
								field="province_id" />
						</select> <input type="hidden" name="province_name" />
					</div>
				</div>
<!-- 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">发送用户组：
					</label>
 -->
					<div class="col-sm-4">
						<input type="hidden" name="messageGroup" class="form-control"
							readonly="readonly" value="1000" />
					</div>
			<!-- 	</div> -->

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">发送方式：</label>

					<div class="col-sm-4">
						<select class="form-control" name="messageType">
							<option value="0">即时发送</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">操作人：</label>

					<div class="col-sm-4">
						<input type="text" name="operator" class="form-control"
							readonly="readonly"
							value="${sessionScope.currUser.user.userName}" />
					</div>
				</div>

				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">

						<button class="btn btn-info" type="button" onclick="save();">
							<i class="ace-icon fa fa-check bigger-110"></i> 保存
						</button>
						&nbsp; &nbsp; &nbsp;

						<button class="btn" type="button" onclick="init();">
							<i class="ace-icon fa fa-repeat bigger-110"></i> 重置
						</button>
						&nbsp; &nbsp; &nbsp;

						<button class="btn btn-success" type="button"
							onclick="returnpage();">
							<i class="ace-icon fa fa-undo bigger-110"></i> 返回
						</button>
					</div>
				</div>

				<jsp:include page="/common/message.jsp"></jsp:include>

			</form>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>