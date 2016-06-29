<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/advance/fleet_quota_list.js"></script>
<div class="page-header">
	<h1>
		财务管理
		<%--<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			用户列表
		</small>--%>
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal"  id="listForm">
			<jsp:include page="/common/page_param.jsp"></jsp:include>
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<%--顶部条件搜索及按钮--%>
					<div class="search-types">
						<div class="item">
							<input type="text" name="fleetName" placeholder="车队名称/队长姓名"  maxlength="11" value="${fleetQuota.fleetName}"/>
						</div>
						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								重置
							</button>
							<div class="item"></div>
							<%--<button class="btn btn-sm btn-primary" type="button" onclick="addChongzhi();">
								充值
							</button>--%>
							<button class="btn btn-sm btn-primary" type="button" onclick="addFenpei();">
								资金分配
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="addZhuan();">
								个人转账
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="addPassword();">
								支付密码修改
							</button>
						</div>
					</div>
					<%--</h4>--%>
					<div class="alert alert-info">
						<label style="font-size: 18px;">账户余额：${fleetQuotaMap.userAccount.accountBalance}元</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label style="font-size: 18px;">未分配资金：${fleetQuotaMap.weifenpeiVal}元</label>
						<input type="hidden" id="sysTransportId" name="sysTransportId" value="${stationId}"/>
					</div>
					<%--<table id="simple-table" class="table table-striped table-bordered table-hover">--%>
					<table id="dynamic-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th>车队名称</th>
							<th>队长姓名</th>
							<th>手机号</th>
							<th>是否分配资金</th>
							<th>可用资金</th>
							<th>上次分配时间</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${fleetQuotaMap.fleetQuotaList}" var="fleetQuota">
							<tr>
								<td>${fleetQuota.fleetName}</td>
								<td>${fleetQuota.realName}</td>
								<td>${fleetQuota.mobilePhone}</td>
								<td>
									<c:choose>
										<c:when test="${fleetQuota.isAllot == 0}">
											未分配
										</c:when>
										<c:when test="${fleetQuota.isAllot == 1}">
											已分配
										</c:when>
										<c:otherwise>未分配</c:otherwise>
									</c:choose>
								</td>
								<td>${fleetQuota.quota}</td>
								<td><fmt:formatDate value="${fleetQuota.updatedDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div><!-- /.col-xs-12 -->
			</div><!-- /.row -->
			<%--分页start--%>
			<%--<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">共 ${pageInfo.total} 条</div>
				</div>
				<div class="col-sm-6">
					<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
						<ul id="ulhandle" class="pagination">
							<li id="previous" class="paginate_button previous" aria-controls="dynamic-table" tabindex="0">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#listForm');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next" class="paginate_button next" aria-controls="dynamic-table" tabindex="0">
								<a id="nexthandle" href="javascript:nextpage('#listForm');" aria-label="Next" >
									<span aria-hidden="true">下一页</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>--%>
			<%--分页 end--%>
		</form>
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->

