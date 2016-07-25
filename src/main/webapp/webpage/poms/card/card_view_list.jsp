<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/card/card_view_list.js"></script>
<script type="text/javascript">
    
	 // 删除用户
	function deleteUser(id){
		bootbox.setLocale("zh_CN");
		bootbox.confirm("确认要删除用户吗？", function (result) {
			if (result) {
				var deleteOptions ={
					url:'<%=basePath%>/web/chargeCard/delete',
					data:{id:id},
					type:'post',
					dataType:'text',
					success:function(data){
						$("#main").html(data);
						$('[data-rel="tooltip"]').tooltip();
					}
				}
				$("#listForm").ajaxSubmit(deleteOptions);
		}
	 })
   }		
	/*function edit(){
		$("#myModal").modal('show');
	}*/
</script>
<div class="page-header">
	<h1>
		充值卡管理
	</h1>
</div><!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<form class="form-horizontal"  id="listForm">
			<jsp:include page="/common/page_param.jsp"></jsp:include>
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<%--顶部条件搜索及按钮--%>
					<div class="search-types">
						<div class="item">
						      <label>用户卡号:</label>
							  <input type="text"  name="ownid" placeholder="请输入卡号"  maxlength="9" value="${sysongyChargeCard.ownid}"/>
						</div>
						<div class="item">
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								重置
							</button>
							<div class="item"></div>
							<button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/card/charge_new.jsp');">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								新建
							</button>
						</div>
					</div>
					<div class="sjny-table-responsive">
					<table id="simple-table" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
						    <th onclick="orderBy(this,'id');commitForm();" id="id_order">充值卡号</th>
							<th onclick="orderBy(this,'ownid');commitForm();" id="ownid_order">用户卡号</th>
							<th onclick="orderBy(this,'password');commitForm();" id="password_order">用户卡密码</th>
							<th onclick="orderBy(this,'useTime');commitForm();" id="useTime_order">充值时间</th>
							<th onclick="orderBy(this,'cash');commitForm();" id="cash_order">充值金额</th>
							<th class="text-center td-w2">操作</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${list}" var="sysongyChargeCard">
							<tr>
							    <td>${sysongyChargeCard.id}</td>
								<td>${sysongyChargeCard.ownid}</td>
								<td>${sysongyChargeCard.password}</td> 
								<td class="hidden-480"><fmt:formatDate value="${sysongyChargeCard.useTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>${sysongyChargeCard.cash}</td>
								<td class="text-center">
								    <!-- <a class=""  title="修改" data-rel="tooltip" onclick="edit();">
										<span class="ace-icon fa fa-pencil bigger-130"></span>
									</a>
									 -->
									<a class="" href="javascript:void(0);" title="编辑" data-rel="tooltip">
											<i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
									</a>
								    <a class="" href="javascript:deleteUser('${sysongyChargeCard.id}');" title="删除" data-rel="tooltip">
										<span class="ace-icon fa fa-trash-o bigger-130"></span>
									</a>												
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
				</div><!-- /.col-xs-12 -->
			</div><!-- /.row -->
		</form>
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->
<jsp:include page="/common/message.jsp"></jsp:include>