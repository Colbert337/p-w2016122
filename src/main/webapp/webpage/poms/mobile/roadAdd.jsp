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

<script src="<%=basePath%>/dist/js/mobile/roadAdd.js"></script>

<div class="">
	<!-- /section:settings.box -->
	<div class="page-header">
		<h1>新建信息</h1>
	</div>
	<!-- /.page-header -->

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal" id="roadform">

				<%-- <jsp:include page="/common/page_param.jsp"></jsp:include> --%>
				
				
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">路况类型：
					</label>

					<div class="col-sm-4">
					<select class="chosen-select col-sm-6"
							name="conditionType">
							<s:option flag="true" gcode="CONDITION_TYPE" form="road"
								field="status" />
						</select>
					</div>
				</div> 
				 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">坐标：</label>

					<div class="col-sm-4">
						<input type="text" name="longitude" placeholder="输入信息内容"
							class="form-control" maxlength="500"></input>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">拍照时坐标：</label>

					<div class="col-sm-4">
						<input type="text" name="captureLongitude" placeholder="输入信息内容"
							class="form-control" maxlength="500"></input>
					</div>
				</div>
 
				
				<div class="form-group">
					<label  class="col-sm-3 control-label no-padding-right">拍照时间：
					</label>
					<div class="col-sm-4">
						<div class="item col-sm-4">
							<div class="input-daterange top" id="j-input-daterange-top">
								<input type="text" class="form-control  timebox" name="captureTime"
									readonly="readonly" />
							</div>
						</div>
						
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">路况说明：</label>

					<div class="col-sm-4">
						<textarea name="conditionMsg" placeholder="路况说明"
							class="form-control"></textarea>
					</div>
				</div>
					<div class="form-group">
					<label for="email" class="col-sm-3 control-label no-padding-right">开始时间：
					</label>

					<div class="col-sm-1">
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<input type="text"  class="form-control timebox" name="startTime"
									readonly="readonly" />
							</div>
						</div>
						
					</div>
				
					<label for="email" class="col-sm-1 control-label no-padding-right">结束时间：
					</label>

					<div class="col-sm-1">
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<input type="text" class="form-control timebox" name="endTime"
									readonly="readonly" />
							</div>
						</div>
						
					</div>
				</div>

				<div class="form-group">
					<label for="email" class="col-sm-3 control-label no-padding-right">详细地址：
					</label>

					<div class="col-sm-4">
						<textarea name="address" placeholder="路况说明" class="form-control"></textarea>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">省份信息：</label>

					<div class="col-sm-4">
						<input type="text" name="captureLongitude" placeholder="输入信息内容"
							class="form-control" maxlength="500"></input>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">省份信息：</label>

					<div class="col-sm-4">
						<input type="text" name="captureLongitude" placeholder="输入信息内容"
							class="form-control" maxlength="500"></input>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">发布人/操作人：</label>

					<div class="col-sm-4">
						<input type="text" name="operator" class="form-control"
							readonly="readonly"
							value="${sessionScope.currUser.user.userName}" />
					</div>
				</div>



				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">

						<button class="btn btn-info" type="button" onclick="saveRoad();">
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
<div id="editModel" class="modal fade" role="dialog"
	aria-labelledby="gridSystemModalLabel" data-backdrop="static"
	tabindex="-1">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editBanner">添加内容</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid" id="content">
					<%--两行表单 开始--%>

					<!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div>
			<!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="saveRoad()">确
					定</button>
				<button class="btn btn-sm" i="close"
					onclick="closeDialog('editModel')">取 消</button>
			</div>
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>