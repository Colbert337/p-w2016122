<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

	<script src="<%=basePath %>/dist/js/poms/liquid/liquid_new.js"></script>

			<div class="main-content">
				<div class="main-content-inner">

					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								新建液厂
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="gastationform">
								
								<jsp:include page="/common/page_param.jsp"></jsp:include>

									<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right"> 液厂名称： </label>

										<div class="col-sm-4">
											<input type="text" name="gas_factory_name" placeholder="输入液厂名称" class="form-control" maxlength="20" value="${gasource.gas_factory_name}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >工艺类型： </label>

										<div class="col-sm-4">
											<input type="text" name="technology_type" placeholder="输入工艺类型" class="form-control" value="${gasource.technology_type}" maxlength="20"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">配送方式： </label>

										<div class="col-sm-4">
											<input type="text" id="delivery_method" name="delivery_method" placeholder="输入配送方式" class="form-control" maxlength="20" value="${gasource.delivery_method}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">价格：</label>

										<div class="col-sm-4">
											<input type="text" name="market_price" placeholder="输入价格" class="form-control" maxlength="8" value="${gasource.market_price}"/>
										</div>
									</div>
	
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">注册地址： </label>
										<div class="col-sm-4">
											<div class="row form-group">
												<div class="col-sm-6">
													<select class="form-control" id="province_id" name="province_id" onchange="chinaChange(this,document.getElementById('city_id'));">
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
															<option value ="950">宁夏回族自治区</option>
															<option value ="990">新疆维吾尔自治区</option>
													</select>
												</div>
												<div class="col-sm-6">
													<select class="form-control" id="city_id" name="city_id">
													</select>
												</div>
											</div>
											<input type="text" id="detail" name="detail" class="form-control" placeholder="输入详细地址" value="${gasource.detail}"/>
											<input type="hidden" id="gas_factory_addr" name="gas_factory_addr" class="col-sm-12"  value="${gasource.address}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">备注： </label>

										<div class="col-sm-4">
											<textarea class="form-control" name="remark" rows="5" value="${gasource.remark}" maxlength="100"></textarea>
										</div>
									</div>
											
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn" type="reset">
												<i class="ace-icon fa fa-repeat bigger-110"></i>
												重置
											</button>
											&nbsp; &nbsp; &nbsp;
											
											<button class="btn btn-success" type="buttom" onclick="returnpage();">
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