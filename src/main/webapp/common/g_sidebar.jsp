<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<!-- #section:basics/sidebar.layout.shortcuts -->
						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>

						<!-- /section:basics/sidebar.layout.shortcuts -->
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div>

				<!-- /.sidebar-shortcuts -->
				<ul class="nav nav-list">
					<li class="active">
						<a href="<%=basePath%>/web/login/">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text"> 总览 </span>
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-desktop"></i>
							<span class="menu-text">
								系统管理
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li class="">
								<a href="javascript:void(0);" onclick="loadPage('#main', '<%=basePath %>/web/system/user/list')">
									<i class="menu-icon fa fa-caret-right"></i>
									系统账号
								</a>
								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="javascript:void(0);" onclick="loadPage('#main', '<%=basePath %>/web/permi/user/list/page')">
									<i class="menu-icon fa fa-caret-right"></i>
									用户管理
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>

					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-list"></i>
							<span class="menu-text"> 文档管理 </span>
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="<%=basePath%>/web/bossDoc/queryAllBossDoc">
									<i class="menu-icon fa fa-caret-right"></i>
									系统文档
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					<%-- <li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-list"></i>
							<span class="menu-text"> 保健管理 </span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>
						<ul class="submenu">
							<li class="">
								<a href="<%=basePath%>/web/health/showcategory">
									<i class="menu-icon fa fa-caret-right"></i>
									保健管理
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li> --%>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-pencil-square-o"></i>
							<span class="menu-text"> 模块管理 </span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<%-- <li class="">
								<a href="<%=basePath %>/web/bossMenu/queryMenuTree">
									<i class="menu-icon fa fa-caret-right"></i>
									系统模块
								</a>
								<b class="arrow"></b>
							</li> --%>

							<li class="">
								<a href="<%=basePath%>/web/bossMenu/goBossMenuList">
									<i class="menu-icon fa fa-caret-right"></i>
									系统菜单
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="<%=basePath%>/web/bossSystem/querySystemPower">
									<i class="menu-icon fa fa-caret-right"></i>
									园所权限
								</a>

								<b class="arrow"></b>
							</li>

						</ul>
					</li>

					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-list-alt"></i>
							<span class="menu-text"> 评价管理 </span>

							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="<%=basePath%>/web/evaluate/item/list?itemType=1">
									<i class="menu-icon fa fa-caret-right"></i>
									身高评价
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="<%=basePath%>/web/evaluate/item/list?itemType=2">
									<i class="menu-icon fa fa-caret-right"></i>
									体重评价
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="<%=basePath%>/web/evaluate/item/list?itemType=3">
									<i class="menu-icon fa fa-caret-right"></i>
									BMI评价
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="<%=basePath%>/web/evaluate/overall/list">
									<i class="menu-icon fa fa-caret-right"></i>
									综合评价
								</a>

								<b class="arrow"></b>
							</li>
						</ul>
					</li>


					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-list-alt"></i>
							<span class="menu-text"> 气站卡片管理 </span>

							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="javascript:void(0);" onclick="loadPage('#main', '<%=basePath %>/web/card/cardList')">
									<i class="menu-icon fa fa-caret-right"></i>
									卡片信息管理
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>


					<!-- <li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-tag"></i>
							<span class="menu-text"> 权限管理 </span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="#">
									<i class="menu-icon fa fa-caret-right"></i>
									用户管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="#">
									<i class="menu-icon fa fa-caret-right"></i>
									角色管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="#">
									<i class="menu-icon fa fa-caret-right"></i>
									资源管理
								</a>

								<b class="arrow"></b>
							</li>
						</ul>
					</li> -->

					<!-- <li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-file-o"></i>

							<span class="menu-text">
								系统设置

								#section:basics/sidebar.layout.badge
								<span class="badge badge-primary">5</span>

								/section:basics/sidebar.layout.badge
							</span>

							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="#">
									<i class="menu-icon fa fa-caret-right"></i>
									支付接口
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="#">
									<i class="menu-icon fa fa-caret-right"></i>
									短信接口
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="#">
									<i class="menu-icon fa fa-caret-right"></i>
									操作日志
								</a>

								<b class="arrow"></b>
							</li>
						</ul>
					</li> -->
				</ul><!-- /.nav-list -->

				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>

			<!-- /section:basics/sidebar -->