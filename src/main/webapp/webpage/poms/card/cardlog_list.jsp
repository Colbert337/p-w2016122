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
<script src="<%=basePath%>/dist/js/card/cardlog_list.js"></script>

<!-- /section:basics/content.breadcrumbs -->
<div class="">

	<!-- /.page-header -->
	<form id="formcard">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="row">
					<div class="col-xs-12">
						<div class="page-header">
							<h1>用户卡轨迹信息</h1>
						</div>
						<div class="search-types">
							<div class="item">
								<label>用户卡号:</label> <input type="text" name="card_no"
									placeholder="输入用户卡号" maxlength="9"
									value="${gascardlog.card_no}" />
							</div>

							<div class="item">
								<label>用户卡类型:</label> <select class="chosen-select "
									name="card_type">
									<s:option flag="true" gcode="CARDTYPE" form="gascardlog"
										field="card_type" />
								</select>
							</div>

							<div class="item">
								<label>用户卡状态:</label> <select class="chosen-select "
									name="card_status">
									<s:option flag="true" gcode="CARDSTATUS" form="gascardlog"
										field="card_status" />
								</select>
							</div>

							<div class="item">
								<label>操作员:</label> <input type="text" name="operator"
									placeholder="输入操作员" maxlength="10"
									value="${gascardlog.operator}" />
							</div>

							<div class="item">
								<div class="input-daterange top" id="j-input-daterange-top">
									<label>操作时间:</label> <input type="text" class=""
										name="optime_after" value="${gascardlog.optime_after}" /> <span
										class=""> <i class="fa fa-exchange"></i>
									</span> <input type="text" class="" name="optime_before"
										value="${gascardlog.optime_before}" />
								</div>
							</div>

							<div class="item">
								<button class="btn btn-sm btn-primary" type="button"
									onclick="commitForm();">
									<i class="ace-icon fa fa-flask align-top bigger-125"></i> 查询
								</button>
								<button class="btn btn-sm" type="button" onclick="init();">
									<i class="ace-icon fa fa-flask align-top bigger-125"></i> 重置
								</button>
							</div>

						</div>

						<div class="clearfix">
							<div class="pull-right tableTools-container"></div>
						</div>

						<div class="table-header">用户卡详细信息列表</div>

						<!-- div.table-responsive -->

						<!-- div.dataTables_borderWrap -->
						<div class="sjny-table-responsive">
							<table id="dynamic-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<%--<th class="center td-w1"><label class="pos-rel">
												<input type="checkbox" class="ace"
												onclick="checkedAllRows(this);" /> <span class="lbl"></span>
										</label></th>--%>
										<th onclick="orderBy(this,'card_no');commitForm();"
											id="card_no_order">用户卡号</th>
										<th onclick="orderBy(this,'card_type');commitForm();"
											id="card_type_order">用户卡类型</th>
										<th onclick="orderBy(this,'card_status');commitForm();"
											id="card_status_order">用户卡状态</th>
										<th onclick="orderBy(this,'card_property');commitForm();"
											id="card_property_order">用户卡属性</th>
										<th onclick="orderBy(this,'workstation');commitForm();"
											id="workstation_order">所属工作站</th>
										<th onclick="orderBy(this,'workstation_resp');commitForm();"
											id="workstation_resp_order">工作站领取人</th>
										<th onclick="orderBy(this,'operator');commitForm();"
											id="operator_order">操作员</th>
										<th onclick="orderBy(this,'batch_no');commitForm();"
											id="batch_no_order">批次号</th>
										<th onclick="orderBy(this,'storage_time');commitForm();"
											id="storage_time_order" class="td-w2"><i
											id="storage_time"
											class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>入库时间</th>
										<th onclick="orderBy(this,'release_time');commitForm();"
											id="release_time_order" class="td-w2"><i
											id="release_time"
											class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>出库时间</th>
										<th onclick="orderBy(this,'optime');commitForm();"
											id="optime_order" class="td-w2"><i id="optime"
											class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>操作时间</th>
										<th onclick="orderBy(this,'action');commitForm();"
											id="action_order">操作动作</th>
											<th onclick="orderBy(this,'action');commitForm();"
											id="action_order">操作</th>
									</tr>
								</thead>

								<tbody>

									<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
										<tr id="listobj">
											<%--<td class="center"><label class="pos-rel"> <input
													type="checkbox" class="ace" id="pks"
													value="${list.card_no}" /> <span class="lbl"></span>
											</label></td>--%>

											<td>${list.card_no}</td>
											<td><s:Code2Name mcode="${list.card_type}"
													gcode="CARDTYPE"></s:Code2Name></td>
											<td><s:Code2Name mcode="${list.card_status}"
													gcode="CARDSTATUS"></s:Code2Name></td>
											<td><s:Code2Name mcode="${list.card_property}"
													gcode="CARDPROPERTY"></s:Code2Name></td>
											<td><s:Code2Name mcode="${list.workstation}"
													gcode="WORKSTATION"></s:Code2Name>
												<s:Code2Name mcode="${list.workstation}" gcode="TRANSTION"></s:Code2Name></td>
											<td>${list.workstation_resp}</td>
											<td>${list.operator}</td>
											<td>${list.batch_no}</td>
											<td><fmt:formatDate value="${list.storage_time}"
													type="both" /></td>
											<td><fmt:formatDate value="${list.release_time}"
													type="both" /></td>
											<td><fmt:formatDate value="${list.optime}" type="both" /></td>
											<td><s:Code2Name mcode="${list.action}" gcode="ACTION"></s:Code2Name></td>
											<td><a href="../web/card/generateaTable?batch_no=${list.batch_no }"  title="导出记录"
												><i
													class="fa fa-bookmark-o bigger-130"></i>
											</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>


				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">
						每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页
						&nbsp;&nbsp;转到第 <input type="text" name="convertPageNum" style="height:25px;width:45px" maxlength="4"/>  页
						<button type="button" class="btn btn-white btn-sm btn-primary" onclick="commitForm();">跳转</button>
					</div>
					</div>
					<div class="col-sm-6">
						<nav>
							<ul id="ulhandle" class="pagination pull-right no-margin">
								<li id="previous"><a href="javascript:void(0);"
									aria-label="Previous" onclick="prepage('#formcard');"> <span
										aria-hidden="true">上一页</span>
								</a></li>
								<li id="next"><a id="nexthandle" href="javascript:void(0);"
									aria-label="Next" onclick="nextpage('#formcard');"> <span
										aria-hidden="true">下一页</span>
								</a></li>
							</ul>
						</nav>
					</div>
				</div>


				<jsp:include page="/common/message.jsp"></jsp:include>


				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</form>
</div>