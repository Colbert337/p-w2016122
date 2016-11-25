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
<style>
<!--

-->
a:link {
	color: #337ab7;
     
}

a:visited {
    color: #337ab7;
    
}
    
a:hover {
    color: #337ab7;
     
    }
    
a:active {
    color: #337ab7;
     
}
</style>
<script src="<%=basePath%>/dist/js/mobile/roadList.js"></script>
<link rel="stylesheet" href="<%=basePath%>/dist/js/message/show.css">
<script src="<%=basePath%>/dist/js/message/show.js"></script>
<div class="" id="div">
	<!-- /.page-header -->
	<form id="formRoad" action="../web/mobile/road/outExcel">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="page-header">
					<h1>路况管理</h1>
				</div>

				<div class="search-types">
					<div class="item">
						<label>路况说明:</label> <input type="text" name="conditionMsg" id="msg"
							placeholder="输入路况说明" maxlength="200" value="${road.conditionMsg}" />
					</div>
					<div class="item">
						<label>路况类型</label> <select name="conditionType" id="type"
							value="${road.conditionType}">
							<s:option flag="true" gcode="CONDITION_TYPE" form="road"
								field="conditionType" />
						</select>
					</div>
					<div class="item">
						<label>审核状态</label> <select name="conditionStatus"
							id="conditionStatus">
							<option value="">--请选择--</option>
							<option value="0">已失效</option>
							<option value="1">待审核</option>
							<option value="2">审核通过</option>
							<option value="3">未通过</option>
						</select>
					</div>
					<div class="item">
						<div class="input-daterange top" id="j-input-daterange-top">
							<label>发布日期:</label> <input type="text" class="timebox"
								name="publisherTime_str" id="publisherTime_str" value="${road.publisherTime_str}"
								readonly="readonly" /> <span class=""> <i
								class="fa fa-exchange"></i>
							</span> <input type="text" class="timebox" name="auditorTime_str" id="auditorTime_str"
								value="${road.auditorTime_str}" readonly="readonly" />
						</div>
					</div>
					<div class="item">
						<label>创建人电话/创建人:</label> <input type="text" name="publisherName" id="publisherName"
													placeholder="输入创建人电话/创建人" maxlength="200" value="${road.publisherName}" />
					</div>
					<div class="item">
						<button class="btn btn-sm btn-primary" type="button"
							onclick="loadPage('#main','<%=basePath%>/webpage/poms/mobile/roadAdd.jsp');">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i>添加
						</button>
						<button class="btn btn-sm btn-primary" type="button"
							onclick="commitForm();">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i>查询
						</button>
						<button class="btn btn-sm" type="button" onclick="init();">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i>重置
						</button>
						<button class="btn btn-sm btn-primary" type="button" onclick="outExcel();">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i>导出Excel
						</button>
						<c:if test="${currUser.user.userName  eq 'sjnyadmin' }">
						<button class="btn btn-sm btn-primary" type="button"
							onclick="restoreRedis()">
							<i class="ace-icon fa fa-flask align-top bigger-125"></i> 恢复redis
						</button>
						</c:if>
					</div>
				</div>

				<div class="clearfix">
					<div class="pull-right tableTools-container"></div>
				</div>

				<div class="table-header">APP信息发送列表</div>

				<!-- div.table-responsive -->

				<!-- div.dataTables_borderWrap -->
				<div class="sjny-table-responsive">
					<table id="dynamic-table"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th onclick="orderBy(this,'start_time');commitForm();"  style="display:none"
									id="start_time_order"><i
										class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>开始时间</th>
								<th onclick="orderBy(this,'end_time');commitForm();" style="display:none"
									id="end_time_order"><i
										class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>结束时间</th>
							 
								<th onclick="orderBy(this,'auditor_phone');commitForm();"
									id="auditor_phone_orber">审核人电话</th>
								<th onclick="orderBy(this,'auditor');commitForm();"
									id="auditor_orber">审核人</th>
								<th onclick="orderBy(this,'condition_type');commitForm();"
									id="condition_type_order">路况类型</th>
								<th onclick="orderBy(this,'condition_status');commitForm();"
									id="condition_status_order">审核状态</th>
								<!-- 	<th onclick="orderBy(this,'img_path');commitForm();"
											id="threshold_max_value_order">缩略图</th> -->
								<th onclick="orderBy(this,'capture_time');commitForm();"
									id="capture_time_order">拍照时间</th>
								<th onclick="orderBy(this,'condition_msg');commitForm();"
									id="condition_msg_order">路况说明</th>
								<th onclick="orderBy(this,'memo');commitForm();" id="memo_orber">备注</th>
								<th onclick="orderBy(this,'publisher_name');commitForm();"
									id="publisher_name_orber">创建人</th>
								<th onclick="orderBy(this,'publisher_phone');commitForm();"
									id="publisher_phone_orber">创建人电话</th>
								<th onclick="orderBy(this,'publisher_time');commitForm();"
									id="publisher_time_order" class="td-w2"><i
									id="created_date"
									class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>发布时间</th>
								<th onclick="orderBy(this,'auditor_time');commitForm();"
									id="auditor_time_orber">审核时间</th>
								<th onclick="orderBy(this,'invalid_count');commitForm();"
									id="invalid_count_orber">失效请求</th>
								<th class="text-center td-w3">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="${list.id }">
								<!-- 隐藏列用于战士详情信息 -->
									<td  style="display:none"><s:Code2Name mcode="${list.conditionType}"
											gcode="CONDITION_TYPE"></s:Code2Name></td>
									<td  style="display:none"><div class="td-inner-warp">${list.conditionMsg}</div></td>
									<td  style="display:none">${list.province}</td>
									<td  style="display:none"><fmt:formatDate value="${list.captureTime}"	type="both" /></td>											
									<td  style="display:none"><s:Code2Name mcode="${list.direction}" gcode="DIRECTION_CODE"></s:Code2Name></td>
									<td  style="display:none"><div class="td-inner-warp">${list.longitude},${list.latitude }</div></td>
									<td  style="display:none"><fmt:formatDate value="${list.startTime}" type="both" /></td>
									<td  style="display:none"><fmt:formatDate value="${list.endTime}" type="both" /></td>
									<td  style="display:none"><c:if test="${list.conditionStatus == '0' }">已失效</c:if>
										<c:if test="${list.conditionStatus == '1' }">待审核</c:if> <c:if
											test="${list.conditionStatus == '2' }">审核通过</c:if> <c:if
											test="${list.conditionStatus == '3' }">未通过</c:if></td>
									<td  style="display:none">${list.auditor}</td>
									<td  style="display:none"><fmt:formatDate value="${list.auditorTime}" type="both" /></td>
									<td  style="display:none">${list.publisherName}</td>
									<td  style="display:none"><div class="td-inner-warp">${list.memo}</div></td>
									<!-- end -->
									<td>${list.auditorPhone}</td>
									<td>${list.auditor}</td>
									<td><s:Code2Name mcode="${list.conditionType}"
											gcode="CONDITION_TYPE"></s:Code2Name></td>
									<td><c:if test="${list.conditionStatus == '0' }">已失效</c:if>
										<c:if test="${list.conditionStatus == '1' }">待审核</c:if> <c:if
											test="${list.conditionStatus == '2' }">审核通过</c:if> <c:if
											test="${list.conditionStatus == '3' }">未通过</c:if></td>
									<%-- 		<td><img width="150" height="150" alt="150x150"
												src="<%=imagePath %>${list.imgPath}" /></td> --%>
									<td><fmt:formatDate value="${list.captureTime}"
											type="both" /></td>
									<td><div class="td-inner-warp">${list.conditionMsg}</div></td>
									<td><div class="td-inner-warp">${list.memo}</div></td>
									<td>${list.publisherName}</td>
									<td>${list.publisherPhone}</td>
									<td><fmt:formatDate value="${list.publisherTime}"
														type="both" /></td>
									<td><fmt:formatDate value="${list.auditorTime}"
											type="both" /></td>
									<%-- <td>${list.operator}</td> --%>
									<td>
										<a class="option-btn-m" href="javascript:void(0);"
												title="查看失效请求"
												onclick="showShixiao('../web/mobile/road/roadListStr?id=${list.id }')"
												data-rel="tooltip"> --${list.invalid_count}-- </a>
										 </td>
									<td class="text-center"><c:if
											test="${list.conditionStatus == '1' }">
											<a class="option-btn-m" href="javascript:void(0);" title="审核"
												data-rel="tooltip"> <i
												class="ace-icon fa fa-pencil bigger-130"
												onclick="updateCheck('${list.conditionImg}',$('#${list.id }'),'${list.id }');"></i>
											</a>
										</c:if><a class="option-btn-m" href="javascript:void(0);"
										title="查看图片" data-rel="tooltip"> <i
											class="ace-icon fa fa-search-plus bigger-130"
											onclick="updateCheck('${list.conditionImg}',$('#${list.id }'));"></i>
									</a> <a class="" href="javascript:void(0);"
										onclick="deleteRoad('${list.id}');" title="删除"
										data-rel="tooltip"> <i
											class="ace-icon fa fa-trash-o bigger-130"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
									aria-label="Previous" onclick="prepage('#formRoad');"> <span
										aria-hidden="true">上一页</span>
								</a></li>
								<li id="next"><a id="nexthandle" href="javascript:void(0);"
									aria-label="Next" onclick="nextpage('#formRoad');"> <span
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
</div>

