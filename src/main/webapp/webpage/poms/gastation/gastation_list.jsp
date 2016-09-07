<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String imagePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>

<script src="<%=basePath %>/dist/js/gastation/gastation_list.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formgastation">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							加注站管理
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>加注站编号:</label>
							<input type="text" name="sys_gas_station_id" placeholder="输入加注站编号"  maxlength="10" value="${gastation.sys_gas_station_id}"/>
						</div>
						
						<div class="item">
						    <label>加注站名称:</label>
							<input type="text" name="gas_station_name" placeholder="输入加注站名称"  maxlength="20" value="${gastation.gas_station_name}"/>
						</div>
						
						<div class="item">
							<label>加注站状态:</label>
							<select class="chosen-select " name="status" >
								<s:option flag="true" gcode="STATION_STATUS" form="gastation" field="status" />
							</select>
						</div>

						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>平台有效期:</label>
								<input type="text" class="" name="expiry_date_after" value="${gastation.expiry_date_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="expiry_date_before" value="${gastation.expiry_date_before}" readonly="readonly"/>
							</div>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/gastation/gastation_new.jsp');">
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
					
					<div class="table-header">加注站详细信息列表</div>

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
									<th onclick="orderBy(this,'salesmen_name');commitForm();" id="salesmen_name_order">销售人员</th>
									<th onclick="orderBy(this,'operations_name');commitForm();" id="operations_name_order">运营人员</th>
									<th onclick="orderBy(this,'admin_username');commitForm();" id="admin_username_order">管理员账号</th>
									<th onclick="orderBy(this,'indu_com_number');commitForm();" id="indu_com_number_order">工商注册号</th>
									<th onclick="orderBy(this,'status');commitForm();" id="status_order">平台状态</th>
									<th onclick="orderBy(this,'address');commitForm();" id="address_order">注册地址</th> 
									<th onclick="orderBy(this,'gas_server');commitForm();" id="status_order">提供服务</th>
									<th onclick="orderBy(this,'lng_price');commitForm();" id="status_order">商品信息</th>
									<th onclick="orderBy(this,'promotions');commitForm();" id="status_order">优惠活动</th>
									<th onclick="orderBy(this,'created_time');commitForm();" id="created_time_order" class="td-w2"><i id="created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>注册日期</th>
									<th onclick="orderBy(this,'expiry_date');commitForm();" id="expiry_date_order" class="td-w2"><i id="expiry_date" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>平台有效期</th>
									<th onclick="orderBy(this,'prepay_balance');commitForm();" id="prepay_balance_order">预付款额度</th>
									<th style="display: none">钱袋编号</th>
									<th class="text-center td-w2">更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<!--<tr id="listobj">-->
								<tr id="${list.sys_gas_station_id}">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.sys_gas_station_id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.sys_gas_station_id}</td>
								 	<td>${list.gas_station_name}</td> 
									<td>${list.salesmen_name}</td>
									<td>${list.operations_name}</td>
									<td>${list.admin_username}</td>
									<td>${list.indu_com_number}</td>
									<td><s:Code2Name mcode="${list.status}" gcode="STATION_STATUS"></s:Code2Name></td>
									<td>${list.address}</td> 
									<td><div class="td-inner-warp">${list.gas_server}</div></td> 
									<td><div class="td-inner-warp">${list.lng_price}</div></td> 
									<td><div class="td-inner-warp">${list.promotions}</div></td> 
									<%-- <td>${list.batch_no}</td>  --%>
									<td><fmt:formatDate value="${list.created_time}" type="both"/></td>
									<td><fmt:formatDate value="${list.expiry_date}" type="both"/></td>
									<td>${list.prepay_balance}</td>
									<td style="display: none">${list.sys_user_account_id}</td>
									<td class="text-center">
										<a class="option-btn-m" href="javascript:void(0);" title="修改" data-rel="tooltip">
											<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
										</a>
										<a class="option-btn-m" href="javascript:void(0);" title="查看图片" data-rel="tooltip">
											<i class="ace-icon fa fa-search-plus bigger-130" onclick="showInnerModel('${list.indu_com_certif}','${list.tax_certif}','${list.lng_certif}','${list.dcp_certif}',$('#${list.sys_gas_station_id}'));"></i>
										</a>
										<a href="javascript:void(0);" title="重置密码" data-rel="tooltip">
											<i class="ace-icon fa fa-key bigger-130" onclick="resetPassword(this);"></i>
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


<div id="innerModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<%--<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>--%>
			<div class="modal-body" >
				<div class="shenhe-items-hd">加注站信息</div>
				<table class="table">
					<tbody>
						<tr>
							<th width="15%">加注站编号</th>
							<td><div id="sys_gas_station_id" name="show"></div></td>
							<th width="15%">加注站名称</th>
							<td><div id="gas_station_name" name="show"> </div></td>
						</tr>
						<tr>
							<th>销售人员</th>
							<td><div id="salesmen_name" name="show"> </div></td>
							<th>运营人员</th>
							<td><div id="operations_name" name="show"> </div></td>
						</tr>
						<tr>
							<th>管理员账号</th>
							<td><div id="admin_username" name="show"> </div></td>
							<th>工商注册号</th>
							<td><div id="indu_com_number" name="show"> </div></td>
						</tr>
						<tr>
							<th>状态</th>
							<td><div id="status" name="show"> </div></td>
							<th>注册地址</th>
							<td><div id="address" name="show"> </div></td>
						</tr>
						<tr>
							<th>提供服务</th>
							<td><div id="gas_server" name="show"> </div></td>
							<th>商品信息</th>
							<td><div id="lng_price" name="show"> </div></td>
						</tr>
						<tr>
							<th>优惠活动</th>
							<td><div id="promotions" name="show"> </div></td>
							<th>注册日期</th>
							<td><div id="created_time" name="show"> </div></td>
						
						</tr>
						<tr>
							<th>平台有效期</th>
							<td><div id="expiry_date" name="show"> </div></td>
							<th>预付款额度</th>
							<td colspan="3"><div id="prepay_balance" name="show"> </div></td>
						</tr>
					</tbody>
				</table>

				<div class="shenhe-items-hd">证件照片</div>
				<div class="row">
					<div class="col-sm-3">
						<a class="gastation-log-colorbox" href="" data-rel="colorbox">
							<img class="img-responsive" src="" alt="" id="innerimg1">
							<div class="title">工商注册证</div>
						</a>
					</div>
					<div class="col-sm-3">
						<a class="gastation-log-colorbox" href="" data-rel="colorbox">
							<img class="img-responsive" src="" alt="" id="innerimg2">
							<div class="title">税务注册证</div>
						</a>
					</div>
					<div class="col-sm-3">
						<a class="gastation-log-colorbox" href="" data-rel="colorbox">
							<img class="img-responsive" src="" alt="" id="innerimg3">
							<div class="title">LNG储装证</div>
						</a>
					</div>
					<div class="col-sm-3">
						<a class="gastation-log-colorbox" href="" data-rel="colorbox">
							<img class="img-responsive" src="" alt="" id="innerimg4">
							<div class="title">危化品证</div>
						</a>
					</div>
				</div>

			</div>

			<div class="modal-footer">
				<button class="btn btn-primary btn-sm"  data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>