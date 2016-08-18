<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/card/card_new.js"></script>

			<div class="main-content">
				<div class="main-content-inner">

					<div class="page-content">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								实体卡入库
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="newcardform">
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="card_no_1"> 用户卡起始编号： </label>

										<div class="col-sm-4">
											<input type="text" id="card_no_1" name="card_no_start" placeholder="卡起始编号" class="form-control" maxlength="9"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="card_no_2"> 用户卡结束编号： </label>

										<div class="col-sm-4">
											<input type="text" id="card_no_2" name="card_no_end" placeholder="卡结束编号" class="form-control" maxlength="9"/>
											<input type="hidden" name="card_no_arr" id="card_no_arr"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 用户卡类型： </label>
										<div class="col-sm-4">
												<select class="form-control" id="card_type" name="card_type">
														<s:option flag="true" gcode="CARDTYPE" />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 用户卡属性： </label>
										<div class="col-sm-4">
												<select class="form-control" id="card_property" name="card_property">
														<s:option flag="true" gcode="CARDPROPERTY" />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="operator">操作人： </label>

										<div class="col-sm-4">
											<input type="text" id="operator" name="operator" class="form-control" readonly="readonly" value="${sessionScope.currUser.user.userName}"/>
										</div>
									</div>
						
									<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" id="dynamic-table_after_handler"> 明细列表： </label>
											<div class="col-sm-7">
												<div id="dynamic-table_div">
													<div class="table-header">用户卡列表</div>
													<table id="dynamic-table" class="table table-striped table-bordered table-hover">
														<thead>
															<tr>
																<th id="card_no_order">用户卡号</th>
																<th id="card_type_order">用户卡类型</th>
																<th id="card_name_order">用户卡属性</th>
																<th id="card_status_order">库存状态</th>
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
												用户卡入库
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
