<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script src="<%=basePath %>/dist/js/integral/addIntegralRule.js"></script>
	<style type="text/css">
	#limitNumber i.form-control-feedback{
		right: -100px;
	}
	#integralLadder i.form-control-feedback{
		display:none !important;
	}
	</style>
	<div class="">
		<!-- /section:settings.box -->
		<div class="page-header">
			<h1>
				新建积分规则
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<form class="form-horizontal" id="integralRuleform">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="form-group">
						<label for="integral_type" class="col-sm-3 control-label no-padding-right">积分类型：</label>
						<div class="col-sm-4">
							<select class="form-control" name="integral_type" onchange="changeintegralType()">
								<s:option flag="true" gcode="INTEGRAL_TYPE" form="integralRuleform" field="integral_type" />
							</select>
							<input type="hidden" name="integral_reward_type"/>
						</div>
					</div>
					<div class="form-group">
						<label for="reward_cycle" class="col-sm-3 control-label no-padding-right">奖励周期：</label>
						<div class="col-sm-4">
							<select class="form-control" name="reward_cycle" onchange="changeRewardCycle()">
								<s:option flag="true" gcode="REWARD_CYCLE" form="integralRuleform" field="reward_cycle" />
							</select>
						</div>
					</div>
					<div class="form-group" id="limitNumber">
						<label for="limit_number" class="col-sm-3 control-label no-padding-right">奖励次数：</label>
						<div class="col-sm-4">
							<label class="radio-inline">
								<input name="limit"  type="radio" class="ace" value="1" checked="checked" onclick="changeLimitType()">
									<span class="lbl"><input type="text" name="limit_number" class="number" style="width:60px" maxlength="4" size="4"/>&nbsp;次</span>
							</label>
							<label class="radio-inline">
								<input name="limit"  type="radio" class="ace" value="2" onclick="changeLimitType()">
									<span class="lbl">不限<input type="hidden" name="nolimit" value="不限"/></span>
							</label>
						</div>
					</div>
					<div class="form-group" id="integralReward">
						<label for="integralreward" class="col-sm-3 control-label no-padding-right">奖励积分：</label>
						<div class="col-sm-4">
							<select  name="integral_reward">
							<s:option flag="true" gcode="INTEGRAL_REWARD" form="integralRuleform" field="integral_reward" />
							</select>
						</div>
					</div>
					<div class="form-group" style="display:none" id="integralLadder">
						<label class="col-sm-3 control-label no-padding-right">奖励积分：</label>
						<div class="col-sm-4">
							<div>
								<button class="btn btn-sm btn-primary" type="button" onclick="addLadder()">新增积分阶梯</button>
								<button class="btn btn-sm btn-primary" type="button" onclick="delLadder()">清空</button>
							</div>
							<div id="dynamic-table_div" style="margin-top: 5px;">
								<input type="hidden" name="reward_type"/>
								<input type="hidden" name="reward_integral"/>
								<input type="hidden" name="reward_factor"/>
								<input type="hidden" name="reward_max"/>
								<table id="dynamic-table" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th style="text-align:center">积分阶梯</th>
											<th style="text-align:center">积分奖励</th>
										</tr>
									</thead>
									<tbody id="integralRule">
										<tr>
											<td><input type="text" maxlength="5" size="5" style="width:50px" class="number" onchange="checkladdermoney(this)" name="ladder_before"/>元~<input type="text"  size="5" style="width:50px" onchange="checkladdermoney(this)" maxlength="5" class="number" name="ladder_after"/>元</td>
											<td>
												<input name="reward_type0"  type="radio" class="ace" value="1" checked="checked" onclick="changeRewardType(this)">
												<span class="lbl">
														<input type="text" class="number" style="width:60px" maxlength="5"  size="5" name="rewardintegral" />
												</span>
												<input name="reward_type0"  type="radio" class="ace" value="2" onclick="changeRewardType(this)">
												<span class="lbl">金额 * <input type="text" class="number" maxlength="5" style="width:60px" onchange="checkfactor(this)"  size="5" name="rewardfactor" disabled='disabled' />%<=<input type="text"  style="width:60px"  class="number" size="5" maxlength="5" name="rewardmax" disabled='disabled' />分</span>
											</td>
										</tr>
									</tbody>
								</table>
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