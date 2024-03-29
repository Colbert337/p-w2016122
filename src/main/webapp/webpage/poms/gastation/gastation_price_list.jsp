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
							<input type="text" id="gsGasPrice.sysGasStationId" name="sysGasStationId" placeholder="输入加注站编号" maxlength="10" value="${gsGasPrice.sysGasStationId}"/>
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
					<div class="sjny-table-responsive">
						<table id="dynamic-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center td-w1" style="display:none">
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
									<th onclick="orderBy(this,'fixed_discount');commitForm();" id="fixed_discount_order">固定折扣</th>
									<th onclick="orderBy(this,'minus_money');commitForm();" id="minus_money_order">立减金额</th>
									<th onclick="orderBy(this,'created_date');commitForm();" id="created_date_order" class="td-w2"><i id="createdDate" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
									<!-- <th class="td-w2"><i id="createTime" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>更新时间</th> -->
									<th class="text-center td-w3">更多操作</th>
								</tr>
							</thead>

							<tr>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">

								<tr id="listobj">
									<td class="center" style="display:none">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.gsGasPriceId}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.sysGasStationId}</td>
								 	<td>${list.gas_station.gas_station_name}</td> 
									<td>${list.productPriceInfo.productPrice}</td>
									<td><s:Code2Name mcode="${list.gasNum}" gcode="CARDTYPE"></s:Code2Name></td>
									<td><s:Code2Name mcode="${list.gasName}" gcode="CARDTYPE"></s:Code2Name></td>
									<%-- <td><fmt:formatDate value="${list.createdDate}" type="both"/></td> --%>
									<td>
										${list.fixed_discount}
									</td>
									<td>${list.minus_money}</td>
									<td><fmt:formatDate value="${list.productPriceInfo.createTime}" type="both"/></td>
									<td class="text-center">
										<a class="option-btn-m" href="javascript:void(0);" title="查看历史价格" data-rel="tooltip">
											<i class="ace-icon fa fa-search-plus bigger-130" onclick="showPriceLog(this);"></i>
										</a>
										<a class="option-btn-m" href="javascript:void(0);" title="修改" data-rel="tooltip">
											<i class="ace-icon fa fa-pencil bigger-130" onclick="getUpdatePage(this);"></i>
										</a>
									</td>
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


<div id="innerModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid" id="tt">
					
				</div>
			</div>
		</div>
	</div>
</div>
