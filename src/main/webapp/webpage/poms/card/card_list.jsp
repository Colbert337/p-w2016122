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
							用户卡管理
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>用户卡号:</label>
							<input type="text" name="card_no" placeholder="输入用户卡号"  maxlength="9" value="${gascard.card_no}"/>
						</div>
						
						<div class="item">
						    <label>用户卡类型:</label>
							<select class="chosen-select " name="card_type" >
									<s:option flag="true" gcode="CARDTYPE" form="gascard" field="card_type"/>
							</select>
						</div>

						<div class="item">
							<label>用户卡状态:</label>
							<select class="chosen-select " name="card_status" >
									 <s:option flag="true" gcode="CARDSTATUS" form="gascard" field="card_status" />
							</select>
						</div>
						
						<div class="item">
						    <label>操作员:</label>
							<input type="text" name="operator" placeholder="输入操作员"  maxlength="10" value="${gascard.operator}"/>
						</div>
						
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>入库时间:</label>
								<input type="text" class="" name="storage_time_after"  value="${gascard.storage_time_after}"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="storage_time_before" value="${gascard.storage_time_before}"/>
							</div>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/card/card_new.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								入库
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/card/card_move.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								出库
							</button>
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
									<th onclick="orderBy(this,'card_type');" id="card_type_order">用户卡类型</th>
									<th onclick="orderBy(this,'card_status');" id="card_status_order">用户卡状态</th>
									<th onclick="orderBy(this,'card_property');" id="card_property_order">用户卡属性</th>
									<th onclick="orderBy(this,'workstation');" id="workstation_order">所属工作站</th>
									<th onclick="orderBy(this,'workstation_resp');" id="workstation_resp_order">工作站领取人</th>
									<th onclick="orderBy(this,'operator');" id="operator_order">操作员</th>
									<th onclick="orderBy(this,'batch_no');" id="batch_no_order">入库批次号</th>
									<th onclick="orderBy(this,'storage_time');" id="storage_time_order" class="td-w2"><i id="storage_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>入库时间</th>
									<th onclick="orderBy(this,'release_time');" id="release_time_order" class="td-w2"><i id="release_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>出库时间</th>
									<th class="text-center">操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr class="logic-card-tbody-tr">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.card_no}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.card_no}</td>
								 	<td><s:Code2Name mcode="${list.card_type}" gcode="CARDTYPE"></s:Code2Name> </td> 
									<td><s:Code2Name mcode="${list.card_status}" gcode="CARDSTATUS"></s:Code2Name> </td>
									<td><s:Code2Name mcode="${list.card_property}" gcode="CARDPROPERTY"></s:Code2Name> </td>
									<td><s:Code2Name mcode="${list.workstation}" gcode="WORKSTATION"></s:Code2Name><s:Code2Name mcode="${list.workstation}" gcode="TRANSTION"></s:Code2Name></td>
									<td>${list.workstation_resp}</td>
									<td>${list.operator}</td> 
									<td>${list.batch_no}</td> 
									<td><fmt:formatDate value="${list.storage_time}" type="both"/></td>
									<td><fmt:formatDate value="${list.release_time}" type="both"/></td>

									<td class="text-center">
										<c:if test="${list.card_status != 0 && list.card_status != 4}">
											<a href="" class="logic-del" title="删除" data-rel="tooltip">
												<i class="ace-icon fa fa-trash-o bigger-130"></i>
											</a>
										</c:if>
									</td>
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

<script src="<%=basePath %>/dist/js/sjny.admin.card.js"></script>