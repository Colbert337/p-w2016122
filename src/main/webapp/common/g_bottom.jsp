<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
			<div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<span class="blue bolder">司集</span>
						<c:if test="${sessionScope.currUser.user.userType == 1}">
							气站管理系统
						</c:if>
						<c:if test="${sessionScope.currUser.user.userType == 2}">
							运输公司管理系统
						</c:if>
						<c:if test="${sessionScope.currUser.user.userType == 4}">
							气站集团管理系统
						</c:if>
						<c:if test="${sessionScope.currUser.user.userType == 5}">
							司集能源运维管理平台
						</c:if>
						&copy; 2015-2016
					</div>
					<!-- /section:basics/footer -->
				</div>
			</div>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>

	<!--弹层-成功提示-->
	<div id="suc-hint" class="all-hidden">
		<!--弹层内容 -->
		<div class="b-dialog-hint">
			<div class="fl-l info-img"><img src="<%=basePath %>/assets/artDialog/image/dialog_success.png" /></div>
			<div class="fl-l dialog-font" id="suc-content">操作成功！</div>
		</div>
	</div>
	
	<!--弹层-失败提示-->
	<div id="fail-hint" class="all-hidden">
		<!--弹层内容 -->
		<div class="b-dialog-hint">
			<div class="fl-l info-img"><img src="<%=basePath %>/assets/artDialog/image/dialog_error.png" /></div>
			<div class="fl-l dialog-font" id="fail-content">操作失败！</div>
		</div>
	</div>
	
	<!--弹层-警告提示-->
	<div id="warn-hint" class="all-hidden">
		<!--弹层内容 -->
		<div class="b-dialog-hint">
			<div class="fl-l info-img"><img src="<%=basePath %>/assets/artDialog/image/dialog_warning.png" /></div>
			<div class="fl-l dialog-font" id="warn-content">警告！</div>
		</div>
	</div>

		<script type="text/javascript">
			//if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath %>/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
			var sjny = sjny || {};
			sjny.basePath = '<%=basePath %>';
		</script>

		<!--[if IE]>
		<script type="text/javascript">
		window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="<%=basePath %>/assets/js/jquery-1.9.1.min.js"></script>
		<!-- ace settings handler -->
		<script src="<%=basePath %>/assets/js/ace-extra.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
		<!--[if lte IE 8]>
		<script src="<%=basePath %>/assets/js/html5shiv.js"></script>
		<script src="<%=basePath %>/assets/js/respond.js"></script>
		<![endif]-->

		<script type="text/javascript"  src="<%=basePath %>/assets/artDialog/dist/dialog-plus-min.js"></script>
		<!-- JqueryValidationEngine表单验证  -->
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/assets/jQueryVE/css/validationEngine.jquery.css" />
		<script type="text/javascript" src="<%=basePath %>/assets/jQueryVE/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="<%=basePath %>/assets/jQueryVE/js/jquery.validationEngine.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/assets/js/bootstrapValidator.js"></script>

		<script src="<%=basePath %>/assets/js/bootstrap.js"></script>

		<!-- page specific plugin scripts -->
		<script src="<%=basePath %>/assets/js/jquery.bootstrap-duallistbox.js"></script>
		<script src="<%=basePath %>/assets/js/bootstrap-switch.min.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.raty.js"></script>
		<script src="<%=basePath %>/assets/js/bootstrap-multiselect.js"></script>
		<script src="<%=basePath %>/assets/js/select2.js"></script>
		<script src="<%=basePath %>/assets/js/typeahead.jquery.js"></script>

		<!--[if lte IE 8]>
	    <script src="<%=basePath %>/assets/js/excanvas.js"></script>
		<![endif]-->
		<script src="<%=basePath %>/assets/js/jquery-ui.custom.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.ui.touch-punch.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.easypiechart.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.sparkline.js"></script>

		<!-- datepicker -->
		<script src="<%=basePath%>/assets/js/date-time/moment.js"></script>
		<script src="<%=basePath%>/assets/js/date-time/daterangepicker.js"></script>
		<script src="<%=basePath%>/assets/js/date-time/bootstrap-datepicker.js"></script>
		<script src="<%=basePath%>/assets/js/date-time/bootstrap-datetimepicker.js"></script>

		<!-- ace scripts -->
		<script src="<%=basePath %>/assets/js/ace/elements.scroller.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.colorpicker.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.fileinput.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.typeahead.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.wysiwyg.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.spinner.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.treeview.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.wizard.js"></script>
		<script src="<%=basePath %>/assets/js/ace/elements.aside.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.ajax-content.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.touch-drag.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.sidebar.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.submenu-hover.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.widget-box.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.settings.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.settings-rtl.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.settings-skin.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.widget-on-reload.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.searchbox-autocomplete.js"></script>

		<!-- 表单提交及请求处理 -->
		<script src="<%=basePath %>/common/js/sysongy_commons.js"></script>
		<script src="<%=basePath %>/common/js/jquery.form.js"></script>
		<!-- datatable -->
		<script src="<%=basePath %>/assets/js/dataTables/jquery.dataTables.js"></script>
		<script src="<%=basePath %>/assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
		<script src="<%=basePath %>/assets/js/dataTables/extensions/buttons/dataTables.buttons.js"></script>
		<script src="<%=basePath %>/assets/js/dataTables/extensions/buttons/buttons.flash.js"></script>
		<script src="<%=basePath %>/assets/js/dataTables/extensions/buttons/buttons.html5.js"></script>
		<script src="<%=basePath %>/assets/js/dataTables/extensions/buttons/buttons.print.js"></script>
		<script src="<%=basePath %>/assets/js/dataTables/extensions/buttons/buttons.colVis.js"></script>
		<script src="<%=basePath %>/assets/js/dataTables/extensions/select/dataTables.select.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.nestable.js"></script>

		<script src="<%=basePath %>/common/js/fileinput.js"></script>
		<script src="<%=basePath %>/assets/js/bootbox.js"></script>
		<script src="<%=basePath %>/assets/js/jquery.colorbox.js"></script>
		<script src="<%=basePath %>/common/js/zh.js"></script>
	</body>
</html>