<!--充值弹层-开始-->
<div id="chongzhiModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="chongzhiModalLabel">充值</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group">
								<label class="col-sm-4 control-label no-padding-right" ><span class="red_star">*</span> 充值方式： </label>
								<div class="col-sm-7" style="padding-bottom: 18px;">
									<div class="radio">
										<label>
											<input name="gender" id="zhifubao" type="radio" class="ace" checked="checked" value="0">
											<span class="lbl"> 支付宝</span>
										</label>
										<label>
											<input name="gender" id="weixin" type="radio" class="ace" value="1">
											<span class="lbl"> 微信</span>
										</label>
										<label>
											<input name="gender" id="yinlian" type="radio" class="ace" value="1">
											<span class="lbl"> 银联</span>
										</label>
										<label>
											<input name="gender" id="chongzhi" type="radio" class="ace" value="1">
											<span class="lbl"> 充值卡</span>
										</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label no-padding-right" for="account_balance"><span class="red_star">*</span> 充值金额： </label>
								<div class="col-sm-7">
									<input type="text" id="account_balance" name="accountBalance" placeholder="充值金额" class="col-xs-10 col-sm-11" />元
								</div>
							</div>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
				<!--底部按钮 -->
				<div class="row">
					<div class="space"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveChongzhi()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('chongzhiModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--充值弹层-结束-->

<!--添加资金分配弹层-开始-->
<div id="fenpeiModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" style="width: 900px;" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="fenpeiModalLabel">资金分配</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="fenpeiForm">
								<!-- #section:elements.form -->
								<input type="hidden" id="station_id" name="stationId" value="${stationId}"/>
								<table id="fenpei" class="table table-striped table-bordered table-hover" width="700">
									<thead>
									<tr>
										<th width="15%">车队名称</th>
										<th width="10%">队长姓名</th>
										<th width="15%">手机号</th>
										<th width="20%">是否分配资金</th>
										<th width="20%">可用资金(元)</th>
									</tr>
									</thead>
									<tbody>
										<c:forEach items="${fleetQuotaMap.fleetQuotaList}" var="fleetQuota" varStatus="suffix">
											<tr>
												<td>${fleetQuota.fleetName}</td>
												<td>${fleetQuota.realName}</td>
												<td>${fleetQuota.mobilePhone}</td>
												<td>
													<input type="hidden" id="tc_fleet_id" name="tcFleetId" value="${fleetQuota.tcFleetId}"/>
													<input type="hidden" id="is_allot_${suffix.index}" name="isAllot" value="${fleetQuota.isAllot}"/>
													<input id="allot_${suffix.index}"
													   <c:if test="${fleetQuota.isAllot != null && fleetQuota.isAllot == 1}"> checked="checked" </c:if>
													   type="checkbox" class="ace ace-switch ace-switch-5 zh" onclick="allocation(this,'${suffix.index}');">
													<span class="lbl middle"></span>
												</td>
												<td>
													<input type="text" id="quota_${suffix.index}" name="quota" value="${fleetQuota.quota}"
													<c:if test="${fleetQuota.isAllot == null || fleetQuota.isAllot == 0}"> readonly="readonly" </c:if> class="col-xs-8 col-sm-8"/>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
				<!--底部按钮 -->
				<div class="row">
					<div class="space"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveFenpei()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('fenpeiModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加资金分配弹层-结束-->

<!--添加个人转账弹层-开始-->
<div id="zhuanModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" style="width: 700px;" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="zhuanModalLabel">个人转账</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="zhuanForm">
								<!-- #section:elements.form -->
								<table id="zhuanTable" class="table table-striped table-bordered table-hover">
									<thead>
									<tr>
										<th width="20%">手机号码</th>
										<th width="10%">对方姓名</th>
										<th width="15%">转账金额</th>
										<th width="25%">用途</th>
										<th width="10%">操作</th>
									</tr>
									</thead>
									<tbody>
										<tr>
											<td id="tr_1">
												<input type="text"  id="mobile_phone_1" name="mobilePhone" class="col-sm-12" onblur="queryDriverInfo(1);"/>
											</td>
											<td>
												<input type="text"  id="full_name_1" name="fullName" class="col-sm-12" readonly="readonly"/>
												<input type="hidden" id="sys_driver_id_1" name="sysDriverId" class="col-sm-12"/>
											</td>
											<td>
												<input type="text" id="amount_1" name="amount" class="col-sm-12"/>
											</td>
											<td>
												<input type="text" id="remark_1" name="remark" class="col-sm-12"/>
											</td>
											<td></td>
										</tr>
									</tbody>
								</table>
								<tr><td colspan="4" align="right"><a href="javascript:addRow();">添加</a></td></tr>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
				<!--底部按钮 -->
				<div class="row">
					<div class="space"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-3"><button class="btn btn-primary" onclick="saveZhuan()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('zhuanModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加个人转账弹层-结束-->

<!--添加修改密码弹层-开始-->
<div id="passwordModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="passwordModalLabel">支付密码修改</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div id="firstDiv" class="col-xs-12" style="display: none;">
							<!-- 首次设置 -->
							您的支付密码还未设置，点击提交按钮后，我们会向您注册账号时的邮箱发送验证邮件，请您及时进行后续操作。
						</div>
						<div id="paswordDiv" class="col-xs-12">
							<form class="form-horizontal" id="passwordForm">
								<!-- #section:elements.form -->
								<div class="form-group">
									<label class="col-sm-4 control-label no-padding-right" ><span class="red_star">*</span> 充值方式： </label>
									<div class="col-sm-7" style="padding-bottom: 18px;">
										<div class="radio">
											<label>
												<input name="gender" id="passwordUpdate" type="radio" class="ace" checked="checked" value="0" onclick="changePassDiv('updatePsDiv','lossPsDiv')">
												<span class="lbl"> 密码修改</span>
											</label>
											<label>
												<input name="gender" id="passwordLoss" type="radio" class="ace" value="1" onclick="changePassDiv('lossPsDiv','updatePsDiv')">
												<span class="lbl"> 密码遗失</span>
											</label>
										</div>
									</div>
								</div>
								<div id="updatePsDiv" style="display: block;">
									<!-- 修改密码 -->
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right" for="old_password"><span class="red_star">*</span> 原支付密码： </label>
										<div class="col-sm-7">
											<input type="password" id="old_password" name="oldPassword" placeholder="原支付密码" class="col-xs-10 col-sm-12" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right" for="new_password"><span class="red_star">*</span> 新支付密码： </label>
										<div class="col-sm-7">
											<input type="password" id="new_password" name="pay_code" placeholder="新支付密码" class="col-xs-10 col-sm-12" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right" for="rep_password"><span class="red_star">*</span> 确认新支付密码： </label>
										<div class="col-sm-7">
											<input type="password" id="rep_password" name="rePassword" placeholder="确认新支付密码" class="col-xs-10 col-sm-12" />
										</div>
									</div>
								</div>
								<div id="lossPsDiv" class="col-xs-12" style="display: none;">
									<!-- 遗失密码 -->
									如果支付密码遗失，我们会向您注册账号时的邮箱发送验证邮件，请您及时进行后续操作。
								</div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
				<!--底部按钮 -->
				<div class="row">
					<div class="space"></div>
					<div class="col-xs-3"></div>
					<div class="col-xs-3"><button class="btn btn-primary" onclick="savePassword()">确   定</button></div>
					<div class="col-xs-6"><button class="btn" i="close" onclick="closeDialog('passwordModel')">取   消 </button></div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<!--添加修改密码弹层-结束-->