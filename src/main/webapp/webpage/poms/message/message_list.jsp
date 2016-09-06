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

<script src="<%=basePath %>/dist/js/message/message_list.js"></script>

<div class="">
	<!-- /.page-header -->
	<form id="formessage">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							APP消息管理
						</h1>
					</div>

					<div class="search-types">
						<div class="item">
						    <label>消息标题:</label>
							<input type="text" name="messageTitle" placeholder="输入消息标题"  maxlength="10" value="${message.messageTitle}"/>
						</div>

						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>消息创建时间:</label>
								<input type="text" class="" name="message_created_time_after" value="${message.message_created_time_after}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="message_created_time_before" value="${message.message_created_time_before}" readonly="readonly"/>
							</div>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/message/message_new.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								广播信息
							</button>
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/message/messageNEW.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								单发/群发
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
					
					<div class="table-header">APP信息发送列表</div>

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
									<th onclick="orderBy(this,'message_title');commitForm();" id="message_title_order">信息标题</th>
									<th onclick="orderBy(this,'message_body');commitForm();" id="message_body_order">信息内容</th>
									<th onclick="orderBy(this,'message_ticker');commitForm();" id="message_ticker_order">信息缩略</th>
									<th onclick="orderBy(this,'message_group');commitForm();" id="message_group_order">发送信息组</th>
									<th onclick="orderBy(this,'operator');commitForm();" id="operator_order">信息创建者</th>
									<th onclick="orderBy(this,'message_send_time');commitForm();" id="message_send_time_order" class="td-w2"><i id="message_send_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>信息发送时间</th>
									<th onclick="orderBy(this,'message_created_time');commitForm();" id="message_created_time_order" class="td-w2"><i id="message_created_time" class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>信息创建时间</th>
									<th class="text-center td-w2">更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
								<!--<tr id="listobj">-->
								<tr id="${list.id}">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.id}"/> 
											<span class="lbl"></span>
										</label>
									</td>

									<td>${list.messageTitle}</td>
								 	<td><div class="td-inner-warp">${list.messageBody}</div></td>
									<td><div class="td-inner-warp">${list.messageTicker}</div></td>
									<td><s:Code2Name mcode="${list.messageGroup}" gcode="MSGGROUP"></s:Code2Name></td>
									<td>${list.operator}</td>
									<td><fmt:formatDate value="${list.messageSendTime}" type="both"/></td>
									<td><fmt:formatDate value="${list.messageCreatedTime}" type="both"/></td>
									<td class="text-center">
										<a class="logic-del" href="javascript:void(0);" title="删除" data-rel="tooltip">
											<i class="ace-icon fa fa-trash-o bigger-130" onclick="del(this);"></i>
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
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formessage');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formessage');">
									<span aria-hidden="true">下一页</span>
								</a>
							</li>  
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
							<th>注册日期</th>
							<td><div id="created_time" name="show"> </div></td>
							<th>平台有效期</th>
							<td><div id="expiry_date" name="show"> </div></td>
						</tr>
						<tr>
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