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

<script src="<%=basePath%>/dist/js/page/url_list.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formpage">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="page-header">
					<h1>页面链接地址</h1>
				</div>

			<%--	<div class="search-types">
					<div class="item">
						<label>页面标题:</label> <input type="text" name="pageTitle"
							placeholder="输入页面标题" maxlength="10" value="${page.pageTitle}" />
					</div>

					<div class="item">
						<label>页面状态:</label>
					</div>
					<div class="item">
						<select  name="pageStatus" value="${page.pageStatus}" >
							<option value="">--请选择--</option>
							<option value="0">生效</option>
							<option value="1">失效</option>
						</select>
					</div>
					<div class="item">
						<div class="input-daterange top" id="j-input-daterange-top">
							<label>页面修改时间:</label> <input type="text" class=""
								name="page_created_time_after"
								value="${page.page_created_time_after}" readonly="readonly" />
							<span class=""> <i class="fa fa-exchange"></i>
							</span> <input type="text" class="" name="page_created_time_before"
								value="${page.page_created_time_before}" readonly="readonly" />
						</div>
					</div>


					<div class="item">
						<button class="btn btn-sm btn-primary" type="button"
							onclick="loadPage('#main','<%=basePath%>/webpage/poms/page/page_new.jsp');">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i> 新建
						</button>
						<button class="btn btn-sm btn-primary" type="button"
							onclick="commitForm();">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i> 查询
						</button>
						<button class="btn btn-sm" type="button" onclick="init();">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i> 重置
						</button>
					</div>
				</div>--%>

				<div class="clearfix">
					<div class="pull-right tableTools-container"></div>
				</div>

				<div class="table-header">已生效的页面URL地址</div>

				<div class="sjny-table-responsive">
					<table id="dynamic-table"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<%--<th class="center td-w1"><label class="pos-rel"> <input
										type="checkbox" class="ace" onclick="checkedAllRows(this);" />
										<span class="lbl"></span>
								</label></th>--%>
								<th  id="id_order"
									width="20%">页面编号</th>
								<th
									id="page_title_order">页面标题</th>
								<!-- <th onclick="orderBy(this,'page_body');commitForm();" id="page_body_order">页面内容</th> -->
							<%--	<th onclick="orderBy(this,'page_ticker');commitForm();"
									id="page_ticker_order">页面缩略</th>--%>
									
								<th
									id="page_creator_order">页面创建者</th>
								<th
									id="page_created_time_order" class="td-w2"><%--<i
									id="message_created_time"
									class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>--%>页面修改时间</th>
								<th onclick="orderBy(this,'page_html');commitForm();"
									id="page_html_order" style="display: none;">编辑内容</th>
							<%--	<th
									id="page_ticker_order">状态</th>--%>
								<th class="text-center td-w2">更多操作</th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="${list.id}">
									<td class="center" style="display:none"><label class="pos-rel"> <input
											type="checkbox" class="ace" id="pks" value="${list.id}" /> <span
											class="lbl"></span>
									</label></td>
									<td>${list.id}</td>
									<td>${list.pageTitle}</td>
									  <%--<td style="display:none">${list.pageBody}</td>--%>
							 	<td style="display:none">${list.pageTicker}</td>
									
									<td>${list.pageCreator}</td>
									<td><fmt:formatDate value="${list.pageCreatedTime}"
											type="both" /></td>
									<td style="display: none;"><xmp>${list.pageHtml}</xmp></td>
									<td style="display:none"><c:if test="${list.pageStatus == '0'}">生效</c:if><c:if test="${list.pageStatus == '1'}"><span style="color:red">失效</span></c:if></td>
									<td class="text-center"><a class="logic-del"
										href="javascript:void(0);" title="页面预览" data-rel="tooltip">
											<i class="ace-icon fa fa-eye bigger-130"
											onclick="preview(this);"></i>
									</a><c:if test="${list.pageStatus == '0'}">
									<a class="option-btn-m"
												href="javascript:void(0);" title="选择" data-rel="tooltip">
													<i class="ace-icon fa fa-check-circle-o bigger-130"
													onclick="chooseUrl(this)"  chooseUrl="${list.id}"></i>
											</a>
										<%--	<a class="logic-del" href="javascript:void(0);" title="失效"
												data-rel="tooltip"> <i
												class="ace-icon fa fa-trash-o bigger-130"
												onclick="del('${list.id}');"></i>
											</a>--%>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>


				<div class="row">
					<div class="col-sm-6">
						<div class="dataTables_info sjny-page" id="dynamic-table_info"
							role="status" aria-live="polite">
							每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共
							${pageInfo.total} 条 <span class="line">|</span> 共
							${pageInfo.pages} 页
						</div>
					</div>
					<div class="col-sm-6">
						<nav>
							<ul id="ulhandle" class="pagination pull-right no-margin">
								<li id="previous"><a href="javascript:void(0);"
									aria-label="Previous" onclick="prepage('#formpage');"> <span
										aria-hidden="true">上一页</span>
								</a></li>
								<li id="next"><a id="nexthandle" href="javascript:void(0);"
									aria-label="Next" onclick="nextpage('#formpage');"> <span
										aria-hidden="true">下一页</span>
								</a></li>
							</ul>
						</nav>
					</div>
				</div>

				<jsp:include page="/common/message.jsp"></jsp:include>

			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</form>

	<form id="previewForm" name="previewForm" method="post"
		action="../web/page/preview" target="colors123"
		onsubmit="openSpecfiyWindown('colors123')">
		<input type="hidden" id="pageHtml" name="pageHtml" /> <input
			type="hidden" id="pageTitle" name="pageTitle" /> <input
			type="hidden" id="pageTicker" name="pageTicker" /> <input
			type="hidden" id="pageBody" name="pageBody" /> <input type="hidden"
			id="page_created_time" name="page_created_time" />
	</form>

</div>
<script>


//$("#pageStatus").find("option[value='${page.pageStatus}']").attr("selected",true);
$("#pageStatus").val("${page.pageStatus}");


	var indexUrlForBannerUrl;//全局公共变量用于设置添加banner图地址
	function chooseUrl(obj){
		indexUrlForBannerUrl=obj.getAttribute('chooseUrl');
		$('#target_url').val('/portal/crm/help/showPage?pageid='+indexUrlForBannerUrl);
		$("#innerModel").modal('hide');
	}
	
</script>