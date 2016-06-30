<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/gastation/gastation_price_list.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							油气品价格管理
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>加注站编号:</label>
							<input type="text" name="sysGasStationId" placeholder="输入加注站编号" maxlength="10" value="${gsGasPrice.sysGasStationId}"/>
						</div>
						
						<div class="item">
							<label>气品类型:</label>
							<select class="chosen-select" name="gasNum" >
								<s:option flag="true" gcode="CARDTYPE" form="gsGasPrice" field="gasNum" />
							</select>
						</div>

						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>创建时间:</label>
								<input type="text" class="" name="created_date_after" value="${gsGasPrice.created_date_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="created_date_before" value="${gsGasPrice.created_date_before}" readonly="readonly"/>
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
					
					<div class="table-header">油气品价格详细信息列表</div>

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
									<th onclick="orderBy(this,'sys_gas_station_id');commitForm();" id="sys_gas_station_id_order">加注站编号</th>
									<th>加注站名称</th>
									<th>当前单价</th>
									<th onclick="orderBy(this,'gas_num');commitForm();" id="gas_num_order">气品类型</th>
									<th onclick="orderBy(this,'gas_name');commitForm();" id="gas_name_order">气品子类型</th>
									<th onclick="orderBy(this,'created_date');commitForm();" id="created_date_order"><i id="createdDate" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
									<th class="text-center">更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.gsGasPriceId}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.sysGasStationId}</td>
								 	<td>${list.gas_station.gas_station_name}</td> 
									<td>${list.gs_gas_source_info.market_price}</td>
									<td><s:Code2Name mcode="${list.gasNum}" gcode="CARDTYPE"></s:Code2Name></td>
									<td><s:Code2Name mcode="${list.gasName}" gcode="CARDTYPE"></s:Code2Name></td>
									<td><fmt:formatDate value="${list.createdDate}" type="both"/></td>
									<td class="text-center">
										<a class="option-btn-m" href="javascript:void(0);" title="查看历史价格" data-rel="tooltip">
											<i class="ace-icon fa fa-search-plus bigger-130" onclick="showPriceLog(this);"></i>
										</a>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
			

			<label>共 ${pageInfo.total} 条</label>
			
			<nav>
				  <ul id="ulhandle" class="pagination pull-right no-margin">
				  
				    <li id="previous">
					      <a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formgastation');">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
				    </li>
				    
				    <li id="next">
					      <a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formgastation');">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
				    </li>
				    
				  </ul>
			</nav>

			<jsp:include page="/common/message.jsp"></jsp:include>


			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>


<div id="innerModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="table-header">油气品价格详细信息列表</div>
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
										<th>价格编号</th>
										<th>气品子类型</th>
										<th>气品单价</th>
										<th>气品单位</th>
										<th><i id="create_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>更新时间</th>
									</tr>
								</thead>
	
								<tbody>
									
								<c:forEach items="${pageInfo2.list}" var="list" varStatus="s">
									<tr id="listobj">
										<td class="center">
											<label class="pos-rel"> 
												<input type="checkbox" class="ace" id="pks" value="${list.product_id}"/> 
												<span class="lbl"></span>
											</label>
										</td>
	
										<td>${list.product_id}</td>
									 	<td><s:Code2Name mcode="${list.productPriceId}" gcode="CARDTYPE"></s:Code2Name></td> 
										<td>${list.productPrice}</td>
										<td><s:Code2Name mcode="${list.productUnit}" gcode="GAS_UNIT"></s:Code2Name></td>
										<td><fmt:formatDate value="${list.createTime}" type="both"/></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
				
	
						<label>共 ${pageInfo2.total} 条</label>
						
						<nav>
							  <ul id="ulhandle" class="pagination pull-right no-margin">
							  
							    <li id="previous">
								      <a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formgastation');">
								        <span aria-hidden="true">&laquo;</span>
								      </a>
							    </li>
							    
							    <li id="next">
								      <a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formgastation');">
								        <span aria-hidden="true">&raquo;</span>
								      </a>
							    </li>
							    
							  </ul>
						</nav>
					</div><!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
