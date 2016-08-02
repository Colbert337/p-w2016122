<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
			<script src="<%=basePath %>/dist/js/mobile/banner_list.js"></script>
			<div class="">
				<form id="formcashback">
						<jsp:include page="/common/page_param.jsp"></jsp:include>
						<div class="page-header">
							<h1>
								首页编辑
							</h1>
						</div><!-- /.page-header -->
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-sm-2">
										<div class="dd dd-draghandle" >
											<ol class="dd-list" id="cashbackol">
												<li class='dd-item dd2-item' data-id='14' onclick='choose(this);' value=''>
													<div class='dd-handle dd2-handle'>
														<i class='normal-icon ace-icon fa fa-clock-o pink bigger-130'></i>
														<i class='drag-icon ace-icon fa fa-arrows bigger-125'></i>
													</div>
													<div class='dd2-content btn-info'>首页图片</div>
												</li>
												<li class='dd-item dd2-item' data-id='14' onclick='choose(this);' value=''>
													<div class='dd-handle dd2-handle'>
														<i class='normal-icon ace-icon fa fa-clock-o pink bigger-130'></i>
														<i class='drag-icon ace-icon fa fa-arrows bigger-125'></i>
													</div>
													<div class='dd2-content'>活动模块</div>
												</li>
											</ol>
										</div>
									</div>

									<div class="col-sm-10">
											<div class="page-header">
											<h1>
												图片列表
											</h1>
										</div>
					
										<div class="search-types">
											<%--<input type="hidden" name="sys_cash_back_no" value=""/>
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
													<input type="text" class="" name="start_date_after"  value="" readonly="readonly"/>
													<span class="">
														<i class="fa fa-exchange"></i>
													</span>
													<input type="text" class="" name="start_date_before" value="" readonly="readonly"/>
												</div>
											</div>--%>
					
											<div class="item">
												<button class="btn btn-sm btn-primary" type="button" id="newbutton">
													<i class="ace-icon fa fa-flask align-top bigger-125"></i>
													新建
												</button>
												<%--<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
													<i class="ace-icon fa fa-flask align-top bigger-125"></i>
													查询
												</button>
												<button class="btn btn-sm" type="button" onclick="init();">
													<i class="ace-icon fa fa-flask align-top bigger-125"></i>
													重置
												</button>--%>
											</div>
										</div>
					
										<div class="clearfix">
											<div class="pull-right tableTools-container"></div>
										</div>
					
										<div class="table-header">图片列表</div>
					
										<!-- div.table-responsive -->
					
										<!-- div.dataTables_borderWrap -->
										<div class="sjny-table-responsive">
											<table id="dynamic-table" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th onclick="orderBy(this,'sys_cash_back_id');commitForm();" id="sys_cash_back_id_order">序号</th>
														<th onclick="orderBy(this,'threshold_min_value');commitForm();" id="threshold_min_value_order">标题</th>
														<th onclick="orderBy(this,'threshold_max_value');commitForm();" id="threshold_max_value_order">缩略图</th>
														<th onclick="orderBy(this,'cash_per');commitForm();" id="cash_per_order">链接地址</th>
														<th onclick="orderBy(this,'status');commitForm();" id="status">版本号</th>
														<th onclick="orderBy(this,'level');commitForm();" id="level">备注</th>
														<th onclick="orderBy(this,'created_date');commitForm();" id="created_date_order" class="td-w2"><i id="created_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
														<th class="text-center td-w3">操作</th>
													</tr>
												</thead>
					
												<tbody>
													
												<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
													<tr id="listobj">
														<td>${list.sort}</td>
 													 	<td>${list.title}</td>
														<td>${list.imgPath}</td>
														<td>${list.targetUrl}</td>
														<td>${list.version}</td>
														<td>${list.remark}</td>
														<td><fmt:formatDate value="${list.createdDate}" type="both"/></td>
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
										
										<%--<div class="row">
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
										</div>--%>
										
									</div>
								</div><!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
						
						<jsp:include page="/common/message.jsp"></jsp:include>
						
						</form>

			</div><!-- /.main-content -->

