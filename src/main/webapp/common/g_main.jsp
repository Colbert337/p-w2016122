<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<jsp:include page="/common/g_head.jsp"/>
	<jsp:include page="/common/g_navbar.jsp"/>

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>
		<jsp:include page="../common/g_sidebar.jsp"/>

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
				<jsp:include page="../common/g_set.jsp"/>
				<jsp:include page="../${current_module}.jsp"></jsp:include>
				
				</div><!-- /.page-content -->
			</div><!-- /main-content-inner -->
		</div><!-- /.main-content -->
		<jsp:include page="../common/g_bottom.jsp"/>
	</div><!-- /.main-content -->

