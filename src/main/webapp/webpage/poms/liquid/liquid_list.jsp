<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/liquid/liquid_list.js"></script>


<!-- /section:basics/content.breadcrumbs -->
<div class="">
	<!-- /.page-header -->
	<form id="formgastation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							液源管理
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>液厂编号:</label>
							<input type="text" name="sys_gas_source_id" placeholder="输入液源编号"  maxlength="32" value="${gasource.sys_gas_source_id}"/>
						</div>
						
						<div class="item">
						    <label>液厂名称:</label>
							<input type="text" name="gas_factory_name" placeholder="输入液源名称"  maxlength="20" value="${gasource.gas_factory_name}"/>
						</div>
						
						<div class="item">
							<label>液厂状态:</label>
							<select class="chosen-select" name="status" >
								<s:option flag="true" gcode="STATION_STATUS" form="gasource" field="status" />
							</select>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/liquid/liquid_new.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								新建
							</button>
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
					
					<div class="table-header">液厂详细信息列表</div>

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
									<th onclick="orderBy(this,'sys_gas_source_id');commitForm();" id="sys_gas_source_id_order">液厂编号</th>
									<th onclick="orderBy(this,'gas_factory_name');commitForm();" id="gas_factory_name_order">液厂名称</th>
									<th onclick="orderBy(this,'technology_type');commitForm();" id="technology_type_order">工艺类型</th>
									<th onclick="orderBy(this,'delivery_method');commitForm();" id="delivery_method_order">配送方式</th>
									<th onclick="orderBy(this,'market_price');commitForm();" id="market_price_order">市场价格</th>
									<th onclick="orderBy(this,'gas_factory_addr');commitForm();" id="gas_factory_addr_order">液厂地址</th>
									<th onclick="orderBy(this,'status');commitForm();" id="status_order">液厂状态</th> 
									<th onclick="orderBy(this,'created_date');commitForm();" id="created_date_order"><i id="created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建日期</th>
									<th onclick="orderBy(this,'updated_date');commitForm();" id="updated_date_order"><i id="updated_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>更新日期</th>
									<th onclick="orderBy(this,'remark');commitForm();" id="remark_order">备注</th>
									<th>更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.sys_gas_source_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.sys_gas_source_id}</td>
								 	<td>${list.gas_factory_name}</td> 
									<td>${list.technology_type}</td>
									<td>${list.delivery_method}</td>
									<td>${list.market_price}</td>
									<td>${list.gas_factory_addr}</td>
									<td><s:Code2Name mcode="${list.status}" gcode="STATION_STATUS"></s:Code2Name></td>
									<td><fmt:formatDate value="${list.created_date}" type="both"/></td>
									<td><fmt:formatDate value="${list.updated_date}" type="both"/></td>
									<td>${list.remark}</td>
									<td>
										<div class="text-center">
											<a href="javascript:void(0);" title="修改" data-rel="tooltip">
												<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
											</a>
										</div>
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
<!-- /.page-content -->