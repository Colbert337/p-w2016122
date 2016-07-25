<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>


<div class="">

	<!-- /.page-header -->
	<form id="formcard">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

			<div class="row">
				<div class="col-xs-12">
					<div class="page-header">
						<h1>
							充值卡管理
						</h1>
					</div>

				<div class="search-types">
						<div class="item">
						    <label>用户卡号:</label>
							  <input type="text" name=id placeholder="输入用户卡号"  maxlength="9" value="${sysongyChargeCard.id}"/>
						</div>																										
						<div class="item">
							<button id="logic-btn-card-search" class="btn btn-sm btn-primary" type="button">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button id="logic-btn-card-reset" class="btn btn-sm" type="button">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								重置
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
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center td-w1">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>
									<th onclick="orderBy(this,'card_no');" id="card_no_order">用户卡号</th>
									<th onclick="orderBy(this,'card_type');" id="card_type_order">用户卡密码</th>
									<th onclick="orderBy(this,'card_status');" id="card_status_order">充值时间</th>
									<th onclick="orderBy(this,'card_property');" id="card_property_order">充值金额</th>
									<th class="text-center">操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${list}" var="sysongyChargeCard" varStatus="s">
								<tr class="logic-card-tbody-tr">
									<td class="center">
										<label class="pos-rel"> 
											 <input type="checkbox" class="ace" id="pks" value="${sysongyChargeCard.id}"/> 
											<span class="lbl"></span>
										</label>
									</td>
								    <td>${sysongyChargeCard.ownid}</td>
								 	<td>${sysongyChargeCard.password}</td> 
									<td>${sysongyChargeCard.useTime}</td>
									<td>${sysongyChargeCard.cash}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			

			  <!-- <div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条|共 ${pageInfo.total} 条|共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formcard');">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formcard');">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>  
						</ul>
					</nav>
				</div>
			</div>-->

			<jsp:include page="/common/message.jsp"></jsp:include>


			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>
<!-- /.page-content -->

<script src="<%=basePath %>/dist/js/sjny.admin.card.js"></script>