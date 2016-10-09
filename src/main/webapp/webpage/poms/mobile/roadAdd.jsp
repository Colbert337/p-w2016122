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



<link rel="stylesheet" href="<%=basePath%>/common/css/fileinput.css" />
<script src="<%=basePath%>/assets/js/date-time/moment.js"></script>
<link rel="stylesheet" href="<%=basePath%>/assets/css/bootstrap-datetimepicker.css" />
<script src="<%=basePath%>/assets/js/date-time/bootstrap-datetimepicker.js"></script>

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
							name="conditionType" id="conditionType"  onchange="changeType('conditionType')" >
							<s:option flag="true" gcode="CONDITION_TYPE" form="road"
								field="status" />
						</select>
					</div>
				</div> 
				 
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">坐标：</label>

					<div class="col-sm-4">
						<input type="text" name="longitude" placeholder="输入坐标,格式为：经度，纬度，（如：12.15,15.475）"
							class="form-control" maxlength="40"></input>
					</div>
				</div>
				<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right">图片：</label>
							<div class="col-sm-4">
								<div class="widget-box">
									<div class="widget-header">
										<h4 class="widget-title">图片上传 <font size="2" color="red" ></font></h4>
									</div>
									<div class="widget-body">
										<div class="widget-main">
										<input type="file" name="image"
												class="projectfile" id="indu_com_certif_select" /> <input
												type="hidden" id="img_path" value=""
												name="conditionImg" /> 
											<button class="btn btn-sm btn-primary btn-file-space"
												type="button"
												onclick="savePhoto(this,'#indu_com_certif_select','#img_path','#show_img');">
												<i class="ace-icon fa fa-check bigger-110"></i> 图片上传
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">拍照时坐标：</label>

					<div class="col-sm-4">
						<input type="text" name="captureLongitude" placeholder="输入拍照坐标,格式为：经度，纬度，（如：12.15,15.475）"
							class="form-control" maxlength="40"></input>
					</div>
				</div>
 
				
				<div class="form-group">
					<label  class="col-sm-3 control-label no-padding-right">拍照时间：
					</label>
			<!-- #section:plugins/date-time.datetimepicker -->
				<div class="input-group col-sm-4">
					<input type="text" name="captureTime_str" class="form-control col-sm-4 timebox" />
					<span class="input-group-addon">
						<i class="fa fa-clock-o bigger-110"></i>
					</span>
				</div>
				<!-- /section:plugins/date-time.datetimepicker -->
				 
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">路况说明：</label>

					<div class="col-sm-4">
						<textarea name="conditionMsg" placeholder="路况说明"
							class="form-control" maxlength="200"></textarea>
					</div>
				</div>
					<div class="form-group">
					<label for="email" class="col-sm-3 control-label no-padding-right">开始时间：
					</label>
					
					<div class="input-group col-sm-4">
					<input type="text" name="startTime_str" id="startTime_str" class="form-control col-sm-4 timebox" />
					<span class="input-group-addon">
						<i class="fa fa-clock-o bigger-110"></i>
					</span>
					</div>
				 
				</div>
					<div class="form-group" id="end">
					<label for="email" class="col-sm-3 control-label no-padding-right">结束时间：
					</label>

					<div class="input-group col-sm-4">
					<input type="text" name="endTime_str" id="endTime_str"  class="form-control col-sm-4 timebox" />
					<span class="input-group-addon">
						<i class="fa fa-clock-o bigger-110"></i>
					</span>
					</div>
				</div>

				<div class="form-group">
					<label for="email" class="col-sm-3 control-label no-padding-right">详细地址：
					</label>

					<div class="col-sm-4">
						<textarea name="address" placeholder="详细地址" class="form-control" maxlength="200"></textarea>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">省份信息：</label>

					<div class="col-sm-4">
						<input type="text" name="province" placeholder="输入信息内容"
							class="form-control" maxlength="20"></input>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">方向：</label>

					<div class="col-sm-4">
						<select class="chosen-select col-sm-6"
							name="direction" >
							<s:option flag="true" gcode="DIRECTION_CODE" form="road"
								field="status" />
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right">备注：</label>

					<div class="col-sm-4">
						<textarea name="memo" placeholder="备注"
							class="form-control" maxlength="500"></textarea>
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
