<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/gastation/gastation_product_price_list.js"></script>

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
					
					<input type="hidden" class="" name="product_id" value="${productPrice.product_id}" readonly="readonly"/>
					
					<div class="search-types">
						<%-- <div class="item">
						    <label>加注站编号:</label>
							<input type="text" name="sys_gas_station_id" placeholder="输入加注站编号"  maxlength="10" value="${productPrice.sys_gas_station_id}"/>
						</div>
						
						<div class="item">
						    <label>加注站名称:</label>
							<input type="text" name="gas_station_name" placeholder="输入加注站名称"  maxlength="20" value="${productPrice.gas_station_name}"/>
						</div> --%>
						
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>更新时间:</label>
								<input type="text" class="" name="created_time_after" value="${productPrice.created_time_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="created_time_before" value="${productPrice.created_time_before}" readonly="readonly"/>
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
							<button class="btn btn-sm" type="button" onclick="returnpage();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								返回
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
									<th onclick="orderBy(this,'id');commitForm();" id="id_order">价格编号</th>
									<th onclick="orderBy(this,'product_price_id');commitForm();" id="product_price_id_order">气品子类型</th>
									<th onclick="orderBy(this,'product_price');commitForm();" id="product_price_order">气品单价</th>
									<th onclick="orderBy(this,'product_unit');commitForm();" id="product_unit_order">气品单位</th>
									<th onclick="orderBy(this,'create_time');commitForm();" id="create_time_order"><i id="created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>更新时间</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.id}</td>
								 	<td><s:Code2Name mcode="${list.productPriceId}" gcode="CARDTYPE"></s:Code2Name></td> 
									<td>${list.productPrice}</td>
									<td><s:Code2Name mcode="${list.productUnit}" gcode="GAS_UNIT"></s:Code2Name></td>
									<td><fmt:formatDate value="${list.createTime}" type="both"/></td>
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
			</div>

			<jsp:include page="/common/message.jsp"></jsp:include>


			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>
