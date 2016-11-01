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

<script src="<%=basePath%>/dist/js/mobile/roadListStr.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formRoad">

		<jsp:include page="/common/page_param.jsp"></jsp:include>

		<div class="row">
			<div class="col-xs-12">

				<div class="page-header">
					<h1>路况失效查看</h1>
				</div>
				<div class="clearfix">
					<div class="pull-right tableTools-container"></div>
				</div>
				<div class="modal-body">
					<div class="shenhe-items-hd">路况信息</div>
					<table class="table">
						<tbody>
							<tr>
								<th width="15%">路况类型</th>
								<td><div id="gas_station_name" >
										<s:Code2Name mcode="${road.conditionType}"
											gcode="CONDITION_TYPE"></s:Code2Name></td>
								<th width="15%">路况类型</th>
								<td><div id="gas_station_name" >
										<c:if test="${road.conditionStatus == '0' }">已失效</c:if>
										<c:if test="${road.conditionStatus == '1' }">待审核</c:if>
										<c:if test="${road.conditionStatus == '2' }">已审核</c:if>
										<c:if test="${road.conditionStatus == '3' }">未通过</c:if></td>

								<th>坐标</th>
								<td><div id="salesmen_name" >
										${road.longitude},${road.latitude}</div></td>
													<input type="hidden" name="id" value="${road.id }" />
							</tr>
							<tr>
								<th>拍照时间</th>

								<td><div id="operations_name" >
										<fmt:formatDate value="${road.captureTime}" type="both" />
									</div></td>


								<th>开始时间</th>
								<td><div id="indu_com_number" >
										<fmt:formatDate value="${road.startTime}" type="both" />
									</div></td>
								<th>结束时间</th>
								<td><div id="status" >
										<fmt:formatDate value="${road.endTime}" type="both" />
									</div></td>
							</tr>
							<tr>
								<th>方向</th>
								<td><div >
										<s:Code2Name mcode="${road.direction}"
											gcode="DIRECTION_CODE"></s:Code2Name>
									</div></td>

								<th>坐标</th>
								<td><div id="salesmen_name" >
										${road.longitude},${road.latitude}</div></td>

								<th>省份信息</th>
								<td><div >${road.province}</div></td>
							</tr>
							<tr>
								<th>路况说明</th>
								<td colspan="7"><div id="admin_username" >${road.conditionMsg}
									</div></td>
							</tr>
						</tbody>
					</table>
					<div class="shenhe-items-hd">创建信息</div>
					<table class="table">
						<tbody>
							<tr>
								<th>创建人</th>
								<td><div id="address" >${road.publisherName}
									</div></td>
								<th>创建人电话</th>
								<td><div id="created_time" >
										${road.publisherPhone}</div></td>

								<th>创建时间</th>
								<td><div id="created_time" >
										<fmt:formatDate value="${list.publisherTime}" type="both" />
									</div></td>
							</tr>
						</tbody>
					</table>


					<div class="shenhe-items-hd">路况图片</div>
					<div class="row">
						<div class="col-sm-3">
							<ul class="ace-thumbnails clearfix">
								<li><a class="" href="" data-rel="colorbox"> <img
										class="img-responsive" src="${list.conditionImg}" alt=""
										id="innerimg1" />
										<div class="text">
											<div class="inner">点击放大</div>
										</div>
								</a></li>
							</ul>
						</div>
					</div>

				</div>
				<div class="sjny-table-responsive">
					<table id="dynamic-table"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								 

								<!-- 	<th onclick="orderBy(this,'img_path');commitForm1();"
											id="threshold_max_value_order">缩略图</th> -->
								<th>坐标</th>
								<th onclick="orderBy(this,'capture_time');commitForm1();"
									id="capture_time_order">拍照时间</th>
								<th onclick="orderBy(this,'condition_msg');commitForm1();"
									id="condition_msg_order">路况说明</th>
								 

								<th onclick="orderBy(this,'publisher_name');commitForm1();"
									id="publisher_name_orber">创建人</th>
								<th onclick="orderBy(this,'publisher_phone');commitForm1();"
									id="publisher_phone_orber">创建人电话</th>
								<th onclick="orderBy(this,'publisher_time');commitForm1();"
									id="publisher_time_order" class="td-w2"><i
									id="created_date"
									class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>发布时间</th>
 
								<th class="text-center td-w3">操作</th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="${list.id }">
								  	<td><div class="td-inner-warp">${list.captureLongitude},${list.captureLatitude }</div></td>
									<td><fmt:formatDate value="${list.captureTime}"
											type="both" /></td>
									<td>${list.conditionMsg}</td>
								 
									<td>${list.publisherName}</td>
									<td>${list.publisherPhone}</td>
									<td><fmt:formatDate value="${list.publisherTime}"
											type="both" /></td>

									<%-- <td>${list.operator}</td> --%>
							 
									<td class="text-center"><a class="option-btn-m" href="javascript:void(0);"
										title="查看图片" data-rel="tooltip"> <i
											class="ace-icon fa fa-search-plus bigger-130"
											onclick="updateCheck('${list.conditionImg}',$('#${list.id }'));"></i>
									</a>  </td>
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
				<div class="col-md-offset-3 col-md-9">

					<button class="btn btn-info" type="button"
						onclick="shixiao();">
						<i class="ace-icon fa fa-check bigger-110"></i> 失效
					</button>
					&nbsp; &nbsp; &nbsp;



					<button class="btn" id="clear" type="button" onclick="returnPage();">
						<i class="ace-icon fa fa-repeat bigger-110"></i> 取消
					</button>

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
				<table class="table">
					<tbody>
						 
						<tr>
							<th>坐标</th>
							<td><div id="salesmen_name" name="show"></div></td>
							<th>拍照时间</th>
							<td><div id="operations_name" name="show"></div></td>
						</tr>
						<tr>
							<th>路况说明</th>
							<td><div id="admin_username" name="show"></div></td>
						</tr>
					</tbody>
				</table>
				<div class="shenhe-items-hd">创建信息</div>
				<table class="table">
					<tbody>
						<tr>
							<th>创建人</th>
							<td><div id="address" name="show"></div></td>
							<th>创建人电话</th>
							<td><div id="created_time" name="show"></div></td>
						</tr>
						<tr>
							<th>创建时间</th>
							<td colspan="3"><div id="created_time" name="show"></div></td>
						</tr>
					</tbody>
				</table>
				 

				<div class="shenhe-items-hd">路况照片</div>
				<div class="row">
					<div class="col-sm-3">
						<ul class="ace-thumbnails clearfix">
							<li><a class="" href="" data-rel="colorbox"> <img
									class="img-responsive" src="" alt="" id="innerimg2" />
									<div class="text">
										<div class="inner">点击放大</div>
									</div>
							</a></li>
						</ul>
					</div>
				</div>

			</div>

			<div class="modal-footer" id="buttonList">
				<button class="btn btn-primary btn-sm" onclick="updateRoad('2')">审核通过</button>
				<button class="btn btn-primary btn-sm" onclick="updateRoad('3')">审核不通过</button>
				<button class="btn btn-primary btn-sm" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>



<script>
	//获取带"/"的项目名，如：/Tmall
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	$("#conditionStatus").val("${road.conditionStatus}");
	
	if("${road.conditionImg}"==""){
	
		$("#innerimg1").attr('src',"/common/images/default_productBig.jpg");
		$("#innerimg1").parent("a").attr("href",
				"/common/images/default_productBig.jpg");
	}else{
		$("#innerimg1").attr("src",  '${road.conditionImg}');
		$("#innerimg1").parent("a").attr("href",
				 '${road.conditionImg}');
	}
	
</script>