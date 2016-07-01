<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
			<script src="<%=basePath %>/dist/js/sysparam/system_cashback.js"></script>
			
			<div class="">
				<form id="formcashback">

						<jsp:include page="/common/page_param.jsp"></jsp:include>
						<div class="page-header">
							<h1>
								返现设置
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									

									<div class="col-sm-2">
										<div class="dd dd-draghandle" >
											<ol class="dd-list" id="cashbackol">
											</ol>
										</div>
									</div>

									<div class="col-sm-10">
											<div class="page-header">
											<h1>
												返现配置列表
											</h1>
										</div>
					
										<div class="search-types">
											<input type="hidden" name="sys_cash_back_no" value="${sysCashBack.sys_cash_back_no}"/>
											<div class="item">
											    <label>优先级:</label>
												<select class="chosen-select" name="level" >
													<s:option flag="true" gcode="CASHBACKLEVEL" form="sysCashBack" field="level"/>
												</select>
											</div>
					
											<div class="item">
												<label>状态:</label>
												<select class="chosen-select " name="status" >
														 <s:option flag="true" gcode="CASHBACKSTATUS" form="sysCashBack" field="status" />
												</select>
											</div>
											
											<div class="item">
												<div class="input-daterange top" id="j-input-daterange-top">
													<label>生效时间:</label>
													<input type="text" class="" name="start_date_after"  value="${sysCashBack.start_date_after}" readonly="readonly"/>
													<span class="">
														<i class="fa fa-exchange"></i>
													</span>
													<input type="text" class="" name="start_date_before" value="${sysCashBack.start_date_before}" readonly="readonly"/>
												</div>
											</div>
					
											<div class="item">
												<button class="btn btn-sm btn-primary" type="button" id="newbutton">
													<i class="ace-icon fa fa-flask align-top bigger-125"></i>
													新建
												</button>
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
					
										<div class="table-header">返现配置信息列表</div>
					
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
														<th onclick="orderBy(this,'sys_cash_back_id');commitForm();" id="sys_cash_back_id_order">触发编号</th> 
<!-- 													<th onclick="orderBy(this,'sys_cash_back_no');commitForm();" id="sys_cash_back_no_order">触发条件</th>-->
														<th onclick="orderBy(this,'threshold_min_value');commitForm();" id="threshold_min_value_order">返现阈值</th>
														<th onclick="orderBy(this,'threshold_max_value');commitForm();" id="threshold_max_value_order">返现阈值</th>
														<th onclick="orderBy(this,'cash_per');commitForm();" id="cash_per_order">返点系数</th>
														<th onclick="orderBy(this,'status');commitForm();" id="status">状态</th>
														<th onclick="orderBy(this,'level');commitForm();" id="level">优先级</th>
														<th onclick="orderBy(this,'start_date');commitForm();" id="start_date_order"><i id="start_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>生效时间</th>
														<th onclick="orderBy(this,'end_date');commitForm();" id="end_date_order"><i id="end_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>失效时间</th>
														<th onclick="orderBy(this,'created_date');commitForm();" id="created_date_order"><i id="created_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
														<th onclick="orderBy(this,'updated_date');commitForm();" id="updated_date_order"><i id="updated_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>修改时间</th>
														<th class="text-center">更多操作</th>
													</tr>
												</thead>
					
												<tbody>
													
												<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
													<tr id="listobj">
														<td class="center">
															<label class="pos-rel"> 
																<input type="checkbox" class="ace" id="pks" value="${list.sys_cash_back_id}"/> 
																<span class="lbl"></span>
															</label>
														</td>
														<td>${list.sys_cash_back_id}</td>
													<%--<td>${list.sys_cash_back_no}</td>--%>
 													 	<td>${list.threshold_min_value}</td> 
														<td>${list.threshold_max_value}</td>
														<td>${list.cash_per} </td>
														<td><s:Code2Name mcode="${list.status}" gcode="CASHBACKSTATUS"></s:Code2Name></td>
														<td><s:Code2Name mcode="${list.level}" gcode="CASHBACKLEVEL"></s:Code2Name><input type="hidden" value="${list.level}"/></td>
														<td><fmt:formatDate value="${list.start_date}" type="both"/></td> 
														<td><fmt:formatDate value="${list.end_date}" type="both"/></td> 
														<td><fmt:formatDate value="${list.created_date}" type="both"/></td>
														<td><fmt:formatDate value="${list.updated_date}" type="both"/></td>
					
														<td class="text-center">
															<a class="option-btn-m" href="javascript:void(0);"  title="修改" data-rel="tooltip">
																<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
															</a>
															<a class="" href="javascript:void(0);" onclick="del(this);" title="删除" data-rel="tooltip">
																<i class="ace-icon fa fa-trash-o bigger-130"></i>
															</a>
														</td>
													</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										
										<div class="row">
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
										</div>
										
									</div>
								</div><!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
						
						<jsp:include page="/common/message.jsp"></jsp:include>
						
						</form>

			</div><!-- /.main-content -->

