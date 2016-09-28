<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/gastation/gastationMap_list.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							加注站地图数据导入
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>加注站编号:</label>
							<input type="text" name="sys_gas_station_id" placeholder="输入加注站编号" maxlength="10" value="${gastation.sys_gas_station_id}"/>
						</div>
						
						<div class="item">
						    <label>加注站名称:</label>
							<input type="text" name="gas_station_name" placeholder="输入加注站名称" maxlength="20" value="${gastation.gas_station_name}"/>
						</div>
						
						<div class="item">
							<label>合作类型:</label>
							<select class="chosen-select " id="select" name="map_type"  >
								<s:option flag="true" gcode="STATION_MAP_TYPE" form="gastation" field="map_type" />
							</select>
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
							<button class="btn btn-sm btn-primary" type="button" onclick="openImportDiv();">
								批量导入
							</button>
							<a class="btn btn-sm btn-primary" href="<%=basePath %>/docs/template/gastation_temp.xls">
								下载模板
							</a>
						</div>
					</div>

					<div class="clearfix">
						<div class="pull-right tableTools-container"></div>
					</div>
					
					<div class="table-header">预存款信息列表</div>

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
									<th onclick="orderBy(this,'sys_gas_station_id');commitForm();" id="sys_gas_station_id_order">加注站编号</th>
									<th onclick="orderBy(this,'gas_station_name');commitForm();" id="gas_station_name_order">加注站名称</th>
									<th onclick="orderBy(this,'contact_phone');commitForm();" id="contact_phone_order">电话号码</th>
									<th onclick="orderBy(this,'province_id');commitForm();" id="province_id_order">所在省</th>
									<th onclick="orderBy(this,'address');commitForm();" id="address_order">地址</th>
									<th >坐标</th>
									<th onclick="orderBy(this,'map_type');commitForm();" id="map_type_order">合作类型</th>
									<th onclick="orderBy(this,'status');commitForm();" id="status_order">平台状态</th>
									<th onclick="orderBy(this,'gas_server');commitForm();" id="gas_server_order">提供服务</th>
									<th onclick="orderBy(this,'LNG_price');commitForm();" id="LNG_price_order">商品信息</th>
									<th onclick="orderBy(this,'promotions');commitForm();" id="promotions_order">优惠活动</th>
									<th onclick="orderBy(this,'created_time');commitForm();" id="created_time_order"  class="td-w2"><i id="created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>注册日期</th>
								 	<th class="text-center td-w3">更多操作</th> 
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.sys_gas_station_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>
									<td>${list.sys_gas_station_id}</td>
								 	<td>${list.gas_station_name}</td> 
								 	<td>${list.contact_phone}</td> 
									<td><s:Code2Name mcode="${list.province_id}" gcode="PROVINCE_CODE"></s:Code2Name></td>
									<td><div class="td-inner-warp">${list.address}</div></td>
									<td>${list.xy}</td>
									<td><s:Code2Name mcode="${list.map_type}" gcode="STATION_MAP_TYPE"></s:Code2Name></td>
									<td><s:Code2Name mcode="${list.status}" gcode="STATION_STATUS"></s:Code2Name></td>
							        <td><div class="td-inner-warp">${list.gas_server}</div></td> 
                                    <td><div class="td-inner-warp">${list.lng_price}</div></td> 
                                    <td><div class="td-inner-warp">${list.promotions}</div></td> 

									<td><fmt:formatDate value="${list.created_time}" type="both"/></td>
									<td style="display: none">${list.sys_user_account_id}</td>
									<td class="text-center">
											<!-- <a class="option-btn-m" href="javascript:void(0);" title="修改" data-rel="tooltip">
												<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
											</a>
											<a class="option-btn-m" href="javascript:void(0);" title="预付款额度" data-rel="tooltip">
												<i class="ace-icon fa fa-credit-card bigger-130" onclick="preDeposit(this);"></i>
											</a> -->
											<a href="javascript:void(0);" onclick="deleteGasMap('${list.sys_gas_station_id}');" title="删除" data-rel="tooltip">
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
					<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formgastation');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formgastation');">
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


<!--提示弹层-开始-->
<div id="importDivModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="alertModalLabel">批量导入</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<form class="form-horizontal" id="importForm" enctype="multipart/form-data">
								<div class="form-group">
									<div class="col-xs-12">
										<input type="file" id="file_import" name="fileImport" onchange="fileFormat()"/>
									</div>
								</div>
							</form>
						</div><!-- /.col -->
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="saveTemplate()">确   定</button>
				<button class="btn btn-sm" i="close" onclick="closeDialog('importDivModel')">取   消 </button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
