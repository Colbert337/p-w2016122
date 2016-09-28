<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

	<link href="<%=basePath %>/umeditor-dev/themes/default/_css/umeditor.css" type="text/css" rel="stylesheet">

	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/umeditor-dev/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/umeditor-dev/_examples/editor_api.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/umeditor-dev/lang/zh-cn/zh-cn.js"></script>
    
    <script src="<%=basePath %>/dist/js/page/page_new.js"></script> 

					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								新建页面
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="formpage">
								
								<jsp:include page="/common/page_param.jsp"></jsp:include>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">页面标题：</label>

										<div class="col-sm-4">
											<input type="text" name="pageTitle" value="${page.pageTitle }" placeholder="输入页面标题" class="form-control" maxlength="30"/>
										</div>
									</div>

									<!-- <div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">信息内容：</label>

										<div class="col-sm-4">
											<input type="text" name="pageBody" placeholder="输入页面内容" class="form-control" maxlength="30"/>
										</div>
									</div> -->
									
									<div class="form-group">
										<label for="email" class="col-sm-3 control-label no-padding-right">信息缩略：</label>

										<div class="col-sm-4">
											<input type="text" name="pageTicker"  value="${page.pageTicker }" placeholder="输入信息缩略" class="form-control" maxlength="30"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">显示标题：</label>

										<div class="col-sm-4">
											<select name="show_title">
												<option value="0">不显示</option>
												<option value="1">显示</option>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">页面编辑：</label>

										<div class="col-sm-4">
											<script type="text/plain" id="myEditor" style="width:1000px;height:240px;">
											</script>
											<input type="hidden" name="pageHtml"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">创建人：</label>

										<div class="col-sm-4">
											<c:choose>
												<c:when test="${sessionScope.currUser.user.realName != null}">
													<input type="text" name="pageCreator" class="form-control" readonly="readonly" value="${sessionScope.currUser.user.realName}"/>
												</c:when>
												<c:when test="${sessionScope.currUser.user.realName == null}">
													<input type="text" name="pageCreator" class="form-control" readonly="readonly" value="${sessionScope.currUser.user.userName}"/>
												</c:when>
											</c:choose>
										</div>
									</div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn" type="button" onclick="init();">
												<i class="ace-icon fa fa-repeat bigger-110"></i>
												重置
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn btn-success" type="button" onclick="returnpage();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn btn-yellow" type="button" onclick="preview();">
												<i class="ace-icon fa fa-eye bigger-110"></i>
												预览
											</button>
										</div>
									</div>

									<jsp:include page="/common/message.jsp"></jsp:include>
										
								</form>
								
								<form id="previewForm" name="previewForm" method="post" action="../web/page/preview" target="colors123" onsubmit="openSpecfiyWindown('colors123')">   
								  <input type="hidden" id="pageHtml" name="pageHtml"/>  
								</form> 						
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div>
					
					
<div id="innerModel" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" data-backdrop="static"  tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>
			<div class="modal-body">
		
			</div><!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-sm" i="close" onclick="hideInnerModel();">关闭</button>
			</div>
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
					