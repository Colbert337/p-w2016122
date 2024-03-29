<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	String imagePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<script src="<%=basePath%>/dist/js/mobile/banner_list.js"></script>
<div class="">
	<form id="listForm">
		<jsp:include page="/common/page_param.jsp"></jsp:include>
		<div class="page-header">
			<h1>首页编辑管理</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<div class="row">
					<%-- <div class="col-sm-2">
						<input type="hidden" id="imgType" name="imgType" value="${mbBanner.imgType}" />
						<div class="dd dd-draghandle">
							<ol class="dd-list" id="cashbackol">
								<li class='dd-item dd2-item' data-id='14'
									onclick='choose(this);' value='0'>
									<div class='dd-handle dd2-handle'>
										<i class='normal-icon ace-icon fa fa-clock-o pink bigger-130'></i>
										<i class='drag-icon ace-icon fa fa-arrows bigger-125'></i>
									</div>
									<div class='dd2-content'>头条推广</div>
								</li>
								<li class='dd-item dd2-item' data-id='14'
									onclick='choose(this);' value='1'>
									<div class='dd-handle dd2-handle'>
										<i class='normal-icon ace-icon fa fa-clock-o pink bigger-130'></i>
										<i class='drag-icon ace-icon fa fa-arrows bigger-125'></i>
									</div>
									<div class='dd2-content'>列表推广</div>
								</li>
							</ol>
						</div>
					</div> --%>
					<input type="hidden" id="imgType" name="imgType" value="${mbBanner.imgType}" />
					<div class="col-sm-15">
					<!-- 	<div class="page-header">
							<h1>图片列表</h1>
						</div> -->
					<div class="search-types">
						<div class="item">
						    <label>标题：</label>
							<input type="text" name="title" placeholder="输入标题"  maxlength="32" value="${mbBanner.title}"/>
						</div>
						<div class="item">
						    <label>创建人：</label>
							<input type="text" name="operator" placeholder="输入创建人"  maxlength="32" value="${mbBanner.operator}"/>
						</div>
						
						<div class="item">
							<div class="input-daterange top" id="j-input-daterange-top">
								<label>修改时间:</label>
								<input type="text" class="" name="createdDate_str" value="${mbBanner.createdDate_str}" readonly="readonly"/>
								<span class="">
									<i class="fa fa-exchange"></i>
								</span>
								<input type="text" class="" name="updatedDate_str" value="${mbBanner.updatedDate_str}" readonly="readonly"/>
							</div>
						</div>

						<div class="item">
						 
							<button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								查询
							</button>
							<button class="btn btn-sm" type="button" onclick="init();">
								<i class="ace-icon fa fa-flask align-top bigger-125"></i>
								重置
							</button>
						</div>
					
							<div class="item">
								<button class="btn btn-sm btn-primary" type="button"
									onclick="bannerAdd('')">
									<i class="ace-icon fa fa-flask align-top bigger-125"></i> 添加
								</button>
							</div>
					</div>

						<div class="clearfix">
							<div class="pull-right tableTools-container"></div>
						</div>

						<div class="table-header">图片列表</div>

						<!-- div.dataTables_borderWrap -->
						<div class="sjny-table-responsive">
							<table id="dynamic-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th onclick="orderBy(this,'sort');commitForm();"
											id="sort_order">序号</th>
										<th onclick="orderBy(this,'title');commitForm();"
											id="title_order">标题</th>
										<!-- 	<th onclick="orderBy(this,'img_path');commitForm();"
											id="threshold_max_value_order">缩略图</th> -->
										<th onclick="orderBy(this,'target_url');commitForm();"
											id="target_url_order">链接地址</th>
										<th onclick="orderBy(this,'content');commitForm();"
											id="content_order">正文</th>
										<th id="level">备注</th>
										<th onclick="orderBy(this,'city_id');commitForm();"
											id="city_id_order">城市</th>
										<th onclick="orderBy(this,'created_date');commitForm();"
											id="created_date_order" class="td-w2"><i
											id="created_date"
											class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
										<th onclick="orderBy(this,'updated_date');commitForm();" 	id="updated_date_order"
											 class="td-w2"><i
										
											class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>修改时间时间</th>
										<th onclick="orderBy(this,'operator');commitForm();"
											id="operator_orber">创建人</th>
										<th class="text-center td-w3">操作</th>
									</tr>
								</thead>

								<tbody>

									<c:forEach items="${pageInfo.list}" var="list" varStatus="s">
										<tr id="${list.mbBannerId }">
											<td>${list.sort}</td>
											<td>${list.title}</td>
											<%-- 		<td><img width="150" height="150" alt="150x150"
												src="<%=imagePath %>${list.imgPath}" /></td> --%>
											<td><div class="td-inner-warp">${list.targetUrl}</div></td>
											<td><div class="td-inner-warp">${list.content}</div></td>
											<td><div class="td-inner-warp">${list.remark}</div></td>
											<td><div class="td-inner-warp">${list.city_name}</div></td>
											<td><fmt:formatDate value="${list.createdDate}"
													type="both" /></td>
											<td style="display:none">${list.operator}</td><!-- 隐藏字段 用于详情显示 -->
											<td><fmt:formatDate value="${list.updatedDate}"
													type="both" /></td>
											<td>${list.operator}</td>
											<td class="text-center"><a class="option-btn-m"
												href="javascript:void(0);" title="修改" data-rel="tooltip">
													<i class="ace-icon fa fa-pencil bigger-130"
													onclick="bannerAdd('${list.mbBannerId}');"></i>
											</a> <a class="option-btn-m" href="javascript:void(0);"
												title="详细信息" data-rel="tooltip"> <i
													class="ace-icon fa fa-search-plus bigger-130"
													onclick="showInnerModel('${list.imgPath}','${list.imgSmPath}',$('#${list.mbBannerId }'));"></i>
											</a> <a class="" href="javascript:void(0);"
												onclick="deleteBanner('${list.mbBannerId}');" title="删除"
												data-rel="tooltip"> <i
													class="ace-icon fa fa-trash-o bigger-130"></i>
											</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页
									&nbsp;&nbsp;转到第 <input type="text"
								name="convertPageNum" style="height: 25px; width: 45px"
								maxlength="4" /> 页
							<button type="button" class="btn btn-white btn-sm btn-primary"
								onclick="commitForm();">跳转</button>
									</div>
								</div>
								<div class="col-sm-6">
									<nav>
										<ul id="ulhandle" class="pagination pull-right no-margin">
											<li id="previous">
												<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#listForm');">
													<span aria-hidden="true">上一页</span>
												</a>
											</li>
											<li id="next">
												<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage('#listForm');">
													<span aria-hidden="true">下一页</span>
												</a>
											</li>
										</ul>
									</nav>
								</div>
							</div>

					</div>
				</div>
				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
		<jsp:include page="/common/message.jsp"></jsp:include>
	</form>

