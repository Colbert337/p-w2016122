<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	String imagePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<script src="<%=basePath%>/dist/js/mobile/appversion_list.js"></script>
<div class="">
	<form id="listForm">
		<jsp:include page="/common/page_param.jsp"></jsp:include>
		<div class="page-header">
			<h1>APP版本管理</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<div class="row">

					<div class="col-sm-15">

					<div class="search-types">


						
				 	<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>添加时间:</label>
								<input type="text" class="" name="created_date_sel" value="${suggest.created_date_sel}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="updated_date_sel" value="${suggest.updated_date_sel}" readonly="readonly"/>
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
					
							<div class="item">
								<button class="btn btn-sm btn-primary" type="button"
									onclick="mbAppVersionAdd('')">
									<i class="ace-icon fa fa-flask align-top bigger-125"></i> 添加
								</button>
							</div>
					</div>

						<div class="clearfix">
							<div class="pull-right tableTools-container"></div>
						</div>

						<div class="table-header">图片列表</div>

						<!-- div.dataTables_borderWrap -->
						<div class="sjny-table-responsive">
							<table id="dynamic-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>序号</th>
										<th
											id="url_order">下载地址</th>
										<th
											id="version_order">版本信息</th>
										<th
											id="code_order">版本号</th>
										<th
											id="isPublish_order">是否发布</th>
										<th id="level">备注</th>
										<th onclick="orderBy(this,'created_date');commitForm();"
											id="created_date_order" class="td-w2"><i
											id="created_date"
											class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>添加时间</th>
										 <th class="text-center td-w3">操作</th>
									</tr>
								</thead>

								<tbody>

									<c:forEach items="${pageInfo.list}" var="list" varStatus="s">

										<tr id="${list.appVersionId }">
											<td class='center' style="width: 30px;">${s.index+1}</td>
											<td>${list.url}</td>
											<td>${list.version}</td>
											<td style="width: 30px;"><div class="td-inner-warp">${list.code}</div></td>
											<td style="width: 10px;"><div class="td-inner-warp">
											<c:if test="${list.isPublish == '1'}">是</c:if>

											<c:if test="${list.isPublish == '0' }">否</c:if>
											</div></td>
											<td>${list.remark}</td>
											<td>${list.createdDateStr}
													</td>
											<td class="text-center"><a class="option-btn-m"
												href="javascript:void(0);" title="修改" data-rel="tooltip">
													<i class="ace-icon fa fa-pencil bigger-130"
													onclick="versionUpdate('${list.appVersionId}');"></i>
											</a> <a class="" href="javascript:void(0);"
												onclick="deleteAppVersion('${list.appVersionId}');" title="删除"
												data-rel="tooltip"> <i
													class="ace-icon fa fa-trash-o bigger-130"></i>
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
						<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
					</div>
					<div class="col-sm-6">
						<nav>
							<ul id="ulhandle" class="pagination pull-right no-margin">
								<li id="previous">
									<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#listForm');">
										<span aria-hidden="true">上一页</span>
									</a>
								</li>
								<li id="next">
									<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#listForm');">
										<span aria-hidden="true">下一页</span>
									</a>
								</li>
							</ul>
						</nav>
					</div>
				</div>
				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
		<jsp:include page="/common/message.jsp"></jsp:include>
	</form>

</div>
<!-- /.main-content -->



