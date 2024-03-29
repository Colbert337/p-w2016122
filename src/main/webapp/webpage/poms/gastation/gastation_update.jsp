<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String imagePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">主页</a>
							</li>

							<li>
								<a href="#">加注站管理</a>
							</li>
							<li class="active">加注站信息管理</li>
						</ul><!-- /.breadcrumb -->

						<!-- #section:basics/content.searchbox -->
						<div class="nav-search" id="nav-search">
							<form class="form-search" >
							
								<input id="retCode" type="hidden" value=" ${ret.retCode}" />
								<input id="retMsg" type="hidden" value=" ${ret.retMsg}" />
								
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->

						<!-- /section:basics/content.searchbox -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								修改加注站信息
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="gastationform">
									<!-- #section:elements.form -->
									<input type="hidden" id="sys_gas_station_id"  name="sys_gas_station_id" value="${station.sys_gas_station_id}" />
							
							<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right"> 所属公司： </label>

										<div class="col-sm-4">
											<input type="text" id="company"  name="company" placeholder="输入所属公司" class="form-control" maxlength="20" value="${station.company}"/>
											
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="gas_station_name"> 加注站名称： </label>

										<div class="col-sm-4">
											<input type="text" id="gas_station_name"  name="gas_station_name" value="${station.gas_station_name}" placeholder="输入加注站名称" class="form-control" maxlength="20"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >加注站类别： </label>

										<div class="col-sm-4">
											<select class="form-control" id="station_level" name="station_level">
												<s:option flag="true" gcode="STATION_LEVEL" form="station" field="station_level"/>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">注册邮箱： </label>

										<div class="col-sm-4">
											<input type="text" id="email" name="email" placeholder="输入注册邮箱" class="form-control"  value="${station.email}" maxlength="50"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="station_manager"> 站长姓名： </label>

										<div class="col-sm-4">
											<input type="text" id="station_manager"  name="station_manager" placeholder="输入站长姓名" class="form-control" maxlength="20" value="${station.station_manager}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="contact_phone">联系电话：</label>

										<div class="col-sm-4">
											<input type="text" id="contact_phone"  name="contact_phone" placeholder="输入联系电话" class="form-control" maxlength="18" value="${station.contact_phone}"/>
										</div>
									</div>

									<div class="form-group">
										<label for="station_telephone" class="col-sm-3 control-label no-padding-right"> 气站座机电话： </label>

										<div class="col-sm-4">
											<input type="text" id="station_telephone"  name="station_telephone" placeholder="输入气站座机电话" class="form-control" maxlength="18" value="${station.station_telephone}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">管理员账号（建议为11位手机号码）：</label>

										<div class="col-sm-4">
											<input class="form-control" name="admin_username" type="text" placeholder="输入管理员账号" maxlength="20" value="${station.admin_username}" readonly="readonly"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="expiry_date">平台有效期：</label>
										<div class="col-sm-4 datepicker-noicon">
										<!-- #section:plugins/date-time.datepicker -->
												<div class="input-group">
														<input class="form-control date-picker" name="expiry_date_frompage"  readonly="readonly" id="expiry_date" type="text" value="${station.expiry_date_frompage}" data-date-format="yyyy-mm-dd" />
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
												</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="salesmen_name"> 销售人员： </label>
										<div class="col-sm-4">
												<input class="form-control" type="text" id="salesmen_name" name="salesmen_name" maxlength="20" value="${station.salesmen_name}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 运管人员： </label>
										<div class="col-sm-4">
												<select class="form-control" id="operations_id" name="operations_id" onchange="setOperationName(this);">
												</select>
												<input type="hidden" id="operations_name" name="operations_name"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right"> 注册地址： </label>
										<div class="col-sm-4">
											<div class="row form-group">
												<div class="col-sm-6">
													<input class="form-control" type="text" id="showprovince" readonly="readonly"/>
													<select class="hidden" name="province_id" id="province" onchange="chinaChange(this,document.getElementById('city'));">
															<option value ="请选择市区">请选择省份</option>
															<option value ="100">北京市</option>
															<option value ="220">天津市</option>
															<option value ="210">上海市</option>
															<option value ="230">重庆市</option>
															<option value ="310">河北省</option>
															<option value ="350">山西省</option>
															<option value ="240">辽宁省</option>
															<option value ="430">吉林省</option>
															<option value ="450">黑龙江省</option>
															<option value ="250">江苏省</option>
															<option value ="570">浙江省</option>
															<option value ="550">安徽省</option>
															<option value ="590">福建省</option>
															<option value ="790">江西省</option>
															<option value ="530">山东省</option>
															<option value ="370">河南省</option>
															<option value ="270">湖北省</option>
															<option value ="730">湖南省</option>
															<option value ="200">广东省</option>
															<option value ="891">海南省</option>
															<option value ="810">四川省</option>
															<option value ="850">贵州省</option>
															<option value ="870">云南省</option>
															<option value ="290">陕西省</option>
															<option value ="930">甘肃省</option>
															<option value ="970">青海省</option>
															<option value ="852">台湾省</option>
															<option value ="770">广西壮族自治区</option>
															<option value ="470">内蒙古自治区</option>
															<option value ="890">西藏自治区</option>
															<option value ="950">宁夏回族自治区 </option>
															<option value ="990">新疆维吾尔自治区</option>
															<option value ="851">香港特别行政区</option>
															<option value ="853">澳门特别行政区</option>
													</select>
												</div>
												<div class="col-sm-6">
													<select class="form-control" id="city" name="city_id">
													</select>
												</div>
											</div>
											<input type="text" id="detail" name="detail" class="form-control" maxlength="30"/>
											<input type="hidden" id="address" name="address" />
										</div>
									</div>
									
									<div class="row">
										<label class="col-sm-3 control-label no-padding-right">地址坐标：</label>
										<div class="col-sm-4">
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group nospace">
														<input type="text" id="longitude" name="longitude" class="form-control" maxlength="20" value="${station.longitude}"/>
													</div>
												</div>
												<div class="col-sm-6">
													<div class="form-group nospace">
														<input type="text" id="latitude" name="latitude" class="form-control" maxlength="20" value="${station.latitude}"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="indu_com_number">工商注册号：</label>

										<div class="col-sm-4">
											<input type="text" id="indu_com_number" name="indu_com_number" class="form-control" value="${station.indu_com_number}" maxlength="18"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">工商注册证书：</label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">工商注册证书照片上传</h5>
												</div>
													
												<ul  id="com_img" class="ace-thumbnails file-space clearfix">
													<li>
														<a href="<%=imagePath %>${station.indu_com_certif}" data-rel="colorbox">
															<img width="150" height="150" alt="150x150"src="<%=imagePath %>${station.indu_com_certif}" />
															<div class="text">
																<div class="inner">点击放大</div>
															</div>
														</a>
													</li>
												</ul>

												<div class="widget-body">
													<div class="widget-main">
														<label for="indu_com_certif_select">
															<input type="file" name="image" class="projectfile" id="indu_com_certif_select" />
														</label>
														<input type="hidden" id="indu_com_certif" name="indu_com_certif"/> 
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#indu_com_certif_select','#indu_com_certif','#com_img');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="tax_number">税务注册号：</label>

										<div class="col-sm-4">
											<input type="text" id="tax_number" name="tax_number" class="form-control" maxlength="18" value="${station.tax_number}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">税务注册证书：</label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">税务注册证书照片上传</h5>
												</div>
													
													<ul  id="com1_img"  class="ace-thumbnails file-space clearfix">
														<li>
															<a href="<%=imagePath %>${station.tax_certif}" data-rel="colorbox">
																<img width="150" height="150" alt="150x150"  id="com1_img" src="<%=imagePath %>${station.tax_certif}" />
																<div class="text">
																	<div class="inner">点击放大</div>
																</div>
															</a>
														</li>
													</ul>

												<div class="widget-body">
													<div class="widget-main">
														<label for="tax_certif_select">
															<input type="file" name="image" class="projectfile"  id="tax_certif_select" />
														</label>
														<input type="hidden" id="tax_certif" name="tax_certif"/> 
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#tax_certif_select','#tax_certif','#com1_img');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">LNG储装证书： </label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">LNG储装证书照片上传</h5>
												</div>
													
													<ul id="com2_img"  class="ace-thumbnails file-space clearfix">
														<li>
															<a href="<%=imagePath %>${station.lng_certif}" data-rel="colorbox">
																<img width="150" height="150" alt="150x150" id="com2_img" src="<%=imagePath %>${station.lng_certif}" />
																<div class="text">
																	<div class="inner">点击放大</div>
																</div>
															</a>
														</li>
													</ul>

												<div class="widget-body">
													<div class="widget-main">
														<label for="lng_certif_select">
															<input type="file" name="image" class="projectfile"  id="lng_certif_select" />
														</label>
														<input type="hidden" id="lng_certif" name="lng_certif"/> 
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#lng_certif_select','#lng_certif','#com2_img');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">危化品证书： </label>
										<div class="col-sm-4">
											<div class="widget-box">
												<div class="widget-header">
													<h5 class="widget-title">危化品证书照片上传</h5>
												</div>
													
													<ul id="com3_img" class="ace-thumbnails file-space clearfix">
														<li>
															<a href="<%=imagePath %>${station.dcp_certif}" data-rel="colorbox">
																<img width="150" height="150" alt="150x150"  src="<%=imagePath %>${station.dcp_certif}"/>
																<div class="text">
																	<div class="inner">点击放大</div>
																</div>
															</a>
														</li>
													</ul>

												<div class="widget-body">
													<div class="widget-main">
														<label for="dcp_certif_select">
															<input type="file" name="image" class="projectfile" id="dcp_certif_select"/>
														</label>
														<input type="hidden" id="dcp_certif" name="dcp_certif"/> 
														<button class="btn btn-sm btn-primary btn-file-space" type="button" onclick="save_photo(this,'#dcp_certif_select','#dcp_certif','#com3_img');">
															<i class="ace-icon fa fa-check bigger-110"></i>
															图片上传
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">加注站状态：</label>

										<div class="col-sm-4">
											<select class="chosen-select " name="status" >
												<s:option gcode="STATION_STATUS" form="station" field="status" />
											</select>										
										</div>
									</div>
									<div class="row">
										<label class="col-sm-3 control-label no-padding-right">提供服务： </label>
										<div class="col-sm-4">
											<div class="row">
												<div class="col-sm-12">
													<div class="form-group nospace">
														<textarea name="gas_server" id="gas_server" style="resize: none;"
									maxlength="500" placeholder="提供服务"
									class="col-xs-10 col-sm-12 limited form-control">${station.gas_server }</textarea>
													</div>
												</div>
												
											</div>
										</div>
									</div>
									
									<div class="row">
										<label class="col-sm-3 control-label no-padding-right">商品信息： </label>
										<div class="col-sm-4">
											<div class="row">
												<div class="col-sm-12">
												<textarea name="lng_price" id="lng_price" style="resize: none;"
									maxlength="500" placeholder="商品价格格式如：LNG：3.2元/KG，LCG：3.2元/KG"
									class="col-xs-10 col-sm-12 limited form-control">${station.lng_price }</textarea>
											</div>
										</div>
										</div><div class="form-group"><span
								class="red_star">商品价格格式如：LNG：3.2元/KG，LCG：3.2元/KG</span>
							</label>

						 
									</div>
									</div>
									<br/>
									<div class="row">
										<label class="col-sm-3 control-label no-padding-right">优惠活动： </label>
										<div class="col-sm-4">
											<div class="row">
												<div class="col-sm-12">
													<div class="form-group nospace">
														<textarea name="promotions" id="promotions" style="resize: none;"
									maxlength="500" placeholder="优惠活动"
									class="col-xs-10 col-sm-12 limited form-control">${station.promotions }</textarea>
													</div>
												</div>
												
											</div>
										</div>
									</div>

									<%--<div class="form-group">
										<label for="fixed_discount" class="col-sm-3 control-label no-padding-right"> 固定折扣： </label>

										<div class="col-sm-4">
											<input type="text"  id="fixed_discount" onKeyUp="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="javascript:if(parseFloat(value) >= 1){value='';}"  name="fixed_discount" placeholder="固定折扣" class="form-control" maxlength="4" value="${station.fixed_discount}"/>
										</div>
										<div class="form-group">	<span class="red_star">固定折扣格式如：0.75 &nbsp(0.75代表75折)</span></div>
									</div>--%>
								<%--<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="price_effective_time"> 价格生效时间： </label>
										<div class="col-sm-4 datepicker-noicon">
											<div class="input-group">
												<input class="form-control date-picker" name="price_effective_time" id="price_effective_time" type="text" readonly="readonly" data-date-format="yyyy-mm-dd" value="${station.price_effective_time}"/>
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
											</div>
										</div>
									</div>--%>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="price_effective_time"> 价格生效时间： </label>
										<%--	<div class="col-sm-4 datepicker-noicon">
												<div class="input-group">
													<input class="form-control date-picker" name="price_effective_time" id="price_effective_time" type="text" readonly="readonly" data-date-format="yyyy-mm-dd" value=""/>
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
												</div>
											</div>--%>
										<div class="col-sm-4">
											<select class="form-control" id="price_effective_time" name="price_effective_time" >
												<s:option flag="true" gcode="price_effective_time" form="station" field="price_effective_time"/>
											</select>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-success" type="button" onclick="returnpage();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>

									<jsp:include page="/common/message.jsp"></jsp:include>
										
								</form>						
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
	</div>

		<!-- inline scripts related to this page -->
	<script type="text/javascript">
	
	var projectfileoptions = {
	        showUpload : false,
	        showRemove : false,
	        language : 'zh',
	        allowedPreviewTypes : [ 'image' ],
	        allowedFileExtensions : [ 'jpg', 'png', 'gif', 'jepg' ],
	        maxFileSize : 1000,
	    }
	// 文件上传框
	$('input[class=projectfile]').each(function() {
	    var imageurl = $(this).attr("value");
	
	    if (imageurl) {
	        var op = $.extend({
	            initialPreview : [ // 预览图片的设置
	            "<img src='" + imageurl + "' class='file-preview-image'>", ]
	        }, projectfileoptions);
	
	        $(this).fileinput(op);
	    } else {
	        $(this).fileinput(projectfileoptions);
	    }
	});
	
		var china=new Object();
		china['100']=new Array('北京市区','北京市辖区');
		china['210']=new Array('上海市区','上海市辖区');
		china['220']=new Array('天津市区','天津市辖区');
		china['230']=new Array('重庆市区','重庆市辖区');
		china['310']=new Array('石家庄', '唐山市', '邯郸市', '秦皇市岛', '保市定', '张家市口', '承德市', '廊坊市', '沧州市', '衡水市', '邢台市');
		china['350']=new Array('太原市','大同市','阳泉市','长治市','晋城市','朔州市','晋中市','运城市','忻州市','临汾市','吕梁市');
		china['240']=new Array('沈阳市','大连市','鞍山市','抚顺市','本溪市','丹东市','锦州市','营口市','阜新市','辽阳市','盘锦市','铁岭市','朝阳市','葫芦岛市');
		china['430']=new Array('长春市','吉林市','四平市','辽源市','通化市','白山市','松原市','白城市','延边州','长白山管委会');
		china['450']=new Array('哈尔滨市','齐齐哈尔市','大庆市','佳木斯市','牡丹江市','七台河市','双鸭山市','黑河市','鸡西市','伊春市','绥化市','鹤岗市','加格达奇市');
		china['250']=new Array('南京市','苏州市','无锡市','常州市','南通市','扬州市','镇江市','泰州市','盐城市','连云港市','宿迁市','淮安市','徐州市');
		china['570']=new Array('杭州市','宁波市','温州市','嘉兴市','湖州市','绍兴市','金华市','衢州市','舟山市','台州市','丽水市');
		china['550']=new Array('合肥市','芜湖市','蚌埠市','淮南市','马鞍山市','淮北市','铜陵市','安庆市','黄山市','滁州市','阜阳市','宿州市','巢湖市','六安市','亳州市','池州市','宣城市');
		china['590']=new Array('福州市','厦门市','莆田市','三明市','泉州市','漳州市','南平市','龙岩市','宁德市');
		china['790']=new Array('南昌市','景德镇市','萍乡市','九江市','新余市','鹰潭市','赣州市','吉安市','宜春市','抚州市','上饶市');
		china['530']=new Array('济南市','青岛市','淄博市','枣庄市','东营市','烟台市','潍坊市','济宁市','泰安市','威海市','日照市','莱芜市','临沂市','德州市','聊城市','滨州市','菏泽市');
		china['370']=new Array('郑州市','开封市','洛阳市','平顶山市','安阳市','鹤壁市','新乡市','焦作市','濮阳市','许昌市','漯河市','三门峡市','南阳市','商丘市','信阳市','周口市','驻马店市');
		china['270']=new Array('武汉市','黄石市','十堰市','荆州市','宜昌市','襄樊市','鄂州市','荆门市','孝感市','黄冈市','咸宁市','随州市');
		china['730']=new Array('长沙市','株洲市','湘潭市','衡阳市','邵阳市','岳阳市','常德市','张家界市','益阳市','郴州市','永州市','怀化市','娄底市');
		china['200']=new Array('广州市','深圳市','珠海市','汕头市','韶关市','佛山市','江门市','湛江市','茂名市','肇庆市','惠州市','梅州市','汕尾市','河源市','阳江市','清远市','东莞市','中山市','潮州市','揭阳市','云浮市');
		china['890']=new Array('文昌市','琼海市','万宁市','五指山市','东方市','儋州市');
		china['810']=new Array('成都市','自贡市','攀枝花市','泸州市','德阳市','绵阳市','广元市','遂宁市','内江市','乐山市','南充市','眉山市','宜宾市','广安市','达州市','雅安市','巴中市','资阳市');
		china['850']=new Array('贵阳市','六盘水市','遵义市','安顺市');
		china['870']=new Array('昆明市','曲靖市','玉溪市','保山市','昭通市','丽江市','普洱市','临沧市');
		china['290']=new Array('西安市','铜川市','宝鸡市','咸阳市','渭南市','延安市','汉中市','榆林市','安康市','商洛市');
		china['930']=new Array('兰州市','金昌市','白银市','天水市','嘉峪关市','武威市','张掖市','平凉市','酒泉市','庆阳市','定西市','陇南市');
		china['970']=new Array('西宁市','海东市','玉树市','格尔木市','德令哈市');
		china['886']=new Array('台北市','高雄市','基隆市','台中市','台南市','新竹市','嘉义市');
		china['770']=new Array('南宁市','柳州市','桂林市','梧州市','北海市','防城港市','钦州市','贵港市','玉林市','百色市','贺州市','河池市','来宾市','崇左市');
		china['470']=new Array('呼和浩特市','包头市','乌海市','赤峰市','通辽市','鄂尔多斯市','呼伦贝尔市','巴彦淖尔市','乌兰察布市'); 
		china['890']=new Array('拉萨市');
		china['891']=new Array('海口市','三亚市','三沙市','儋州市');
		china['950']=new Array('银川市','石嘴山市','吴忠市','固原市','中卫市');
		china['990']=new Array('乌鲁木齐市','克拉玛依市');
		china['852']=new Array('台北市','高雄市','基隆市','台中市','台南市','新竹市','嘉义市');
		
		//初始化销售（运管）负责人下拉框
		$.ajax({
			   type: "POST",
			   url:'<%=basePath%>/web/permi/user/list/userType?userType=5',
	           dataType:'text',
	           async:false,
	           success:function(data){
	           		if(data != ""){
			        	   var s = JSON.parse(data);
			        	   for(var i=0;i<s.length;i++){
			        		   $("#operations_id").append("<option value='"+s[i].userName+"''>"+s[i].userName+" - "+s[i].realName+"</option>");
			        	   }
	           		}
	            }
		});
		
		//下拉框默认选中当前对象的值
		var province_id = '${station.province_id}';
		var city_id =  '${station.city_id}';
		var detail = '${station.address}';
		var operations_id = '${station.operations_id}';
		
		if(province_id!=null && province_id!=""){
			$("#province").find("option[value="+province_id+"]").attr("selected",true);
			$("#province").trigger("change");
			$("#city").find("option[value="+city_id+"]").attr("selected",true);
			$("#detail").val(detail.split(" ")[2]);
			
			$("#showprovince").val($("#province").find("option:selected").text());
		}

		if(operations_id!=null){
			$("#operations_id").find("option[value="+operations_id+"]").attr("selected",true);
		}
		
		function chinaChange(province, city) {
			var pv, cv;
			var i, ii;
			
			pv = province.value;
			cv = city.value;
			city.length = 1;
			
			if (pv == '0') {
				return;
			}
			
			if (typeof (china[pv]) == 'undefined'){
				$("#city").find("option").remove();
				return;
			} 
	
			for (i = 0; i < china[pv].length; i++) { 
				ii = i;
			
				city.options[ii] = new Option();
				city.options[ii].text = china[pv][i];
				//city.options[ii].value = china[pv][i];
				city.options[ii].value = parseInt(pv+i)
				}
		};
	
	//datepicker plugin
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		language: 'cn'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
		var contral = "0";
		
			//bootstrap验证控件		
		    $('#gastationform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	company: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '所属公司不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '加注站名称不能超过20个汉字'
		                    },regexp: {
								regexp: /^[^\s+/?%#&=:-]+$/,
								message: '不能输入特殊字符：+，/，?，%，#，&，=，:，-，空格'
							}
		                }
		            },
		        	gas_station_name: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '加注站名称不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '加注站名称不能超过20个汉字'
		                    },regexp: {
								regexp: /^[^\s+/?%#&=:-]+$/,
								message: '不能输入特殊字符：+，/，?，%，#，&，=，:，-，空格'
							}
		                }
		            },
		            station_level: {
		                validators: {
		                    notEmpty: {
		                        message: '加注站级别不能为空'
		                    }
		                }
		            },
		            email: {
		                validators: {
		                    notEmpty: {
		                        message: '加注站注册邮箱不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 50,
		                        message: '加注站注册邮箱不能超过50个字符'
		                    }
		                }
		            },
		            expiry_date_frompage: {
		                message: 'The cardno is not valid',
		                validators: { 
		                    notEmpty: {
		                        message: '平台有效期不能为空'
		                    },
		                    callback: {
		                    	message: '平台有效期必须大于等于当前日期',
		                    	callback: function (value, validator, $field) {
	                                 if(compareDate(new Date().toLocaleDateString(),value)){
	                                	 return false;
	                                 }
	                                 return true;
	                            }
		                    }
		                },
		                trigger: 'change'
		            },
		            station_manager: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '加注站站长不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '加注站站长不能超过20个汉字'
		                    }
		                }
		            },
		            contact_phone: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '联系电话不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]*[1-9][0-9]*$',
		                        message: '联系电话必须是数字'
		                    }
		                }
		            },
		            salesmen_name: {
		                validators: {
		                    notEmpty: {
		                        message: '销售负责人不能为空'
		                    }
		                }
		            },
		            operations_id: {
		                validators: {
		                    notEmpty: {
		                        message: '运管负责人不能为空'
		                    }
		                }
		            },
		            province: {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址省不能为空'
		                    }
		                }
		            },
		            city : {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址市不能为空'
		                    }
		                }
		            },
		            address: {
		                validators: {
		                    notEmpty: {
		                        message: '注册详细地址不能为空'
		                    }
		                }
		            },
		            longitude: {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址经度'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]+([.]{1}[0-9]+){0,1}$',
		                        message: '注册地址经度必须是数字'
		                    }
		                }
		            },
		            latitude: {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址经度'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]+([.]{1}[0-9]+){0,1}$',
		                        message: '注册地址纬度必须是数字'
		                    }
		                }
		            },
					indu_com_number: {
		                message: 'The cardno is not valid',
						validators: {
							stringLength: {
								max: 18,
								message: '工商注册号不能超过18位'
							}
						}
		                }
		            },
					tax_number: {
		                message: 'The cardno is not valid',
						validators: {
							stringLength: {
								max: 18,
								message: '税务注册号不能超过18位'
							}
						}
					},
				price_effective_time :{
					validators: {
						notEmpty: {
							message: '请选择价格生效时间'
						}
					}
				}
		    });
			    
		function save(){
			$("#address").val($("#province").find("option:selected").text()+" "+$("#city").find("option:selected").text()+" "+$("#detail").val());


			if($('#price_effective_time  option:selected').text()=='--请选择--'){
					alert('请选择价格生效时间');
				 return false;
			 }

			/*手动验证表单，当是普通按钮时。*/
			$('#gastationform').data('bootstrapValidator').validate();
			if(!$('#gastationform').data('bootstrapValidator').isValid()){
				return ;
			}

			//匹配这些中文标点符号 。 ？ ！ ， 、 ； ： “ ” ‘ ' （ ） 《 》 〈 〉 【 】 『 』 「 」 ﹃ ﹄ 〔 〕 … — ～ ﹏ ￥
			var reg = /[\u3002|\uff1f|\uff01|\uff0c|\u3001|\uff1b|\uff1a|\u201c|\u201d|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c|\u300d|\ufe43|\ufe44|\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5]/;
			if(reg.test($('#gas_station_name').val()) || reg.test($('#company').val())){
				alert('所属公司和加注站名称中不能包含中文标点符号');
				return false;
			}
			var options ={   
		            url:'../web/gastation/saveGastation',   
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
						$("#modal-table").modal("show");
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
						
			$("#gastationform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '<%=basePath%>/web/gastation/gastationList');
		}
		
		function setSalesmenName(obj){
			$("#salesmen_name").val($(obj).val());
		}
		
		function setOperationName(obj){
			$("#operations_name").val($(obj).val());
		}
		
function save_photo(fileobj,obj,obj1,obj2){
			
			$(fileobj).parents("div").find("input[name=uploadfile]").each(function(){
				$(this).attr("name","");
			});
			
			$(fileobj).parent("div").find("input:first").attr("name","uploadfile");
			
			if($(obj).val()==null || $(obj).val()==""){
				bootbox.alert("请先上传文件");
				return;
			}
			
			var multipartOptions ={   
					url:'../crmInterface/crmBaseService/web/upload?stationid='+$("#sys_gas_station_id").val(),
		            type:'post',                    
		            dataType:'text',
		            enctype:"multipart/form-data",
		            success:function(data){
		            	var s = JSON.parse(data);
		            	if(s.success == true){
		            		bootbox.alert("上传成功");
		            		$(obj2).hide();
		            		$(obj1).val(s.obj);
		            	}
		            	
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#gastationform").ajaxSubmit(multipartOptions);
		}
		
		jQuery(function($) {
			var $overflow = '';
			var colorbox_params = {
				rel: 'colorbox',
				reposition:true,
				scalePhotos:true,
				scrolling:false,
				previous:'<i class="ace-icon fa fa-arrow-left"></i>',
				next:'<i class="ace-icon fa fa-arrow-right"></i>',
				close:'&times;',
				current:'{current} of {total}',
				maxWidth:'100%',
				maxHeight:'100%',
				onOpen:function(){
					$overflow = document.body.style.overflow;
					document.body.style.overflow = 'hidden';
				},
				onClosed:function(){
					document.body.style.overflow = $overflow;
				},
				onComplete:function(){
					$.colorbox.resize();
				}
			};
		
			$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
			$("#cboxLoadingGraphic").html("<i class='ace-icon fa fa-spinner orange fa-spin'></i>");//let's add a custom loading icon
			
			
			$(document).one('ajaxloadstart.page', function(e) {
				$('#colorbox, #cboxOverlay').remove();
		   });
		})
		</script>
