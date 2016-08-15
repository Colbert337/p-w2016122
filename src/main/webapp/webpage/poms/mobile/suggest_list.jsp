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


<div class="">
	<script type="text/javascript"
		src="<%=basePath%>/common/js/jquery.form.js"></script>
	<script src="<%=basePath%>/dist/js/mobile/suggest_list.js"></script>
	<!-- /.page-header -->
	<form id="formcard">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="row">
					<div class="col-xs-12">
						<div class="page-header">
							<h1>意见投诉</h1>
						</div>

						<div class="search-types">
							<div class="item">
								<label>关键字:</label> <input type="text" id="text" name="suggest"
									placeholder="输入用关键字" maxlength="9" value="${suggest.suggest}" />
							</div>

							<div class="item">
								<div class="input-daterange top" id="j-input-daterange-top">
									<label>投诉时间:</label> <input type="text" class=""
										name="created_date_sel" value="${suggest.created_date_sel}"
										readonly="readonly" /> <span class=""> <i
										class="fa fa-exchange"></i>
									</span> <input type="text" class="" name="updated_date_sel"
										value="${suggest.updated_date_sel}" readonly="readonly" />
								</div>
							</div>

							<div class="item">

								<button id="logic-btn-card-search"
									class="btn btn-sm btn-primary" type="button">
									<i class="ace-icon fa fa-flask align-top bigger-125"></i> 查询
								</button>
								<button id="logic-btn-card-reset" class="btn btn-sm"
									type="button">
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
										<th class="center td-w1"><label class="pos-rel">
												<input type="checkbox" class="ace"
												onclick="checkedAllRows(this);" /> <span class="lbl"></span>
										</label></th>
										<th onclick="orderBy(this,'user_name');commitForm();"
											id="user_name_order">司机名称</th>
										<th onclick="orderBy(this,'mobile_phone');commitForm();"
											id="mobile_phone_order">司机电话</th>
										<th onclick="orderBy(this,'suggest');commitForm();" id="suggest_order">内容</th>
										<th onclick="orderBy(this,'suggest_res');commitForm();"
											id="suggest_res_order">来源</th>
										<th onclick="orderBy(this,'follow_up');commitForm();"
											id="follow_up_order">跟进人</th>
										<th onclick="orderBy(this,'created_date');commitForm();"
											id="created_date_order" class="td-w2"><i id="storage_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>投诉时间</th>
										<th onclick="orderBy(this,'updated_date');commitForm();"
											id="updated_date_order" class="td-w2"><i id="storage_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>修改时间</th>
										<th onclick="orderBy(this,'memo');commitForm();" id="memo_order">备注</th>
									</tr>
								</thead>

								<tbody>

									<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
										<tr class="logic-card-tbody-tr">
											<td class="center"><label class="pos-rel"> <input
													type="checkbox" class="ace" id="pks"
													value="${list.mb_user_suggest_id}" /> <span class="lbl"></span>
											</label></td>
											<td>${list.user_name}</td>
											<td>${list.mobile_phone}</td>
											<td>${list.suggest}</td>
											<td>${list.suggest_res}</td>
											<td>${list.follow_up}</td>
											<td><fmt:formatDate value="${list.created_date}" type="both" /></td>
											<td><fmt:formatDate value="${list.updated_date}" type="both" /></td>
											<td>${list.memo}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>


				<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formcard');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formcard');">
									<span aria-hidden="true">下一页</span>
								</a>
							</li>  
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
<!-- /.page-content -->
