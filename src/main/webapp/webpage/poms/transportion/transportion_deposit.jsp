<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/transportion/transportion_deposit.js"></script>


			<div class="main-content">
				<div class="main-content-inner">

					<!-- /section:basics/content.breadcrumbs -->
					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								运输公司保证金设置
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="transportionform">
								
								<jsp:include page="/common/page_param.jsp"></jsp:include>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 运输公司名称： </label>

										<div class="col-sm-4">
											<label class="control-label no-padding-right" id="stationame">${param.stationame}</label>
											<input type="hidden" name="stationId" value="${param.stationid}"/>
											<input type="hidden" name="accountId" value="${param.acconutid}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label for="email" class="col-sm-3 control-label no-padding-right" >充值金额： </label>

										<div class="col-sm-4">
											<input type="text" name="deposit" placeholder="输入保证金" class="form-control" maxlength="8"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >转账方式： </label>
										<div class="col-sm-4">
											<select class="form-control" name="depositType" >
												<s:option flag="true" gcode="RECHARGE_TYPE"/>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right">企业名称：</label>

										<div class="col-sm-4">
											<input type="text" name="company" placeholder="输入企业名称" class="form-control" maxlength="20"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="expiry_date">转账时间： </label>
										<div class="col-sm-4 datepicker-noicon">
											<div class="input-group">
												<input class="form-control date-picker" name="depositTime_page" id="depositTime_page" type="text" readonly="readonly" data-date-format="yyyy-mm-dd"/>
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
												</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">操作人员：</label>

										<div class="col-sm-4">
											<input type="text" id="operator" name="operator" class="form-control"  maxlength="10" value="${sessionScope.currUser.user.userName}" readonly="readonly"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">备注： </label>

										<div class="col-sm-4">
											<textarea class="form-control" name="memo" rows="5" maxlength="100"></textarea>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn btn-success" type="button" onclick="returnpage2();">
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
