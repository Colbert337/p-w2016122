<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script type="text/javascript" src="<%=basePath %>/dist/js/poms/sysparam/system_cashback_update.js"></script>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<div class="page-content">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								配置返现规则
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="cashbackform">
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 配置对象： </label>
										
										<div class="col-sm-3">
											<label class="control-label no-padding-right" > <s:Code2Name mcode="${sysCashBack.sys_cash_back_no}" gcode="CASHBACK"></s:Code2Name> </label>
											<input type="hidden" name="sys_cash_back_id" value="${sysCashBack.sys_cash_back_id}"/>
											<input type="hidden" name="sys_cash_back_no" value="${sysCashBack.sys_cash_back_no}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 阈最小值： </label>
										
										<div class="col-sm-3">
											<input type="text"  name="threshold_min_value" placeholder="输入该阈最小值" class="col-xs-10 col-sm-5" maxlength="6" value="${sysCashBack.threshold_min_value}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">阈最大值（包含）： </label>

										<div class="col-sm-3">
											<input type="text"   name="threshold_max_value" placeholder="输入该阈最大值" class="col-xs-10 col-sm-5" maxlength="6" value="${sysCashBack.threshold_max_value}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">返现系数： </label>

										<div class="col-sm-3">
											<input type="text"  name="cash_per" placeholder="输入返现系数" class="col-xs-10 col-sm-5" maxlength="5" value="${sysCashBack.cash_per}"/>
											<p class="text-error">（返现金额=充值金额 X 返现系数）</p>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 状态： </label>
										<div class="col-sm-2">
												<select class="form-control" name="status">
														<s:option flag="true" gcode="CASHBACKSTATUS" form="sysCashBack" field="status" />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 优先级： </label>
										<div class="col-sm-2">
												<select class="form-control"  name="level">
														<s:option flag="true" gcode="CASHBACKLEVEL" form="sysCashBack" field="level"  />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 生效日期： </label>
										<div class="col-sm-2">
												<div class="input-group">
														<input class="form-control date-picker" name="start_date_after" type="text" readonly="readonly" data-date-format="yyyy-mm-dd"  value="${sysCashBack.start_date_after}"/>
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
												</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 失效日期： </label>
										<div class="col-sm-2">
												<div class="input-group">
														<input class="form-control date-picker" name="start_date_before"  type="text" readonly="readonly" data-date-format="yyyy-mm-dd" value="${sysCashBack.start_date_before}"/>
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
												</div>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-success" type="buttom" onclick="returnpage();">
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