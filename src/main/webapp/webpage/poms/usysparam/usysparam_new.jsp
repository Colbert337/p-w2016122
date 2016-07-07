<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/usysparam/usysparam_new.js"></script>

			<div class="main-content">
				<div class="main-content-inner">

					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								配置系统参数
							</h1>
						</div>

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="formusysparam">
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="transportion_name">GCODE： </label>

										<div class="col-sm-4">
											<input type="text" name="gcode" placeholder="输入GCODE" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="transportion_name">MCODE： </label>

										<div class="col-sm-4">
											<input type="text" name="mcode" placeholder="输入MCODE" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="transportion_name">MNAME：</label>

										<div class="col-sm-4">
											<input type="text" name="mname" placeholder="输入MANME" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="transportion_name">DATA： </label>

										<div class="col-sm-4">
											<input type="text" name="data" placeholder="输入DATA" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="transportion_name">SCODE： </label>

										<div class="col-sm-4">
											<input type="text" name="scode" placeholder="输入SCODE" class="form-control"/>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="ace-icon fa fa-repeat bigger-110"></i>
												重置
											</button>
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-success" type="button" onclick="returnpage();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>

									<jsp:include page="/common/message.jsp"></jsp:include>
										
								</form>						
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
	</div>
