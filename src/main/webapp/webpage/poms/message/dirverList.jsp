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
<script src="<%=basePath %>/dist/js/message/dirverList.js"></script>
<form id="formgastation">
	<jsp:include page="/common/page_param.jsp"></jsp:include>
	<div class="row">
		<div class="item">
			<label>手机号码:</label> <input type="text" name="mobilePhone"
				placeholder="手机号码" maxlength="10" value="${driver.mobilePhone}" />
			<label>姓名:</label> <input type="text" name="fullName"
				placeholder="姓名" maxlength="10" value="${driver.fullName}" />
			<button class="btn btn-sm btn-primary" type="button"
				onclick="scher();">
				<i class="ace-icon fa fa-flask align-top bigger-125"></i> 查询
			</button>
		</div>
		<div class="sjny-table-responsive">
			<table id="dynamic-table"
				class="table table-striped table-bordered table-hover col-sm-10">
				<thead>
					<tr>
						<th class="center td-w1"><label class="pos-rel"> <!-- <input
								type="checkbox" class="ace" onclick="checkedAllRows(this);" /> -->
								<span class="lbl"></span>
						</label></th> 
						<th onclick="orderBy(this,'user_name');commitForm();"
							id="user_name_order">账号</th>
						<th onclick="orderBy(this,'full_name');commitForm();"
							id="full_name_order">姓名</th>
						<!-- 	<th onclick="orderBy(this,'img_path');commitForm();"
											id="threshold_max_value_order">缩略图</th> -->
						<th onclick="orderBy(this,'mobile_phone');commitForm();"
							id="mobile_phone_order">电话号码</th>
						<th onclick="orderBy(this,'plate_number');commitForm();"
							id="plate_number_order">车牌号</th>
						<th onclick="orderBy(this,'expiry_date');commitForm();"
							id="expiry_date_order">年检有效期</th>


					</tr>
				</thead>

				<tbody>

					<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
						<tr id="${list.sysDriverId }">
							 <td class="center"><label class="pos-rel"> <input
									type="checkbox" class="ace checkbox"
									onchange="checkchange(this)" value1="${list.userName}" value="${list.deviceToken}"  id="${list.deviceToken}" /> <span
									class="lbl"></span>
							</label></td> 
							<td>${list.userName}</td>
							<td>${list.fullName}</td>
							<%-- 		<td><img width="150" height="150" alt="150x150"
												src="<%=imagePath %>${list.imgPath}" /></td> --%>
							<td>${list.mobilePhone}</td>
							<td>${list.plateNumber}</td>

							<td><fmt:formatDate value="${list.expiryDate}" type="both" /></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- /.col -->
	</div>

	<div class="row">
		<div class="col-sm-6">
			<div class="dataTables_info sjny-page" id="dynamic-table_info"
				role="status" aria-live="polite">
				每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共
				${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages}
				页
			</div>
		</div>
		<div class="col-sm-6">
			<nav>
				<ul id="ulhandle" class="pagination pull-right no-margin">
					<li id="previous"><a href="javascript:void(0);"
						aria-label="Previous" onclick="prepage('#formgastation');"> <span
							aria-hidden="true">上一页</span>
					</a></li>
					<li id="next"><a id="nexthandle" href="javascript:void(0);"
						aria-label="Next" onclick="nextpage('#formgastation');"> <span
							aria-hidden="true">下一页</span>
					</a></li>
				</ul>
			</nav>
		</div>
	</div>
	<jsp:include page="/common/message.jsp"></jsp:include>
</form>



