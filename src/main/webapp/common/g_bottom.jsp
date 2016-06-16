<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
			<div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<span class="blue bolder">司集</span>
						运维管理平台 &copy; 2015-2016
						&nbsp; &nbsp;
						<span class="action-buttons">
							<!-- <a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a> -->
						</span>
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
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePath %>/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
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
		
		
		<script src="<%=basePath %>/assets/js/jquery.nestable.js"></script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				$('.easy-pie-chart.percentage').each(function(){
					var $box = $(this).closest('.infobox');
					var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
					var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
					var size = parseInt($(this).data('size')) || 50;
					$(this).easyPieChart({
						barColor: barColor,
						trackColor: trackColor,
						scaleColor: false,
						lineCap: 'butt',
						lineWidth: parseInt(size/10),
						animate: ace.vars['old_ie'] ? false : 1000,
						size: size
					});

					/*文本域输入字数限制提示*/
					$('textarea.limited').inputlimiter({
						remText: '%n character%s remaining...',
						limitText: 'max allowed : %n.'
					});
				})
			
				$('.sparkline').each(function(){
					var $box = $(this).closest('.infobox');
					var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
					$(this).sparkline('html',
									 {
										tagValuesAttribute:'data-values',
										type: 'bar',
										barColor: barColor ,
										chartRangeMin:$(this).data('min') || 0
									 });
				});
			
			
			  //flot chart resize plugin, somehow manipulates default browser resize event to optimize it!
			  //but sometimes it brings up errors with normal resize event handlers
			  /*$.resize.throttleWindow = false;*/

			  var placeholder = $('#piechart-placeholder').css({'width':'90%' , 'min-height':'150px'});
			  var data = [
				{ label: "social networks",  data: 38.7, color: "#68BC31"},
				{ label: "search engines",  data: 24.5, color: "#2091CF"},
				{ label: "ad campaigns",  data: 8.2, color: "#AF4E96"},
				{ label: "direct traffic",  data: 18.6, color: "#DA5430"},
				{ label: "other",  data: 10, color: "#FEE074"}
			  ]
			  /*function drawPieChart(placeholder, data, position) {
			 	  $.plot(placeholder, data, {
					series: {
						pie: {
							show: true,
							tilt:0.8,
							highlight: {
								opacity: 0.25
							},
							stroke: {
								color: '#fff',
								width: 2
							},
							startAngle: 2
						}
					},
					legend: {
						show: true,
						position: position || "ne", 
						labelBoxBorderColor: null,
						margin:[-30,15]
					}
					,
					grid: {
						hoverable: true,
						clickable: true
					}
				 })
			 }
			 drawPieChart(placeholder, data);*/
			
			 /**
			 we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
			 so that's not needed actually.
			 */
			 /*placeholder.data('chart', data);
			 placeholder.data('draw', drawPieChart);*/

				var d1 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d1.push([i, Math.sin(i)]);
				}
			
				var d2 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d2.push([i, Math.cos(i)]);
				}
			
				var d3 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.2) {
					d3.push([i, Math.tan(i)]);
				}
				
			
				var sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});
				/*$.plot("#sales-charts", [
					{ label: "Domains", data: d1 },
					{ label: "Hosting", data: d2 },
					{ label: "Services", data: d3 }
				], {
					hoverable: true,
					shadowSize: 0,
					series: {
						lines: { show: true },
						points: { show: true }
					},
					xaxis: {
						tickLength: 0
					},
					yaxis: {
						ticks: 10,
						min: -2,
						max: 2,
						tickDecimals: 3
					},
					grid: {
						backgroundColor: { colors: [ "#fff", "#fff" ] },
						borderWidth: 1,
						borderColor:'#555'
					}
				});*/
			
			
				$('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('.tab-content')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					//var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			
			
				$('.dialogs,.comments').ace_scroll({
					size: 300
			    });
				
				
				//Android's default browser somehow is confused when tapping on label which will lead to dragging the task
				//so disable dragging when clicking on label
				var agent = navigator.userAgent.toLowerCase();
				if(ace.vars['touch'] && ace.vars['android']) {
				  $('#tasks').on('touchstart', function(e){
					var li = $(e.target).closest('#tasks li');
					if(li.length == 0)return;
					var label = li.find('label.inline').get(0);
					if(label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation() ;
				  });
				}
			
				$('#tasks').sortable({
					opacity:0.8,
					revert:true,
					forceHelperSize:true,
					placeholder: 'draggable-placeholder',
					forcePlaceholderSize:true,
					tolerance:'pointer',
					stop: function( event, ui ) {
						//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
						$(ui.item).css('z-index', 'auto');
					}
					}
				);
				$('#tasks').disableSelection();
				$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
					if(this.checked) $(this).closest('li').addClass('selected');
					else $(this).closest('li').removeClass('selected');
				});
			
			
				//show the dropdowns on top or bottom depending on window height and menu position
				$('#task-tab .dropdown-hover').on('mouseenter', function(e) {
					var offset = $(this).offset();
			
					var $w = $(window)
					if (offset.top > $w.scrollTop() + $w.innerHeight() - 100) 
						$(this).addClass('dropup');
					else $(this).removeClass('dropup');
				});
			
			})
		</script>

		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		<link rel="stylesheet" href="<%=basePath %>/assets/css/ace.onpage-help.css" />
		<link rel="stylesheet" href="<%=basePath %>/docs/assets/js/themes/sunburst.css" />

		<script type="text/javascript"> ace.vars['base'] = '..'; </script>
		<script src="<%=basePath %>/assets/js/ace/elements.onpage-help.js"></script>
		<script src="<%=basePath %>/assets/js/ace/ace.onpage-help.js"></script>
		<script src="<%=basePath %>/docs/assets/js/rainbow.js"></script>
		<script src="<%=basePath %>/docs/assets/js/language/generic.js"></script>
		<script src="<%=basePath %>/docs/assets/js/language/html.js"></script>
		<script src="<%=basePath %>/docs/assets/js/language/css.js"></script>
		<script src="<%=basePath %>/docs/assets/js/language/javascript.js"></script>

	</body>
</html>