</div>
<!-- /.main-content -->
<div id="editModel" class="modal fade" role="dialog"
	aria-labelledby="gridSystemModalLabel" data-backdrop="static"
	tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="editBanner">添加内容</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal" id="editForm">
								<!-- #section:elements.form -->
								<%--<h5 class="header smaller lighter blue">基本信息</h5>--%>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="title"><span class="red_star">*</span> 标题： </label>
									<div class="col-sm-8">
										<input type="text" name="title" id="title" data-onFlag=""
											placeholder="标题" maxlength="10" class="col-xs-10 col-sm-12" />
										<input type="hidden" name="mbBannerId" id="mb_banner_id" /> <input
											type="hidden" id="stationId" value="${stationId}" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="remark"> 正文： </label>
									<div class="col-sm-8">
										<textarea name="content" id="content" style="resize: none;"
											maxlength="50" placeholder="正文"
											class="col-xs-10 col-sm-12 limited form-control"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"><span
										class="red_star">*</span> 图片： </label>
									<div class="col-sm-8">
										<div class="widget-box">
											<div class="widget-header">
												<h4 class="widget-title">图片上传</h4>
											</div>
											<div class="widget-body">
												<div class="widget-main">
													<img id="show_img" width="150" height="150" alt="150x150"
														src="" /> <input type="file" name="image"
														class="projectfile" id="indu_com_certif_select" /> <input
														type="hidden" id="img_path" name="imgPath" />
													<button class="btn btn-sm btn-primary btn-file-space"
														type="button"
														onclick="savePhoto(this,'#indu_com_certif_select','#img_path','#show_img');">
														<i class="ace-icon fa fa-check bigger-110"></i> 图片上传
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"><span
										class="red_star">*</span>缩略图： </label>
									<div class="col-sm-8">
										<div class="widget-box">
											<div class="widget-header">
												<h4 class="widget-title">图片上传</h4>
											</div>
											<div class="widget-body">
												<div class="widget-main">
													<img id="show_sm_img" width="150" height="150"
														alt="150x150" src="" /> <input type="file" name="image2"
														class="projectfile" id="indu_com_sm_select" /> <input
														type="hidden" id="img_sm_path" name="imgSmPath" />
													<button class="btn btn-sm btn-primary btn-file-space"
														type="button"
														onclick="savePhoto(this,'#indu_com_sm_select','#img_sm_path','#show_sm_img');">
														<i class="ace-icon fa fa-check bigger-110"></i> 图片上传
													</button>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="target_url"><span class="red_star">*</span> 链接地址：
									</label>
									<div class="col-sm-8">
										<input type="text" id="target_url" name="targetUrl"
											placeholder="链接地址" class="col-xs-10 col-sm-12" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="target_url"><span class="red_star">*</span>顺序 </label>
									<div class="col-sm-8">
										<input type="text" id="sort" placeholder="顺序" maxlength="11"
											name="sort" class="col-xs-10 col-sm-12" />
									</div>
								</div>


								<div class="form-group">
									<%-- <label class="col-sm-3 control-label no-padding-right"
										for="version">版本号： </label>
									<div class="col-sm-3">
										<div class="row">
											<div class="col-sm-3">
												<div class="btn-group j-android-versions">
													<span class="btn btn-primary btn-white dropdown-toggle" >选择安卓版本号<i class="ace-icon fa fa-angle-down icon-on-right"></i>
													</span>
													<ul class="dropdown-menu">
														<c:forEach items="${datas}" var="one" >
															<li>
																<div class="checkbox">
																	<label>
																		<input name="form-field-checkbox" type="checkbox" id="c${one }" values="${one }"  checked="checkbox" class="ace">
																		<span class="lbl"> ${one }</span>
																	</label>
																</div>
															</li>
														</c:forEach>
														 
													</ul>
												</div>
											</div>
										
										</div>
									</div> --%>

									<label class="col-sm-3 control-label no-padding-right"
										for="version">城市列表： </label>
									<div class="col-sm-3">
										<div class="row">
											<div class="col-sm-3">
												<div class="btn-group j-android-versions">
													<span class="btn btn-primary btn-white dropdown-toggle">城市列表<i
														class="ace-icon fa fa-angle-down icon-on-right"></i>
													</span>
													<ul class="dropdown-menu">
														<li>
															<div class="checkbox">
																<label> <input name="" type="checkbox"
																	id="allche" onchange="checkedchange(this)" class="ace">
																	<span class="lbl"> 全选</span>
																</label>
															</div>
														</li>
														<c:forEach items="${city}" var="one">
															<li>
																<div class="checkbox">
																	<label> <input name="form-field-checkbox"
																		type="checkbox" value2="${one.cityId }"
																		id="c${one.cityId }" values="${one.cityName }"
																		onclick="checkedclick( )" class="ace checked" /> <span
																		class="lbl"> ${one.cityName }</span>
																	</label>
																</div>
															</li>
														</c:forEach>

													</ul>
												</div>
											</div>

										</div>
									</div>
									<%-- <label class="col-sm-3 control-label no-padding-right"
										for="version">城市： </label>
									<div class="col-sm-8">
										<select id="city" class="combobox col-sm-8">
											<option></option>
											<c:forEach items="${city}" var="one">
												<option value="${one.cityId }">${one.cityName }</option>
											</c:forEach>
										</select>
									</div> --%>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right"
										for="remark"> 备注： </label>
									<div class="col-sm-8">
										<textarea name="remark" id="remark" style="resize: none;"
											maxlength="50" placeholder="备注"
											class="col-xs-10 col-sm-12 limited form-control"></textarea>
									</div>
								</div>
								<input type="hidden" name="imgType" value="${mbBanner.imgType}" />
							</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
					<%--两行表单 结束--%>
				</div>
			</div>
			<!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" onclick="saveBanner()">确
					定</button>
				<button class="btn btn-sm" i="close"
					onclick="closeDialog('editModel')">取 消</button>
			</div>
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>
<div id="innerModel" class="modal fade" role="dialog"
	aria-labelledby="gridSystemModalLabel" data-backdrop="static"
	tabindex="-1">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="gridSystemModalLabel"></h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<%--两行表单 开始--%>
					<div class="shenhe-items">
						<div class="shenhe-items-hd">详细信息</div>

						<div class="shenhe-items-bd">
							<div class="row">
								<div class="col-xs-6">
									<label class="control-label no-padding-right">序号:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>
								<div class="col-xs-6">
									<label class="control-label no-padding-right">标题:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>

								<div class="col-xs-6">
									<label class="control-label no-padding-right">链接地址:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>
								<div class="col-xs-6">
									<label class="control-label no-padding-right">正文:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>
								<div class="col-xs-6">
									<label class="control-label no-padding-right">备注:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>
								<div class="col-xs-6">
									<label class="control-label no-padding-right">城市列表:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>
								<div class="col-xs-6">
									<label class="control-label no-padding-right">创建时间:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>
								<div class="col-xs-6">
									<label class="control-label no-padding-right">创建人:</label> <label
										class="control-label no-padding-right" name="show"></label>
								</div>
							</div>
						</div>
					</div>
					<div class="shenhe-items">
						<div class="shenhe-items-hd">图片</div>
						<div class="shenhe-items-bd">
							<div class="row">
								<div class="col-xs-5">
									<label class="control-label no-padding-right"></label>
									<ul class="ace-thumbnails clearfix">
										<li><a href="" data-rel="colorbox">图片<img
												class="img-responsive" src="" alt="" id="innerimg1">
												<div class="text">
													<div class="inner">点击放大</div>
												</div>
										</a></li>
									</ul>
								</div>
								<div class="col-xs-5">
									<label class="control-label no-padding-right"></label>
									<ul class="ace-thumbnails clearfix">
										<li><a href="" data-rel="colorbox"> 缩略图<img
												class="img-responsive" src="" alt="" id="innerimg2">
												<div class="text">
													<div class="inner">点击放大</div>
												</div>
										</a></li>
									</ul>
								</div>

							</div>
						</div>
					</div>
					<%--两行表单 结束--%>
				</div>
			</div>
			<!-- /.modal-content -->
			<div class="modal-footer">
				<button class="btn btn-primary btn-sm" data-dismiss="modal">关闭</button>
			</div>
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>
<script>
	var imgType = $("[name=imgType]").eq(0).val();

	$("li[value=" + imgType + "]").children("div:last").addClass("btn-info")
</script>
