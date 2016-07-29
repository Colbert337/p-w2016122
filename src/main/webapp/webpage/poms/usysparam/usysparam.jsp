<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/usysparam/usysparam.js"></script>

<div class="">

	<form id="formusysparam">

	<jsp:include page="/common/page_param.jsp"></jsp:include>

	<div class="row">
		<div class="col-xs-12">

					<div class="page-header">
						<h1>
							系统参数配置
						</h1>
					</div>
					<div class="search-types">
						<div class="item">
						    <label>GCODE:</label>
							<input type="text" name="gcode" placeholder="输入GCODE" value="${usysparam.gcode}"/>
						</div>
						
						<div class="item">
						    <label>MCODE:</label>
							<input type="text" name="mcode" placeholder="输入MCODE" maxlength="20" value="${usysparam.mcode}"/>
						</div>
						
						<div class="item">
							<label>MNAME:</label>
							<input type="text" name="mname" placeholder="输入MCODE" maxlength="20" value="${usysparam.mname}"/>
						</div>

						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/usysparam/usysparam_new.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								新建
							</button>
							<button class="btn btn-sm btn-primary" type="reset" onclick="commitForm();">
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
			
					<div class="table-header">系统参数配置信息列表</div>

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
									<th onclick="orderBy(this,'gcode');commitForm();" id="gcode_order">GCODE</th>
									<th onclick="orderBy(this,'mcode');commitForm();" id="mcode_order">MCODE</th>
									<th onclick="orderBy(this,'mname');commitForm();" id="mname_order">MNAME</th>
									<th onclick="orderBy(this,'data');commitForm();" id="data_order">DATA</th>
									<th onclick="orderBy(this,'scode');commitForm();" id="admin_username_order">SCODE</th>
									<th class="text-center td-w3">更多操作</th>
								</tr>
							</thead>

							<tbody>
								
							<c:forEach items="${list}" var="list" varStatus="s">
								<tr id="listobj">
									<td class="center">
										<label class="pos-rel"> 
											<input type="checkbox" class="ace" id="pks" value="${list.gcode}"/> 
											<span class="lbl"></span>
										</label>
									</td>
									<td>${list.gcode}</td>
								 	<td>${list.mcode}</td> 
									<td>${list.mname}</td>
									<td>${list.data}</td>
									<td>${list.scode}</td>
									<td class="text-center">
										<a class="" href="javascript:void(0);" title="修改" data-rel="tooltip">
											<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
										</a>
										<a href="javascript:void(0);" class="" title="删除" data-rel="tooltip">
											<i class="ace-icon fa fa-trash-o bigger-130" onclick="delUsysparam(this);"></i> 
										</a>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

	<%-- 		<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<nav>
						<ul id="ulhandle" class="pagination pull-right no-margin">
							<li id="previous">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#formtransportion');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next">
								<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#formtransportion');">
									<span aria-hidden="true">下一页</span>
								</a>
							</li>  
						</ul>
					</nav>
				</div>
			</div>
 --%>
			<jsp:include page="/common/message.jsp"></jsp:include>


			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</form>
</div>