<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

	<script src="<%=basePath %>/dist/js/card/card_move.js"></script>
	
			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">主页</a>
							</li>

							<li>
								<a href="#">资源管理</a>
							</li>
							<li class="active">用户卡管理</li>
						</ul><!-- /.breadcrumb -->

						<!-- #section:basics/content.searchbox -->
						<div class="nav-search" id="nav-search">
							<form class="form-search" >
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->

						<!-- /section:basics/content.searchbox -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								用户卡出库
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="newcardform">
								
									<input id="retCode" type="hidden" value=" ${ret.retCode}" />
									<input id="retMsg" type="hidden" value=" ${ret.retMsg}" />
									
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="card_no_1">用户卡起始编号： </label>

										<div class="col-sm-4">
											<input type="text" id="card_no_1"  name="card_no_start" placeholder="卡起始编号" class="form-control" maxlength="9"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="card_no_2">用户卡结束编号： </label>

										<div class="col-sm-4">
											<input type="text" id="card_no_2"  name="card_no_end" placeholder="卡结束编号" class="form-control" maxlength="9"/>
											<input type="hidden" name="card_no_arr" id="card_no_arr"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">调拨对象类型：</label>
										<div class="col-sm-4">
												<select class="form-control" id="workstation_type" name="workstation_type" onchange="init_workstation(this);">
													<s:option flag="true" gcode="STATION_TYPE" />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">调拨工作站：</label>
										<div class="col-sm-4">
												<select class="form-control" id="workstation" name="workstation">
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">工作站领取人：</label>
										<div class="col-sm-4">
												<input type="text" name="workstation_resp" class="form-control" id="workstation_resp" maxlength="20"/>
												<%-- <select class="form-control" id="workstation_resp" name="workstation_resp">
												</select> --%>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">操作人：</label>
										<div class="col-sm-4">
											<input type="text" id="operator" name="operator" class="form-control" maxlength="10" value="${sessionScope.currUser.user.userName}" readonly="readonly"/>
										</div>
									</div>
						
									<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" id="dynamic-table_after_handler">明细列表：</label>
											<div class="col-sm-7">
												<div id="dynamic-table_div">
													<div class="table-header">用户卡列表</div>
													<table id="dynamic-table" class="table table-striped table-bordered table-hover">
														<thead>
															<tr>
																<th id="card_no_order">用户卡号</th>
																<th id="card_type_order">调拨工作站</th>
																<th id="card_name_order">工作站领取人</th>
																<th id="card_status_order">用户卡状态</th>
																<th id="operator_order">操作人</th>
															</tr>
														</thead>
														<tbody>
														</tbody>
													</table>
												</div>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="button" onclick="dynamicSheet();" id="init_dynamic_data">
												<i class="ace-icon fa fa-check bigger-110"></i>
												生成卡列表
											</button>
											
											<button class="btn btn-info" type="button" onclick="save();" disabled="disabled" id="storage_data">
												<i class="ace-icon fa fa-check bigger-110"></i>
												用户卡出库
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset" onclick="init();">
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
