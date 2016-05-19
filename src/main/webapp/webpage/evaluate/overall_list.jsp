<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link href="<%=basePath %>/assets/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<script src="<%=basePath %>/assets/zTree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
	function addEvaluate(){
		$("#evaluateForm").submit();
	}
</script>
<div class="page-header">
	<h1>
		评价管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			综合评价设置
		</small>
	</h1>
</div>
<div class="row">
    <div class="col-xs-12">
    	 <div class="pull-right btn-bottom">
    	 	<button class="btn btn-xs btn-primary" onclick="addEvaluate()">保存评价</button>
    	 </div>
    	 <form id="evaluateForm" name="evaluateForm" action="<%=basePath %>/web/evaluate/overall/update" method="post">
	         <table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr style="text-align: center;">
						<th>BMI</th>
						<th>身高别年龄</th>
						<th>体重别年龄</th>
						<th>综合评价</th>
						<th>专家建议</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${overallEvaluateList}" var ="overallEvaluate">
						<tr>
							<td>
								<c:choose>
									<c:when test="${overallEvaluate.bmiLevel == 1}">
										高
									</c:when>
									<c:when test="${overallEvaluate.bmiLevel == 2}">
										中
									</c:when>
									<c:when test="${overallEvaluate.bmiLevel == 3}">
										低
									</c:when>
								</c:choose>
								<input type="hidden" id="overallEvaluationId" name="overallEvaluationId" value="${overallEvaluate.bossOverallEvaluationId }"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${overallEvaluate.heightLevel == 1}">
										高
									</c:when>
									<c:when test="${overallEvaluate.heightLevel == 2}">
										中
									</c:when>
									<c:when test="${overallEvaluate.heightLevel == 3}">
										低
									</c:when>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${overallEvaluate.weightLevel == 1}">
										高
									</c:when>
									<c:when test="${overallEvaluate.weightLevel == 2}">
										中
									</c:when>
									<c:when test="${overallEvaluate.weightLevel == 3}">
										低
									</c:when>
								</c:choose>
							</td>
							<td><textarea id="evaluate" name="evaluate" rows="" cols="50">${overallEvaluate.evaluate }</textarea> </td>
							<td><textarea id="suggest" name="suggest" rows="" cols="50">${overallEvaluate.suggest }</textarea> </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</div>

<!--删除菜单信息的弹层-开始-->
		<div id="deleteBossMenuDiv" class="all-hidden">
			<input id="deleteBossMenuId" type="hidden"/>
			<div style="margin-top: 50px;margin-bottom:10px;text-align: center;font-size: 25px;color: red">您确定要删除此菜单信息？ </div>
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-3"><button class="btn btn-primary" i="close" onclick="deleteByMenuId()">确   定</button></div>
				<div class="col-xs-6"><button class="btn" i="close" >取   消 </button></div>
			</div>
			<div class="clear"></div>
		</div>
	<!--弹层-结束-->