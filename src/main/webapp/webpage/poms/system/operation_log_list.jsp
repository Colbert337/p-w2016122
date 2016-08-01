<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/sysparam/operation_log_list.js"></script>

<div class="">

	<form id="formoperation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							系统业务日志
						</h1>
					</div>

					<div class="search-types">	
						<div class="item">
						    <label>系统日志编号:</label>
							<input type="text" name="sysDriverId" placeholder="输入系统日志编号"  maxlength="32" value="${driver.sysDriverId}"/>
						</div>
						
						<div class="item">
						    <label>平台类型:</label>
							<select class="chosen-select " name="level" >
								<s:option flag="true" gcode="CASHBACKLEVEL" form="sysCashBack" field="level"/>
							</select>
						</div>

						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>申请时间:</label>
								<input type="text" name="createdDate_after" value="${driver.createdDate_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" name="createdDate_before" value="${driver.createdDate_before}" readonly="readonly"/>
							</div>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								重置
							</button>
						</div>
					</div>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					<div class="table-header">会员信息详细信息列表</div>

					<!-- div.table-responsive -->

					<!-- div.dataTables_borderWrap -->
					<div>
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
											<span class="lbl"></span>
										</label>
									</th>
									<th onclick="orderBy(this,'sys_driver_id');commitForm();" id="sys_driver_id_order">个人用户编号</th>
									<th onclick="orderBy(this,'user_name');commitForm();" id="user_name_order">会员账号</th>
									<th onclick="orderBy(this,'card_id');commitForm();" id="card_id_order">实体卡号</th>
									<th onclick="orderBy(this,'regis_source');commitForm();" id="regis_source_order">注册来源</th>
									<th onclick="orderBy(this,'regis_source');commitForm();" id="regis_source_order">注册公司</th>
									<th onclick="orderBy(this,'sys_transport_id');commitForm();" id="sys_transport_id_order">关联运输公司</th>
									<th onclick="orderBy(this,'is_ident');commitForm();" id="address_order">可用余额</th> 
									<th onclick="orderBy(this,'created_date');commitForm();" id="created_time_order"><i id="created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>申请时间</th>
									<th onclick="orderBy(this,'checked_status');commitForm();" id="address_order">实体卡状态</th>
									<th onclick="orderBy(this,'checked_status');commitForm();" id="checked_status_order">审核状态</th> 
									<th onclick="orderBy(this,'checked_status');commitForm();" id="checked_status_order">用户状态</th> 
									<th>更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.sysDriverId}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.sysDriverId}</td>
								 	<td>${list.userName}</td> 
								 	<td id="sysUserAccountId" style="display: none;">${list.sysUserAccountId}</td>
									<td>${list.cardId}</td>
									<c:choose>
										<c:when test="${fn:contains(list.stationId, 'GS')}">
											<td>加注站</td>
										</c:when>
										<c:when test="${fn:contains(list.stationId, 'T')}">
											<td>运输公司</td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>
									<td>${list.regisSource}</td>
									<td>${list.stationId}</td>
									<td>${list.account.accountBalance}</td> 
									<td><fmt:formatDate value="${list.createdDate}" type="both"/></td>
									<td><s:Code2Name mcode="${list.cardInfo.card_status}" gcode="CARDSTATUS"></s:Code2Name></td>
									<td><s:Code2Name mcode="${list.checkedStatus}" gcode="CHECKED_STATUS"></s:Code2Name></td>
									<td><s:Code2Name mcode="${list.account.account_status}" gcode="ACCOUNT_STATUS"></s:Code2Name></td>
									<td>
										<div class="btn-toolbar">
											<div class="btn-group">
												<button data-toggle="dropdown" class="btn btn-info btn-sm dropdown-toggle">
													状态修改
													<span class="ace-icon fa fa-caret-down icon-on-right"></span>
												</button>

												<ul class="dropdown-menu dropdown-info dropdown-menu-right">
													<li>
														<a href="javascript:void(0);" onclick="change(this,0);">冻结用户</a>
													</li>
													
													<li>
														<a href="javascript:void(0);" onclick="change(this,1);">冻结卡</a>
													</li>

													<li>
														<a href="javascript:void(0);" onclick="change(this,2);">解冻</a>
													</li>
												</ul>
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