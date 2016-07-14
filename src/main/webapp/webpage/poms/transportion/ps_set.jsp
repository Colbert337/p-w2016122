<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<jsp:include page="../../../common/g_head.jsp"/>
	<jsp:include page="../../../common/g_navbar.jsp"/>

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
		<%--<jsp:include page="../../../common/g_sidebar.jsp"/>--%>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>
					</div>
					<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
				<jsp:include page="../../../common/g_set.jsp"/>
				<div id="main">

					<%--内容开始--%>


						<div class="page-header">
							<h1>
								设置支付密码
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									运输公司账户：${userName}
								</small>
							</h1>
						</div><!-- /.page-header -->
						<%--单行表单 开始--%>
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="passwordForm" action="<%=basePath%>/web/transportion/save/payCode" method="post">
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right" for="pay_code"> <span class="red_star">*</span>支付密码： </label>
										<div class="col-sm-4">
											<input type="password" id="pay_code" name="pay_code" placeholder="支付密码" class="col-xs-10 col-sm-12" />
											<input type="hidden" id="stationId" name="sys_transportion_id" value="${stationId}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-4 control-label no-padding-right" for="re_password"> <span class="red_star">*</span>确认密码： </label>
										<div class="col-sm-4">
											<input type="password" id="re_password" name="rePassword" placeholder="确认密码" class="col-xs-10 col-sm-12" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-8">
											<button class="btn btn-info" type="submit" >
												确定
											</button>
											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												重置
											</button>
										</div>
									</div>
								</form>
							</div><!-- /.col -->
						</div><!-- /.row -->
						<%--单行表单结束--%>
					<%--内容结束--%>

				</div>
				</div><!-- /.page-content -->
			</div><!-- /main-content-inner -->
		</div><!-- /.main-content -->
		<jsp:include page="../../../common/g_bottom.jsp"/>
		<script src="<%=basePath %>/dist/js/transportion/ps_set.js"></script>
	</div><!-- /.main-content -->