<div id="innerModel" class="modal fade" role="dialog"
	aria-labelledby="gridSystemModalLabel" data-backdrop="static"
	tabindex="-1">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<%--<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>--%>
			<div class="modal-body">
				<div class="shenhe-items-hd">路况信息</div>
				<div class="row">
					<div class="col-md-4">
						<ul class="ace-thumbnails clearfix">
							<li><a class="" href="" data-rel="colorbox"> <img
									class="img-responsive" src="" alt="" id="innerimg1" />
								<div class="text">
									<div class="inner">点击放大</div>
								</div>
							</a></li>
						</ul>
					</div>
					<div class="col-md-8">
						<table class="table">
							<tbody>
							<tr>
								<th>路况类型</th>
								<td><div id="indu_com_number" name="show"></div></td>
								<th>路况说明</th>
								<td colspan="3"><div id="status" name="show"></div></td>

							</tr>
							<tr>
								<th>省份信息</th>
								<td><div name="show"></div></td>
								<th>拍照时间</th>
								<td><div id="prepay_phone" name="show"></div></td>
							</tr>
							<tr>
								<th>方向</th>
								<td><div id="salesmen_name" name="show"></div></td>
								<th>坐标</th>
								<td><div id="salesmen_name" name="show"></div></td>
							</tr>
							<tr>
								<th>开始时间</th>
								<td><div id="prepay_phone" name="show"></div></td>
								<th>结束时间</th>
								<td><div id="prepay_balance" name="show"></div></td>
							</tr>
							<tr>
								<th width="15%">审核状态</th>
								<td><div id="sys_gas_station_id" name="show"></div></td>
								<th width="15%">审核人</th>
								<td><div id="gas_station_name" name="show"></div></td>
							</tr>
							<tr>
								<th>审核时间</th>
								<td><div id="operations_name" name="show"></div></td>
								<th>创建人</th>
								<td><div id="admin_username" name="show"></div></td>
							</tr>
							<tr>
								<th>备注</th>
								<td colspan="3"><div id="prepay_balance" name="show"></div></td>

							</tr>
							<!-- <tr>
								<th>创建人</th>
								<td><div id="address" name="show"></div></td>
								<th>创建人电话</th>
								<td><div id="created_time" name="show"></div></td>
							</tr> -->
							</tbody>
						</table>
					</div>
				</div>

				<%--<div class="shenhe-items-hd">创建信息</div>
				<table class="table">
					<tbody>

						<tr>
							<th>创建时间</th>
							<td colspan="3"><div id="created_time" name="show"></div></td>
						</tr>
					</tbody>
				</table>--%>
				<%--<div class="shenhe-items-hd">审核信息</div>
				<table class="table">
					<tr>
						<th>审核时间</th>
						<td colspan="3"><div id="prepay_balance" name="show"></div></td>

					</tr>
					</tbody>
				</table>
				<div class="shenhe-items-hd">其他信息</div>
				<table class="table">
					</tbody>
				</table>--%>

				<%--<div class="shenhe-items-hd">路况照片</div>
				<div class="row">
					<div class="col-sm-3">
						<ul class="ace-thumbnails clearfix">
							<li><a class="" href="" data-rel="colorbox"> <img
									class="img-responsive" src="" alt="" id="innerimg1" />
									<div class="text">
										<div class="inner">点击放大</div>
									</div>
							</a></li>
						</ul>
					</div>
				</div>--%>

			</div>
			<input type="hidden" id="roadId" />
			<div class="modal-footer" id="buttonList">
				<button class="btn btn-primary btn-sm" onclick="updateRoad('2')">审核通过</button>
				<button class="btn btn-primary btn-sm" onclick="showContent('3')">审核不通过</button>
				<button class="btn btn-primary btn-sm" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
<div id="content" class="modal fade" role="dialog"
	aria-labelledby="gridSystemModalLabel" data-backdrop="static"
	tabindex="-1">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<%--<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>--%>
			<div class="modal-body">
			<textarea id="contentmes" placeholder="请输入原因"
							class="form-control" maxlength="150"></textarea>
			</div>
			<div class="modal-footer" id="buttonList">
				<button class="btn btn-primary btn-sm" onclick="updateRoad('3')">确定</button>
				 <button class="btn btn-primary btn-sm" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>


<script>
	$("#conditionStatus").val("${road.conditionStatus}");

	
</script>