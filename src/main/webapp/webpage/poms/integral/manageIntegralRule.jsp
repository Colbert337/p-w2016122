<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/integral/manageIntegralRule.js"></script>
<!-- /section:basics/content.breadcrumbs -->
<div class="">
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<form class="form-horizontal"  id="integralRuleForm">
				<jsp:include page="/common/page_param.jsp"></jsp:include>
					<div class="page-header">
						<h1>
							积分规则管理
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/integral/addIntegralRule.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125">新建</i>
							</button>
						</div>
					</div>
					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					<div class="table-header">积分规则信息列表</div>
					<!-- div.table-responsive -->
					<!-- div.dataTables_borderWrap -->
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th style="width:15%" onclick="orderBy(this,'integral_type');commitForm();" id="integral_type_order">积分类别</th>
									<th style="width:15%" onclick="orderBy(this,'reward_cycle');commitForm();" id="reward_cycle_order">限制周期</th>
									<th style="width:10%"onclick="orderBy(this,'limit_number');commitForm();" id="limit_number_order">限制次数</th>
									<th style="width:20%" onclick="orderBy(this,'integral_reward');commitForm();" id="integral_reward_order">积分奖励</th>
									<th style="width:5%" class="text-center td-w3">更多操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
									<tr>
										<td><s:Code2Name mcode="${list.integral_type}" gcode="INTEGRAL_TYPE"></s:Code2Name></td>
										<td><s:Code2Name mcode="${list.reward_cycle}" gcode="REWARD_CYCLE"></s:Code2Name></td>
										<td>${list.limit_number}</td>
										<td>
											<c:if test="${list.integral_reward_type=='1'}">
												<s:Code2Name mcode="${list.integral_reward}" gcode="INTEGRAL_REWARD"></s:Code2Name>分
											</c:if>
											<c:if test="${list.integral_reward_type=='2'}">
												<c:forTokens items="${list.integralStr}" delims="," var="integralStr">
													${integralStr}<br/>
												</c:forTokens>
											</c:if>
										</td>
										<td>
											<div class="text-center">
												<a class="option-btn-m" href="javascript:void(0);" title="修改" data-rel="tooltip">
													<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate('${list.integral_rule_id}');"></i>
												</a>
												<a class="option-btn-m" href="javascript:deleteIntegralRule('${list.integral_rule_id}');" title="删除" data-rel="tooltip">
													<span class="ace-icon fa fa-trash-o bigger-130"></span>
												</a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
					</div>
					<div class="col-sm-6">
						<nav>
							<ul id="ulhandle" class="pagination pull-right no-margin">
								<li id="previous">
									<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#integralRuleForm');">
										<span aria-hidden="true">上一页</span>
									</a>
								</li>
								<li id="next">
									<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#integralRuleForm');">
										<span aria-hidden="true">下一页</span>
									</a>
								</li>
							</ul>
						</nav>
					</div>
				</div>
				<jsp:include page="/common/message.jsp"></jsp:include>
			<!-- PAGE CONTENT ENDS -->
			</form>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->